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
public class FileGroupRepository implements IGroupRepository {
    private List<Group> groups;
    private ICharacterRepository charRepo;
    private int idCount = 0;
    
    public FileGroupRepository(ICharacterRepository cRepo){
        groups = new ArrayList<Group>();
        charRepo = cRepo;
    }

    /**
     * @return the groups
     */
    @Override
    public List<Group> getGroups() {
        return groups;
    }

    /**
     * @param groups the groups to set
     */
    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
    
    /**
     * Get the group with matching name or id
     * @return the group
     */
    public Group getGroup(){
        Scanner scan = new Scanner(System.in);
        String input = "";
        try {
            System.out.println("Input group name or ID");
            input = scan.nextLine();
            int intInput = Integer.parseInt(input);
            return findGroup(intInput);
        }
        catch(Exception e){
            return findGroup(input);
        }
    }
    
    /**
     * Save the group
     * @param g group to save
     */
    public void save(Group g, String file) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))){
            bw.write("g");
            bw.newLine();
            // One line for character info (CSV)
            bw.write(toCSV(g));
            bw.newLine();
            // One line for characters
            bw.write(String.join(",", g.getCharacters().toString()));
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace(); 
        }
    }
    
    public String toCSV(Group g){
        String s = g.getId() + "," + g.getName() + "," + g.getDescription().replaceAll(",", "");
        return s;
    }
    
    /**
     * Load the group
     */
    @Override
    public void load(BufferedReader br) {
        try {
            String line = br.readLine();
            String[] values = line.split(",");
            int id = Integer.parseInt(values[0]);
            Group newGroup = new Group(id, values[1], values[2]);
            groups.add(newGroup);
            
            // Update id count
            if(id >= idCount) idCount = id + 1;
            
            // Characters in groups
            line = br.readLine();
            if(!line.isEmpty()){
                line = line.substring(1, line.length() - 1); 
                values = line.split(", ");
                
                for(String c:values){
                    Character character = charRepo.findCharacter(Integer.parseInt(c));
                    newGroup.getCharacters().add(character);
                    character.getGroups().add(newGroup);
                }
            }
            
            
        } catch(IOException e){
            e.printStackTrace();
        } catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Could not load group");
        }
        
    }

    /**
     * Delete a group
     * @param g group to delete
     */
    @Override
    public void delete(Group g) {
        groups.remove(g);
    }
    
    /**
     * Find a group using the group name
     * @param n name of the group
     * @return Group found
     */
    @Override
    public Group findGroup(String n) {
        for (Group g:getGroups()){
            if (n.equalsIgnoreCase(g.getName())) return g;
        }
        // Group was not found
        System.out.println("Group not found");
        return null;
    }
    
    /**
     * Find a group using the id
     * @param id of the group
     * @return Group found
     */
    @Override
    public Group findGroup(int id) {
        for (Group g:getGroups()){
            if (g.getId() == id) return g;
        }
        // Group was not found
        System.out.println("Group not found");
        return null;
    }

    /**
     * @return the charRepo
     */
    public ICharacterRepository getCharRepo() {
        return charRepo;
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
