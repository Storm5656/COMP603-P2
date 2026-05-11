/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment2;

/**
 *
 * @author ren
 */
public class Character {
    private int id;
    private String name;
    private String pronouns;
    private int age;
    private String dob;
    private String species;
    private String occupation;
    private String description;
    
    public Character(int i, String n, String p, int a, String d, String s, String o, String desc){
        id = i;
        name = n;
        pronouns = p;
        age = a;
        dob = d;
        species = s;
        occupation = o;
        description = desc;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the pronouns
     */
    public String getPronouns() {
        return pronouns;
    }

    /**
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * @return the dob
     */
    public String getDob() {
        return dob;
    }

    /**
     * @return the species
     */
    public String getSpecies() {
        return species;
    }

    /**
     * @return the occupation
     */
    public String getOccupation() {
        return occupation;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }
    
    
    
}
