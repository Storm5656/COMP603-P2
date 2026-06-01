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
public class RelationshipController {
    private OCMS view;
    private RelationshipHandler rh;
    private Relationship selectedRel;
    private CharacterModel selectedChar;
    
    public RelationshipController(OCMS v){
        view = v;
        rh = new RelationshipHandler();
        selectedRel = null;
        selectedChar = null;
    }
    
    public void updateRelationship(){
        String[] inputs = view.getRelInputs();
        if (selectedRel == null){
            createRelationship(inputs);
        }
        else{
            editRelationship(inputs);
        }
    }
    
    private void createRelationship(String[] inputs){
        CharacterModel c1 = view.getCharController().getCharacter(inputs[0]);
        CharacterModel c2 = view.getCharController().getCharacter(inputs[1]);
        System.out.println(c1.getName() + " " + c1.getId());
        System.out.println(c2.getName() + " " + c2.getId());
        
        if(c1 != c2 && c1 != null && c2 != null){
            Relationship r = new Relationship(-1, c1, c2, inputs[2], inputs[3], inputs[4]);
            rh.create(r);
        }
        else{
            view.showError("Select two different characters for the relationship");
        }
    }
    
    private void editRelationship(String[] inputs){
        CharacterModel c1 = view.getCharController().getCharacter(inputs[0]);
        CharacterModel c2 = view.getCharController().getCharacter(inputs[1]);
        if(c1 != c2){
            Relationship r = new Relationship(selectedRel.getId(), c1, c2, inputs[2], inputs[3], inputs[4]);
            rh.edit(r);
        }
        else{
            view.showError("Select two different characters for the relationship");
        }
    }
    
    public void deleteRel(){
        int response = view.showConfirmation();
        if (response == JOptionPane.YES_OPTION) {
            rh.delete(selectedRel);
        }
        else{
            System.out.println("Cancelling deletion");
        }
    }
    
    public Relationship getRelationship(String n){
        if (n == null){
            return null;
        }
        return rh.get(n);
    }
    
    public String[] getRelationships(){
        if(selectedChar != null){
            return rh.getCharRelationships(selectedChar);
        }
        else{
            return new String[0];
        }
    }
    
    /**
     * @return the selected relationship
     */
    public Relationship getSelectedRel() {
        return selectedRel;
    }

    /**
     * @param selectedRel the relationship to select
     */
    public void selectRel(Relationship selectedRel) {
        this.selectedRel = selectedRel;
    }

    /**
     * @return the selected character
     */
    public CharacterModel getSelectedChar() {
        return selectedChar;
    }

    /**
     * @param selectedChar the character to select
     */
    public void selectChar(CharacterModel selectedChar) {
        this.selectedChar = selectedChar;
    }
    
    public void selectChar(String n){
        CharacterModel c = view.getCharController().getCharacter(n);
        this.selectedChar = c;
    }
}
