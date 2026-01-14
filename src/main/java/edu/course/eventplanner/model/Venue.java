package edu.course.eventplanner.model;

public class Venue implements Comparable<Venue>{
    private final String name;
    private final double cost;
    private final int capacity;
    private final int tables;
    private final int seatsPerTable;
    public Venue(String name, double cost, int capacity, int tables, int seatsPerTable) {
        this.name = name;
        this.cost = cost;
        this.capacity = capacity;
        this.tables = tables;
        this.seatsPerTable = seatsPerTable;
    }
    public String getName() { return name; }
    public double getCost() { return cost; }
    public int getCapacity() { return capacity; }
    public int getTables() { return tables; }
    public int getSeatsPerTable() { return seatsPerTable; }

    @Override
    public int compareTo(Venue other) {
        if (this.cost < other.cost) {
            return -1;
        } else if (this.cost > other.cost) {
            return 1;
        } else {
            if (this.capacity < other.capacity) {
                return -1;
            } else if (this.capacity > other.capacity) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}
