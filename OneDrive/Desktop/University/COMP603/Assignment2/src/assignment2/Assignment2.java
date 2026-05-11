/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package assignment2;
import javax.swing.*;

/**
 *
 * @author ren
 */
public class Assignment2 {

    /**
     * @param args the command line arguments
     */
    
    // CLEAN ALL OF THIS UP
    public static void main(String[] args) {
        OCMS frame = new OCMS();
        
        // Resize form
        frame.setVisible(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        // Resize properly
        frame.setSplitHeight();
        
        // Set up
        DatabaseManager.establishConnection();
        frame.refreshCharacterList();
        
    
    }
    
}
