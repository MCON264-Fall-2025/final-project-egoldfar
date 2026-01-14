package edu.course.eventplanner;

import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.model.Venue;
import edu.course.eventplanner.service.GuestListManager;
import edu.course.eventplanner.service.SeatingPlanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SeatingPlannerTest {

    private GuestListManager guestListManager;
    private Venue venue;

    @BeforeEach
    void setUp() {
        guestListManager = new GuestListManager();
        venue = new Venue("Test Venue", 500, 20, 4, 4);
    }

    @Test
    void testGenerateSeatingWithNoGuests() {
        SeatingPlanner planner = new SeatingPlanner(venue, guestListManager);
        Map<Integer, Guest[]> seating = planner.generateSeating();
        assertTrue(seating.isEmpty());
    }

    @Test
    void testGenerateSeatingWithOneGroup() {
        guestListManager.addGuest(new Guest("Alice", "Family"));
        guestListManager.addGuest(new Guest("Bob", "Family"));
        guestListManager.addGuest(new Guest("Charlie", "Family"));

        SeatingPlanner planner = new SeatingPlanner(venue, guestListManager);
        Map<Integer, Guest[]> seating = planner.generateSeating();

        assertEquals(1, seating.size());
        Guest[] table = seating.get(0);
        assertEquals(4, table.length);
        assertEquals("Alice", table[0].getName());
        assertEquals("Bob", table[1].getName());
        assertEquals("Charlie", table[2].getName());
        assertNull(table[3]);
    }

    @Test
    void testGenerateSeatingWithMultipleGroups() {
        guestListManager.addGuest(new Guest("Alice", "Family"));
        guestListManager.addGuest(new Guest("Bob", "Family"));
        guestListManager.addGuest(new Guest("Eve", "Friends"));
        guestListManager.addGuest(new Guest("Frank", "Friends"));

        SeatingPlanner planner = new SeatingPlanner(venue, guestListManager);
        Map<Integer, Guest[]> seating = planner.generateSeating();

        assertEquals(2, seating.size());
    }

    @Test
    void testGenerateSeatingFillsMultipleTables() {
        // Add more guests than one table can hold (venue has 4 seats per table)
        guestListManager.addGuest(new Guest("Guest1", "BigGroup"));
        guestListManager.addGuest(new Guest("Guest2", "BigGroup"));
        guestListManager.addGuest(new Guest("Guest3", "BigGroup"));
        guestListManager.addGuest(new Guest("Guest4", "BigGroup"));
        guestListManager.addGuest(new Guest("Guest5", "BigGroup"));

        SeatingPlanner planner = new SeatingPlanner(venue, guestListManager);
        Map<Integer, Guest[]> seating = planner.generateSeating();

        assertEquals(2, seating.size());
        // First table should be full
        Guest[] table1 = seating.get(0);
        for (int i = 0; i < 4; i++) {
            assertNotNull(table1[i]);
        }
        // Second table should have 1 guest and 3 nulls
        Guest[] table2 = seating.get(1);
        assertNotNull(table2[0]);
        assertNull(table2[1]);
        assertNull(table2[2]);
        assertNull(table2[3]);
    }

    @Test
    void testTableArrayLengthMatchesSeatsPerTable() {
        Venue smallVenue = new Venue("Small Venue", 200, 10, 2, 2);
        guestListManager.addGuest(new Guest("Alice", "Group"));

        SeatingPlanner planner = new SeatingPlanner(smallVenue, guestListManager);
        Map<Integer, Guest[]> seating = planner.generateSeating();

        Guest[] table = seating.get(0);
        assertEquals(2, table.length);
    }
}
