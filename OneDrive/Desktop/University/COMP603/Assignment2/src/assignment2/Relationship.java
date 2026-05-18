/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment2;

/**
 *
 * @author ren
 */
public class Relationship {
    private int id;
    private Character char1;
    private Character char2;
    private String dynamic1;
    private String dynamic2;
    private String description;
    
    public Relationship(int i, Character c1, Character c2, String d1, String d2, String desc){
        id = i;
        char1 = c1;
        char2 = c2;
        dynamic1 = d1;
        dynamic2 = d2;
        description = desc;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the char1
     */
    public Character getChar1() {
        return char1;
    }

    /**
     * @return the char2
     */
    public Character getChar2() {
        return char2;
    }

    /**
     * @return the dynamic1
     */
    public String getDynamic1() {
        return dynamic1;
    }

    /**
     * @return the dynamic2
     */
    public String getDynamic2() {
        return dynamic2;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }
}
