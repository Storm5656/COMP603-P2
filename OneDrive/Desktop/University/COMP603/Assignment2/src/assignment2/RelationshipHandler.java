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
public class RelationshipHandler {
    //create relationship
    // make sure no duplicates
    
    public void createRelationship(Character c1, Character c2, String d1, String d2, String desc){
        try{
            Connection conn = DatabaseManager.getConnection();
            // Check if relationship already exists
            String checkSql =
                "SELECT * FROM RELATIONSHIPS " +
                "WHERE (CHAR1_ID = ? AND CHAR2_ID = ?) " +
                "OR (CHAR1_ID = ? AND CHAR2_ID = ?)";

            PreparedStatement checkPs = conn.prepareStatement(checkSql);

            checkPs.setInt(1, c1.getId());
            checkPs.setInt(2, c2.getId());
            checkPs.setInt(3, c2.getId());
            checkPs.setInt(4, c1.getId());

            ResultSet rs = checkPs.executeQuery();

            // If a row exists, don't insert
            if (rs.next()) {
                System.out.println("Relationship already exists.");
                return;
            }
            
            String sql = "INSERT INTO RELATIONSHIPS (CHAR1_ID, CHAR2_ID, DYNAMIC1, DYNAMIC2, DESCRIPTION) "
                    + "VALUES (?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, c1.getId());
            ps.setInt(2, c2.getId());
            ps.setString(3, d1);
            ps.setString(4, d2);
            ps.setString(5, desc);
            ps.executeUpdate();
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    //edit relationship
    public void editRelationship(Relationship r){
        String sql = "UPDATE RELATIONSHIPS "
                + "SET CHAR1_ID = ?, CHAR2_ID = ?, DYNAMIC1 = ?, DYNAMIC2 = ?, DESCRIPTION = ? "
                + "WHERE REL_ID = ?";
        Connection conn = DatabaseManager.getConnection();
        
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, r.getChar1().getId());
            ps.setInt(2, r.getChar2().getId());
            ps.setString(3, r.getDynamic1());
            ps.setString(4, r.getDynamic2());
            ps.setString(5, r.getDescription());
            ps.setInt(6, r.getId());
            
            ps.executeUpdate();
        } catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
    //get relationship
    public Relationship getRelationship(String names){
        String[] split = names.split("&");
        CharacterHandler ch = new CharacterHandler();
        int c1 = ch.getCharacter(split[0].trim()).getId();
        int c2 = ch.getCharacter(split[1].trim()).getId();
        
        Relationship r = null;
        
        Connection conn = DatabaseManager.getConnection();
        String sql = "SELECT * FROM RELATIONSHIPS "
                + "WHERE (CHAR1_ID = ? AND CHAR2_ID = ?) "
                + "OR (CHAR1_ID = ? AND CHAR2_ID = ?)";
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, c1);
            ps.setInt(2, c2);
            ps.setInt(3, c2);
            ps.setInt(4, c1);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                int id = rs.getInt("REL_ID");
                Character char1 = ch.getCharacter(rs.getInt("CHAR1_ID"));
                Character char2 = ch.getCharacter(rs.getInt("CHAR2_ID"));
                String d1 = rs.getString("DYNAMIC1");
                String d2 = rs.getString("DYNAMIC2");
                String desc = rs.getString("DESCRIPTION");
                r = new Relationship(id, char1, char2, d1, d2, desc);
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return r;
    }
    
    //get characters relationships
    public String[] getCharRelationships(Character c) {
        List<String> relationships = new ArrayList<String>();
        Connection conn = DatabaseManager.getConnection();
        String sql = "SELECT DISTINCT c.NAME "
                + "FROM CHARACTERS c "
                + "JOIN RELATIONSHIPS r "
                + "ON c.CHAR_ID = r.CHAR1_ID "
                + "OR c.CHAR_ID = r.CHAR2_ID "
                + "WHERE ? IN (r.CHAR1_ID, r.CHAR2_ID)"
                + "AND c.CHAR_ID <> ?";
        
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, c.getId());
            ps.setInt(2, c.getId());
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                relationships.add(c.getName() + " & " + rs.getString("NAME"));
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        
        return relationships.toArray(new String[0]);
    }
}
