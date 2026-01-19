package edu.course.eventplanner.service;

import edu.course.eventplanner.model.Task;
import java.util.*;

public class TaskManager {
    private final Deque<Task> upcoming = new LinkedList<>();
    private final Stack<Task> completed = new Stack<>();
    public void addTask(Task task) {
        upcoming.addLast(task);
    }
    public Task executeNextTask() {
        if (upcoming.isEmpty()) {
            return null;
        }
        Task next = upcoming.remove();
        completed.add(next);
        return next;
    }
    public Task undoLastTask() {
        if (completed.isEmpty()) {
            return null;
        }
        Task undone = completed.pop();
        return undone;
    }
    public int remainingTaskCount() { return upcoming.size(); }
}
