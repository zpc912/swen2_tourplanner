import com.example.tourplanner.bl.AppLogicFactory;
import com.example.tourplanner.bl.IAppLogic;
import com.example.tourplanner.dal.common.DALFactory;
import com.example.tourplanner.dal.dao.ITourLogDAO;
import com.example.tourplanner.models.Tour;
import com.example.tourplanner.models.TourLog;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TourLogDAOTest {

    private static final ITourLogDAO tourLogDAO = DALFactory.createTourLogDAO();
    private static final IAppLogic appLogic = AppLogicFactory.getAppLogic();

    private static final Tour tour1 = new Tour("tour1-id", "tour1", "Description", "Linz", "Wien", "AUTO", 0.0f, null, null);
    private static final Tour tour2 = new Tour("tour2-id", "tour2", "Description", "Linz", "Wien", "AUTO", 0.0f, null, null);

    private static final TourLog tourLog1 = new TourLog("tourLog1-id", tour1, LocalDate.now(), "Comment", "Difficulty", "00:00:00", "Rating");
    private static final TourLog tourLog2 = new TourLog("tourLog2-id", tour2, LocalDate.now(), "Comment", "Difficulty", "00:00:00", "Rating");
    private static final TourLog tourLog3 = new TourLog("tourLog3-id", tour1, LocalDate.now(), "Comment", "Difficulty", "00:00:00", "Rating");


    @BeforeAll
    public static void beforeAll() {
        try {
            appLogic.createTour(tour1);
            appLogic.createTour(tour2);

            tourLogDAO.addNewTourLog(tourLog1, tour1);
            tourLogDAO.addNewTourLog(tourLog2, tour2);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }


    @AfterAll
    public static void afterAll() {
        appLogic.deleteTour(tour1.getTourId());
        appLogic.deleteTour(tour2.getTourId());
    }


    @Test
    public void test_addNewTourLog() {
        try {
            String result = tourLogDAO.addNewTourLog(tourLog3, tour1);

            assertEquals(result, tourLog3.getTourLogId());
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test_updateTourLog() {
        try {
            tourLog3.setComment("New Comment");
            boolean result = tourLogDAO.updateTourLog(tourLog3.getTourLogId(), tourLog3);

            assertTrue(result);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test_deleteTourLog() {
        try {
            boolean result = tourLogDAO.deleteTourLog(tourLog3.getTourLogId());

            assertTrue(result);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test_getLogOfTour() {
        try {
            List<TourLog> logOfTour = tourLogDAO.getLogOfTour(tour2);

            assertEquals(logOfTour.get(0).getTourLogId(), tourLog2.getTourLogId());
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
