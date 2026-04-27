package assignment2.classes;


import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ren
 */
public class GroupController implements IController{
    private GroupService group;
    private boolean running;
    private Scanner scan = new Scanner(System.in);
    
    public GroupController(ICharacterRepository cRepo){
        group = new GroupService(cRepo);
    }

    /**
     * @return the group
     */
    public GroupService getGroup() {
        return group;
    }

    /**
     * @param group the group to set
     */
    public void setGroup(GroupService group) {
        this.group = group;
    }
    
    /**
     * Display the group menu in the console
     */
    @Override
    public void viewMenu(){
        running = true;
        while(running){
            System.out.println("");
            System.out.println("Group Menu");
            System.out.println("1) Create a group");
            System.out.println("2) List all groups");
            System.out.println("3) View a group");
            System.out.println("4) Edit a group");
            System.out.println("5) Add character to group");
            System.out.println("6) Remove character from group");
            System.out.println("7) Delete a group");
            System.out.println("8) Back");
            selectOption();
        }
    }
    
    /**
     * Select which menu option was chosen by the user
     */
    @Override
    public void selectOption(){
        int selection = 0;
        while(true){
            try{
                selection = scan.nextInt();
                scan.nextLine();
                if (selection <= 8 && selection != 0) break;
                else System.out.println("Input valid selection");
            }catch(Exception e){
                System.out.println("Input valid selection");
                scan.nextLine();
            }
        }
        
        switch(selection){
            case(1):
                createGroup();
                break;
            case(2):
                listGroups();
                break;
            case(3):
                viewGroup();
                break;
            case(4):
                editGroup();
                break;
            case(5):
                addToGroup();
                break;
            case(6):
                removeFromGroup();
                break;
            case(7):
                deleteGroup();
                break;
            case(8):
                back();
                break;
        }
    }
    
    /**
     * Create a group based on the users input
     */
    public void createGroup(){
        System.out.println("Creating Group!");
        
        System.out.println("Input group name:");
        String name = scan.nextLine();
        System.out.println("Input group description:");
        String desc = scan.nextLine();
        
        group.createGroup(name, desc);
    }
    
    /**
     * List the name and id of every group
     */
    public void listGroups(){
        System.out.println("Listing all groups (name | id)");
        for(Group g:group.getRepo().getGroups()){
            System.out.println(g.getName() + " | " + g.getId());
        }
    }
    
    /**
     * Shows in depth group details
     */
    public void viewGroup(){
        System.out.println("Viewing group details!");
        Group g = group.getRepo().getGroup();
        group.viewGroup(g);
    }
    
    /**
     * Edits the details of a group
     */
    public void editGroup(){
        Group g = group.getRepo().getGroup();
        
        if (g == null) return;
        
        System.out.println("Editing group!");
        System.out.println("1) Edit Name");
        System.out.println("2) Edit Description");
        
        int x;
        
         while(true)
        {
            try{
                x = scan.nextInt();
                scan.nextLine();
                if(x == 1 || x == 2) break;
                else System.out.println("Input valid selection");
            }catch(Exception e){
                System.out.println("Input valid selection");
                scan.nextLine(); // clear invalid input
            }
        }
        
        System.out.println("Input edit:");
        String input = scan.nextLine();
        
        group.updateGroup(x, input, g);
    }
    
    /**
     * User selects group and character
     * Character is added to group
     */
    public void addToGroup(){
        System.out.println("Adding character to group!");
        group.addCharacter();
    }
    
    /**
     * User selects group and character
     * Character is removed from group
     */
    public void removeFromGroup(){
        Group g = group.getRepo().getGroup();
        System.out.println("Select character to remove from group");
        group.removeCharacter();
    }
    
     /**
     * Delete a group
     */
    public void deleteGroup(){
        System.out.println("Deleting group!");
        Group g = group.getRepo().getGroup();
        
        group.deleteGroup(g);
    }
    
    /**
     * Return to main menu
     */
    @Override
    public void back(){
        running = false;
    }
}
