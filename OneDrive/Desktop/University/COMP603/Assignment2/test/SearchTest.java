/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */

import assignment2.Models.CharacterHandler;
import assignment2.Models.DatabaseManager;
import assignment2.Models.Entities.CharacterModel;
import assignment2.Models.Entities.Group;
import assignment2.Models.Entities.Tag;
import assignment2.Models.GroupHandler;
import assignment2.Models.SearchManager;
import assignment2.Models.TagHandler;
import java.util.ArrayList;
import java.util.List;
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
public class SearchTest {
    private SearchManager sm;
    private CharacterHandler ch;
    private GroupHandler gh;
    private TagHandler th;
    
    public SearchTest() {
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
        sm = new SearchManager();
        ch = new CharacterHandler();
        th = new TagHandler();
        gh = new GroupHandler();
        DatabaseManager.clearDatabase();
    }
    
    //Search by name
    @Test
    public void searchByName(){
        CharacterModel c = new CharacterModel(0,"Bob","He/Him",20,"2006-01-01","Human","Farmer","Original description");
        ch.create(c);
        c = new CharacterModel(0,"Charlie", "He/Him", 22, "2004-03-01", "Human", "Farmer", "Original description");
        ch.create(c);
        c = new CharacterModel(0,"Charlotte", "She/Her", 21, "2005-01-01", "Human", "Farmer", "Original description");
        ch.create(c);
        
        String[] chars = sm.filterName(ch.getAll(), "Ch");
        
        assertEquals(2, chars.length);
        assertEquals("Charlie", chars[0]);
        assertEquals("Charlotte", chars[1]);
        
    }
    
    //Search by tag
    @Test
    public void searchByTag(){
        CharacterModel c = new CharacterModel(0,"Bob","He/Him",20,"2006-01-01","Human","Farmer","Original description");
        ch.create(c);
        c = new CharacterModel(0,"Charlie", "He/Him", 22, "2004-03-01", "Human", "Farmer", "Original description");
        ch.create(c);
        c = new CharacterModel(0,"Charlotte", "She/Her", 21, "2005-01-01", "Human", "Farmer", "Original description");
        ch.create(c);
        
        Tag t = new Tag(0,"Test");
        th.create(t);
        t = th.get("Test");
        
        th.addCharTag(ch.get("Bob"), t);
        
        List<String> tags = new ArrayList<>();
        tags.add(t.getName());
        
        String[] chars = sm.filterTag(ch.getAll(), tags);
        
        assertEquals("Bob", chars[0]);
    }
    
    //Search by group
    @Test
    public void searchByGroup(){
        CharacterModel c = new CharacterModel(0,"Bob","He/Him",20,"2006-01-01","Human","Farmer","Original description");
        ch.create(c);
        c = new CharacterModel(0,"Charlie", "He/Him", 22, "2004-03-01", "Human", "Farmer", "Original description");
        ch.create(c);
        c = new CharacterModel(0,"Charlotte", "She/Her", 21, "2005-01-01", "Human", "Farmer", "Original description");
        ch.create(c);
        
        Group g = new Group(0, "Test", "Description");
        gh.create(g);
        
        ch.addCharacterToGroup(ch.get("Bob"), gh.get("Test"));
        
        String[] chars = sm.filterGroup(ch.getAll(), "Test");
        
        assertEquals("Bob", chars[0]);
    }

    //Search by all
    @Test
    public void searchBoth(){
        CharacterModel c = new CharacterModel(0,"Bob","He/Him",20,"2006-01-01","Human","Farmer","Original description");
        ch.create(c);
        c = new CharacterModel(0,"Charlie", "He/Him", 22, "2004-03-01", "Human", "Farmer", "Original description");
        ch.create(c);
        c = new CharacterModel(0,"Charlotte", "She/Her", 21, "2005-01-01", "Human", "Farmer", "Original description");
        ch.create(c);
        
        Group g = new Group(0, "GroupTest", "Description");
        gh.create(g);
        
        ch.addCharacterToGroup(ch.get("Bob"), gh.get("GroupTest"));
        ch.addCharacterToGroup(ch.get("Charlie"), gh.get("GroupTest"));
        
        Tag t = new Tag(0,"TagTest");
        th.create(t);
        t = th.get("TagTest");
        
        th.addCharTag(ch.get("Charlie"), t);
        
        List<String> tags = new ArrayList<>();
        tags.add(t.getName());
        
        String[] chars = ch.getAll();
        
        chars = sm.filterTag(chars, tags);
        chars = sm.filterGroup(chars, "GroupTest");
        
        assertEquals("Charlie", chars[0]);
    }
    
    // Search does not exist
    @Test
    public void searchDoesNotExist(){
        CharacterModel c = new CharacterModel(0,"Bob","He/Him",20,"2006-01-01","Human","Farmer","Original description");
        ch.create(c);
        c = new CharacterModel(0,"Charlie", "He/Him", 22, "2004-03-01", "Human", "Farmer", "Original description");
        ch.create(c);
        c = new CharacterModel(0,"Charlotte", "She/Her", 21, "2005-01-01", "Human", "Farmer", "Original description");
        ch.create(c);
        
        String[] chars = ch.getAll();
        
        chars = sm.filterName(chars, "DoesNotExist");
        
        assertEquals(0, chars.length);
    }
}
