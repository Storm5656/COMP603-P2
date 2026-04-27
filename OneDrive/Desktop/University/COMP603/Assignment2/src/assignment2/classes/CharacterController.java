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
public class CharacterController implements IController{
    private CharacterService character;
    private boolean running;
    private Scanner scan = new Scanner(System.in);
    
    public CharacterController(){
        character = new CharacterService();
    }
    
    /**
     * @return the character service
     */
    public CharacterService getCharacter() {
        return character;
    }

    /**
     * @param character the character service to set
     */
    public void setCharacter(CharacterService character) {
        this.character = character;
    }
    
    /**
     * Display the character menu in the console
     */
    @Override
    public void viewMenu(){
        running = true;
        while(running){
            System.out.println("");
            System.out.println("Character Menu");
            System.out.println("1) Create a character");
            System.out.println("2) List all characters");
            System.out.println("3) View a character");
            System.out.println("4) Edit a character");
            System.out.println("5) Delete a character");
            System.out.println("6) Back");
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
                if (selection <= 6 && selection != 0) break;
                else System.out.println("Input valid selection");
            }catch(Exception e){
                System.out.println("Input valid selection");
                scan.nextLine();
            }
        }
        
        switch(selection){
            case(1):
                createCharacter();
                break;
            case(2):
                listCharacters();
                break;
            case(3):
                viewCharacter();
                break;
            case(4):
                editCharacter();
                break;
            case(5):
                deleteCharacter();
                break;
            case(6):
                back();
                break;
        }
    }
    
    /**
     * Create a character based on the users input
     */
    public void createCharacter(){
        System.out.println("Creating Character!");
        
        System.out.println("Input character name:");
        String name = scan.nextLine();
        System.out.println("Input character pronouns:");
        String pronouns = scan.nextLine();
        System.out.println("Input character age:");
        int age;
        
        while(true){
            try{
                age = scan.nextInt();
                scan.nextLine();
                break;
            }
            catch(Exception e){
                System.out.println("Input valid integer for character age");
                scan.nextLine();
            }
        }
        
        System.out.println("Input character dob:");
        String dob = scan.nextLine();
        System.out.println("Input character species:");
        String species = scan.nextLine();
        System.out.println("Input character occupation:");
        String occupation = scan.nextLine();
        
        character.createCharacter(name, pronouns, age, dob, species, occupation);
    }
    
    /**
     * List the name and id of every character
     */
    public void listCharacters(){
        System.out.println("Listing all characters (name | id)");
        for(Character c:character.getRepo().getCharacters()){
            System.out.println(c.getName() + " | " + c.getId());
        }
    }
    
    /**
     * Shows in depth character details
     */
    public void viewCharacter(){
        System.out.println("Viewing character details!");
        Character c = character.getRepo().getCharacter();
        character.viewCharacter(c);
    }
    
    /**
     * Edits details of a character
     */
    public void editCharacter(){
        Character c = character.getRepo().getCharacter();
        
        if (c == null) return;
        
        System.out.println("Editing character!");
        System.out.println("1) Edit Name");
        System.out.println("2) Edit Pronouns");
        System.out.println("3) Edit Age");
        System.out.println("4) Edit Date of birth");
        System.out.println("5) Edit Species");
        System.out.println("6) Edit Occupation");
        
        int x;
        
        while(true)
        {
            try{
                x = scan.nextInt();
                scan.nextLine();
                if(x < 7 && x != 0) break;
                else System.out.println("Input valid selection");
            }catch(Exception e){
                System.out.println("Input valid selection");
                scan.nextLine(); // clear invalid input
            }
        }
        
        System.out.println("Input edit:");
        String input = scan.nextLine();
        
        character.updateCharacter(x, input, c);
    }
    
    /**
     * Delete a character
     */
    public void deleteCharacter(){
        System.out.println("Deleting character!");
        Character c = character.getRepo().getCharacter();
        character.deleteCharacter(c);
    }
    
    /**
     * Return to main menu
     */
    @Override
    public void back(){
        running = false;
    }
}
