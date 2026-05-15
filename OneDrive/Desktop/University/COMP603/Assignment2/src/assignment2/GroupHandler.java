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
    
    // add character to group
    
}
