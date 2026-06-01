/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */

import assignment2.Models.CharacterHandler;
import assignment2.Models.DatabaseManager;
import assignment2.Models.Entities.CharacterModel;
import assignment2.Models.Entities.Relationship;
import assignment2.Models.RelationshipHandler;
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
public class RelationshipTest {
    private RelationshipHandler rh;
    private CharacterHandler ch;
    
    public RelationshipTest() {
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
        rh = new RelationshipHandler();
        DatabaseManager.clearDatabase();
    }
    
    @Test
    public void createRelationship(){
        CharacterModel c1 = new CharacterModel(0,"Bob","He/Him",20,"2006-01-01","Human","Farmer","Original description");
        CharacterModel c2 = new CharacterModel(0,"Charlie", "He/Him", 22, "2004-03-01", "Human", "Farmer", "Original description");
        ch.create(c1);
        ch.create(c2);
        c1 = ch.get("Bob");
        c2 = ch.get("Charlie");
        
        Relationship r = new Relationship(0, c1, c2, "Younger brother", "Older brother", "Description");
        rh.create(r);
        r = rh.get("Bob & Charlie");
        
        assertNotNull(r);
        assertEquals("Bob", r.getChar1().getName());
        assertEquals("Charlie", r.getChar2().getName());
    }
    
    @Test
    public void editRelationship(){
        CharacterModel c1 = new CharacterModel(0,"Bob","He/Him",20,"2006-01-01","Human","Farmer","Original description");
        CharacterModel c2 = new CharacterModel(0,"Charlie", "He/Him", 22, "2004-03-01", "Human", "Farmer", "Original description");
        ch.create(c1);
        ch.create(c2);
        c1 = ch.get("Bob");
        c2 = ch.get("Charlie");
        
        Relationship original = new Relationship(0, c1, c2, "Younger brother", "Older brother", "Original description");
        rh.create(original);
        original = rh.get("Bob & Charlie");
        
        Relationship edited = new Relationship(original.getId(), c1, c2, "Friend", "Best friend", "Edited description");
        rh.edit(edited);
        edited = rh.get("Bob & Charlie");
        
        assertNotNull(edited);
        assertEquals("Friend", edited.getDynamic1());
        assertEquals("Best friend", edited.getDynamic2());
        assertEquals("Edited description", edited.getDescription());
    }
    
    @Test
    public void deleteRelationship(){
        CharacterModel c1 = new CharacterModel(0,"Bob","He/Him",20,"2006-01-01","Human","Farmer","Original description");
        CharacterModel c2 = new CharacterModel(0,"Charlie", "He/Him", 22, "2004-03-01", "Human", "Farmer", "Original description");
        ch.create(c1);
        ch.create(c2);
        c1 = ch.get("Bob");
        c2 = ch.get("Charlie");
        
        Relationship r = new Relationship(0, c1, c2, "Younger brother", "Older brother", "Description");
        rh.create(r);
        r = rh.get("Bob & Charlie");
        rh.delete(r);
        
        
        assertNull(rh.get("Bob & Charlie"));
    }
    
    @Test
    public void duplicateRelationship(){
        CharacterModel c1 = new CharacterModel(0,"Bob","He/Him",20,"2006-01-01","Human","Farmer","Original description");
        CharacterModel c2 = new CharacterModel(0,"Charlie", "He/Him", 22, "2004-03-01", "Human", "Farmer", "Original description");
        ch.create(c1);
        ch.create(c2);
        c1 = ch.get("Bob");
        c2 = ch.get("Charlie");
        
        Relationship r = new Relationship(0, c1, c2, "Younger brother", "Older brother", "Description");
        rh.create(r);
        rh.create(r);
        
        assertEquals(1, rh.getCharRelationships(c1).length);
    }
    
    @Test
    public void createSelfRelationship(){
        CharacterModel c1 = new CharacterModel(0,"Bob","He/Him",20,"2006-01-01","Human","Farmer","Original description");
        ch.create(c1);
        c1 = ch.get("Bob");
        
        Relationship r = new Relationship(0, c1, c1, "Younger brother", "Older brother", "Description");
        rh.create(r);
        
        assertNull(rh.get("Bob & Bob"));
    }
    
    @Test
    public void getNonExistantReturnsNull(){
        Relationship r = rh.get("DoesNotExist & AlsoDoesNotExist");
        
        assertNull(r);
    }
}
