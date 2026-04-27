package assignment2.classes;

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
public class SearchController implements IController{
    private SearchService service;
    private boolean running;
    private Scanner scan = new Scanner(System.in);
    private ICharacterRepository characters;
    private IGroupRepository groups;
    
    public SearchController(ICharacterRepository c, IGroupRepository g){
        service = new SearchService();
        characters = c;
        groups = g;
    }
    
    /**
     * Display the search menu in the console
     */
    @Override
    public void viewMenu(){
        running = true;
        while(running){
            System.out.println("");
            System.out.println("Search Menu");
            System.out.println("1) Search by name");
            System.out.println("2) Filter by tag");
            System.out.println("3) Filter by group");
            System.out.println("4) Back");
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
                if (selection <= 8 && selection != 0) break;
                else System.out.println("Input valid selection");
            }catch(Exception e){
                System.out.println("Input valid selection");
                scan.nextLine();
            }
        }
        
        switch(selection){
            case(1):
                searchName(characters.getCharacters());
                break;
            case(2):
                filterTag(characters.getCharacters());
                break;
            case(3):
                filterGroup(characters.getCharacters());
                break;
            case(4):
                back();
                break;
        }
    }
    
    public void searchName(List<Character> c){
        service.searchName(c);
        service.search();
    }
    
    public void filterTag(List<Character> c){
        service.searchTag(c);
        service.search();
    }
     
    public void filterGroup(List<Character> c){
        service.searchGroup(c, groups);
        service.search();
    }
    
    /**
     * Return to main menu
     */
    @Override
    public void back(){
        running = false;
    }
}
