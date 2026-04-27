package assignment2.classes;


import java.io.BufferedReader;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

/**
 *
 * @author ren
 */
public interface IRelationshipRepository {
    public ICharacterRepository getCharRepo();
    public List<Relationship> getRelationships();
    public void save(Relationship r, String file);
    public void load(BufferedReader br);
    public void deleteRelationship(Relationship r);
    public List<Relationship> getCharRelationships();
    public List<Relationship> findCharRelationships(Character c);
    public Relationship getRelationship();
    public Relationship findRelationship(Character c1, Character c2);
}
