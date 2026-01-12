import java.util.HashMap;
import java.util.Map;

public class LookupManager {
    // Fast lookup for riders by Rider ID 
    private Map<String, Rider> riderMap;
    // Fast lookup for orders by Order ID 
    // Note: The Order class would be created by the "Queue" teammate
    private Map<String, Order> orderMap; 

    public LookupManager() {
        this.riderMap = new HashMap<>();
        this.orderMap = new HashMap<>();
    }

    // Rider Management 
    public void addRider(Rider rider) {
        riderMap.put(rider.getRiderId(), rider);
    }

    public Rider searchRiderById(String id) {
        return riderMap.get(id); // O(1) time complexity 
    }

    //  Search Functions 
    public void searchOrderById(String orderId) {
        Order order = orderMap.get(orderId);
        if (order != null) {
            System.out.println("Order Found: " + order);
        } else {
            System.out.println("Order ID " + orderId + " not found.");
        }
    }

    public void searchOrdersByStudent(String studentName) {
        System.out.println("Searching for orders by: " + studentName);
        for (Order order : orderMap.values()) {
            if (order.getStudentName().equalsIgnoreCase(studentName)) {
                System.out.println(order);
            }
        }
    }

    public void displayAllRiders() {
        System.out.println("\n--- Campus Riders ---");
        for (Rider r : riderMap.values()) {
            System.out.println(r);
        }
    }
}
