package edu.course.eventplanner;

import edu.course.eventplanner.model.Venue;
import edu.course.eventplanner.util.Generators;

import edu.course.eventplanner.service.VenueSelector;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class VenueSelectorTest {
    static List<Venue> venueList = new ArrayList<>();
    static VenueSelector venueSelector;
    @BeforeAll
    public static void setUp() throws Exception {
        venueList.add(new Venue("A", 1000, 100, 10, 10));
        venueList.add(new Venue("B", 2000, 300, 30, 10));
        venueList.add(new Venue("C", 3000, 300, 10, 30));
        venueList.add(new Venue("D", 4000, 400, 10, 10));
        venueSelector = new VenueSelector(venueList);
    }

    @Test
    public void testVenueSelector() {
        assertNull(venueSelector.selectVenue(100, 500));
        assertEquals(venueSelector.selectVenue(1000, 100), venueList.get(0));
        assertEquals(venueSelector.selectVenue(5000, 250), venueList.get(1));
        assertNull(venueSelector.selectVenue(4000, 500));
    }

    @Test
    public void testGenerateVenues() {
        List<Venue> venues = Generators.generateVenues();
        assertNotNull(venues);
        assertEquals(3, venues.size());
        assertEquals("Community Hall", venues.get(0).getName());
        assertEquals("Garden Hall", venues.get(1).getName());
        assertEquals("Grand Ballroom", venues.get(2).getName());
    }

    @Test
    public void testEmptyVenueList() {
        VenueSelector emptySelector = new VenueSelector(new ArrayList<>());
        assertNull(emptySelector.selectVenue(5000, 100));
    }

}
