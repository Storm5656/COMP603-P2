/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment2.Controllers;

import assignment2.Models.*;
import assignment2.Models.Entities.*;
import assignment2.OCMS;
import javax.swing.JOptionPane;

/**
 *
 * @author ren
 */
public class GroupController {
    private OCMS view;
    private GroupHandler gh;
    private Group selected;
    
    public GroupController(OCMS v){
        view = v;
        gh = new GroupHandler();
        selected = null;
    }
    
    public void updateGroup(){
        String[] inputs = view.getGroupInputs();
        if (selected == null){
            createGroup(inputs);
        }
        else{
            editGroup(inputs);
        }
    }
    
    public Group getGroup(){
        String n = view.getGroupInputs()[0];
        return gh.get(n);
    }
    
    public Group getGroup(String n){
        return gh.get(n);
    }
    
    public String[] getAll(){
        return gh.getAll();
    }
    
    public String[] getCharacters(){
        return gh.getAllCharInGroup(selected);
    }
    
    public String[] getTags(){
        return gh.getTags(selected);
    }
    
    public void deleteGroup(){
        int response = view.showConfirmation();
        if (response == JOptionPane.YES_OPTION) {
            gh.delete(selected);
        }
        else{
            System.out.println("Cancelling deletion");
        }
    }
    
    private void createGroup(String[] inputs){
        Group g = new Group(-1, inputs[0], inputs[1]);
        gh.create(g);
    }
    
    private void editGroup(String[] inputs){
        Group g = new Group(selected.getId(), inputs[0], inputs[1]);
        gh.create(g);
    }

    /**
     * @return the selected group
     */
    public Group getSelected() {
        return selected;
    }

    /**
     * @param selected the group to select
     */
    public void select(Group selected) {
        this.selected = selected;
    }
    
}
