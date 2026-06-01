/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment2.Controllers;

import assignment2.Models.Entities.CharacterModel;
import assignment2.Models.Entities.Group;
import assignment2.Models.SearchManager;
import assignment2.OCMS;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ren
 */
public class SearchController {
    private OCMS view;
    private SearchManager sm;
    private String[] chars;
    
    public SearchController(OCMS v){
        view = v;
        sm = new SearchManager();
        chars = view.getCharController().getAll();
    }
    
    public String[] search(){
        String[] inputs = view.getSearchInputs();
        
        
        String name = inputs[0];
        String group = inputs[1];
        List<String> tags = new ArrayList<>();
        
        
        if(!name.equals("")){
            chars = sm.filterName(chars, name);
        }
        if(!group.equals("")){
            chars = sm.filterGroup(chars, group);
        }
        if(inputs.length > 2){
            for(int i = 2; i < inputs.length; i++){
                tags.add(inputs[i]);
            }
            chars = sm.filterTag(chars, tags);
        }
        
        return chars;
    }
    
    public String[] addTagToSearchFilter(){
        String[] inputs = view.getSearchInputs();
        List<String> tags = new ArrayList<>();
        tags.add(view.getCharForSearch());
        
        if (inputs.length < 2){
            return tags.toArray(String[]::new);
        }
        else{
            for(int i = 2; i < inputs.length; i++){
                if(!"".equals(inputs[i])){
                    tags.add(inputs[i]);
                }
            }
            return tags.toArray(String[]::new);
        }
    }
}
