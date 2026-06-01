/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */

import assignment2.Models.CharacterHandler;
import assignment2.Models.DatabaseManager;
import assignment2.Models.Entities.CharacterModel;
import assignment2.Models.Entities.Group;
import assignment2.Models.GroupHandler;
import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ren
 */
public class GroupTest {
    private GroupHandler gh;
    private CharacterHandler ch;
    
    public GroupTest() {
    }
    
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
        gh = new GroupHandler();
        ch = new CharacterHandler();
        DatabaseManager.clearDatabase();
    }

    @Test
    public void createGroupTest(){
        Group g = new Group(0, "TestGroup", "");
        
        gh.create(g);
        Group result = gh.get("TestGroup");
        
        assertNotNull(result);
        assertEquals("TestGroup", result.getName());
    }
    
    @Test
    public void editGroupTest(){
        Group g = new Group(0, "Villagers", "Original description");
        gh.create(g);
        
        Group original = gh.get("Villagers");
        
        Group edited = new Group(original.getId(), "Innocents", "Edited description");
        
        gh.edit(edited);
        
        Group result = gh.get(original.getId());
        
        assertNotNull(result);
        assertEquals("Innocents", result.getName());
        assertEquals("Edited description", result.getDescription());
    }
    
    @Test
    public void addCharToGroupTest(){
        Group g = new Group(0, "Villagers", "Original description");
        gh.create(g);
        
        CharacterModel c = new CharacterModel(0,"Bob","He/Him",20,"2006-01-01","Human","Farmer","Original description");
        ch.create(c);
        
        g = gh.get("Villagers");
        c = ch.get("Bob");
        
        ch.addCharacterToGroup(c, g);
        String[] chars = gh.getAllCharInGroup(g);
        
        assertNotNull(c);
        assertNotNull(g);
        assertEquals("Bob", chars[0]);
    }
    
    @Test
    public void removeCharFromGroupTest(){
        Group g = new Group(0, "Villagers", "Original description");
        gh.create(g);
        
        CharacterModel c = new CharacterModel(0,"Bob","He/Him",20,"2006-01-01","Human","Farmer","Original description");
        ch.create(c);
        
        g = gh.get("Villagers");
        c = ch.get("Bob");
        
        ch.addCharacterToGroup(c, g);
        ch.removeCharacterFromGroup(c, g);
        String[] chars = gh.getAllCharInGroup(g);
        
        if(chars.length != 0){
            fail();
        }
    }
    
    @Test
    public void deleteGroupTest(){
        Group g = new Group(0, "TestGroup", "");
        
        gh.create(g);
        Group result = gh.get("TestGroup");
        
        assertNotNull(result);
        
        gh.delete(result);
        result = gh.get("TestGroup");
        
        assertNull(result);
    }
    
    @Test
    public void getNonExistentGroupReturnsNull() {
        assertNull(gh.get("DoesNotExist"));
    }

    @Test
    public void createDuplicateGroupTest() {
        Group g = new Group(0, "Villagers", "Description");
        
        gh.create(g);
        gh.create(g);

        assertEquals(1, gh.getAll().length);
    }

    @Test
    public void removeCharacterNotInGroupTest() {
        Group g = new Group(0, "Villagers", "Description");
        CharacterModel c = new CharacterModel(0,"Bob","He/Him",20,"2006-01-01","Human","Farmer","Original description");

        gh.create(g);
        ch.create(c);

        g = gh.get("Villagers");
        c = ch.get("Bob");

        ch.removeCharacterFromGroup(c, g);

        assertEquals(0, gh.getAllCharInGroup(g).length);
    }

    @Test
    public void deleteGroupWithMembersTest() {
        Group g = new Group(0, "Villagers", "Description");
        CharacterModel c = new CharacterModel(0,"Bob","He/Him",20,"2006-01-01","Human","Farmer","Original description");

        gh.create(g);
        ch.create(c);

        g = gh.get("Villagers");
        c = ch.get("Bob");

        ch.addCharacterToGroup(c, g);

        gh.delete(g);

        assertNull(gh.get("Villagers"));

        // Character should still exist
        assertNotNull(ch.get("Bob"));
    }

    @Test
    public void emptyGroupHasNoMembersTest() {
        Group g = new Group(0, "Villagers", "Description");

        gh.create(g);

        g = gh.get("Villagers");

        assertEquals(0, gh.getAllCharInGroup(g).length);
    }
}
