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
public class RelationshipController implements IController{
    private RelationshipService relationship;
    private boolean running;
    private Scanner scan = new Scanner(System.in);
    
    public RelationshipController(ICharacterRepository charRepo){
        relationship = new RelationshipService(charRepo);
    }

    /**
     * @return the relationship
     */
    public RelationshipService getRelationship() {
        return relationship;
    }

    /**
     * @param relationship the relationship to set
     */
    public void setRelationship(RelationshipService relationship) {
        this.relationship = relationship;
    }
    
    /**
     * Display the relationship menu in the console
     */
    @Override
    public void viewMenu(){
        running = true;
        while(running){
            System.out.println("");
            System.out.println("Relationship Menu");
            System.out.println("1) Create a relationship");
            System.out.println("2) View a character's relationships");
            System.out.println("3) Edit a relationship description");
            System.out.println("4) Delete a relationship");
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
                createRelationship();
                break;
            case(2):
                listCharRelationships();
                break;
            case(3):
                editRelationship();
                break;
            case(4):
                deleteRelationship();
                break;
            case(5):
                back();
                break;
        }
    }
    
    /**
     * Get user input to create a relationship between 2 characters
     */
    public void createRelationship(){
        System.out.println("Creating Relationship!");
        
        System.out.println("Select first character");
        
        FileRelationshipRepository charRepo = (FileRelationshipRepository)relationship.getRepo();
        Character c1 = charRepo.getCharRepo().getCharacter();
        
        if (c1 == null) return;
        
        System.out.println("Select second character");
        Character c2 = charRepo.getCharRepo().getCharacter();
        
        if (c2 == null) return;
        if (c1 == c2){
            System.out.println("Cannot form a relationship between the same character");
            return;
        }
        
        System.out.println("Input relationship description:");
        String desc = scan.nextLine();
        relationship.createRelationship(c1, c2, desc);
    }
    
    /**
     * Lists all of the relationships that the selected character is in to the console
     */
    public void listCharRelationships(){
        System.out.println("Select character to list relationships of:");
        FileRelationshipRepository charRepo = (FileRelationshipRepository)relationship.getRepo();
        Character c = charRepo.getCharRepo().getCharacter();
        if (c == null) return;
        
        System.out.println("Listing " + c.getName() + " relationships (Character 1 name & Character 2 name | Description");
        relationship.viewCharRelationships(c);
    }
    
    /**
     * Edit the details of a relationship
     */
    public void editRelationship(){
        Relationship r = relationship.getRepo().getRelationship();
        if (r == null) return;
        
        System.out.println("Editing relationship description!");
        System.out.println("Input new description: ");
        String input = scan.nextLine();
        
        r.setDescription(input);   
    }
    
    /**
     * Delete the relationship between two characters
     */
    public void deleteRelationship(){
        System.out.println("Deleting relationship!");
        Relationship r = relationship.getRepo().getRelationship();
        relationship.getRepo().deleteRelationship(r);
    }
    
    /**
     * Return to main menu
     */
    @Override
    public void back(){
        running = false;
    }
    
}
