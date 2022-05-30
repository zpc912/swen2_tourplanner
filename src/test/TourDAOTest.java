import com.example.tourplanner.bl.AppLogicFactory;
import com.example.tourplanner.bl.IAppLogic;
import com.example.tourplanner.dal.common.DALFactory;
import com.example.tourplanner.dal.dao.ITourDAO;
import com.example.tourplanner.models.Tour;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TourDAOTest {

    private static final ITourDAO tourDAO = DALFactory.createTourDAO();
    private static final IAppLogic appLogic = AppLogicFactory.getAppLogic();

    private static final Tour tour1 = new Tour("tour1-id", "tour1", "Description", "Linz", "Wien", "AUTO", 0.0f, null, null);
    private static final Tour tour2 = new Tour("tour2-id", "tour2", "Description", "Linz", "Wien", "AUTO", 0.0f, null, null);
    private static final Tour tour3 = new Tour("tour3-id", "tour3", "Description", "Linz", "Wien", "AUTO", 0.0f, null, null);


    @BeforeAll
    public static void beforeAll() {
        try {
            tourDAO.addNewTour(tour1);
            tourDAO.addNewTour(tour2);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }


    @AfterAll
    public static void afterAll() {
        try {
            tourDAO.deleteTour(tour1.getTourId());
            tourDAO.deleteTour(tour2.getTourId());

            appLogic.deleteTourImage("map_" + tour1.getTourId());
            appLogic.deleteTourImage("map_" + tour2.getTourId());
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test_getAllTours() {
        try {
            List<Tour> allTours = tourDAO.getAllTours();

            assertNotNull(allTours);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test_findById() {
        try {
            Tour result = tourDAO.findById(tour1.getTourId());

            assertEquals(result.getTourId(), tour1.getTourId());
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test_addNewTour() {
        try {
            String result = tourDAO.addNewTour(tour3);

            assertEquals(result, tour3.getTourId());
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test_updateTour() {
        try {
            tour3.setTourDesc("New Description");
            boolean result = tourDAO.updateTour(tour3.getTourId(), tour3);

            assertTrue(result);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test_deleteTour() {
        try {
            boolean result = tourDAO.deleteTour(tour3.getTourId());

            assertTrue(result);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
