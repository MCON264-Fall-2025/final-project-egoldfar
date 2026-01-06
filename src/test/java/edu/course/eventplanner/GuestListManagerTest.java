package edu.course.eventplanner;

import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.service.GuestListManager;

import edu.course.eventplanner.util.Generators;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class GuestListManagerTest {

    static GuestListManager guestListManager;
    static int numberOfGuests = 0;

    @BeforeAll
    public static void setUpClass() {
        guestListManager = new GuestListManager();
        numberOfGuests = 10;
        List<Guest> guests = Generators.GenerateGuests(numberOfGuests);
        for (Guest guest : guests) {
            guestListManager.addGuest(guest);
        }
    }

    @Test
    public void testGuestListManagerSize() {
        assertEquals(numberOfGuests, guestListManager.getGuestCount());
    }

    @Test
    public void testAddGuest() {
        Guest temp = new Guest("Random Use", "family");
        guestListManager.addGuest(temp);
        assertEquals(++numberOfGuests, guestListManager.getGuestCount());
        assertEquals(temp, guestListManager.findGuest("Random Use"));
    }

    @Test
    public void testRemoveGuest() {
        Guest temp = new Guest("Random Use", "family");
        guestListManager.addGuest(temp);
        assertFalse(guestListManager.removeGuest("Random Use"));
    }

    @Test
    public void testFindGuest() {
        Guest temp = new Guest("Random Use", "family");
        guestListManager.addGuest(temp);
        assertTrue(guestListManager.removeGuest("Random Use"));
        assertEquals(temp, guestListManager.findGuest("Random Use"));
        assertNull(guestListManager.findGuest("qwertyuiop"));
    }

    @Test
    public void testGetGuestsByGroup() {
        Map<String, ArrayList<Guest>> seating = guestListManager.getGuestsByGroup();
        Set<String> seatingKeys = seating.keySet();
        for (String s : seatingKeys) {
            for(Guest g :seating.get(s)) {
                assertEquals(s,g.getGroupTag());
            }
        }
    }
}
