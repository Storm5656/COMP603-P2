package assignment2.classes;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * @author ren
 */
public class CharacterService {
    private ICharacterRepository repo;
    
    public CharacterService(){
        repo = new FileCharacterRepository();
    }

    /**
     * @return the repo
     */
    public ICharacterRepository getRepo() {
        return repo;
    }

    /**
     * @param repo the repo to set
     */
    public void setRepo(ICharacterRepository repo) {
        this.repo = repo;
    }
    
    /**
     * Create a character and add it to the repo
     * @param name name of character
     * @param pronouns pronouns of character
     * @param age age of character
     * @param dob date of birth of character
     * @param species species of character
     * @param occupation occupation of character
     */
    public void createCharacter(String name, String pronouns, int age, String dob, String species, String occupation){
        Character newChar = new Character(repo.getIdCount(), name, pronouns, age, dob, species, occupation);
        repo.getCharacters().add(newChar);
        System.out.println("Successfully created character");
        repo.setIdCount(repo.getIdCount() + 1);
    }
    
    /**
     * Delete a character
     * @param c character to delete
     */
    public void deleteCharacter(Character c){
        if(c != null){
            repo.delete(c);
            System.out.println("Successfully deleted character");
        }
        else System.out.println("Could not find character to remove");
    }
    
    /**
     * View character in full detail
     * @param c character to view
     */
    public void viewCharacter(Character c){
        if (c == null) return;
        System.out.println("Id: " + c.getId());
        System.out.println("Name: " + c.getName());
        System.out.println("Pronouns: " + c.getPronouns());
        System.out.println("Age: " + c.getAge());
        System.out.println("Date of birth: " + c.getDob());
        System.out.println("Species: " + c.getSpecies());
        System.out.println("Occupation: " + c.getOccupation());
        System.out.println("----------------------------");
        System.out.println("Groups:");
        for(Group g:c.getGroups()){
            System.out.println(g.getName());
        }
        System.out.println("----------------------------");
        System.out.println("Tags:");
        for(Tag t:c.getTags()){
            System.out.println(t.getName());
        }
        System.out.println("----------------------------");
    }
    
    /**
     * Update character details
     * @param x index of what to update
     * @param input 
     */
    public void updateCharacter(int x, String input, Character c){
        switch(x){
            // Update name
            case(1):
                c.setName(input);
                break;
            // Update pronouns
            case(2):
                c.setPronouns(input);
                break;
            // Update age
            case(3):
                try{
                    int age = Integer.parseInt(input);
                    c.setAge(age);
                }catch(Exception e){
                    System.out.println("Could not update age, input not valid");
                }
                break;
            // Update dob
            case(4):
                c.setDob(input);
                break;
            // Update species
            case(5):
                c.setSpecies(input);
                break;
            // Update occupation
            case(6):
                c.setOccupation(input);
                break;
            default:
                System.out.println("Something went wrong");
        }
    }
}

