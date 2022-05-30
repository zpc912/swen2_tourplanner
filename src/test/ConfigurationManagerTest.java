import com.example.tourplanner.bl.ConfigurationManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ConfigurationManagerTest {

    private static final ConfigurationManager configurationManager = new ConfigurationManager();

    private static final String DbPackage = "dal.postgres";
    private static final String DbConnectionString = "jdbc:postgresql://localhost:5432/tourplanner_db";
    private static final String DbUser = "tourplanner_user";
    private static final String DbPassword = "tourplanner123";
    private static final String ConsumerKey = ""; // fill in value
    private static final String ConsumerSecret = ""; // fill in value


    @Test
    public void test_DbPackage() {
        String result = configurationManager.getConfigProperty("DbPackage");

        assertEquals(result, DbPackage);
    }


    @Test
    public void test_DbConnectionString() {
        String result = configurationManager.getConfigProperty("DbConnectionString");

        assertEquals(result, DbConnectionString);
    }


    @Test
    public void test_DbUser() {
        String result = configurationManager.getConfigProperty("DbUser");

        assertEquals(result, DbUser);
    }


    @Test
    public void test_DbPassword() {
        String result = configurationManager.getConfigProperty("DbPassword");

        assertEquals(result, DbPassword);
    }


    @Test
    public void test_ConsumerKey() {
        String result = configurationManager.getConfigProperty("ConsumerKey");

        assertEquals(result, ConsumerKey);
    }


    @Test
    public void test_ConsumerSecret() {
        String result = configurationManager.getConfigProperty("ConsumerSecret");

        assertEquals(result, ConsumerSecret);
    }
}
