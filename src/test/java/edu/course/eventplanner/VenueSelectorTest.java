package edu.course.eventplanner;

import edu.course.eventplanner.model.Venue;
import edu.course.eventplanner.util.Generators;

import edu.course.eventplanner.service.VenueSelector;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.TreeSet;

public class VenueSelectorTest {
    static TreeSet<Venue> venueSet = new TreeSet<>();
    static VenueSelector venueSelector;
    @BeforeAll
    public static void setUp() throws Exception {
        venueSet.add(new Venue("A", 1000, 100, 10, 10));
        venueSet.add(new Venue("B", 2000, 300, 30, 10));
        venueSet.add(new Venue("C", 3000, 300, 10, 30));
        venueSet.add(new Venue("D", 4000, 400, 10, 10));
        venueSelector = new VenueSelector(venueSet);
    }

    @Test
    public void testVenueSelector() {
        assertNull(venueSelector.selectVenue(100, 500));
        // Find venue A by name since TreeSet ordering may differ
        Venue venueA = venueSet.stream().filter(v -> v.getName().equals("A")).findFirst().orElse(null);
        Venue venueB = venueSet.stream().filter(v -> v.getName().equals("B")).findFirst().orElse(null);
        assertEquals(venueA, venueSelector.selectVenue(1000, 100));
        assertEquals(venueB, venueSelector.selectVenue(5000, 250));
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
        VenueSelector emptySelector = new VenueSelector(new TreeSet<>());
        assertNull(emptySelector.selectVenue(5000, 100));
    }

}
