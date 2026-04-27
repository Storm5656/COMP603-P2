package assignment2.classes;


import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ren
 */
public class SearchService {
    private ISearchStrategy strat;
    
    public void searchName(List<Character> c){
        strat = new SearchByName(c);
    }
    
    public void searchGroup(List<Character> c, IGroupRepository g){
        strat = new SearchByGroup(c, g);
    }
    
    public void searchTag(List<Character> c){
        strat = new SearchByTag(c);
    }
    
    public void search(){
        strat.getKey();
        showResults(strat.filter());
    }
    
    public void showResults(List<Character> characters){
        System.out.println("Showing search results (Character name | Character ID");
        for(Character c:characters){
            System.out.println(c.getName() + " | " + c.getId());
        }
    }
    
}
