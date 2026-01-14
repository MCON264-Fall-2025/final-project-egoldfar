package edu.course.eventplanner.service;

import edu.course.eventplanner.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SeatingPlanner {
    private final Venue venue;

    public SeatingPlanner(Venue venue) {
        this.venue = venue;
    }

    public Map<Integer, List<Guest>> generateSeating(List<Guest> guests) {
        int seatsPerTable = venue.getSeatsPerTable();
        int currentTable = 0;
        Map<Integer, List<Guest>> seating = new HashMap<>();

        if (guests == null || guests.isEmpty()) {
            return seating;
        }

        // Group guests by their group tag
        Map<String, ArrayList<Guest>> byGroup = new HashMap<>();
        for (Guest guest : guests) {
            if (!byGroup.containsKey(guest.getGroupTag())) {
                byGroup.put(guest.getGroupTag(), new ArrayList<>());
            }
            byGroup.get(guest.getGroupTag()).add(guest);
        }

        // Assign guests to tables, keeping groups together
        for (String key : byGroup.keySet()) {
            ArrayList<Guest> groupGuests = byGroup.get(key);
            int neededTablesForGroup = groupGuests.size() / seatsPerTable;
            if (groupGuests.size() % seatsPerTable != 0) neededTablesForGroup++;
            int currentGuestIndex = 0;
            for (int i = 0; i < neededTablesForGroup; i++) {
                List<Guest> tableGuests = new ArrayList<>();
                for (int j = 0; j < seatsPerTable; j++) {
                    if (currentGuestIndex < groupGuests.size()) {
                        tableGuests.add(groupGuests.get(currentGuestIndex));
                        currentGuestIndex++;
                    }
                }
                seating.put(currentTable, tableGuests);
                currentTable++;
            }
        }

        return seating;
    }
}
