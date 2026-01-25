package manager;

import ds.MyArrayList;
import ds.MyHashMap;
import ds.MyPriorityQueue;
import ds.OrderComparator;
import model.Order;

public class OrderManager {

    private MyPriorityQueue<Order> pendingQueue;
    private MyHashMap<String, Order> orderMap;
    private MyArrayList<Order> orderList;
    private int nextSequence;

    public OrderManager() {

        pendingQueue = new MyPriorityQueue<>(new OrderComparator<Order>() {
            public int compare(Order a, Order b) {
                if (a.getPriority() != b.getPriority()) {
                    return a.getPriority() - b.getPriority(); // urgent first
                }
                return a.getSequence() - b.getSequence(); // FIFO
            }
        });

        orderMap = new MyHashMap<>();
        orderList = new MyArrayList<>();
        nextSequence = 1;
    }

    private String clean(String s) {
        return s == null ? "" : s.trim();
    }

    public boolean createOrder(String id, String name,
            String pickup, String delivery,
            int priority) {

        id = clean(id);
        name = clean(name);
        pickup = clean(pickup);
        delivery = clean(delivery);

        if (id.isEmpty() || name.isEmpty() ||
                pickup.isEmpty() || delivery.isEmpty()) {
            return false;
        }

        if (priority != 1 && priority != 2) {
            return false;
        }

        if (orderMap.containsKey(id)) {
            return false;
        }

        Order o = new Order(id, name, pickup, delivery, priority, nextSequence);
        nextSequence++;

        orderMap.put(id, o);
        orderList.add(o);
        pendingQueue.add(o);

        return true;
    }

    public void addOrder(Order order) {
        if (order == null)
            return;
        orderMap.put(order.getOrderId(), order);
        orderList.add(order);
        pendingQueue.add(order);
    }

    public Order findOrderById(String id) {
        return orderMap.get(clean(id));
    }

    public MyArrayList<Order> getAllOrders() {
        return orderList;
    }

    public MyArrayList<Order> getOrdersByStatus(String status) {
        MyArrayList<Order> result = new MyArrayList<>();

        for (int i = 0; i < orderList.size(); i++) {
            Order o = orderList.get(i);
            if (o != null && o.getStatus().equals(status)) {
                result.add(o);
            }
        }
        return result;
    }

    private void cleanTop() {
        while (!pendingQueue.isEmpty()) {
            Order top = pendingQueue.peek();
            if (top.getStatus().equals(Order.PENDING)) {
                return;
            }
            pendingQueue.poll();
        }
    }

    public Order peekNextPending() {
        cleanTop();
        return pendingQueue.peek();
    }

    public Order getNextOrder() {
        return peekNextPending();
    }

    public boolean assignNextPending(String riderId) {
        cleanTop();
        if (pendingQueue.isEmpty()) {
            return false;
        }

        Order o = pendingQueue.poll();
        o.setStatus(Order.ASSIGNED);
        o.setRiderId(clean(riderId));

        return true;
    }

    public boolean assignOrderById(String id, String riderId) {
        Order o = findOrderById(id);
        if (o == null)
            return false;
        if (!o.getStatus().equals(Order.PENDING))
            return false;

        o.setStatus(Order.ASSIGNED);
        o.setRiderId(clean(riderId));
        return true;
    }

    public boolean completeOrder(String id) {
        Order o = findOrderById(id);
        if (o == null)
            return false;
        if (!o.getStatus().equals(Order.ASSIGNED))
            return false;

        o.setStatus(Order.DELIVERED);
        return true;
    }

    public boolean cancelOrder(String id) {
        Order o = findOrderById(id);
        if (o == null)
            return false;
        if (o.getStatus().equals(Order.DELIVERED))
            return false;

        o.setStatus(Order.CANCELLED);
        return true;
    }
}