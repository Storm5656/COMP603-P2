package assignment2.classes;

import java.util.ArrayList;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ren
 */
public class SearchByTag implements ISearchStrategy{
    private List<Character> characters;
    private Tag key;
    
    public SearchByTag(List<Character> c){
        characters = c;
    }
    
    public void getKey(){
        System.out.println("Search by tag: ");
        System.out.println("TO DO");
    }
    
    public List<Character> filter(){
        List<Character> result = new ArrayList<>();
        
        for(Character c:characters){
            if(c.getTags().contains(key)) {
                result.add(c);
            }
        }
        return result;
    }
}
