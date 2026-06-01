/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment2.Models;

import assignment2.Models.Entities.CharacterModel;
import assignment2.Models.Entities.Relationship;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ren
 */
public class RelationshipHandler extends AbstractHandler<Relationship>{
   
    @Override
    public void create(Relationship obj){
        try{
            // Check if relationship already exists
            String checkSql =
                "SELECT * FROM RELATIONSHIPS " +
                "WHERE (CHAR1_ID = ? AND CHAR2_ID = ?) " +
                "OR (CHAR1_ID = ? AND CHAR2_ID = ?)";

            PreparedStatement checkPs = conn.prepareStatement(checkSql);

            checkPs.setInt(1, obj.getChar1().getId());
            checkPs.setInt(2, obj.getChar2().getId());
            checkPs.setInt(3, obj.getChar2().getId());
            checkPs.setInt(4, obj.getChar1().getId());

            ResultSet rs = checkPs.executeQuery();

            // If a row exists, don't insert
            if (rs.next()) {
                System.out.println("Relationship already exists.");
                return;
            }
            
            String sql = "INSERT INTO RELATIONSHIPS (CHAR1_ID, CHAR2_ID, DYNAMIC1, DYNAMIC2, DESCRIPTION) "
                    + "VALUES (?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, obj.getChar1().getId());
            ps.setInt(2, obj.getChar2().getId());
            ps.setString(3, obj.getDynamic1());
            ps.setString(4, obj.getDynamic2());
            ps.setString(5, obj.getDescription());
            ps.executeUpdate();
            
        }catch(SQLException ex){
            ex.printStackTrace();
            System.out.println(ex);
        }
    }
    
    @Override
    public void edit(Relationship obj){
        String sql = "UPDATE RELATIONSHIPS "
                + "SET CHAR1_ID = ?, CHAR2_ID = ?, DYNAMIC1 = ?, DYNAMIC2 = ?, DESCRIPTION = ? "
                + "WHERE REL_ID = ?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, obj.getChar1().getId());
            ps.setInt(2, obj.getChar2().getId());
            ps.setString(3, obj.getDynamic1());
            ps.setString(4, obj.getDynamic2());
            ps.setString(5, obj.getDescription());
            ps.setInt(6, obj.getId());
            
            ps.executeUpdate();
        } catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
    @Override
    public void delete(Relationship obj){
        if(obj == null){
            return;
        }
        String sql = "DELETE FROM RELATIONSHIPS WHERE REL_ID = ?";
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, obj.getId());
            ps.executeUpdate();
        } catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
    @Override
    public Relationship get(String n){
        Relationship r = null;
        
        String sql = "SELECT * FROM RELATIONSHIPS "
                + "WHERE (CHAR1_ID = ? AND CHAR2_ID = ?) "
                + "OR (CHAR1_ID = ? AND CHAR2_ID = ?)";
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            String[] split = n.split("&");
            CharacterHandler ch = new CharacterHandler();
            int c1 = ch.get(split[0].trim()).getId();
            int c2 = ch.get(split[1].trim()).getId();
        
            
            ps.setInt(1, c1);
            ps.setInt(2, c2);
            ps.setInt(3, c2);
            ps.setInt(4, c1);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                int id = rs.getInt("REL_ID");
                CharacterModel char1 = ch.get(rs.getInt("CHAR1_ID"));
                CharacterModel char2 = ch.get(rs.getInt("CHAR2_ID"));
                String d1 = rs.getString("DYNAMIC1");
                String d2 = rs.getString("DYNAMIC2");
                String desc = rs.getString("DESCRIPTION");
                r = new Relationship(id, char1, char2, d1, d2, desc);
            } 
            
            rs.close();
        }catch(Exception ex){
        }
        return r;
    }
    
    @Override
    public Relationship get(int i){
        CharacterHandler ch = new CharacterHandler();
        Relationship r = null;
        
        String sql = "SELECT * FROM RELATIONSHIPS "
                + "WHERE REL_ID = ?";
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, i);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                int id = rs.getInt("REL_ID");
                CharacterModel char1 = ch.get(rs.getInt("CHAR1_ID"));
                CharacterModel char2 = ch.get(rs.getInt("CHAR2_ID"));
                String d1 = rs.getString("DYNAMIC1");
                String d2 = rs.getString("DYNAMIC2");
                String desc = rs.getString("DESCRIPTION");
                r = new Relationship(id, char1, char2, d1, d2, desc);
            } 
            
            rs.close();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return r;
    }

    //get characters relationships
    public String[] getCharRelationships(CharacterModel c) {
        List<String> relationships = new ArrayList<>();
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
            
            rs.close();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        
        return relationships.toArray(String[]::new);
    }
}
