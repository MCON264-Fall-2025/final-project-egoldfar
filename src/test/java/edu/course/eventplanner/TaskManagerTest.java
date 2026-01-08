package edu.course.eventplanner;

import edu.course.eventplanner.model.Task;
import edu.course.eventplanner.service.TaskManager;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

public class TaskManagerTest {
    @Test
    void testExecutingTaskMovesItToCompleted() {
        // Arrange
        TaskManager taskManager = new TaskManager();
        Task task = new Task("Shop for flowers");
        taskManager.addTask(task);

        // Act
        Task executed = taskManager.executeNextTask();

        // Assert
        assertEquals(task, executed, "The task executed should be the one we added");
        assertEquals(0, taskManager.remainingTaskCount(), "Queue should be empty after execution");
    }

    @Test
    void testQueueOrderFIFO() {
        // arrange
        TaskManager taskManager = new TaskManager();
        Task first = new Task("First Task");
        Task second = new Task("Second Task");
        taskManager.addTask(first);
        taskManager.addTask(second);

        // Act & Assert (FIFO)
        assertEquals(first, taskManager.executeNextTask(), "First task added should be first executed");
        assertEquals(second, taskManager.executeNextTask(), "Second task added should be second executed");
    }
    @Test
    void testUndoLastTaskLIFO(){
        //arrange
        TaskManager taskManager = new TaskManager();
        Task first = new Task("First Task");
        Task second = new Task("Second Task");
        taskManager.addTask(first);
        taskManager.addTask(second);
        //Execute both tasks
        taskManager.executeNextTask(); // Finishes T1
        taskManager.executeNextTask(); // Finishes T2
        //act and assert
        assertEquals(second, taskManager.undoLastTask(), "Second task added should be undone first");
        assertEquals(first, taskManager.undoLastTask(), "First task added should be undone second");
    }
    @Test
    void testExecutingEmptyQueueReturnsNull() {
        TaskManager taskManager = new TaskManager();
        // Assert - Queue behavior
        assertNull(taskManager.executeNextTask(), "Executing empty queue should return null");
    }

    @Test
    void testUndoingEmptyStackReturnsNull() {
        TaskManager taskManager = new TaskManager();
        // Assert - Stack behavior
        assertNull(taskManager.undoLastTask(), "Undoing empty stack should return null");
    }

}
