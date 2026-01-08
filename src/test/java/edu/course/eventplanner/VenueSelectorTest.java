package edu.course.eventplanner;

import edu.course.eventplanner.model.Venue;
import edu.course.eventplanner.service.VenueSelector;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class VenueSelectorTest {

    @Test
    void testIfVenueWithSmallerCapacityWinsWithCostTie() {
        //arrange
        Venue venue1 = new Venue("Chinka", 500, 50, 5, 10);
        Venue venue2 = new Venue("Eden Palace", 500, 40, 4, 10);

        List<Venue> venues = new ArrayList<Venue>();
        venues.add(venue1);
        venues.add(venue2);

        VenueSelector venuesSelector = new VenueSelector(venues);

        // act
        // Budget 600, Guests 15. Both venues are valid.
        Venue result = venuesSelector.selectVenue(600, 15);

        // assert
        // Check if the correct object (the tie-breaker winner) is returned
        assertNotNull(result, "The method should return a venue");
        assertEquals(venue2, result, "Eden Palace should be chosen because it has the smaller capacity tie-breaker");
    }
    @Test
    void testNoValidVenueDueToBudget() {
        //arrange
        Venue expensiveVenue = new Venue("Chinka", 5000, 50, 5, 10);
        List<Venue> venues = new ArrayList<>();
        venues.add(expensiveVenue);
        VenueSelector venuesSelector = new VenueSelector(venues);

        //act - Budget (600) is lower than the cost (5000)
        Venue result = venuesSelector.selectVenue(600, 20);

        //assert
        assertNull(result, "Should return null if budget is insufficient");
    }

    @Test
    void testNoValidVenueDueToCapacity() {
        //arrange
        Venue smallVenue = new Venue("Eden Palace", 500, 40, 4, 10);
        List<Venue> venues = new ArrayList<>();
        venues.add(smallVenue);
        VenueSelector venuesSelector = new VenueSelector(venues);

        //act - Capacity (40) is less than the guestCount (60)
        Venue result = venuesSelector.selectVenue(600, 60);

        //assert
        assertNull(result, "Should return null if capacity is too small");
    }

    @Test
    void testCheapestPriceWinsRegardlessOfCapacity() {
        // Arrange
        // Venue 1 is cheap but huge capacity
        Venue cheapButBig = new Venue("Cheap", 400, 100, 10, 10);
        // Venue 2 is expensive but small capacity
        Venue expensiveButSmall = new Venue("Expensive", 800, 20, 2, 10);

        List<Venue> venues = new ArrayList<>();
        venues.add(expensiveButSmall); // Add expensive one first
        venues.add(cheapButBig);

        VenueSelector selector = new VenueSelector(venues);

        //act
        Venue result = selector.selectVenue(1000, 10);

        // Assert- make sure that cheapButBig wins bc price is the first rule, so the cheaper venue must win
        assertEquals(cheapButBig, result, "The cheapest venue should be selected first, even if its capacity is larger");
    }
}