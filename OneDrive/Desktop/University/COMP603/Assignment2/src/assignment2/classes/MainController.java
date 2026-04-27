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
public class MainController implements IController {
    private CharacterController c = new CharacterController();
    private GroupController g = new GroupController(c.getCharacter().getRepo());
    private RelationshipController r = new RelationshipController(c.getCharacter().getRepo());
    private SearchController s = new SearchController(c.getCharacter().getRepo(), g.getGroup().getRepo());
    private TagController t = new TagController(c.getCharacter().getRepo());
    private Save saveFile = new Save(c.getCharacter().getRepo(), g.getGroup().getRepo(), t.getTag().getRepo(), r.getRelationship().getRepo());
    
    public MainController(){
        c.getCharacter().getRepo().setTagRepo(t.getTag().getRepo());
        saveFile.load();
    }
    
    /**
     * @return the c
     */
    public CharacterController getC() {
        return c;
    }

    /**
     * @param c the c to set
     */
    public void setC(CharacterController c) {
        this.c = c;
    }
    
    @Override
    public void viewMenu(){
        while(true){
            System.out.println("");
            System.out.println("Main Menu");
            System.out.println("1) Character Menu");
            System.out.println("2) Group Menu");
            System.out.println("3) Relationship Menu");
            System.out.println("4) Tags Menu");
            System.out.println("5) Search Menu");
            System.out.println("6) Exit");
            selectOption();
        }
    }
    
    @Override
    public void selectOption(){
        Scanner scan = new Scanner(System.in);
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
                characterMenu();
                break;
            case(2):
                groupMenu();
                break;
            case(3):
                relationshipMenu();
                break;
            case(4):
                tagMenu();
                break;
            case(5):
                searchMenu();
                break;
            case(6):
                back();
                break;
        }
    }
    
    @Override
    public void back(){
        saveFile.save();
        System.exit(0);
    }
    
    public void characterMenu(){
        c.viewMenu();
    }
    
    public void groupMenu(){
        g.viewMenu();
    }
    
    public void relationshipMenu(){
        r.viewMenu();
    }
    
    public void searchMenu(){
        s.viewMenu();
    }
    
    public void tagMenu(){
        t.viewMenu();
    }
}
