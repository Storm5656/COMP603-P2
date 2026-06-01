/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import assignment2.Models.CharacterHandler;
import assignment2.Models.DatabaseManager;
import assignment2.Models.Entities.CharacterModel;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.AfterClass;

/**
 *
 * @author ren
 */
public class CharacterTest {
    private CharacterHandler ch;
    
    public CharacterTest() {
    }

    // Establish connection to database and create tables
    @BeforeClass
    public static void setUpClass(){
        DatabaseManager.getConnection();
        
    }
    
    // Close database
    @AfterClass
    public static void tearDownClass(){
        DatabaseManager.closeConnections();
    }
    
    @Before
    public void setUp() {
        ch = new CharacterHandler();
        DatabaseManager.clearDatabase();
    }
    
    @Test
    public void createCharacterTest(){
        CharacterModel c = new CharacterModel(0, "Bob", "", 0, "", "", "", "");
        
        ch.create(c);
        CharacterModel result = ch.get("Bob");
        
        assertNotNull(result);
        assertEquals("Bob", result.getName());
        assertTrue(result.getId() > 0);
    }
    
    @Test
    public void editCharacterTest(){
        // Create initial character
        CharacterModel c = new CharacterModel(0,"Bob","He/Him",20,"2006-01-01","Human","Farmer","Original description");
        ch.create(c);

        // Get the created character so we have the correct ID
        CharacterModel original = ch.get("Bob");

        // Create edited version using same ID
        CharacterModel edited = new CharacterModel(original.getId(),"Robert","He/Him",25,"2001-01-01","Elf","Wizard","Updated description");

        ch.edit(edited);

        // Verify changes
        CharacterModel result = ch.get(original.getId());

        assertNotNull(result);
        assertEquals("Robert", result.getName());
        assertEquals(original.getId(), result.getId());
        assertEquals(25, result.getAge());
        assertEquals("Elf", result.getSpecies());
        assertEquals("Wizard", result.getOccupation());
        assertEquals("Updated description", result.getDescription());
    }
    
    @Test
    public void deleteCharacterTest(){
        CharacterModel c = new CharacterModel(0,"Bob","He/Him",20,"2006-01-01","Human","Farmer","Original description");
        ch.create(c);
        
        CharacterModel character = ch.get("Bob");
        
        assertNotNull(character);
        
        ch.delete(character);
        
        assertNull(ch.get("Bob"));
    }
    
    @Test
    public void createDuplicateChar(){
        CharacterModel c = new CharacterModel(0,"Bob","He/Him",20,"2006-01-01","Human","Farmer","Original description");
        ch.create(c);
        ch.create(c);
        
        assertNotNull(ch.get("Bob"));
        
        assertEquals(1, ch.getAll().length);
    }
    
    @Test
    public void getNonExistantCharEqualsNull(){
        assertNull(ch.get("DoesNotExist"));
    }
}
