package edu.course.eventplanner;

import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.model.Venue;
import edu.course.eventplanner.service.SeatingPlanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SeatingPlannerTest {

    private List<Guest> guests;
    private Venue venue;

    @BeforeEach
    void setUp() {
        guests = new ArrayList<>();
        venue = new Venue("Test Venue", 500, 20, 4, 4);
    }

    @Test
    void testGenerateSeatingWithNoGuests() {
        SeatingPlanner planner = new SeatingPlanner(venue);
        Map<Integer, List<Guest>> seating = planner.generateSeating(guests);
        assertTrue(seating.isEmpty());
    }

    @Test
    void testGenerateSeatingWithOneGroup() {
        guests.add(new Guest("Alice", "Family"));
        guests.add(new Guest("Bob", "Family"));
        guests.add(new Guest("Charlie", "Family"));

        SeatingPlanner planner = new SeatingPlanner(venue);
        Map<Integer, List<Guest>> seating = planner.generateSeating(guests);

        assertEquals(1, seating.size());
        List<Guest> table = seating.get(0);
        assertEquals(3, table.size());
        assertEquals("Alice", table.get(0).getName());
        assertEquals("Bob", table.get(1).getName());
        assertEquals("Charlie", table.get(2).getName());
    }

    @Test
    void testGenerateSeatingWithMultipleGroups() {
        guests.add(new Guest("Alice", "Family"));
        guests.add(new Guest("Bob", "Family"));
        guests.add(new Guest("Eve", "Friends"));
        guests.add(new Guest("Frank", "Friends"));

        SeatingPlanner planner = new SeatingPlanner(venue);
        Map<Integer, List<Guest>> seating = planner.generateSeating(guests);

        assertEquals(2, seating.size());
    }

    @Test
    void testGenerateSeatingFillsMultipleTables() {
        // Add more guests than one table can hold (venue has 4 seats per table)
        guests.add(new Guest("Guest1", "BigGroup"));
        guests.add(new Guest("Guest2", "BigGroup"));
        guests.add(new Guest("Guest3", "BigGroup"));
        guests.add(new Guest("Guest4", "BigGroup"));
        guests.add(new Guest("Guest5", "BigGroup"));

        SeatingPlanner planner = new SeatingPlanner(venue);
        Map<Integer, List<Guest>> seating = planner.generateSeating(guests);

        assertEquals(2, seating.size());
        // First table should be full (4 guests)
        List<Guest> table1 = seating.get(0);
        assertEquals(4, table1.size());
        // Second table should have 1 guest
        List<Guest> table2 = seating.get(1);
        assertEquals(1, table2.size());
    }

    @Test
    void testNoTableExceedsCapacity() {
        Venue smallVenue = new Venue("Small Venue", 200, 10, 2, 2);
        guests.add(new Guest("Alice", "Group"));
        guests.add(new Guest("Bob", "Group"));
        guests.add(new Guest("Charlie", "Group"));

        SeatingPlanner planner = new SeatingPlanner(smallVenue);
        Map<Integer, List<Guest>> seating = planner.generateSeating(guests);

        for (List<Guest> table : seating.values()) {
            assertTrue(table.size() <= 2);
        }
    }
}
