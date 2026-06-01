import assignment2.Models.CharacterHandler;
import assignment2.Models.DatabaseManager;
import assignment2.Models.Entities.CharacterModel;
import assignment2.Models.Entities.Group;
import assignment2.Models.Entities.Tag;
import assignment2.Models.GroupHandler;
import assignment2.Models.TagHandler;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class TagTest {

    private GroupHandler gh;
    private CharacterHandler ch;
    private TagHandler th;

    @BeforeClass
    public static void setUpClass() {
        DatabaseManager.getConnection();
    }

    @AfterClass
    public static void tearDownClass() {
        DatabaseManager.closeConnections();
    }

    @Before
    public void setUp() {
        gh = new GroupHandler();
        ch = new CharacterHandler();
        th = new TagHandler();

        DatabaseManager.clearDatabase();
    }

    @Test
    public void createTagTest() {
        Tag t = new Tag(0, "Test");
        th.create(t);

        Tag result = th.get("Test");

        assertNotNull(result);
        assertEquals("Test", result.getName());
    }

    @Test
    public void addTagToCharTest() {
        CharacterModel c = new CharacterModel(0,"Bob","He/Him",20,"2006-01-01","Human","Farmer","Original description");
        Tag t = new Tag(0, "Test");

        ch.create(c);
        th.create(t);

        c = ch.get("Bob");
        t = th.get("Test");

        assertNotNull(c);
        assertNotNull(t);

        th.addCharTag(c, t);

        String[] tags = ch.getTags(c);

        assertEquals(1, tags.length);
        assertEquals("Test", tags[0]);
    }

    @Test
    public void removeTagFromCharTest() {
        CharacterModel c = new CharacterModel(0,"Bob","He/Him",20,"2006-01-01","Human","Farmer","Original description");
        Tag t = new Tag(0, "Test");

        ch.create(c);
        th.create(t);

        c = ch.get("Bob");
        t = th.get("Test");

        th.addCharTag(c, t);
        th.removeCharTag(c, t);

        assertEquals(0, ch.getTags(c).length);
    }

    @Test
    public void addTagToGroupTest() {
        Group g = new Group(0, "Villagers", "Description");
        Tag t = new Tag(0, "Test");

        gh.create(g);
        th.create(t);

        g = gh.get("Villagers");
        t = th.get("Test");

        assertNotNull(g);
        assertNotNull(t);

        th.addGroupTag(g, t);

        String[] tags = gh.getTags(g);

        assertEquals(1, tags.length);
        assertEquals("Test", tags[0]);
    }

    @Test
    public void removeTagFromGroupTest() {
        Group g = new Group(0, "Villagers", "Description");
        Tag t = new Tag(0, "Test");

        gh.create(g);
        th.create(t);

        g = gh.get("Villagers");
        t = th.get("Test");

        th.addGroupTag(g, t);
        th.removeGroupTag(g, t);

        assertEquals(0, gh.getTags(g).length);
    }

    @Test
    public void deleteTagTest() {
        Tag t = new Tag(0, "Test");

        th.create(t);

        t = th.get("Test");

        assertNotNull(t);

        th.delete(t);

        assertNull(th.get("Test"));
    }
    
    @Test
    public void createDuplicateTag(){
        Tag t = new Tag(0, "Test");
        th.create(t);
        th.create(t);
        
        assertEquals(1, th.getAll().length);
    }
    
    @Test
    public void getNonExistentTagReturnsNull() {
        assertNull(th.get("DoesNotExist"));
    }
    
    @Test
    public void removingNonExistentTagFromCharacterDoesNothing() {  
        CharacterModel c = new CharacterModel(0,"Bob","He/Him",20,"2006-01-01","Human","Farmer","Original description");
        Tag t = new Tag(0, "Test");

        ch.create(c);
        th.create(t);

        c = ch.get("Bob");
        t = th.get("Test");

        th.removeCharTag(c, t);

        assertEquals(0, ch.getTags(c).length);
    }
}