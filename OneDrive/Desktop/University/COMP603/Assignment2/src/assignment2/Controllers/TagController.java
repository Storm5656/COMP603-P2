/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment2.Controllers;

import assignment2.Models.*;
import assignment2.Models.Entities.*;
import assignment2.OCMS;

/**
 *
 * @author ren
 */
public class TagController {
    private OCMS view;
    private TagHandler th;
    
    public TagController(OCMS v){
        view = v;
        th = new TagHandler();
    }
    
    public void createTag(){
        String[] inputs = view.getTagInputs();
        th.create(new Tag(-1, inputs[0]));
    }
    
    public Tag getTag(){
        String n = view.getTagInputs()[0];
        return th.get(n);
    }
    
    public Tag getTag(String n){
        return th.get(n);
    }
    
    public String[] getAll(){
        return th.getAll();
    }
    
    public void addTagToChar(){
        String[] inputs = view.getTagInputs();
        if(inputs[2]!= "" && inputs[1] != ""){
            Tag t = getTag(inputs[2]);
            CharacterModel c = view.getCharController().getCharacter(inputs[1]);

            th.addCharTag(c, t);
        }
        else{
            view.showError("Select tag and character");
        }
    }
    
    public void addTagToGroup(){
        String[] inputs = view.getTagInputs();
        if(!"".equals(inputs[3]) && !"".equals(inputs[4])){
            Tag t = getTag(inputs[4]);
            Group g = view.getGroupController().getGroup(inputs[3]);

            th.addGroupTag(g, t);
        }
        else{
            view.showError("Select tag and group");
        }
    }

    public void deleteTag() {
        String[] inputs = view.getTagInputs();
        if(!"".equals(inputs[5])){
            Tag t = getTag(inputs[5]);
            th.delete(t);
        }
    }
}
