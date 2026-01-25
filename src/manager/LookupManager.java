package manager;

import ds.MyHashMap;
import model.Order;
import model.Rider;

public class LookupManager {
    private MyHashMap<String, Rider> riderMap;
    private MyHashMap<String, Order> orderMap;

    public LookupManager() {
        // Custom HashMap
        this.riderMap = new MyHashMap<>();
        this.orderMap = new MyHashMap<>();
    }

    // Rider Management
    public void addRider(Rider rider) {
        riderMap.put(rider.getRiderId(), rider);
    }

    public Rider searchRiderById(String id) {
        return riderMap.get(id); // O(1) average time complexity
    }

    // Search Functions
    public void searchOrderById(String orderId) {
        Order order = orderMap.get(orderId);
        if (order != null) {
            System.out.println("Order Found: " + order.toString());
        } else {
            System.out.println("Order ID " + orderId + " not found.");
        }
    }

    public void searchOrdersByStudent(Order[] allOrders, String studentName) {
        System.out.println("Searching for orders by: " + studentName);
        boolean found = false;
        for (int i = 0; i < allOrders.length; i++) {
            if (allOrders[i] != null && allOrders[i].getStudentName().equalsIgnoreCase(studentName)) {
                System.out.println(allOrders[i].toString());
                found = true;
            }
        }
        if (!found)
            System.out.println("No orders found for student: " + studentName);
    }

    public Rider findAvailableRider() {
        // Return first available rider for simplicity
        ds.MyLinkedList<String> keys = riderMap.keySet();
        ds.Node<String> curr = keys.head;
        while (curr != null) {
            Rider r = riderMap.get(curr.data);
            if (r != null && r.getStatus().equals("Available")) {
                return r;
            }
            curr = curr.nextNode;
        }
        return null;
    }

    public Rider findClosestRider(String targetLocation, graph.CampusGraph graph) {
        Rider closest = null;
        double minDistance = Double.MAX_VALUE;

        ds.MyLinkedList<String> keys = riderMap.keySet();
        ds.Node<String> curr = keys.head;
        while (curr != null) {
            Rider r = riderMap.get(curr.data);
            if (r != null && r.getStatus().equals("Available")) {
                double dist = graph.getShortestPath(r.getCurrentLocation(), targetLocation);
                if (dist != -1 && dist < minDistance) {
                    minDistance = dist;
                    closest = r;
                }
            }
            curr = curr.nextNode;
        }
        return closest;
    }

    public ds.MyLinkedList<Rider> getAllRiders() {
        ds.MyLinkedList<Rider> riders = new ds.MyLinkedList<>();
        ds.MyLinkedList<String> keys = riderMap.keySet();
        ds.Node<String> curr = keys.head;
        while (curr != null) {
            riders.insertAtHead(riderMap.get(curr.data));
            curr = curr.nextNode;
        }
        return riders;
    }
}
