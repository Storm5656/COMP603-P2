package assignment2.classes;


import java.util.ArrayList;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

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
    
    private List<Tag> tags = new ArrayList<Tag>();
    private List<Relationship> relationships = new ArrayList<Relationship>();
    private List<Group> groups = new ArrayList<Group>();
    
    public Character(int i, String n, String p, int a, String d, String s, String o){
        id = i;
        name = n;
        pronouns = p;
        age = a;
        dob = d;
        species = s;
        occupation = o;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the pronouns
     */
    public String getPronouns() {
        return pronouns;
    }

    /**
     * @param pronouns the pronouns to set
     */
    public void setPronouns(String pronouns) {
        this.pronouns = pronouns;
    }

    /**
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * @return the dob
     */
    public String getDob() {
        return dob;
    }

    /**
     * @param dob the dob to set
     */
    public void setDob(String dob) {
        this.dob = dob;
    }

    /**
     * @return the species
     */
    public String getSpecies() {
        return species;
    }

    /**
     * @param species the species to set
     */
    public void setSpecies(String species) {
        this.species = species;
    }

    /**
     * @return the occupation
     */
    public String getOccupation() {
        return occupation;
    }

    /**
     * @param occupation the occupation to set
     */
    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    /**
     * @return the tags
     */
    public List<Tag> getTags() {
        return tags;
    }

    /**
     * @param tags the tags to set
     */
    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    /**
     * @return the relationships
     */
    public List<Relationship> getRelationships() {
        return relationships;
    }

    /**
     * @param relationships the relationships to set
     */
    public void setRelationships(List<Relationship> relationships) {
        this.relationships = relationships;
    }

    /**
     * @return the groups
     */
    public List<Group> getGroups() {
        return groups;
    }

    /**
     * @param groups the groups to set
     */
    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
    
    public String toString(){
        return Integer.toString(id);
    }
}
