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
public class SearchByGroup implements ISearchStrategy {
    private List<Character> characters;
    private Group key;
    private IGroupRepository groups;
    
    public SearchByGroup(List<Character> c, IGroupRepository g){
        characters = c;
        groups = g;
    }
    
    public void getKey(){
        System.out.println("Search by group: ");
        key = groups.getGroup();
    }
    
    public List<Character> filter(){
        List<Character> result = new ArrayList<Character>();
        for(Character c: characters){
            if (c.getGroups().contains(key)){
                result.add(c);
            }
        }
        
        return result;
    }
}
