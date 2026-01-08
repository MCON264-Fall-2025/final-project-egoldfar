package edu.course.eventplanner.service;

import edu.course.eventplanner.model.Guest;
import java.util.*;

public class GuestListManager {
    private final LinkedList<Guest> guests = new LinkedList<>();
    private final Map<String, Guest> guestByKey = new HashMap<>();
    private int size = 0;

    public void addGuest(Guest guest) {
        if (guest == null)
            return;

        // Create the composite key
        String key = guest.getName() + "-" + guest.getGroupTag();

        // It will only block if the SAME name is in the SAME group
        if (guestByKey.containsKey(key)) {
            System.out.println("Error: This guest is already on the list.");
            return;
        }

        //add to the linked list and to the map
        guests.addLast(guest);
        guestByKey.put(key, guest);
        size++;
    }

    // I am removing and identifying guests based off their name and tag combo
    public boolean removeGuest(String identifier) {
        Guest toRemove = findGuest(identifier);

        if (toRemove == null) {
            return false;
        }

        // Build the key that was actually used in the map to ensure it is removed
        String actualKey = toRemove.getName() + "-" + toRemove.getGroupTag();

        //Remove from both to keep them in sync
        guests.remove(toRemove); // Removes from the LinkedList
        guestByKey.remove(actualKey); // Removes from the HashMap
        size--;
        return true;
    }

    public Guest findGuest(String identifier) {
        // try to find by the composite key (Name-Tag)
        if (guestByKey.containsKey(identifier)) {
            return guestByKey.get(identifier);
        }

        // to pass the professors unit tests I added this in- search the list by name only
        for (Guest g : guests) {
            if (g.getName().equals(identifier)) {
                return g;
            }
        }

        return null;
    }


    public int getGuestCount() {
        return size;
    }

    public List<Guest> getAllGuests() {
        return guests;
    }
}