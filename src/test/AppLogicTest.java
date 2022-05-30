import com.example.tourplanner.bl.AppLogicFactory;
import com.example.tourplanner.bl.IAppLogic;
import com.example.tourplanner.models.Tour;
import com.example.tourplanner.models.TourLog;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AppLogicTest {

    private static final IAppLogic appLogic = AppLogicFactory.getAppLogic();

    private static final Tour tour1 = new Tour("tour1-id", "tour1", "Description", "Linz", "Wien", "AUTO", 0.0f, null, null);
    private static final Tour tour2 = new Tour("tour2-id", "tour2", "Description", "Linz", "Wien", "AUTO", 0.0f, null, null);
    private static final Tour tour3 = new Tour("tour3-id", "tour3", "Description", "Linz", "Wien", "AUTO", 0.0f, null, null);

    private static final TourLog tourLog1 = new TourLog("tourLog1-id", tour1, LocalDate.now(), "Comment", "Difficulty", "00:00:00", "Rating");
    private static final TourLog tourLog2 = new TourLog("tourLog2-id", tour2, LocalDate.now(), "Comment", "Difficulty", "00:00:00", "Rating");
    private static final TourLog tourLog3 = new TourLog("tourLog3-id", tour1, LocalDate.now(), "Comment", "Difficulty", "00:00:00", "Rating");


    @BeforeAll
    public static void beforeAll() {
        appLogic.createTour(tour1);
        appLogic.createTour(tour2);

        appLogic.createTourLog(tourLog1, tour1);
        appLogic.createTourLog(tourLog2, tour2);
    }


    @AfterAll
    public static void afterAll() {
        appLogic.deleteTour(tour1.getTourId());
        appLogic.deleteTourImage("map_" + tour1.getTourId());

        appLogic.deleteTour(tour2.getTourId());
        appLogic.deleteTourImage("map_" + tour2.getTourId());

        appLogic.deleteTourLog(tourLog1.getTourLogId());
        appLogic.deleteTourLog(tourLog2.getTourLogId());
    }


    @Test
    public void test_getAllTours() {
        List<Tour> allTours = appLogic.getAllTours();

        assertNotNull(allTours);
    }


    @Test
    public void test_getTourById() {
        Tour tour = appLogic.getTourById(tour1.getTourId());

        assertEquals(tour.getTourId(), tour1.getTourId());
    }


    @Test
    public void test_createNewTour() {
        String result = appLogic.createTour(tour3);

        assertEquals(result, tour3.getTourId());
    }


    @Test
    public void test_deleteTour() {
        boolean result = appLogic.deleteTour(tour3.getTourId());

        assertTrue(result);
    }


    @Test
    public void test_updateTour() {
        tour1.setTourDesc("New Description");
        boolean result = appLogic.updateTour(tour1);

        assertTrue(result);
    }


    @Test
    public void test_createTourLog() {
        String result = appLogic.createTourLog(tourLog3, tour1);

        assertEquals(result, tourLog3.getTourLogId());
    }


    @Test
    public void test_getLogOfTour() {
        List<TourLog> result = appLogic.getLogOfTour(tour1);

        assertNotNull(result);
    }


    @Test
    public void test_deleteTourLog() {
        boolean result = appLogic.deleteTourLog(tourLog3.getTourLogId());

        assertTrue(result);
    }


    @Test
    public void test_updateTourLog() {
        tourLog1.setComment("New Comment");
        boolean result = appLogic.updateTourLog(tourLog1);

        assertTrue(result);
    }


    @Test
    public void test_requestTourDetails() {
        ArrayList<String> result = appLogic.requestTourDetails(tour1);

        assertNotNull(result);
    }


    @Test
    public void test_requestTourMap() {
        String result = appLogic.requestTourMap(tour1);

        assertNotNull(result);
    }


    @Test
    public void test_getTourImage() {
        File file = appLogic.getTourImage("map_" + tour1.getTourId());

        assertTrue(file.exists());
    }


    @Test
    public void test_deleteTourImage() {
        boolean result = appLogic.deleteTourImage("map_" + tour3.getTourId());

        assertTrue(result);
    }


    @Test
    public void test_getAllTourLogs() {
        List<TourLog> result = appLogic.getAllTourLogs();

        assertNotNull(result);
    }
}
