package edu.course.eventplanner;

import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.service.GuestListManager;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class GuestListMangerTest {

    @Test
    void testAddGuestStoresInMasterList() {
        //arrange
        GuestListManager manager = new GuestListManager();
        Guest guest = new Guest("Aviva", "Family");
        //act
        manager.addGuest(guest);
        // assert- Should be present in the LinkedList
        assertTrue(manager.getAllGuests().contains(guest));
    }

    @Test
    void testAddGuestIsFindableInMap() {
        //arrange
        GuestListManager manager = new GuestListManager();
        Guest guest = new Guest("Aviva", "Family");
        //act
        manager.addGuest(guest);
        // assert- Should be found in Map via findGuest using composite key
        assertEquals(guest, manager.findGuest("Aviva-Family"));
    }

    @Test
    void testRemoveGuestClearsFromList() {
        //arrange
        GuestListManager manager = new GuestListManager();
        Guest guest = new Guest("Aviva", "Friends");
        //act
        manager.addGuest(guest);
        // Updated to pass both name and tag
        manager.removeGuest("Aviva-Friends");
        // assert- Guest should no longer be in the LinkedList
        assertFalse(manager.getAllGuests().contains(guest));
    }

    @Test
    void testRemoveGuestClearsFromMap() {
        //arrange
        GuestListManager manager = new GuestListManager();
        manager.addGuest(new Guest("Aviva", "Friends"));
        //act
        // Updated to pass both name and tag
        manager.removeGuest("Aviva-Friends");
        // assert- Guest should no longer be in the Map
        assertNull(manager.findGuest("Aviva-Friends"));
    }

    @Test
    void testDuplicateNameWithDifferentTagIsAdded() {
        //arrange
        GuestListManager manager = new GuestListManager();
        manager.addGuest(new Guest("Aviva", "Family"));
        // This is no longer a duplicate because the Tag is different
        manager.addGuest(new Guest("Aviva", "Work"));

        //assert- Should allow different groups even if name is the same
        assertEquals(2, manager.getGuestCount());
    }

    @Test
    void testExactDuplicateIsNotAdded() {
        //arrange
        GuestListManager manager = new GuestListManager();
        manager.addGuest(new Guest("Aviva", "Family"));
        manager.addGuest(new Guest("Aviva", "Family"));

        //assert- Should ignore exact duplicates (Same Name AND Same Tag)
        assertEquals(1, manager.getGuestCount());
    }

    @Test
    void testLookingUpGuestByNameAndTag() {
        GuestListManager manager = new GuestListManager();
        Guest guest = new Guest("Aviva", "Co-Worker");
        manager.addGuest(guest);
        Guest found = manager.findGuest("Aviva-Co-Worker");
        assertNotNull(found, "Lookup should find the guest by composite key");
        assertEquals("Co-Worker", found.getGroupTag(), "Lookup should return the correct guest data");
    }
}