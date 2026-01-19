public class LookupManager {
    private MyHashMap<String, Rider> riderMap;
    private MyHashMap<String, Order> orderMap; 

    public LookupManager() {
        //Custom HashMap
        this.riderMap = new MyHashMap<>();
        this.orderMap = new MyHashMap<>();
    }

    //Rider Management
    public void addRider(Rider rider) {
        riderMap.put(rider.getRiderId(), rider);
    }

    public Rider searchRiderById(String id) {
        return riderMap.get(id); // O(1) average time complexity 
    }

    //Search Functions
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
        if (!found) System.out.println("No orders found for student: " + studentName);
    }
}
