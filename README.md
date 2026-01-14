# Event Planner Mini

A console-based event planning application for managing guests, venues, seating arrangements, and preparation tasks.

## Data Structures Used

### GuestListManager
- **LinkedList<Guest>**: Used for storing guests because it provides O(1) insertion and there is no need for random access in this system.
- **HashMap<String, Guest>**: Used for fast lookup by guest name, avoiding the need to traverse the entire list.

### TaskManager
- **Deque<Task>**: Used for upcoming tasks because tasks are added to both ends — new tasks are added to the back, and undone tasks are pushed to the front.
- **Stack<Task>**: Used for completed tasks because we only add to the top and remove in LIFO order when undoing.

### VenueSelector
- **ArrayList<Venue>**: Stores venues and is sorted before selection to improve efficiency by only checking venues within budget.

### SeatingPlanner
- **HashMap<String, ArrayList<Guest>>**: Groups guests by category (group tag) for easier seating arrangement.
- **HashMap<Integer, List<Guest>>**: Maps table numbers to their assigned guests.

## Algorithms

### Sorting
- **Collections.sort()** (TimSort): Used in VenueSelector to sort venues by cost. Venues implement `Comparable<Venue>` to enable natural ordering.

### Searching
- **HashMap lookup**: Used for finding guests by name in GuestListManager.
- **Linear search**: Used in VenueSelector to find a suitable venue within budget after sorting.

## Big-O Complexity

| Operation | Time Complexity | Explanation |
|-----------|-----------------|-------------|
| **Finding a guest** | O(1) | HashMap lookup by name |
| **Selecting a venue** | O(n log n) | Sorting dominates; linear search through sorted list is O(n) |
| **Generating seating** | O(n) | Iterates through all guests once, grouped by category |

## Design Rationale

The rationale behind each data structure choice is to optimize for the most common operations:

- **LinkedList + HashMap for guests**: The justification is that we need both efficient iteration (LinkedList) and fast lookup by name (HashMap O(1)). This dual structure ensures we never sacrifice one operation for the other.

- **Deque + Stack for tasks**: The explanation for using a Deque is that it supports FIFO execution of tasks. The Stack naturally handles LIFO undo operations, allowing retrieval of the most recently completed task.

- **Sorted ArrayList for venues**: Sorting venues by cost allows early termination when searching, improving average-case performance.

- **HashMap for seating**: Using group tags as keys provides O(1) access to guest groups, making the seating algorithm efficient.



