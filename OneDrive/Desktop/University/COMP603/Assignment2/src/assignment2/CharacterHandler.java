/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment2;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;
import java.sql.*;

/**
 * @author ren
 */
public class CharacterHandler {
    // creating character
    public void createCharacter(String name, String pronouns, String dob, int age, String species, String occupation, String description){
        try {
            Connection conn = DatabaseManager.getConnection();
            if (age != 0){
                String sql = "INSERT INTO CHARACTERS (NAME, PRONOUNS, DOB, AGE, SPECIES, OCCUPATION, DESCRIPTION)"
                    + "VALUES (?,?,?,?,?,?,?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, name);
                ps.setString(2, pronouns);
                ps.setString(3, dob);
                ps.setInt(4, age);
                ps.setString(5, species);
                ps.setString(6, occupation);
                ps.setString(7, description);
                ps.executeUpdate();
            }
            else{
                String sql = "INSERT INTO CHARACTERS (NAME, PRONOUNS, DOB, SPECIES, OCCUPATION, DESCRIPTION)"
                    + "VALUES (?,?,?,?,?,?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, name);
                ps.setString(2, pronouns);
                ps.setString(3, dob);
                ps.setString(4, species);
                ps.setString(5, occupation);
                ps.setString(6, description);
                ps.executeUpdate();
            }
            System.out.println("Character Created!");
        } catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
    public String[] getAllCharacters(){
        List<String> list = new ArrayList<String>();
        String sql = "SELECT NAME FROM CHARACTERS";
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
    
    public Character getCharacter(String n){
        String sql = "SELECT * FROM CHARACTERS WHERE NAME = ?";
        Character c = null;
        Connection conn = DatabaseManager.getConnection();
        
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
                c = new Character(id, name, pronouns, age, dob, species, occupation, desc);
            
            }
            
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return c;
    }
    
    public void editCharacter(Character c){
        String sql = "UPDATE CHARACTERS "
                + "SET NAME = ?, PRONOUNS = ?, AGE = ?, DOB = ?, SPECIES = ?, OCCUPATION = ?, DESCRIPTION = ? "
                + "WHERE CHAR_ID = ?";
        Connection conn = DatabaseManager.getConnection();
        
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, c.getName());
            ps.setString(2, c.getPronouns());
            ps.setInt(3, c.getAge());
            ps.setString(4, c.getDob());
            ps.setString(5, c.getSpecies());
            ps.setString(6, c.getOccupation());
            ps.setString(7, c.getDescription());
            ps.setInt(8, c.getId());
            
            ps.executeUpdate();
        } catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    public void addCharacterToGroup(Character c, Group g) {
        if (g == null || c == null){
            return;
        }
        Connection conn = DatabaseManager.getConnection();
        String sql = "INSERT INTO CHARACTER_GROUPS VALUES (?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, c.getId());
            ps.setInt(2, g.getId());
            ps.executeUpdate();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
    public void removeCharacterFromGroup(Character c, Group g){
        if (g == null || c == null){
            return;
        }
        Connection conn = DatabaseManager.getConnection();
        String sql = "DELETE FROM CHARACTER_GROUPS WHERE CHARACTER_ID = ? AND GROUP_ID = ?";
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, c.getId());
            ps.setInt(2, g.getId());
            ps.executeUpdate();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
}
