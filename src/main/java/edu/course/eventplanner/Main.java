package edu.course.eventplanner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.model.Task;
import edu.course.eventplanner.model.Venue;
import edu.course.eventplanner.service.GuestListManager;
import edu.course.eventplanner.service.SeatingPlanner;
import edu.course.eventplanner.service.VenueSelector;
import edu.course.eventplanner.service.TaskManager;
import edu.course.eventplanner.util.Generators;

public class Main {

    static Scanner keyboard = new Scanner(System.in);

    public static void main(String[] args) {

        GuestListManager guestListManager = new GuestListManager();
        VenueSelector venueSelector = null;
        Venue venue = null;
        SeatingPlanner seatingPlanner = null;
        TaskManager taskManager = new TaskManager();

        int choice;
        do {
            choice = printMenu();
            switch (choice) {
                case 0:
                    break;
                case 1:
                    venueSelector = loadSampleData(guestListManager);
                    break;
                case 2:
                    addGuest(guestListManager);
                    break;
                case 3:
                    removeGuest(guestListManager);
                    break;
                case 4:
                    if (venueSelector == null) {
                        System.out.println("Please load sample data first to get available venues.");
                    } else {
                        venue = venueSelect(venueSelector, guestListManager);
                        if (venue != null) {
                            seatingPlanner = new SeatingPlanner(venue);
                        }
                    }
                    break;
                case 5:
                    if (seatingPlanner != null) {
                        seatGuests(seatingPlanner, guestListManager);
                    } else {
                        System.out.println("You must select a venue first.");
                    }
                    break;
                case 6: 
                    addTask(taskManager);
                    break;
                case 7:
                    executeTask(taskManager);
                    break;
                case 8:
                    undoTask(taskManager);
                    break;
                case 9:
                    printEventSummary(guestListManager, venue, taskManager, seatingPlanner);
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 0);
    }

    public static int printMenu() {
        System.out.println("\nEvent Planner Menu");
        System.out.println("1. Load sample data");
        System.out.println("2. Add guest");
        System.out.println("3. Remove guest");
        System.out.println("4. Select venue");
        System.out.println("5. Generate seating chart");
        System.out.println("6. Add preparation task");
        System.out.println("7. Execute next task");
        System.out.println("8. Undo last task");
        System.out.println("9. Print event summary");
        System.out.println("0. Quit");
        int choice = intInput("Choice: ");
        return choice;
    }

    public static VenueSelector loadSampleData(GuestListManager guestListManager) {
        // Load guests from Generators
        List<Guest> guests = Generators.GenerateGuests(20);
        for (Guest guest : guests) {
            guestListManager.addGuest(guest);
        }
        System.out.println("Loaded " + guests.size() + " guests.");
        
        // Load venues from Generators and create VenueSelector
        List<Venue> venues = Generators.generateVenues();
        System.out.println("Loaded " + venues.size() + " venues.");
        System.out.println("Sample data loaded successfully.");
        return new VenueSelector(venues);
    }

    public static void addGuest(GuestListManager guestListManager) {
        if(keyboard.hasNextLine()) keyboard.nextLine();
        System.out.print("Enter guest name: ");
        String name = keyboard.nextLine();
        System.out.print("Enter guest group: ");
        String group = keyboard.nextLine();
        Guest guest = new Guest(name, group);
        guestListManager.addGuest(guest);
    }

    public static void removeGuest(GuestListManager guestListManager) {
        System.out.print("Enter guest name to remove: ");
        String name = keyboard.nextLine();
        boolean removed = guestListManager.removeGuest(name);
        if (removed) {
            System.out.println("Guest " + name + " removed.");
        } else {
            System.out.println("Guest " + name + " not found.");
        }
    }

    public static Venue venueSelect(VenueSelector venueSelector, GuestListManager guestListManager) {
        System.out.print("Enter budget for venue: ");
        double budget = keyboard.nextDouble();
        int guestCount;
        if (guestListManager.getGuestCount() == 0) {
            guestCount = intInput("Enter number of guests: ");
        } else {
            guestCount = guestListManager.getGuestCount();
        }
        Venue venue = venueSelector.selectVenue(budget, guestCount);
        if (venue != null) {
            System.out.println("Selected venue: " + venue.getName());
        } else {
            System.out.println("No suitable venue found within budget.");
        }
        return venue;
    }

    public static void seatGuests(SeatingPlanner seatingPlanner, GuestListManager guestListManager) {
        Map<Integer, List<Guest>> seating = seatingPlanner.generateSeating(guestListManager.getAllGuests());
        for (Integer tableNumber : seating.keySet()) {
            System.out.println("Table " + (tableNumber + 1) + ":");
            List<Guest> tableGuests = seating.get(tableNumber);
            for (Guest guest : tableGuests) {
                System.out.println("  - " + guest.getName());
            }
        }
    }

    public static void addTask(TaskManager taskManager) {
        if(keyboard.hasNextLine()) keyboard.nextLine();
        System.out.print("Enter task description: ");
        String description = keyboard.nextLine();
        Task task = new Task(description);
        taskManager.addTask(task);
    }

    public static void executeTask(TaskManager taskManager) {
        Task task = taskManager.executeNextTask();
        if (task != null) {
            System.out.println("Executed task: " + task.getDescription());
        } else {
            System.out.println("No tasks to execute.");
        }
    }

    public static void undoTask(TaskManager taskManager) {
        Task task = taskManager.undoLastTask();
        if (task != null) {
            System.out.println("Undid task: " + task.getDescription());
        } else {
            System.out.println("No tasks to undo.");
        }
    }

    public static void printEventSummary(GuestListManager guestListManager, Venue venue, TaskManager taskManager, SeatingPlanner seatingPlanner) {
        System.out.println("\n===== Event Summary =====");
        
        // Guest information
        System.out.println("\n--- Guests ---");
        System.out.println("Total guests: " + guestListManager.getGuestCount());
        if (guestListManager.getGuestCount() > 0) {
            System.out.println("Guest list:");
            for (Guest guest : guestListManager.getAllGuests()) {
                System.out.println("  - " + guest.getName() + " (Group: " + guest.getGroupTag() + ")");
            }
        }
        
        // Venue information
        System.out.println("\n--- Venue ---");
        if (venue != null) {
            System.out.println("Selected venue: " + venue.getName());
            System.out.println("Capacity: " + venue.getCapacity());
            System.out.println("Cost: $" + venue.getCost());
            System.out.println("Tables: " + venue.getTables() + " (" + venue.getSeatsPerTable() + " seats each)");
        } else {
            System.out.println("No venue selected.");
        }
        
        // Seating information
        System.out.println("\n--- Seating Arrangement ---");
        if (seatingPlanner != null && guestListManager.getGuestCount() > 0) {
            Map<Integer, List<Guest>> seating = seatingPlanner.generateSeating(guestListManager.getAllGuests());
            for (Integer tableNumber : seating.keySet()) {
                System.out.println("Table " + (tableNumber + 1) + ":");
                List<Guest> tableGuests = seating.get(tableNumber);
                for (Guest guest : tableGuests) {
                    System.out.println("  - " + guest.getName());
                }
            }
        } else if (venue == null) {
            System.out.println("No venue selected - cannot generate seating.");
        } else {
            System.out.println("No guests to seat.");
        }
        
        // Task information
        System.out.println("\n--- Tasks ---");
        System.out.println("Remaining tasks: " + taskManager.remainingTaskCount());
        
        System.out.println("\n============================");
    }

    public static int intInput(String question) {
        while (true)
            try {
                System.out.print(question);
                int input = keyboard.nextInt();
                return input;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please try again.");
                keyboard.nextLine();
            }
    }
}
