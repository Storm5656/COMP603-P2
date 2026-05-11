package assignment2;
import java.sql.*;
/**
 *
 * @author ren
 */
public class DatabaseManager {
    private static String dbPath = System.getProperty("user.home") + "/AppData/Roaming/CharacterManager/db";
    private static final String URL = "jdbc:derby:" + dbPath + ";create=true";
    private static Connection conn;
    
    public static Connection getConnection() {
        return conn;
    }
    
    public static void establishConnection() {
        //Establish a connection to Database
        try{
            conn=DriverManager.getConnection(URL);
            setupDatabase();

        } catch (SQLException ex){
            ex.printStackTrace();
        }
        
    }
    
    public static void closeConnections() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    
    public static void setupDatabase(){
        try (Statement s = conn.createStatement()){
        
            // Create character table
            s.executeUpdate("CREATE TABLE CHARACTERS" +
                    "(CHAR_ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY," +
                    "NAME VARCHAR(100) NOT NULL UNIQUE," +
                    "PRONOUNS VARCHAR(100)," +
                    "DOB VARCHAR(20)," +
                    "AGE INT," +
                    "SPECIES VARCHAR(100)," +
                    "OCCUPATION VARCHAR(100)," +
                    "DESCRIPTION VARCHAR(1500))");
            
            // Create group table
            s.executeUpdate("CREATE TABLE USER_GROUPS" +
                    "(GROUP_ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
                    + "NAME VARCHAR(100) NOT NULL,"
                    + "DESCRIPTION VARCHAR(1500))");
            
            // Create tag table
            s.executeUpdate("CREATE TABLE TAGS"
                    + "(TAG_ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
                    + "NAME VARCHAR(100) NOT NULL UNIQUE)");
            
            // Create relationship table
            s.executeUpdate("CREATE TABLE RELATIONSHIPS"
                    + "(REL_ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
                    + "CHAR1_ID INT NOT NULL,"
                    + "CHAR2_ID INT NOT NULL,"
                    + "DYNAMIC1 VARCHAR(100),"
                    + "DYNAMIC2 VARCHAR(100),"
                    + "DESCRIPTION VARCHAR(1500),"
                    + "FOREIGN KEY (CHAR1_ID) REFERENCES CHARACTERS(CHAR_ID),"
                    + "FOREIGN KEY (CHAR2_ID) REFERENCES CHARACTERS(CHAR_ID),"
                    + "CHECK (CHAR1_ID <> CHAR2_ID))");
            
            // Create character groups table
            s.executeUpdate("CREATE TABLE CHARACTER_GROUPS"
                    + "(CHARACTER_ID INT NOT NULL,"
                    + "GROUP_ID INT NOT NULL,"
                    + "PRIMARY KEY (CHARACTER_ID, GROUP_ID),"
                    + "FOREIGN KEY (CHARACTER_ID) REFERENCES CHARACTERS(CHAR_ID),"
                    + "FOREIGN KEY (GROUP_ID) REFERENCES USER_GROUPS(GROUP_ID))");
            
            // Create character tags table
            s.executeUpdate("CREATE TABLE CHARACTER_TAGS"
                    + "(CHARACTER_ID INT NOT NULL,"
                    + "TAG_ID INT NOT NULL,"
                    + "PRIMARY KEY (CHARACTER_ID, TAG_ID),"
                    + "FOREIGN KEY (CHARACTER_ID) REFERENCES CHARACTERS(CHAR_ID),"
                    + "FOREIGN KEY (TAG_ID) REFERENCES TAGS(TAG_ID))");
            
            // Create group tags table
            s.executeUpdate("CREATE TABLE GROUP_TAGS"
                    + "(GROUP_ID INT NOT NULL,"
                    + "TAG_ID INT NOT NULL,"
                    + "PRIMARY KEY (GROUP_ID, TAG_ID),"
                    + "FOREIGN KEY (GROUP_ID) REFERENCES USER_GROUPS(GROUP_ID),"
                    + "FOREIGN KEY (TAG_ID) REFERENCES TAGS(TAG_ID))");
            
            System.out.println("Database created successfully!");
        } catch(SQLException ex){
            if (ex.getSQLState().equals("X0Y32")) {
                System.out.println("Table already exists");
            } else {
                ex.printStackTrace();
            }
        }
        
    }
}
