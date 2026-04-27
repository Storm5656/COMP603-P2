package assignment2.classes;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ren
 */
public class GroupService {
    private IGroupRepository repo;
    
    public GroupService(ICharacterRepository cRepo){
        repo = new FileGroupRepository(cRepo);
    }

    /**
     * @return the repo
     */
    public IGroupRepository getRepo() {
        return repo;
    }

    /**
     * @param repo the repo to set
     */
    public void setRepo(IGroupRepository repo) {
        this.repo = repo;
    }
    
    /**
     * Create a group and add it to the repo
     * @param name of group
     * @param description for group
     */
    public void createGroup(String name, String description){
        Group newGroup = new Group(repo.getIdCount(), name, description);
        repo.getGroups().add(newGroup);
        System.out.println("Successfully created group");
        repo.setIdCount(repo.getIdCount() + 1);
    }
    
    /**
     * Delete a group
     * @param g group to delete
     */
    public void deleteGroup(Group g){
        if(g != null) {
            repo.delete(g);
            
            // Remove group from all characters
            for(Character c:getRepo().getCharRepo().getCharacters()){
                if(c.getGroups().contains(g)){
                    c.getGroups().remove(g);
                }
            }
            
            
            System.out.println("Successfully deleted group");
        }
        else System.out.println("Could not find group to delete");
    }
    
    /**
     * View group in full detail
     * @param g group to view
     */
    public void viewGroup(Group g){
        if (g == null) return;
        System.out.println("Id: " + g.getId());
        System.out.println("Name: " + g.getName());
        System.out.println("Description: " + g.getDescription());
        System.out.println("----------------------------");
        System.out.println("Character List");
        for(Character c:g.getCharacters()){
            System.out.println(c.getName());
        }
        System.out.println("----------------------------");
    }
    
    /**
     * Update group details
     * @param x index of what to edit
     * @param input What to update to
     * @param g group to update
     */
    public void updateGroup(int x, String input, Group g){
        switch(x){
            //Update name
            case(1):
                g.setName(input);
                break;
            case(2):
                g.setDescription(input);
            default:
                System.out.println("Something went wrong");
        }
    }
    
    public void addCharacter(){
        System.out.println("Select group");
        Group g = repo.getGroup();
        System.out.println("Select character");
        Character c = repo.getCharRepo().getCharacter();
        
        if (g.getCharacters().contains(c)) return;
        g.getCharacters().add(c);
        c.getGroups().add(g);
    }
    public void removeCharacter(){
        System.out.println("Select group");
        Group g = repo.getGroup();
        System.out.println("Select character");
        Character c = repo.getCharRepo().getCharacter();
        
        
        g.getCharacters().remove(c);
        c.getGroups().remove(g);
    }
}
