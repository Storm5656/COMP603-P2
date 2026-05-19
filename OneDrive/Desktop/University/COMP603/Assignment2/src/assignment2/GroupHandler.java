/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment2;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ren
 */
public class GroupHandler {
    // create group
    public void createGroup(String n, String d){
        try{
            Connection conn = DatabaseManager.getConnection();
            String sql = "INSERT INTO USER_GROUPS (NAME, DESCRIPTION) "
                    + "VALUES (?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, n);
            ps.setString(2, d);
            
            ps.executeUpdate();
            
        } catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    // edit group details
    
    public String[] getAllGroups(){
        List<String> list = new ArrayList<String>();
        String sql = "SELECT NAME FROM USER_GROUPS";
        Connection conn = DatabaseManager.getConnection();
        
        try (Statement s = conn.createStatement(); 
            ResultSet rs = s.executeQuery(sql);){
            while (rs.next()) {
            list.add(rs.getString("NAME"));
            }
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        
        return list.toArray(new String[0]);
    }
    
    public Group getGroup(String n){
        String sql = "SELECT * FROM USER_GROUPS WHERE NAME = ?";
        Connection conn = DatabaseManager.getConnection();
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
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return g;
    }
    
    public String[] getAllCharInGroup(Group g){
        if (g == null){
            return null;
        }
        List<String> charNames = new ArrayList<String>();
        
        String sql = "SELECT c.NAME FROM CHARACTERS c "
                + "JOIN CHARACTER_GROUPS cg "
                + "ON c.CHAR_ID = cg.CHARACTER_ID "
                + "WHERE cg.GROUP_ID = ?";
        Connection conn = DatabaseManager.getConnection();
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, g.getId());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                charNames.add(rs.getString(1));
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return charNames.toArray(new String[0]);
    }
    
    public void editGroup(Group g){
        String sql = "UPDATE USER_GROUPS "
                + "SET NAME = ?, DESCRIPTION = ? "
                + "WHERE GROUP_ID = ?";
        Connection conn = DatabaseManager.getConnection();
        
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, g.getName());
            ps.setString(2, g.getDescription());
            ps.setInt(3, g.getId());
            
            ps.executeUpdate();
        } catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    public String[] getTags(Group g) {
        List<String> tags = new ArrayList<String>();
        Connection conn = DatabaseManager.getConnection();
        String sql = "SELECT t.NAME FROM TAGS t "
                + "JOIN GROUP_TAGS gt ON t.TAG_ID = gt.TAG_ID "
                + "WHERE gt.GROUP_ID = ?";
        
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, g.getId());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                tags.add(rs.getString("NAME"));
            }
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return tags.toArray(new String[0]);
    }
    
}
