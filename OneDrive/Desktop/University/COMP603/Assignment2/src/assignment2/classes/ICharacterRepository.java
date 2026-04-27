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
public interface ICharacterRepository {
    public void setTagRepo(ITagRepository t);
    public List<Character> getCharacters();
    public void save(Character c, String file);
    public void load(BufferedReader br);
    public void delete(Character c);
    public Character getCharacter();
    public Character findCharacter(String n);
    public Character findCharacter(int id);
    public int getIdCount();
    public void setIdCount(int i);
}
