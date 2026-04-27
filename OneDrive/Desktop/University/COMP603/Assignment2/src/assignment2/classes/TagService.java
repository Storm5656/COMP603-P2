package assignment2.classes;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ren
 */
public class TagService {
    private ITagRepository repo = new FileTagRepository();
    
    
    public void createTag(String name){
        Tag newTag = new Tag(repo.getIdCount(), name);
        getRepo().getTags().add(newTag);
        System.out.println("Successfully created tag");
        repo.setIdCount(newTag.getId() + 1);
    }
    public void addTagToCharacter(Character c, Tag t){
        c.getTags().add(t);
        System.out.println("Tag added to character");
    }

    public void removeTagFromCharacter(Character c, Tag t){
        if(c.getTags().contains(t)){
            c.getTags().remove(t);
            System.out.println("Tag successfully removed");
        }
        else System.out.println("Character does not have this tag assigned");
    }

    /**
     * @return the repo
     */
    public ITagRepository getRepo() {
        return repo;
    }
}
