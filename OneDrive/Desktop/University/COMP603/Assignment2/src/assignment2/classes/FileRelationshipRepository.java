package assignment2.classes;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
public class FileRelationshipRepository implements IRelationshipRepository{
    private List<Relationship> relationships;
    private ICharacterRepository charRepo;
    
    public FileRelationshipRepository(ICharacterRepository characterRepo){
        relationships = new ArrayList<Relationship>();
        charRepo = characterRepo;
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

    @Override
    public void save(Relationship r, String file) {
         try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))){
            bw.write("r");
            bw.newLine();
            // One line for relationship info (CSV)
            bw.write(toCSV(r));
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace(); 
        }
    }
    
    @Override
    public void load(BufferedReader br){
        try{
            String line = br.readLine();
            String[] values = line.split(",");
            Character c1 = charRepo.findCharacter(Integer.parseInt(values[0]));
            Character c2 = charRepo.findCharacter(Integer.parseInt(values[1]));
            
            Relationship newRelationship = new Relationship(c1, c2, values[2]);
            relationships.add(newRelationship);
            c1.getRelationships().add(newRelationship);
            c2.getRelationships().add(newRelationship);
            
        }catch(IOException e){
            e.printStackTrace();
        } catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Could not load group");
        }
    }
    
    public String toCSV(Relationship r){
        String output = r.getChar1().getId() + "," + r.getChar2().getId() + "," + r.getDescription().replaceAll(",", "");
        return output;
    }

    @Override
    public void deleteRelationship(Relationship r) {
        relationships.remove(r);
    }

    /**
     * Get all of the relationships a character is in
     * @return a list of the relationships
     */
    @Override
    public List<Relationship> getCharRelationships() {
        // Get character
        Character c = getCharRepo().getCharacter();
        
        if(c != null){
            return findCharRelationships(c);
        } else return null;
    }

    /**
     * Returns list of relationships that include a given character
     * @param c Character
     * @return 
     */
    @Override
    public List<Relationship> findCharRelationships(Character c) {
        List<Relationship> r = new ArrayList<Relationship>();
        
        for(Relationship rel:getRelationships()){
            if(rel.contains(c)) r.add(rel);
        }
        
        return r;
    }

    /**
     * Get the relationship between 2 characters
     * @return Relationship
     */
    @Override
    public Relationship getRelationship() {
        System.out.println("Character 1");
        Character c1 = getCharRepo().getCharacter();
        System.out.println("Character 2");
        Character c2 = getCharRepo().getCharacter();
        
        if (c1 != null && c2 != null){
            return findRelationship(c1, c2);
        }
        else return null;
    }

    @Override
    public Relationship findRelationship(Character c1, Character c2) {
        for(Relationship r:getRelationships()){
            if(r.contains(c1, c2)) return r;
        }
        System.out.println("Could not find relationship with selected characters");
        return null;
    }

    /**
     * @return the charRepo
     */
    public ICharacterRepository getCharRepo() {
        return charRepo;
    }
    
}
