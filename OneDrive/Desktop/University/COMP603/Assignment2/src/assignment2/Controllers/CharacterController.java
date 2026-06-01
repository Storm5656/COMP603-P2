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
public class CharacterController {
    private OCMS view;
    private CharacterHandler ch;
    private CharacterModel selected;
    
    public CharacterController(OCMS v){
        view = v;
        ch = new CharacterHandler();
        selected = null;
    }
    
    public void updateCharacter(){
        String[] inputs = view.getCharInputs();
        if (selected == null){
            createCharacter(inputs);
        }
        else{
            editCharacter(inputs);
        }
    }
    
    public CharacterModel getCharacter(){
        String n = view.getCharInputs()[0];
        return ch.get(n);
    }
    
    public CharacterModel getCharacter(String n){
        if(n == null) return null;
        return ch.get(n);
    }
    
    public void manageGroup(){
        CharacterModel c = ch.get(view.getCharAddToGroup());
        
        //If character is already in group, remove from group
        GroupHandler gh = new GroupHandler();
        Group g = view.getGroupController().getSelected();
        String[] groups = ch.getGroups(c);
        
        if (g == null){
            view.showError("Please select a group before adding characters");
            return;
        }
        
        for (String group : groups) {
            if (group.equals(g.getName())) {
                ch.removeCharacterFromGroup(c, g);
                return;
            }
        }
        // Else add the character to the group
        ch.addCharacterToGroup(c, g);
    }
    
    public String[] getAll(){
        return ch.getAll();
    }
    
    private void createCharacter(String[] inputs){
        int a = 0;
        try{
            a = Integer.parseInt(inputs[2]);
        } catch (NumberFormatException ex){
            view.showError("Age is not a valid integer, setting age to 0");
        }
        CharacterModel c = new CharacterModel(-1, inputs[0], inputs[1], a, inputs[3], inputs[4], inputs[5], inputs[6]);
        ch.create(c);
    }
    
    private void editCharacter(String[] inputs){
        int a = 0;
        try{
            a = Integer.parseInt(inputs[2]);
        } catch (NumberFormatException ex){
            view.showError("Age is not a valid integer, setting age to 0");
        }
        CharacterModel c = new CharacterModel(selected.getId(), inputs[0], inputs[1], a, inputs[3], inputs[4], inputs[5], inputs[6]);
        ch.edit(c);
    }
    
    public String[] getGroups(){
        return ch.getGroups(selected);
    }
    
    public String[] getRelationships(){
        RelationshipHandler rh = new RelationshipHandler();
        return rh.getCharRelationships(selected);
    }
    
    public String[] getTags(){
        return ch.getTags(selected);
    }
    
    public void deleteChar(){
        int response = view.showConfirmation();
        if (response == JOptionPane.YES_OPTION) {
            ch.delete(selected);
        }
        else{
            System.out.println("Cancelling deletion");
        }
    }

    /**
     * @return the selected character
     */
    public CharacterModel getSelected() {
        return selected;
    }

    /**
     * @param selected the character to select
     */
    public void select(CharacterModel selected) {
        this.selected = selected;
    }
}
