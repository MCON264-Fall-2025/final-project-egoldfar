package edu.course.eventplanner.service;

import edu.course.eventplanner.model.Venue;
import java.util.*;

public class VenueSelector {
    private final List<Venue> venues;

    public VenueSelector(List<Venue> venues) { this.venues = venues; }
    public Venue selectVenue(double budget, int guestCount) {
        Venue venue = null;
        for (Venue v : venues) {
            if(v.getCost()<=budget){
                if(v.getCapacity()>=guestCount){
                    if(venue==null){
                        venue = v;
                    } else if (v.getCost() < venue.getCost()) {
                        venue = v;
                    }
                }
            }
        }
        return venue;
    }
}
