# Event Planner Mini

This project demonstrates practical use of data structures:
linked lists, stacks, queues, maps, trees, sorting, and searching.

## What You Must Do
- Implement all TODO methods
- Write JUnit 5 tests for core logic
- Pass instructor autograding tests
- Explain your design choices in this README

See Canvas assignment for full requirements.
 
1- Where and why you used each required data structure
 I used a LinkedList in GuestListManager.java to store the master list of guests.
It allows for efficient O(1) additions at the end of the list.- which is exactly how we want to enter out guests
 I used a HashMap in GuestListManager.java to allow for O(1) searching of guests by their name (the key).
It prevents the need to loop through the entire list every time we want to find someone.
 I used Map<String, Queue<Guest>> in SeatingPlanner.java during the grouping phase to categorize guests by their tags.
The Map provides fast access to group categories, while the Queue preserves the original order of guests within those groups.
 I used a Queue in SeatingPlanner.java to manage the flow of guests into the seating chart. 
By using a Queue, the system guarantees a FIFO seating process, ensuring that guests are seated at the earliest available table based on their arrival order, which maintains fairness.
 I used a TreeMap (BST)	in SeatingPlanner.java to store the final seating chart.
A TreeMap is a BST implementation that keeps the table IDs sorted automatically in ascending order which I wanted
to organize my seating planner.
 I used a Deque (FIFO) in TaskManager.java for upcoming tasks. Using FIFO ensures that tasks are handled in the order they were created, 
which is the exact functionality I wanted the tasks to do list in. I used Deque instead of a regular Queue because i needed
to be able to have the addFirst() and add the task i just undid to the front of the Deque.
 I used a Stack (LIFO) in TaskManager.java for the undo history. Using LIFO allows us to pop the very last completed task back into the queue.

2- Which sorting and searching algorithms you used
 I used the Bubble Sort in VenueSelector to manually sort venues by cost and capacity O(N^2)  bc I thought I had to make my own
I could have used Collections.sort() for more efficeincy but I coded my own instead. (Professor said she wont take off points:)
 I used a Red-Black-Tree under the hood in SeatingPlanner via TreeMap. 
This ensures table IDs are always stored in their natural numerical order O(log N).
 I used Key-Based Searching= Hashing in GuestListManager through a HashMap for guest lookups by name O(1)
I used a linear search in SeatingPlanner in my generateSeating method to filter guests by Group Tag, where every guest must be checked once O(N)

3- Big-O complexity for:
 Finding a guest: O(1). Since I used a HashMap where the guest's name is the unique key,
finding a guest does not depend on the number of guests in the system, and therefore it's a constant time operation.
 Selecting a venue: O(N^2). Selecting a venue uses the Bubble Sort algorithm. 
Bubble sort uses nested loops to compare and swap elements, making its complexity O(N^2), where N is the number of venues.
 Generating seating: O(N). Generating the seating chart is O(n) because it consists of two sequential linear passes.
First, we iterate through the guest list to group them by tag. Second, we iterate through those groups to assign seats.
Since the number of operations increases proportionally with the number of guests it maintains linear time complexity
 
