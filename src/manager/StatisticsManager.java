package manager;

public class StatisticsManager {
    private int totalOrders;
    private int urgentOrders;
    private double totalDistance;
    private int completedOrders;

    public void recordOrder(int priority) {
        totalOrders++;
        if (priority == 2)
            urgentOrders++; // 2 = Urgent in our requirement
    }

    public void recordCompletion(double distance) {
        completedOrders++;
        totalDistance += distance;
    }

    public void displayStats() {
        System.out.println("\n--- Stats ---");
        System.out.println("Total Orders Placed: " + totalOrders);
        System.out.println("Urgent Orders: " + urgentOrders);
        System.out.println("Completed Deliveries: " + completedOrders);
        System.out.println("Total Distance Covered: " + String.format("%.2f", totalDistance) + " km");
        System.out.println("Average Distance per Delivery: " +
                (completedOrders == 0 ? 0 : String.format("%.2f", totalDistance / completedOrders)) + " km");
    }

    public int getTotalOrders() {
        return totalOrders;
    }

    public int getUrgentOrders() {
        return urgentOrders;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public int getCompletedOrders() {
        return completedOrders;
    }

    public double getAvgDistance() {
        return completedOrders == 0 ? 0 : totalDistance / completedOrders;
    }
}
