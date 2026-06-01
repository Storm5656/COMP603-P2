/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment2.Models;
import assignment2.Models.Entities.CharacterModel;
import assignment2.Models.Entities.Group;
import assignment2.Models.Entities.Tag;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ren
 */
public class TagHandler extends AbstractHandler<Tag>{
    
     @Override
    public void create(Tag obj) {
        String sql = "INSERT INTO TAGS (NAME) VALUES (?)";
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, obj.getName());
            ps.executeUpdate();
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void edit(Tag obj) {
        String sql = "UPDATE TAGS SET NAME = ?";
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, obj.getName());
            ps.executeUpdate();
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(Tag obj) {
        String sql = "DELETE FROM TAGS WHERE TAG_ID = ?";
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, obj.getId());
            ps.executeUpdate();
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public Tag get(String n) {
        Tag t = null;
        String sql = "SELECT * FROM TAGS WHERE NAME = ?";
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, n);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                t = new Tag(rs.getInt("TAG_ID"), n);
            }
            
            rs.close();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        
        return t;
    }

    @Override
    public Tag get(int i) {
        Tag t = null;
        String sql = "SELECT * FROM TAGS WHERE TAG_ID = ?";
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, i);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                t = new Tag(i, rs.getString("NAME"));
            }
            
            rs.close();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        
        return t;
    }
    
    public String[] getAll(){
        List<String> tags = new ArrayList<>();
        String sql = "SELECT NAME FROM TAGS";
        try(Statement s = conn.createStatement();
                ResultSet rs = s.executeQuery(sql)){
            while(rs.next()){
                tags.add(rs.getString("NAME"));
            }
            
            rs.close();
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        
        return tags.toArray(String[]::new);
    }
    
    //add tag to character
    public void addCharTag(CharacterModel c, Tag t){
        String sql = "INSERT INTO CHARACTER_TAGS VALUES (?,?)";
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, c.getId());
            ps.setInt(2, t.getId());
            ps.executeUpdate();
        } catch(SQLException ex){
            removeCharTag(c,t);
        }
    }
    
    //add tag to group
    public void addGroupTag(Group g, Tag t){
        String sql = "INSERT INTO GROUP_TAGS VALUES (?,?)";
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, g.getId());
            ps.setInt(2, t.getId());
            ps.executeUpdate();
        } catch(SQLException ex){
            removeGroupTag(g,t);
        }
    }
    
    //remove tag from character
    public void removeCharTag(CharacterModel c, Tag t){
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
        String sql = "DELETE FROM GROUP_TAGS WHERE GROUP_ID = ? AND TAG_ID = ?";
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, g.getId());
            ps.setInt(2, t.getId());
            ps.executeUpdate();
            
        } catch(SQLException ex){
            ex.printStackTrace();
        }
    }
   
}
