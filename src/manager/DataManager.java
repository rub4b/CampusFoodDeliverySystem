package manager;

import java.io.*;
import model.Order;
import model.Rider;
import ds.MyArrayList;
import ds.MyLinkedList;
import ds.Node;

public class DataManager {
    private static final String DATA_DIR = "data/";

    public void saveData(MyArrayList<Order> orders, MyLinkedList<Rider> riders) {
        File dir = new File(DATA_DIR);
        if (!dir.exists())
            dir.mkdirs();

        saveToFile("orders.txt", orders);
        saveToFile("riders.txt", riders);
    }

    @SuppressWarnings("unchecked")
    private void saveToFile(String filename, Object list) {
        try (PrintWriter out = new PrintWriter(new FileWriter(DATA_DIR + filename))) {
            if (list instanceof MyArrayList) {
                MyArrayList<Order> orders = (MyArrayList<Order>) list;
                for (int i = 0; i < orders.size(); i++) {
                    Order o = orders.get(i);
                    out.println(o.getOrderId() + "," + o.getStudentName() + "," + o.getPickupLocation() + "," +
                            o.getDeliveryLocation() + "," + o.getPriority() + "," + o.getStatus() + ","
                            + o.getRiderId());
                }
            } else if (list instanceof MyLinkedList) {
                MyLinkedList<Rider> riders = (MyLinkedList<Rider>) list;
                Node<Rider> curr = riders.head;
                while (curr != null) {
                    Rider r = curr.data;
                    out.println(
                            r.getRiderId() + "," + r.getName() + "," + r.getCurrentLocation() + "," + r.getStatus());
                    curr = curr.nextNode;
                }
            }
        } catch (IOException e) {
            System.err.println("Error saving " + filename + ": " + e.getMessage());
        }
    }
}
