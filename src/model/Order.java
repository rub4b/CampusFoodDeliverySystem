package model;

public class Order {
    public static final String PENDING = "PENDING";
    public static final String ASSIGNED = "ASSIGNED";
    public static final String DELIVERED = "DELIVERED";
    public static final String CANCELLED = "CANCELLED";

    private String orderId;
    private String studentName;
    private String pickupLocation;
    private String deliveryLocation;
    private int priority;
    private int sequence;
    private String status;
    private String riderId;

    public Order(String orderId, String studentName,
            String pickupLocation, String deliveryLocation,
            int priority, int sequence) {

        this.orderId = orderId;
        this.studentName = studentName;
        this.pickupLocation = pickupLocation;
        this.deliveryLocation = deliveryLocation;
        this.priority = priority;
        this.sequence = sequence;
        this.status = PENDING;
        this.riderId = "-";
    }

    public String getOrderId() {
        return orderId;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public String getDeliveryLocation() {
        return deliveryLocation;
    }

    public int getPriority() {
        return priority;
    }

    public int getSequence() {
        return sequence;
    }

    public String getStatus() {
        return status;
    }

    public String getRiderId() {
        return riderId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRiderId(String riderId) {
        this.riderId = riderId;
    }

    @Override
    public String toString() {
        return "OrderID: " + orderId
                + " | Student: " + studentName
                + " | Pickup: " + pickupLocation
                + " | Delivery: " + deliveryLocation
                + " | Priority: " + priority
                + " | Seq: " + sequence
                + " | Status: " + status
                + " | Rider: " + riderId;
    }
}
