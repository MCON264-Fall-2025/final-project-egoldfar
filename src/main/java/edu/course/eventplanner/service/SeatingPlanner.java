package edu.course.eventplanner.service;

import edu.course.eventplanner.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SeatingPlanner {
    private final Venue venue;
    GuestListManager guestListManager;
    public SeatingPlanner(Venue venue, GuestListManager guestListManager) {
        this.venue = venue;
        this.guestListManager = guestListManager;
    }
    public Map<Integer, Guest[]> generateSeating() {
        int seatsPerTable = venue.getSeatsPerTable();
        int currentTable = 0;
        int currentGuest = 0;
        Map<Integer,Guest[]> seating = new HashMap<>();
        Map<String, ArrayList<Guest>> byGroup = guestListManager.getGuestsByGroup();
            for(String key : byGroup.keySet()) {
                ArrayList<Guest> groupGuests = byGroup.get(key);
                int neededTablesForGroup = groupGuests.size() / seatsPerTable;
                if (groupGuests.size() % seatsPerTable != 0) neededTablesForGroup++;
                currentGuest = 0;
                for(int i = 0; i < neededTablesForGroup; i++) {
                    Guest[] tableGuests = new Guest[seatsPerTable];
                    for(int j = 0; j < seatsPerTable; j++) {
                        if (currentGuest < groupGuests.size()) {
                            tableGuests[j] = groupGuests.get(currentGuest);
                            currentGuest++;
                        } else {
                            tableGuests[j] = null;
                        }
                    }
                    seating.put(currentTable, tableGuests);
                    currentTable++;
                }
            }

        return seating;
    }
}
