package edu.course.eventplanner;

import edu.course.eventplanner.model.Task;
import edu.course.eventplanner.service.TaskManager;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class TaskManagerTest {

    static ArrayList<Task> tasks = new ArrayList<>();
    TaskManager taskManager;

    @BeforeAll
    static void setUp(){
        tasks.add(new Task("ABC"));
        tasks.add(new Task("DEF"));
        tasks.add(new Task("GHI"));
        tasks.add(new Task("JKL"));
        tasks.add(new Task("MNO"));
    }

    @BeforeEach
    void setUpEach(){
        taskManager=new TaskManager();
    }

    @Test
    void testAddingTask(){
        int i = 0;
        for(Task t:tasks){
            taskManager.addTask(t);
            assertTrue(tasks.contains(t));
            assertEquals(taskManager.remainingTaskCount(), ++i);
        }
    }
    @Test
    void testCompletingTasks(){
        assertNull(taskManager.executeNextTask());
        for(Task t:tasks){
            taskManager.addTask(t);
        }
        assertEquals(taskManager.remainingTaskCount(), tasks.size());

        for(Task t:tasks){
            assertEquals(taskManager.executeNextTask(), t);
        }
        assertNull(taskManager.executeNextTask());
        assertEquals(taskManager.remainingTaskCount(), 0);
    }

    @Test
    void testUndoingTasks(){
        // Test that undoLastTask returns the most recently completed task (LIFO)
        for(Task t : tasks){
            taskManager.addTask(t);
        }
        
        // Execute all tasks
        for(int i = 0; i < tasks.size(); i++){
            taskManager.executeNextTask();
        }
        assertEquals(0, taskManager.remainingTaskCount());
        
        // Undo should return tasks in reverse order (LIFO)
        for(int i = tasks.size() - 1; i >= 0; i--){
            assertEquals(tasks.get(i), taskManager.undoLastTask());
        }
        
        // No more tasks to undo
        assertNull(taskManager.undoLastTask());
    }
}
