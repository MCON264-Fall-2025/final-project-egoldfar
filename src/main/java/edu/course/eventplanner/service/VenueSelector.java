package edu.course.eventplanner.service;

import edu.course.eventplanner.model.Venue;
import java.util.*;

public class VenueSelector {
    private final List<Venue> venues;

    public VenueSelector(List<Venue> venues) { this.venues = new ArrayList<>(venues); }

    public Venue selectVenue(double budget, int guestCount) {
        if (venues.isEmpty()) {
            return null;
        }
        Collections.sort(venues);
        Venue venue = null;
        int i = 0;
        Venue next = venues.get(i);
        while (next.getCost() <= budget && i < venues.size()) {
                if(next.getCapacity()>=guestCount){
                    if(venue==null){
                        venue = next;
                    } else if (next.getCapacity() < venue.getCapacity()) {
                        venue = next;
                    }
                }
                next = venues.get(i++);
        }
        return venue;
    }
}
