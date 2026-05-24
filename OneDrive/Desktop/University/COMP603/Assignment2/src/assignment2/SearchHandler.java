/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment2;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.Arrays;
import java.util.Collections;

/**
 *
 * @author ren
 */
public class SearchHandler {
    
    public String[] filterName(String[] chars, String n){
        List<String> c = new ArrayList<>();
       
        for(String character:chars){
            if(character.contains(n)){
                c.add(character);
            }
        }
        
        return c.toArray(String[]::new);
    }
    
    public String[] filterGroup(String[] chars, String g){
        if(chars.length == 0){
            return new String[0];
        }
        Connection conn = DatabaseManager.getConnection();
        List<String> filtered = new ArrayList<>();
        String sql = "SELECT c.NAME FROM CHARACTERS c "
                + "JOIN CHARACTER_GROUPS cg ON c.CHAR_ID = cg.CHARACTER_ID "
                + "WHERE cg.GROUP_ID = (SELECT g.GROUP_ID FROM USER_GROUPS g WHERE g.NAME = ?)";
        
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, g);
            ResultSet rs = ps.executeQuery();
            
            List<String> c = new ArrayList<>(Arrays.asList(chars));
            
            while(rs.next()){
                if(c.contains(rs.getString(1))){
                    filtered.add(rs.getString(1));
                }
            }
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        
        return filtered.toArray(String[]::new);
    }
    
    public String[] filterTag(String[] chars, List<String> tags){
        if(chars.length == 0){
            return new String[0];
        }
        
        Connection conn = DatabaseManager.getConnection();
        List<String> filtered = new ArrayList<>();
        String placeholders =
            String.join(",", Collections.nCopies(tags.size(), "?"));

        String sql =
                "SELECT c.NAME FROM CHARACTERS c "
              + "JOIN CHARACTER_TAGS ct ON ct.CHARACTER_ID = c.CHAR_ID "
              + "WHERE ct.TAG_ID IN ("
              + "SELECT t.TAG_ID FROM TAGS t WHERE t.NAME IN ("
              + placeholders
              + "))";
        
        System.out.println(sql);
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            int i = 1;
            for(String t:tags){
                ps.setString(i, t);
                i++;
            }
            ResultSet rs = ps.executeQuery();
            List<String> c = new ArrayList<>(Arrays.asList(chars));
            
            while(rs.next()){
                if(c.contains(rs.getString(1))){
                    filtered.add(rs.getString(1));
                }
            }
            
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return filtered.toArray(String[]::new);
    }
}
