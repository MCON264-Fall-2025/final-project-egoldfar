package edu.course.eventplanner.service;

import edu.course.eventplanner.model.Venue;
import java.util.*;

public class VenueSelector {
    private final TreeSet<Venue> venues;

    public VenueSelector(Collection<Venue> venues) { this.venues = new TreeSet<>(venues); }

    public Venue selectVenue(double budget, int guestCount) {
        if (venues.isEmpty()) {
            return null;
        }
        // TreeSet is sorted by cost first, then by capacity (smallest first)
        // So the first venue that fits criteria is the cheapest with least excess capacity
        for (Venue venue : venues) {
            if (venue.getCost() > budget) {
                break; // All remaining venues will be over budget
            }
            if (venue.getCapacity() >= guestCount) {
                return venue; // First fit: cheapest with least excess capacity
            }
        }
        return null;
    }
}
