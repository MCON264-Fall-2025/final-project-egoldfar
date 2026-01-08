package edu.course.eventplanner;

import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.model.Venue;
import edu.course.eventplanner.service.SeatingPlanner;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class SeatingPlannerTest {

    @Test
    void testGuestGroupingByTag() {
        // Arrange
        Venue venue = new Venue("Mates Avraham", 100, 6, 3, 2);
        SeatingPlanner planner = new SeatingPlanner(venue);
        List<Guest> guests = new ArrayList<>();

        // add guests in mixed order
        guests.add(new Guest("Alice", "Family"));
        guests.add(new Guest("Josh", "CoWorker"));
        guests.add(new Guest("Bob", "Family"));

        // act
        Map<Integer, List<Guest>> result = planner.generateSeating(guests);

        // assert - Bob should be pulled into Table 1 with Alice because they share the Family tag
        assertEquals("Bob", result.get(1).get(1).getName(), "Guests with the same tag must be seated together");
    }

    @Test
    void testSeatingPlanRespectsTableLimit() {
        // arrange - Venue only has 1 table
        Venue smallVenue = new Venue("Eden Palace", 100, 5, 1, 5);
        SeatingPlanner planner = new SeatingPlanner(smallVenue);
        List<Guest> guests = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            guests.add(new Guest("Guest " + i, "Family"));
        }

        // act
        Map<Integer, List<Guest>> result = planner.generateSeating(guests);

        // assert - should not create more tables than the venue allows
        assertEquals(1, result.size(), "Planner should not exceed the venue table limit");
    }

    @Test
    void testEmptyGuestListBehavior() {
        // Arrange
        Venue venue = new Venue("Hall", 1000, 50, 5, 10);
        SeatingPlanner planner = new SeatingPlanner(venue);

        // Act
        Map<Integer, List<Guest>> result = planner.generateSeating(new ArrayList<>());

        // Assert
        assertTrue(result.isEmpty(), "An empty guest list should return an empty map");
    }
}