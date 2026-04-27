package assignment2.classes;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ren
 */
public class Save {
    private ICharacterRepository cRepo;
    private IGroupRepository gRepo;
    private ITagRepository tRepo;
    private IRelationshipRepository rRepo;
    private String file;
    
    public Save(ICharacterRepository c, IGroupRepository g, ITagRepository t, IRelationshipRepository r){
        cRepo = c;
        gRepo = g;
        tRepo = t;
        rRepo = r;
        file = "./resources/saveFile.txt";
    }
    
    public void save(){
        try {
            FileWriter writer = new FileWriter(file, false); 
            writer.write("Save File \n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        saveTags();
        saveCharacters();
        saveGroups();
        saveRelationships();
    }
    public void load(){
        try (BufferedReader br = new BufferedReader(new FileReader(file))){
            String line = br.readLine();
            
            while ((line = br.readLine()) != null){
                switch (line){
                case "c":
                    cRepo.load(br);
                    break;
                case "g":
                    gRepo.load(br);
                    break;
                case "t":
                    tRepo.load(br);
                    break;
                case "r":
                    rRepo.load(br);
                    break;
                }
            }
        }
        catch(IOException e){
            
        }
    }
    
    
    private void saveCharacters(){
        System.out.println("CHARACTER SAVES");
        for(Character c:cRepo.getCharacters()){
            cRepo.save(c, file);
        }
        
    }
    
    private void saveGroups(){
        System.out.println("GROUP SAVES");
        for(Group g:gRepo.getGroups()){
            gRepo.save(g, file);
        }
    }
    
    private void saveTags(){
        System.out.println("TAG SAVES");
        for(Tag t:tRepo.getTags()){
            tRepo.save(t, file);
        }
    }
    
    private void saveRelationships(){
        System.out.println("RELATIONSHIP SAVES");
        for(Relationship r:rRepo.getRelationships()){
            rRepo.save(r, file);
        }
    }
}
