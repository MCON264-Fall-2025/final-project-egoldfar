package edu.course.eventplanner.model;

import java.util.HashMap;

public class Guest {
    private final String name;
    private final String groupTag;
    public Guest(String name, String groupTag) {
        this.name = name;
        this.groupTag = groupTag;
    }
    public String getName() { return name; }
    public String getGroupTag() { return groupTag; }

}
