package assignment2.classes;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ren
 */
public class Relationship {
    private Character char1;
    private Character char2;
    private String description;
    
    public Relationship(Character c1, Character c2, String d){
        char1 = c1;
        char2 = c2;
        description = d;
    }

    /**
     * @return the char1
     */
    public Character getChar1() {
        return char1;
    }

    /**
     * @param char1 the char1 to set
     */
    public void setChar1(Character char1) {
        this.char1 = char1;
    }

    /**
     * @return the char2
     */
    public Character getChar2() {
        return char2;
    }

    /**
     * @param char2 the char2 to set
     */
    public void setChar2(Character char2) {
        this.char2 = char2;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    public boolean contains(Character c){
        return (char1 == c || char2 == c);
    }
    public boolean contains(Character c1, Character c2){
        return ((char1 == c1 || char2 == c1)&&(char1 == c2 || char2 == c2));
    }
    
    public String toString(){
        String s = Integer.toString(char1.getId()) + "," + Integer.toString(char2.getId());
        return s;
    }
}
