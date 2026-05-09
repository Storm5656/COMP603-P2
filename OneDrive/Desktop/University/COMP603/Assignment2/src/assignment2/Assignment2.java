/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package assignment2;
import assignment2.classes.*;
import javax.swing.*;

/**
 *
 * @author ren
 */
public class Assignment2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MainController menu = new MainController();
        OCMS frame = new OCMS();
        
        System.out.println(System.getProperty("user.dir"));
        
        // Resize form
        frame.setVisible(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        // Resize properly
        frame.setSplitHeight();
        
        
        DatabaseManager.establishConnection();
    }
    
}
