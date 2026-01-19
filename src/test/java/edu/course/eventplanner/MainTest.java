package edu.course.eventplanner;

import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.model.Task;
import edu.course.eventplanner.model.Venue;
import edu.course.eventplanner.service.GuestListManager;
import edu.course.eventplanner.service.SeatingPlanner;
import edu.course.eventplanner.service.TaskManager;
import edu.course.eventplanner.service.VenueSelector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    private GuestListManager guestListManager;
    private TaskManager taskManager;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        guestListManager = new GuestListManager();
        taskManager = new TaskManager();
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void testLoadSampleData() {
        VenueSelector venueSelector = Main.loadSampleData(guestListManager);
        assertNotNull(venueSelector);
        assertEquals(20, guestListManager.getGuestCount());
    }

    @Test
    void testAddGuest() {
        String input = "\nJohn Doe\nfamily\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        Main.addGuest(scanner, guestListManager);
        assertEquals(1, guestListManager.getGuestCount());
        assertNotNull(guestListManager.findGuest("John Doe"));
    }

    @Test
    void testRemoveGuestSuccess() {
        guestListManager.addGuest(new Guest("Jane Doe", "friends"));
        String input = "Jane Doe\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        Main.removeGuest(scanner, guestListManager);
        assertEquals(0, guestListManager.getGuestCount());
        assertTrue(outputStream.toString().contains("removed"));
    }

    @Test
    void testRemoveGuestNotFound() {
        String input = "NonExistent\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        Main.removeGuest(scanner, guestListManager);
        assertTrue(outputStream.toString().contains("not found"));
    }

    @Test
    void testVenueSelectWithGuests() {
        guestListManager.addGuest(new Guest("Guest1", "family"));
        VenueSelector venueSelector = Main.loadSampleData(guestListManager);
        String input = "5000\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        Venue venue = Main.venueSelect(scanner, venueSelector, guestListManager);
        assertNotNull(venue);
    }

    @Test
    void testVenueSelectNoSuitableVenue() {
        guestListManager.addGuest(new Guest("Guest1", "family"));
        VenueSelector venueSelector = Main.loadSampleData(guestListManager);
        String input = "100\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        Venue venue = Main.venueSelect(scanner, venueSelector, guestListManager);
        assertNull(venue);
        assertTrue(outputStream.toString().contains("No suitable venue"));
    }

    @Test
    void testVenueSelectWithNoGuests() {
        VenueSelector venueSelector = Main.loadSampleData(new GuestListManager());
        String input = "5000\n50\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        GuestListManager emptyManager = new GuestListManager();
        Venue venue = Main.venueSelect(scanner, venueSelector, emptyManager);
        assertNotNull(venue);
    }

    @Test
    void testSeatGuests() {
        guestListManager.addGuest(new Guest("Guest1", "family"));
        guestListManager.addGuest(new Guest("Guest2", "friends"));
        Venue venue = new Venue("Test Venue", 1000, 50, 5, 10);
        SeatingPlanner seatingPlanner = new SeatingPlanner(venue);
        Main.seatGuests(seatingPlanner, guestListManager);
        assertTrue(outputStream.toString().contains("Table"));
    }

    @Test
    void testAddTask() {
        String input = "\nSetup decorations\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        Main.addTask(scanner, taskManager);
        assertEquals(1, taskManager.remainingTaskCount());
    }

    @Test
    void testExecuteTaskWithTasks() {
        taskManager.addTask(new Task("Test task"));
        Main.executeTask(taskManager);
        assertEquals(0, taskManager.remainingTaskCount());
        assertTrue(outputStream.toString().contains("Executed task"));
    }

    @Test
    void testExecuteTaskNoTasks() {
        Main.executeTask(taskManager);
        assertTrue(outputStream.toString().contains("No tasks to execute"));
    }

    @Test
    void testUndoTaskWithTasks() {
        taskManager.addTask(new Task("Test task"));
        taskManager.executeNextTask();
        Main.undoTask(taskManager);
        assertTrue(outputStream.toString().contains("Undid task"));
    }

    @Test
    void testUndoTaskNoTasks() {
        Main.undoTask(taskManager);
        assertTrue(outputStream.toString().contains("No tasks to undo"));
    }

    @Test
    void testPrintMenu() {
        String input = "5\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        int choice = Main.printMenu(scanner);
        assertEquals(5, choice);
        assertTrue(outputStream.toString().contains("Event Planner Menu"));
    }

    @Test
    void testIntInput() {
        String input = "42\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        int result = Main.intInput(scanner, "Enter number: ");
        assertEquals(42, result);
    }

    @Test
    void testIntInputWithInvalidThenValid() {
        String input = "abc\n123\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        int result = Main.intInput(scanner, "Enter number: ");
        assertEquals(123, result);
        assertTrue(outputStream.toString().contains("Invalid input"));
    }

    @Test
    void testBuildEventSummaryWithNoData() {
        String summary = Main.buildEventSummary(guestListManager, null, taskManager, null);
        assertTrue(summary.contains("Event Summary"));
        assertTrue(summary.contains("Total guests: 0"));
        assertTrue(summary.contains("No venue selected"));
        assertTrue(summary.contains("No venue selected - cannot generate seating"));
    }

    @Test
    void testBuildEventSummaryWithVenueNoGuests() {
        Venue venue = new Venue("Test Venue", 1000, 50, 5, 10);
        SeatingPlanner seatingPlanner = new SeatingPlanner(venue);
        String summary = Main.buildEventSummary(guestListManager, venue, taskManager, seatingPlanner);
        assertTrue(summary.contains("Test Venue"));
        assertTrue(summary.contains("No guests to seat"));
    }

    @Test
    void testBuildEventSummaryWithFullData() {
        guestListManager.addGuest(new Guest("Alice", "family"));
        guestListManager.addGuest(new Guest("Bob", "friends"));
        Venue venue = new Venue("Grand Hall", 2000, 100, 10, 10);
        SeatingPlanner seatingPlanner = new SeatingPlanner(venue);
        taskManager.addTask(new Task("Setup"));
        
        String summary = Main.buildEventSummary(guestListManager, venue, taskManager, seatingPlanner);
        assertTrue(summary.contains("Total guests: 2"));
        assertTrue(summary.contains("Alice"));
        assertTrue(summary.contains("Bob"));
        assertTrue(summary.contains("Grand Hall"));
        assertTrue(summary.contains("Table"));
        assertTrue(summary.contains("Remaining tasks: 1"));
    }

    @Test
    void testPrintEventSummary() {
        Main.printEventSummary(guestListManager, null, taskManager, null);
        assertTrue(outputStream.toString().contains("Event Summary"));
    }
}
