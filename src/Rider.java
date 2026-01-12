public class Rider {
    private String riderId;
    private String name;
    private String currentLocation;
    private String status; // Available, Delivering, Offline

    public Rider(String riderId, String name, String currentLocation) {
        this.riderId = riderId;
        this.name = name;
        this.currentLocation = currentLocation;
        this.status = "Available"; 
    }

    // Getters and Setters
    public String getRiderId() { return riderId; }
    public String getName() { return name; }
    public String getCurrentLocation() { return currentLocation; }
    public void setCurrentLocation(String location) { this.currentLocation = location; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return String.format("ID: %s | Name: %s | Location: %s | Status: %s", 
                              riderId, name, currentLocation, status);
    }
}
