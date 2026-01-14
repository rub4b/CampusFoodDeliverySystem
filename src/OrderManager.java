/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package orders;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Comparator;
import model.Order;

/**
 *
 * @author ASUS
 */
public class OrderManager {
    private PriorityQueue<Order> pendingQueue;
    private HashMap<String, Order> orderMap;
    private ArrayList<Order> orderList;
    private int nextSequence;

    public OrderManager() {
        pendingQueue = new PriorityQueue<>(new Comparator<Order>() {
            @Override
            public int compare(Order a, Order b) {
                if (a.getPriority() != b.getPriority()) {
                    return a.getPriority() - b.getPriority(); // urgent first
                }
                return a.getSequence() - b.getSequence();     // FIFO
            }
        });

        orderMap = new HashMap<>();
        orderList = new ArrayList<>();
        nextSequence = 1;
    }

    private String clean(String s) {
        return s == null ? "" : s.trim();
    }

    public boolean createOrder(String id, String name, String pickup,
                               String delivery, int priority) {

        id = clean(id);
        name = clean(name);
        pickup = clean(pickup);
        delivery = clean(delivery);

        if (id.isEmpty() || name.isEmpty() || pickup.isEmpty() || delivery.isEmpty())
            return false;

        if (priority != 1 && priority != 2)
            return false;

        if (orderMap.containsKey(id))
            return false;

        Order o = new Order(id, name, pickup, delivery, priority, nextSequence);
        nextSequence++;

        orderMap.put(id, o);
        orderList.add(o);
        pendingQueue.add(o);

        return true;
    }

    public Order findOrderById(String id) {
        return orderMap.get(clean(id));
    }

    public ArrayList<Order> getAllOrders() {
        return orderList;
    }

    public ArrayList<Order> getOrdersByStatus(String status) {
        ArrayList<Order> result = new ArrayList<>();
        for (Order o : orderList) {
            if (o.getStatus().equals(status)) {
                result.add(o);
            }
        }
        return result;
    }

    public ArrayList<Order> searchByStudentName(String keyword) {
        ArrayList<Order> result = new ArrayList<>();
        keyword = clean(keyword).toLowerCase();

        for (Order o : orderList) {
            if (o.getStudentName().toLowerCase().contains(keyword)) {
                result.add(o);
            }
        }
        return result;
    }

    // Lazy removal
    private void cleanTop() {
        while (!pendingQueue.isEmpty()) {
            Order top = pendingQueue.peek();
            if (top.getStatus().equals(Order.PENDING)) return;
            pendingQueue.poll();
        }
    }

    public Order peekNextPending() {
        cleanTop();
        return pendingQueue.peek();
    }

    public boolean assignNextPending(String riderId) {
        cleanTop();
        if (pendingQueue.isEmpty()) return false;

        Order o = pendingQueue.poll();
        o.setStatus(Order.ASSIGNED);
        o.setRiderId(clean(riderId));

        return true;
    }

    public boolean assignOrderById(String id, String riderId) {
        Order o = findOrderById(id);
        if (o == null) return false;
        if (!o.getStatus().equals(Order.PENDING)) return false;

        o.setStatus(Order.ASSIGNED);
        o.setRiderId(clean(riderId));
        return true;
    }

    public boolean completeOrder(String id) {
        Order o = findOrderById(id);
        if (o == null) return false;
        if (!o.getStatus().equals(Order.ASSIGNED)) return false;

        o.setStatus(Order.DELIVERED);
        return true;
    }

    public boolean cancelOrder(String id) {
        Order o = findOrderById(id);
        if (o == null) return false;
        if (o.getStatus().equals(Order.DELIVERED)) return false;

        o.setStatus(Order.CANCELLED);
        return true;
    }
}
