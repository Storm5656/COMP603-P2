/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment2.Models;

import assignment2.Models.Entities.CharacterModel;
import assignment2.Models.Entities.Group;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ren
 */
public class CharacterHandler extends AbstractHandler<CharacterModel>{
    @Override
    public void create(CharacterModel obj) {
        String sql = "INSERT INTO CHARACTERS (NAME, PRONOUNS, DOB, AGE, SPECIES, OCCUPATION, DESCRIPTION)"
                + "VALUES (?,?,?,?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql);){
            ps.setString(1, obj.getName());
            ps.setString(2, obj.getPronouns());
            ps.setString(3, obj.getDob());
            ps.setInt(4, obj.getAge());
            ps.setString(5, obj.getSpecies());
            ps.setString(6, obj.getOccupation());
            ps.setString(7, obj.getDescription());
            ps.executeUpdate();
            
            System.out.println("Character Created!");
        } catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void edit(CharacterModel obj) {
        String sql = "UPDATE CHARACTERS "
                + "SET NAME = ?, PRONOUNS = ?, AGE = ?, DOB = ?, SPECIES = ?, OCCUPATION = ?, DESCRIPTION = ? "
                + "WHERE CHAR_ID = ?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, obj.getName());
            ps.setString(2, obj.getPronouns());
            ps.setInt(3, obj.getAge());
            ps.setString(4, obj.getDob());
            ps.setString(5, obj.getSpecies());
            ps.setString(6, obj.getOccupation());
            ps.setString(7, obj.getDescription());
            ps.setInt(8, obj.getId());
            ps.executeUpdate();
        } catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(CharacterModel obj) {
        if (obj == null){
            return;
        }
        try {
            // Start transaction
            conn.setAutoCommit(false);

            // Delete relationships
            String sql1 = "DELETE FROM RELATIONSHIPS "
                        + "WHERE CHAR1_ID = ? OR CHAR2_ID = ?";

            try (PreparedStatement ps = conn.prepareStatement(sql1)) {
                ps.setInt(1, obj.getId());
                ps.setInt(2, obj.getId());
                ps.executeUpdate();
            }

            // Delete group links
            String sql2 = "DELETE FROM CHARACTER_GROUPS "
                        + "WHERE CHARACTER_ID = ?";

            try (PreparedStatement ps = conn.prepareStatement(sql2)) {
                ps.setInt(1, obj.getId());
                ps.executeUpdate();
            }

            // Delete tag links
            String sql3 = "DELETE FROM CHARACTER_TAGS "
                        + "WHERE CHARACTER_ID = ?";

            try (PreparedStatement ps = conn.prepareStatement(sql3)) {
                ps.setInt(1, obj.getId());
                ps.executeUpdate();
            }

            // Delete character
            String sql4 = "DELETE FROM CHARACTERS "
                        + "WHERE CHAR_ID = ?";

            try (PreparedStatement ps = conn.prepareStatement(sql4)) {
                ps.setInt(1, obj.getId());
                ps.executeUpdate();
            }

            // Save all changes
            conn.commit();

        } catch (SQLException e) {

            try {
                // Undo everything if something failed
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    @Override
    public CharacterModel get(String n) {
        String sql = "SELECT * FROM CHARACTERS WHERE NAME = ?";
        CharacterModel c = null;
        
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, n);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()){
                int id = rs.getInt("CHAR_ID");
                String name = rs.getString("NAME");
                String pronouns = rs.getString("PRONOUNS");
                int age = rs.getInt("AGE");
                String dob = rs.getString("DOB");
                String species = rs.getString("SPECIES");
                String occupation = rs.getString("OCCUPATION");
                String desc = rs.getString("DESCRIPTION");
                c = new CharacterModel(id, name, pronouns, age, dob, species, occupation, desc);
            }  
            
            rs.close();
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return c;
    }
    
    @Override
    public CharacterModel get(int i) {
        String sql = "SELECT * FROM CHARACTERS WHERE CHAR_ID = ?";
        CharacterModel c = null;
        
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, i);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()){
                int id = rs.getInt("CHAR_ID");
                String name = rs.getString("NAME");
                String pronouns = rs.getString("PRONOUNS");
                int age = rs.getInt("AGE");
                String dob = rs.getString("DOB");
                String species = rs.getString("SPECIES");
                String occupation = rs.getString("OCCUPATION");
                String desc = rs.getString("DESCRIPTION");
                c = new CharacterModel(id, name, pronouns, age, dob, species, occupation, desc);
            }
            
            rs.close();
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return c;
    }

    public String[] getAll() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT NAME FROM CHARACTERS";
        
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
     * Adds the character to the group in the database
     * @param c Character
     * @param g Group
     */
    public void addCharacterToGroup(CharacterModel c, Group g) {
        if (g == null || c == null){
            return;
        }
        String sql = "INSERT INTO CHARACTER_GROUPS VALUES (?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, c.getId());
            ps.setInt(2, g.getId());
            ps.executeUpdate();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
    /**
     * Removes character from the given group in the database
     * @param c Character to remove 
     * @param g Group to remove from
     */
    public void removeCharacterFromGroup(CharacterModel c, Group g){
        if (g == null || c == null){
            return;
        }
        String sql = "DELETE FROM CHARACTER_GROUPS WHERE CHARACTER_ID = ? AND GROUP_ID = ?";
        
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, c.getId());
            ps.setInt(2, g.getId());
            ps.executeUpdate();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    /**
     * Gets all groups a character is in
     * @param c Character to check
     * @return Array of names of each group the character is in
     */
    public String[] getGroups(CharacterModel c) {
        List<String> groups = new ArrayList<>();
        String sql = "SELECT g.NAME FROM USER_GROUPS g "+
                "JOIN CHARACTER_GROUPS cg ON g.GROUP_ID = cg.GROUP_ID "+
                "WHERE cg.CHARACTER_ID = ?";
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, c.getId());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                groups.add(rs.getString("NAME"));
            }
            
            rs.close();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        
        return groups.toArray(String[]::new);
    }

    /**
     * Get all tags attached to a character
     * @param c Character to check
     * @return Array of names of each tag the character has
     */
    public String[] getTags(CharacterModel c) {
        List<String> tags = new ArrayList<>();
        String sql = "SELECT t.NAME FROM TAGS t "
                + "JOIN CHARACTER_TAGS ct ON t.TAG_ID = ct.TAG_ID "
                + "WHERE ct.CHARACTER_ID = ?";
        
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, c.getId());
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
