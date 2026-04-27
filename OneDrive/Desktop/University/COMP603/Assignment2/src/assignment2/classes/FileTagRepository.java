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
public class FileTagRepository implements ITagRepository{
    private List<Tag> tags;
    private int idCount = 0;
    
    public FileTagRepository(){
        tags = new ArrayList<Tag>();
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
     * Save the tag
     * @param t tag to save
     */
    @Override
    public void save(Tag t, String file) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))){
            bw.write("t");
            bw.newLine();
            // One line for tag info (CSV)
            bw.write(t.getId() + "," + t.getName());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace(); 
        }
    }
    
    /**
     * Load the tag
     */
    @Override
    public void load(BufferedReader br) {
        try{
            String line = br.readLine();
            String[] values = line.split(",");
            int id = Integer.parseInt(values[0]);
            String name = values[1];
            
            // Load tag
            Tag newTag = new Tag(id, name);
            tags.add(newTag);
            
            // Update id count
            if(id >= idCount) idCount = id + 1;
            
        } catch(IOException e){
            e.printStackTrace();
        } catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Could not load tag");
        }
    }
    
    public Tag getTag(){
        Scanner scan = new Scanner(System.in);
        String input = "";
        try {
            System.out.println("Input tag name or ID");
            input = scan.nextLine();
            int intInput = Integer.parseInt(input);
            return findTag(intInput);
        }
        catch(Exception e){
            return findTag(input);
        }
    }
    
    public Tag findTag(String n){
        for(Tag t:getTags()){
            if (n.equalsIgnoreCase(t.getName())) return t;
        }
        // Tag was not found
        System.out.println("Tag not found");
        return null;
    }
    
    public Tag findTag(int id){
        for (Tag t:getTags()){
            if (t.getId() == id) return t;
        }
        // Tag was not found
        System.out.println("Tag not found");
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
}
