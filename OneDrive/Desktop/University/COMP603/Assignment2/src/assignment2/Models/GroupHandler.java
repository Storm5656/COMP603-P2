/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment2.Models;
import assignment2.Models.Entities.Group;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ren
 */
public class GroupHandler extends AbstractHandler<Group>{
    
    @Override
    public void create(Group obj){
        try{
            String sql = "INSERT INTO USER_GROUPS (NAME, DESCRIPTION) "
                    + "VALUES (?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, obj.getName());
            ps.setString(2, obj.getDescription());
            ps.executeUpdate();
            
        } catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
    @Override
    public void edit(Group obj){
        String sql = "UPDATE USER_GROUPS "
                + "SET NAME = ?, DESCRIPTION = ? "
                + "WHERE GROUP_ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, obj.getName());
            ps.setString(2, obj.getDescription());
            ps.setInt(3, obj.getId());
            
            ps.executeUpdate();
        } catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
    @Override
    public void delete(Group obj){
        if (obj == null){
            return;
        }
        try {
            // Start transaction
            conn.setAutoCommit(false);

            // Delete character-group links
            String sql1 = "DELETE FROM CHARACTER_GROUPS "
                        + "WHERE GROUP_ID = ?";

            try (PreparedStatement ps = conn.prepareStatement(sql1)) {
                ps.setInt(1, obj.getId());
                ps.executeUpdate();
            }

            // Delete group tags
            String sql2 = "DELETE FROM GROUP_TAGS "
                        + "WHERE GROUP_ID = ?";

            try (PreparedStatement ps = conn.prepareStatement(sql2)) {
                ps.setInt(1, obj.getId());
                ps.executeUpdate();
            }

            // Delete the group itself
            String sql3 = "DELETE FROM USER_GROUPS "
                        + "WHERE GROUP_ID = ?";

            try (PreparedStatement ps = conn.prepareStatement(sql3)) {
                ps.setInt(1, obj.getId());
                ps.executeUpdate();
            }

            // Save all changes
            conn.commit();

        } catch (SQLException e) {
            try {
                // Undo all changes if something failed
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            e.printStackTrace();
        }
    }
    
    @Override
    public Group get(String n){
        String sql = "SELECT * FROM USER_GROUPS WHERE NAME = ?";
        Group g = null;
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, n);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                int id = rs.getInt("GROUP_ID");
                String name = rs.getString("NAME");
                String desc = rs.getString("DESCRIPTION");
                g = new Group(id, name, desc);
            }
            
            rs.close();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return g;
    }
    
    @Override
    public Group get(int i){
        String sql = "SELECT * FROM USER_GROUPS WHERE GROUP_ID = ?";
        Group g = null;
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, i);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                int id = rs.getInt("GROUP_ID");
                String name = rs.getString("NAME");
                String desc = rs.getString("DESCRIPTION");
                g = new Group(id, name, desc);
            }
            
            rs.close();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return g;
    }
    
    public String[] getAll(){
        List<String> list = new ArrayList<>();
        String sql = "SELECT NAME FROM USER_GROUPS";
        
        try (Statement s = conn.createStatement(); 
            ResultSet rs = s.executeQuery(sql);){
            while (rs.next()) {
            list.add(rs.getString("NAME"));
            }
            
            rs.close();
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        
        return list.toArray(String[]::new);
    }
    
    /**
     * Gets every character in a specific group
     * @param g Group to get characters from
     * @return Array of character names in the group
     */
    public String[] getAllCharInGroup(Group g){
        if (g == null){
            return null;
        }
        List<String> charNames = new ArrayList<>();
        
        String sql = "SELECT c.NAME FROM CHARACTERS c "
                + "JOIN CHARACTER_GROUPS cg "
                + "ON c.CHAR_ID = cg.CHARACTER_ID "
                + "WHERE cg.GROUP_ID = ?";
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, g.getId());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                charNames.add(rs.getString(1));
            }
            
            rs.close();            
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return charNames.toArray(String[]::new);
    }

    /**
     * Gets all of the tags attached to a group
     * @param g Group to get tags of
     * @return Array of names of the tags
     */
    public String[] getTags(Group g) {
        List<String> tags = new ArrayList<>();
        String sql = "SELECT t.NAME FROM TAGS t "
                + "JOIN GROUP_TAGS gt ON t.TAG_ID = gt.TAG_ID "
                + "WHERE gt.GROUP_ID = ?";
        
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, g.getId());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                tags.add(rs.getString("NAME"));
            }
            
            rs.close();
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return tags.toArray(String[]::new);
    }
    
}
