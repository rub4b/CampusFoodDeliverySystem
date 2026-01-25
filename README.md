# Campus Food Delivery System

A robust, terminal-based food delivery management system designed for campus environments. This system optimizes order assignments using graph algorithms and tracks delivery statistics using custom data structures.

## 🚀 Features

- **Location & Route Management**: Build and maintain a map of the campus with locations and distances.
- **Order Lifecycle**: Create, view, cancel, and complete delivery orders.
- **Priority Orders**: Support for Normal and Urgent delivery priorities.
- **Smart Assignment**: Automatically assigns orders to the closest available rider using Dijkstra's shortest path algorithm.
- **Search System**: Quickly look up orders by ID or student name, or find rider details.
- **Statistics Portfolio**: Track total orders, urgent orders, completion rates, and average delivery distances.
- **Undo System**: Ability to revert recent actions like creating or assigning orders.
- **Data Persistence**: Save and load system state to maintain data between sessions.

## 🛠️ Data Structures & Algorithms

To ensure optimal performance and educational integrity, this project uses custom implementations of core data structures:
- **MyHashMap**: Used for the adjacency list and distance tracking.
- **MyPriorityQueue**: Optimized for Dijkstra's algorithm.
- **MyArrayList**: For dynamic storage of orders.
- **MyLinkedList**: For managing riders and location keys.
- **MyStack**: Powers the undo functionality.

### Core Algorithm: Dijkstra's
The system uses **Dijkstra's Shortest Path Algorithm** to:
1. Calculate the shortest route between a rider and a pickup point.
2. Determine the most efficient path from pickup to delivery.
3. Identify the "Closest Rider" for smart assignment.

## 📖 How to Run

1. **Compile**:
   ```cmd
   javac -d bin src/*.java src/ds/*.java src/graph/*.java src/manager/*.java src/model/*.java
   ```
2. **Run**:
   ```cmd
   java -cp bin Main
   ```

## 🎮 Usage Guide

Upon launching, the system displays a main menu with 12 options:
1. **Manage locations**: Add new campus buildings or defined routes between them.
2. **Create new order**: Input student details, pickup location, delivery destination, and priority.
3. **View orders**: Filter orders by status (Pending, Assigned, Delivered).
4. **View riders**: List all registered riders and their current status/location.
5. **Assign next order (Smart)**: This is the core logic that finds the best available rider for the highest priority pending order.
6. **Complete an order**: Mark a delivery as finished, which updates stats and moves the rider.
7. **Cancel/delete order**: Remove an order from the system.
8. **Search**: Find specific data quickly.
9. **View Statistics**: See a summary of system performance.
10. **Undo**: Revert the last significant change.
11. **Save Data**: Export current state to files.
12. **Exit**: Gracefully shut down.

---
*Developed for Data Structures Course Project.*
