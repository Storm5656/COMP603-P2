package assignment2.classes;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ren
 */
public class FileCharacterRepository implements ICharacterRepository{
    private List<Character> characters;
    private ITagRepository tags;
    private int idCount = 0;
    
    public FileCharacterRepository(){
        characters = new ArrayList<Character>();
    }
    
    /**
     * @return the characters
     */
    @Override
    public List<Character> getCharacters() {
        return characters;
    }
    
    /**
     * @param characters the characters to set
     */
    public void setCharacters(List<Character> characters) {
        this.characters = characters;
    }
    
    /**
     * Get the character with matching character name or id
     * @return the character
     */
    public Character getCharacter(){
        Scanner scan = new Scanner(System.in);
        String input = "";
        try {
            System.out.println("Input character name or ID");
            input = scan.nextLine();
            int intInput = Integer.parseInt(input);
            return findCharacter(intInput);
        }
        catch(Exception e){
            return findCharacter(input);
        }
    }
    
    /**
     * Save the character
     * @param c character to save
     * @param file file path
     */
    @Override
    public void save(Character c, String file) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))){
            bw.write("c");
            bw.newLine();
            // One line for character info (CSV)
            bw.write(toCSV(c));
            bw.newLine();
            // One line for tags
            bw.write(String.join(",", c.getTags().toString()));
            bw.newLine();
            
        } catch (IOException e) {
            e.printStackTrace(); 
        }
    }
    
    @Override
    public void load(BufferedReader br){
        try{
            // Create character
            String line = br.readLine();
            String[] values = line.split(",");
            
            int id = Integer.parseInt(values[0]);
            int age = Integer.parseInt(values[3]);
            Character newCharacter = new Character(id, values[1], values[2], age, values[4], values[5], values[6]);
            characters.add(newCharacter);
            
            // Update id count
            if(id >= getIdCount()) setIdCount(id + 1);
            
            // Tags
            line = br.readLine();
            line = line.substring(1, line.length() - 1);
            
            if(!line.isEmpty()) {
                values = line.split(", ");

                for(String s:values){
                    Tag t = tags.findTag(Integer.parseInt(s));
                    newCharacter.getTags().add(t);
                }   
            }
        }catch(IOException e){
            e.printStackTrace();
        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Could not load character");
        }
    }
    
    public String toCSV(Character c){
        String Id = Integer.toString(c.getId());
        String age = Integer.toString(c.getAge());
        String species = c.getSpecies().replace(",","");
        String occupation = c.getOccupation().replace(",","");
        String csvLine = String.format("%s,%s,%s,%s,%s,%s,%s", Id, c.getName(), c.getPronouns(), age, c.getDob(), species, occupation);
        
        return csvLine;
    }

    /**
     * Delete a character
     * @param c character to delete
     */
    @Override
    public void delete(Character c) {
        // Remove from all groups
        for (Group group : c.getGroups()) {
            if (group.getCharacters() != null) {
                group.getCharacters().remove(c);
            }
        }

        List<Relationship> r = new ArrayList<>(c.getRelationships());
        
        // Remove all relationships involving this character
        for (Relationship rel : r) {
            rel.getChar1().getRelationships().remove(rel);
            rel.getChar2().getRelationships().remove(rel);
            
            rel.setChar1(null);
            rel.setChar2(null);
        }

        // Delete the character
        characters.remove(c);
    }

    /**
     * Find a character using the character name
     * @param n name of the character
     * @return Character found
     */
    @Override
    public Character findCharacter(String n) {
        for (Character c:getCharacters()){
            if (n.equalsIgnoreCase(c.getName())) return c;
        }
        // Character was not found
        System.out.println("Character not found");
        return null;
    }
    
    /**
     * Find a character using the id
     * @param id of the character
     * @return Character found
     */
    @Override
    public Character findCharacter(int id) {
        for (Character c:getCharacters()){
            if (c.getId() == id) return c;
        }
        // Character was not found
        System.out.println("Character not found");
        return null;
    }

    /**
     * @return the idCount
     */
    public int getIdCount() {
        return idCount;
    }

    /**
     * @param idCount the idCount to set
     */
    public void setIdCount(int idCount) {
        this.idCount = idCount;
    }
    
    public void setTagRepo(ITagRepository t){
        tags = t;
    }
}
