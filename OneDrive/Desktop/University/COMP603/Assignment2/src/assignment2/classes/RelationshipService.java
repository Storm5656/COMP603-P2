package assignment2.classes;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ren
 */
public class RelationshipService{
    private IRelationshipRepository repo;
    
    public RelationshipService(ICharacterRepository charRepo){
        repo = new FileRelationshipRepository(charRepo);
    }

    /**
     * @return the repo
     */
    public IRelationshipRepository getRepo() {
        return repo;
    }

    /**
     * @param repo the repo to set
     */
    public void setRepo(IRelationshipRepository repo) {
        this.repo = repo;
    }
    
    /**
     * Create a relationship and add it to the repo
     * @param c1 First character
     * @param c2 Second character
     * @param description Description 
     */
    public void createRelationship(Character c1, Character c2, String description){
        Relationship newRel = new Relationship(c1, c2, description);
        repo.getRelationships().add(newRel);
        c1.getRelationships().add(newRel);
        c2.getRelationships().add(newRel);
        System.out.println("Successfully created relationship");
    }
    
    /**
     * View all relationships involving a given character
     * @param c Character
     */
    public void viewCharRelationships(Character c){
        if (c == null) return;
        for(Relationship r:repo.findCharRelationships(c)){
            if (r != null){
                if (r.getChar1() == c){
                    System.out.println(c.getName() + " & " + r.getChar2().getName() + " | " + r.getDescription());
                } else System.out.println(c.getName() + " & " + r.getChar1().getName() + " | " + r.getDescription());  
            }      
        }
    }
    
    /**
     * View the details of a single relationship
     * @param r Relationship
     */
    public void viewRelationship(Relationship r){
        System.out.println("Relationship");
        System.out.println("First Character: " + r.getChar1());
        System.out.println("Second Character: " + r.getChar2());
        System.out.println("Description: " + r.getDescription());
    }
}
