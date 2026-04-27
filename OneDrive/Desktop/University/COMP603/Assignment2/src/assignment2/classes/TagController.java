package assignment2.classes;

import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ren
 */
public class TagController implements IController{
    private TagService tag;
    private ICharacterRepository characters;
    private boolean running;
    private Scanner scan = new Scanner(System.in);
    
    public TagController(ICharacterRepository c){
        characters = c;
        tag = new TagService();
    }
    
    /**
     * Display the tag menu in the console
     */
    @Override
    public void viewMenu(){
        running = true;
        while(running){
            System.out.println("");
            System.out.println("Tag Menu");
            System.out.println("1) Create a tag");
            System.out.println("2) Add a tag to character");
            System.out.println("3) Remove a tag from a character");
            System.out.println("4) List all tags");
            System.out.println("5) Back");
            selectOption(); 
        }
    }
    
    /**
     * Select which menu option was chosen by the user
     */
    @Override
    public void selectOption(){
        int selection = 0;
        while(true){
            try{
                selection = scan.nextInt();
                scan.nextLine();
                if (selection <= 5 && selection != 0) break;
                else System.out.println("Input valid selection");
            }catch(Exception e){
                System.out.println("Input valid selection");
                scan.nextLine();
            }
        }
        
        switch(selection){
            case(1):
                createTag();
                break;
            case(2):
                addTag();
                break;
            case(3):
                removeTag();
                break;
            case(4):
                listTags();
                break;
            case(5):
                back();
                break;
        }
    }
    
    public void createTag(){
        System.out.println("Creating tag!");
        
        System.out.println("Input tag name:");
        String name = scan.nextLine();
        getTag().createTag(name);
    }
    
    public void addTag(){
        System.out.println("Adding tag to character!");
        System.out.println("Select tag");
        Tag t = getTag().getRepo().getTag();
        System.out.println("Select character to add tag to");
        Character c = characters.getCharacter();
        
        getTag().addTagToCharacter(c, t);
    }
    
    public void removeTag(){
        System.out.println("Removing tag from character!");
        System.out.println("Select tag");
        Tag t = getTag().getRepo().getTag();
        System.out.println("Select character to remove tag from");
        Character c = characters.getCharacter();
        
        getTag().removeTagFromCharacter(c, t);
    }
    
    public void listTags(){
        System.out.println("Listing all tags (Name | Id)");
        for(Tag t:getTag().getRepo().getTags()){
            System.out.println(t.getName() + " | " + t.getId());
        }
    }
    
    public void back(){
        running = false;
    }

    /**
     * @return the tag
     */
    public TagService getTag() {
        return tag;
    }
}
