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
        int numTables = venue.getTables();
        int seatsPerTable = venue.getSeatsPerTable();
        int currentTable = 0;
        int currentGuest = 0;
        Map<Integer,Guest[seatsPerTable]> seating = new HashMap<>();
        Map<String, ArrayList<Guest>> byGroup = guestListManager.getGuestsByGroup();
            for(String key : byGroup.keySet()) {
                int neededTablesForGroup = byGroup.get(key).size()/ seatsPerTable;
                if (byGroup.get(key).size()% seatsPerTable != 0) neededTablesForGroup++;
                currentGuest = 0;
                for(int i = 0; i < neededTablesForGroup; i++) {
                    for(int j = 0; j < seatsPerTable; j++) {

                    }
                    currentTable++;
                }
            }

        return seating;
    }
}
