import java.util.Scanner;

import model.Order;
import model.Rider;
import manager.LookupManager;
import manager.OrderManager;
import manager.StatisticsManager;
import manager.DataManager;
import graph.CampusGraph;

public class Main {
    // 1. Initialize the single instances of your managers
    static CampusGraph campusMap = new CampusGraph();
    static OrderManager orderManager = new OrderManager();
    static LookupManager lookupManager = new LookupManager();
    static StatisticsManager statsManager = new StatisticsManager();
    static DataManager dataManager = new DataManager();
    static ds.MyStack<model.UndoAction> undoStack = new ds.MyStack<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeMap();
        initializeRiders();

        boolean running = true;
        while (running) {
            printMenu();
            if (!scanner.hasNextInt()) {
                System.out.println("Please enter a number.");
                scanner.next();
                continue;
            }
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    manageLocationsMenu();
                    break;
                case 2:
                    createNewOrder();
                    break;
                case 3:
                    viewOrdersMenu();
                    break;
                case 4:
                    viewRiders();
                    break;
                case 5:
                    assignOrder();
                    break;
                case 6:
                    completeOrder();
                    break;
                case 7:
                    cancelOrder();
                    break;
                case 8:
                    searchMenu();
                    break;
                case 9:
                    statsManager.displayStats();
                    break;
                case 10:
                    undo();
                    break;
                case 11:
                    dataManager.saveData(orderManager.getAllOrders(), lookupManager.getAllRiders());
                    System.out.println("Data saved.");
                    break;
                case 12:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void initializeMap() {
        campusMap.addLocation("Library");
        campusMap.addLocation("Canteen");
        campusMap.addLocation("Hostel");
        campusMap.addRoute("Library", "Canteen", 1);
        campusMap.addRoute("Canteen", "Hostel", 2);
    }

    private static void initializeRiders() {
        lookupManager.addRider(new Rider("R1", "John", "Library"));
        lookupManager.addRider(new Rider("R2", "Alice", "Canteen"));
    }

    private static void printMenu() {
        System.out.println("\n--- Campus Food Delivery System ---");
        System.out.println("1. Manage locations and routes");
        System.out.println("2. Create new order");
        System.out.println("3. View orders");
        System.out.println("4. View riders");
        System.out.println("5. Assign next order to rider (Smart)");
        System.out.println("6. Complete an order");
        System.out.println("7. Cancel/delete order");
        System.out.println("8. Search (order/rider)");
        System.out.println("9. View Statistics");
        System.out.println("10. Undo Last Operation");
        System.out.println("11. Save Data");
        System.out.println("12. Exit");
        System.out.print("Choice: ");
    }

    private static void manageLocationsMenu() {
        while (true) {
            System.out.println("\n--- Manage Locations & Routes ---");
            System.out.println("1. Add Location");
            System.out.println("2. Add Route");
            System.out.println("3. Display Adjacency List");
            System.out.println("0. Go Back");
            System.out.print("Choice: ");
            if (!scanner.hasNextInt()) {
                scanner.next();
                continue;
            }
            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 0)
                return;

            switch (choice) {
                case 1:
                    System.out.print("Location Name (0 to go back): ");
                    String loc = scanner.nextLine();
                    if (loc.equals("0"))
                        break;
                    loc = normalize(loc);
                    campusMap.addLocation(loc);
                    undoStack.push(new model.UndoAction(model.UndoAction.Type.ADD_LOCATION, loc));
                    break;
                case 2:
                    System.out.print("From (0 to go back): ");
                    String from = scanner.nextLine();
                    if (from.equals("0"))
                        break;
                    from = normalize(from);

                    System.out.print("To (0 to go back): ");
                    String to = scanner.nextLine();
                    if (to.equals("0"))
                        break;
                    to = normalize(to);

                    System.out.print("Distance (0 to go back): ");
                    if (!scanner.hasNextInt()) {
                        scanner.next();
                        break;
                    }
                    int dist = scanner.nextInt();
                    scanner.nextLine();
                    if (dist == 0)
                        break;

                    campusMap.addRoute(from, to, dist);
                    break;
                case 3:
                    campusMap.displayGraph();
                    break;
            }
        }
    }

    private static void createNewOrder() {
        Order newOrder = inputOrderDetails();
        if (newOrder != null) {
            orderManager.addOrder(newOrder);
            statsManager.recordOrder(newOrder.getPriority());
            undoStack.push(new model.UndoAction(model.UndoAction.Type.ADD_ORDER, newOrder.getOrderId()));
            System.out.println("Order created successfully.");
        }
    }

    private static void viewOrdersMenu() {
        while (true) {
            System.out.println("\n--- View Orders ---");
            System.out.println("1. All Orders");
            System.out.println("2. Pending Orders");
            System.out.println("3. Assigned Orders");
            System.out.println("4. Delivered Orders");
            System.out.println("0. Go Back");
            System.out.print("Choice: ");
            if (!scanner.hasNextInt()) {
                scanner.next();
                continue;
            }
            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 0)
                return;

            switch (choice) {
                case 1:
                    displayOrders(orderManager.getAllOrders());
                    break;
                case 2:
                    displayOrders(orderManager.getOrdersByStatus(Order.PENDING));
                    break;
                case 3:
                    displayOrders(orderManager.getOrdersByStatus(Order.ASSIGNED));
                    break;
                case 4:
                    displayOrders(orderManager.getOrdersByStatus(Order.DELIVERED));
                    break;
            }
        }
    }

    private static void displayOrders(ds.MyArrayList<Order> orders) {
        System.out.println("\n--- Orders List (" + orders.size() + " found) ---");
        if (orders.isEmpty()) {
            System.out.println("No orders found.");
        } else {
            for (int i = 0; i < orders.size(); i++) {
                System.out.println(orders.get(i));
            }
        }
        System.out.println("------------------------------------");
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }

    private static void viewRiders() {
        ds.MyLinkedList<Rider> riders = lookupManager.getAllRiders();
        ds.Node<Rider> curr = riders.head;
        if (curr == null) {
            System.out.println("No riders registered.");
            return;
        }
        while (curr != null) {
            System.out.println(curr.data);
            curr = curr.nextNode;
        }
    }

    private static void completeOrder() {
        System.out.print("Enter Order ID to complete (0 to go back): ");
        String id = scanner.nextLine();
        if (id.equals("0"))
            return;
        Order o = orderManager.findOrderById(id);
        if (o != null && o.getStatus().equals(Order.ASSIGNED)) {
            orderManager.completeOrder(id);
            Rider r = lookupManager.searchRiderById(o.getRiderId());
            if (r != null) {
                r.setCurrentLocation(o.getDeliveryLocation());
                r.setStatus("Available");

                // Track stats on completion
                double d1 = campusMap.getShortestPath(r.getCurrentLocation(), o.getPickupLocation());
                double d2 = campusMap.getShortestPath(o.getPickupLocation(), o.getDeliveryLocation());
                if (d1 != -1 && d2 != -1)
                    statsManager.recordCompletion(d1 + d2);
            }
            System.out.println("Order " + id + " completed.");
        } else {
            System.out.println("Order not found or not in Assigned status.");
        }
    }

    private static void cancelOrder() {
        System.out.print("Enter Order ID to cancel (0 to go back): ");
        String id = scanner.nextLine();
        if (id.equals("0"))
            return;
        Order o = orderManager.findOrderById(id);
        if (o != null) {
            if (o.getStatus().equals(Order.ASSIGNED)) {
                Rider r = lookupManager.searchRiderById(o.getRiderId());
                if (r != null)
                    r.setStatus("Available");
            }
            orderManager.cancelOrder(id);
            System.out.println("Order " + id + " cancelled.");
        } else {
            System.out.println("Order not found.");
        }
    }

    private static void searchMenu() {
        while (true) {
            System.out.println("\n--- Search ---");
            System.out.println("1. Order by ID");
            System.out.println("2. Orders by Student Name");
            System.out.println("3. Rider by ID");
            System.out.println("0. Go Back");
            System.out.print("Choice: ");
            if (!scanner.hasNextInt()) {
                scanner.next();
                continue;
            }
            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 0)
                return;

            switch (choice) {
                case 1:
                    System.out.print("Order ID (0 to go back): ");
                    String id = scanner.nextLine();
                    if (id.equals("0"))
                        break;
                    Order o = orderManager.findOrderById(id);
                    System.out.println(o != null ? o : "Not found.");
                    break;
                case 2:
                    System.out.print("Student Name (0 to go back): ");
                    String name = scanner.nextLine();
                    if (name.equals("0"))
                        break;
                    ds.MyArrayList<Order> all = orderManager.getAllOrders();
                    boolean found = false;
                    for (int i = 0; i < all.size(); i++) {
                        if (all.get(i).getStudentName().equalsIgnoreCase(name)) {
                            System.out.println(all.get(i));
                            found = true;
                        }
                    }
                    if (!found)
                        System.out.println("None found.");
                    break;
                case 3:
                    System.out.print("Rider ID (0 to go back): ");
                    String rId = scanner.nextLine();
                    if (rId.equals("0"))
                        break;
                    Rider r = lookupManager.searchRiderById(rId);
                    System.out.println(r != null ? r : "Not found.");
                    break;
            }
        }
    }

    private static Order inputOrderDetails() {
        System.out.print("Order ID (0 to go back): ");
        String id = scanner.nextLine();
        if (id.equals("0"))
            return null;

        System.out.print("Student Name (0 to go back): ");
        String name = scanner.nextLine();
        if (name.equals("0"))
            return null;

        System.out.print("Pickup (0 to go back): ");
        String pickupRaw = scanner.nextLine();
        if (pickupRaw.equals("0"))
            return null;
        String pickup = normalize(pickupRaw);

        System.out.print("Delivery (0 to go back): ");
        String deliveryRaw = scanner.nextLine();
        if (deliveryRaw.equals("0"))
            return null;
        String delivery = normalize(deliveryRaw);

        System.out.print("Priority (1-Normal, 2-Urgent, 0-Go back): ");
        if (!scanner.hasNextInt()) {
            scanner.next();
            return null;
        }
        int priority = scanner.nextInt();
        scanner.nextLine();
        if (priority == 0)
            return null;

        return new Order(id, name, pickup, delivery, priority, 0);
    }

    private static String normalize(String s) {
        if (s == null || s.isEmpty())
            return "";
        s = s.trim().toLowerCase();
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }

    private static void assignOrder() {
        Order nextOrder = orderManager.getNextOrder();
        if (nextOrder == null) {
            System.out.println("No pending orders.");
            return;
        }

        // SMART ASSIGNMENT: Picks the closest rider
        Rider rider = lookupManager.findClosestRider(nextOrder.getPickupLocation(), campusMap);
        if (rider == null) {
            System.out.println("No available riders.");
            return;
        }

        System.out.println(
                "Smart Assignment: Picked closest Rider " + rider.getName() + " (" + rider.getCurrentLocation() + ")");

        System.out.println("--- Leg 1: To Pickup ---");
        double dist1 = campusMap.getShortestPath(rider.getCurrentLocation(), nextOrder.getPickupLocation());

        System.out.println("--- Leg 2: To Delivery ---");
        double dist2 = campusMap.getShortestPath(nextOrder.getPickupLocation(), nextOrder.getDeliveryLocation());

        if (dist1 != -1 && dist2 != -1) {
            System.out.println("\nAssigned " + rider.getName() + " to Order " + nextOrder.getOrderId());
            System.out.println("Total Estimated Distance: " + (dist1 + dist2) + " km");
            nextOrder.setStatus(Order.ASSIGNED);
            nextOrder.setRiderId(rider.getRiderId());
            rider.setStatus("Delivering");
            undoStack.push(new model.UndoAction(model.UndoAction.Type.ASSIGN_ORDER, nextOrder.getOrderId()));
        } else {
            System.out.println("Error: Path could not be calculated.");
        }
    }

    private static void undo() {
        model.UndoAction action = undoStack.pop();
        if (action == null) {
            System.out.println("Nothing to undo.");
            return;
        }

        switch (action.getType()) {
            case ADD_ORDER:
                orderManager.cancelOrder((String) action.getData());
                System.out.println("Undone: Created Order " + action.getData() + " cancelled.");
                break;
            case ASSIGN_ORDER:
                Order o = orderManager.findOrderById((String) action.getData());
                if (o != null) {
                    Rider r = lookupManager.searchRiderById(o.getRiderId());
                    if (r != null)
                        r.setStatus("Available");
                    o.setStatus(Order.PENDING);
                }
                System.out.println("Undone: Order " + action.getData() + " back to Pending.");
                break;
            case ADD_LOCATION:
                System.out.println("Undone: Addition of " + action.getData()
                        + " cannot be fully deleted from graph adjacency (logic not implemented), but flagged.");
                break;
        }
    }
}