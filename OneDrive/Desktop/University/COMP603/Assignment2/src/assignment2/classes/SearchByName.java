package assignment2.classes;

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
public class SearchByName implements ISearchStrategy {
    private List<Character> characters;
    private String key;
    
    public SearchByName(List<Character> c){
        characters = c;
    }
    
    public void getKey(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Search by name: ");
        key = scan.nextLine();
    }
    
    public List<Character> filter(){
        List<Character> result = new ArrayList<Character>();
        
        if (key.length() > 1){
            result = characters.stream()
                    .filter(c -> c.getName().toLowerCase().contains(key.toLowerCase()))
                    .toList(); 
        } else{
            result = characters.stream()
                    .filter(c -> c.getName().toLowerCase().startsWith(key.toLowerCase()))
                    .toList();
        }
        return result;
    }
}
