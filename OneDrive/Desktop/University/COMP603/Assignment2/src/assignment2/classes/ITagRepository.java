package assignment2.classes;


import java.io.BufferedReader;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

/**
 *
 * @author ren
 */
public interface ITagRepository {
    public List<Tag> getTags();
    public void save(Tag t, String file);
    public void load(BufferedReader br);
    public Tag getTag();
    public Tag findTag(String n);
    public Tag findTag(int id);
    public int getIdCount();
    public void setIdCount(int i);
}
