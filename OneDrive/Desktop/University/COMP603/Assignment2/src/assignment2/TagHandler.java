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
public class TagHandler {
    //create tag
    public void createTag(String n){
        Connection conn = DatabaseManager.getConnection();
        String sql = "INSERT INTO TAGS (NAME) VALUES (?)";
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, n);
            ps.executeUpdate();
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
    //add tag to character
    public void addCharTag(Character c, Tag t){
        Connection conn = DatabaseManager.getConnection();
        String sql = "INSERT INTO CHARACTER_TAGS VALUES (?,?)";
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, c.getId());
            ps.setInt(2, t.getId());
            ps.executeUpdate();
        } catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    //add tag to group
    public void addGroupTag(Group g, Tag t){
        Connection conn = DatabaseManager.getConnection();
        String sql = "INSERT INTO GROUP_TAGS VALUES (?,?)";
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, g.getId());
            ps.setInt(2, t.getId());
            ps.executeUpdate();
        } catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    //remove tag from character
    public void removeCharTag(Character c, Tag t){
        Connection conn = DatabaseManager.getConnection();
        String sql = "DELETE FROM CHARACTER_TAGS WHERE CHARACTER_ID = ? AND TAG_ID = ?";
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, c.getId());
            ps.setInt(2, t.getId());
            ps.executeUpdate();
            
        } catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    //remove tag from group
    public void removeGroupTag(Group g, Tag t){
        Connection conn = DatabaseManager.getConnection();
        String sql = "DELETE FROM GROUP_TAGS WHERE GROUP_ID = ? AND TAG_ID = ?";
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, g.getId());
            ps.setInt(2, t.getId());
            ps.executeUpdate();
            
        } catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    //get tag
    public Tag getTag(String n){
        Tag t = null;
        Connection conn = DatabaseManager.getConnection();
        String sql = "SELECT * FROM TAGS WHERE NAME = ?";
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, n);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                t = new Tag(rs.getInt("TAG_ID"), rs.getString("NAME"));
            }
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return t;
    }
    //get all tags
    public String[] getTags(){
        List<String> tags = new ArrayList<String>();
        Connection conn = DatabaseManager.getConnection();
        String sql = "SELECT NAME FROM TAGS";
        try(Statement s = conn.createStatement();
                ResultSet rs = s.executeQuery(sql)){
            while(rs.next()){
                tags.add(rs.getString("NAME"));
            }
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        
        return tags.toArray(new String[0]);
    }
}
