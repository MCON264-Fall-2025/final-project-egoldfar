package edu.course.eventplanner.service;

import edu.course.eventplanner.model.Guest;
import java.util.*;

public class GuestListManager {
    private final LinkedList<Guest> guests = new LinkedList<>();
    private final Map<String, Guest> guestByName = new HashMap<>();
    public void addGuest(Guest guest) {
        /* TODO */
        guests.add(guest);
    }
    public boolean removeGuest(String guestName) {
        /*TODO*/
        
        return false; }
    public Guest findGuest(String guestName) {
        /*TODO*/
        return null;
    }
    public int getGuestCount() { return guests.size(); }
    public List<Guest> getAllGuests() { return guests; }
}
