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
public interface IGroupRepository {
    public ICharacterRepository getCharRepo();
    public List<Group> getGroups();
    public void save(Group g, String file);
    public void load(BufferedReader br);
    public void delete(Group g);
    public Group getGroup();
    public Group findGroup(String n);
    public Group findGroup(int id);
    public int getIdCount();
    public void setIdCount(int i);
}
