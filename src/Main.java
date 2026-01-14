/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;
import java.util.Scanner;
import model.Order;
import orders.OrderManager;

/**
 *
 * @author ASUS
 */
public class Main {
   

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        OrderManager om = new OrderManager();

        while (true) {
            System.out.println("\n=== ORDER MANAGEMENT ===");
            System.out.println("1. Create Order");
            System.out.println("2. View All Orders");
            System.out.println("3. View Pending Orders");
            System.out.println("4. Assign Next Pending Order");
            System.out.println("5. Complete Order");
            System.out.println("6. Cancel Order");
            System.out.println("0. Exit");
            System.out.print("Choice: ");

            int c = Integer.parseInt(sc.nextLine());

            if (c == 0) break;

            if (c == 1) {
                System.out.print("Order ID: ");
                String id = sc.nextLine();
                System.out.print("Student Name: ");
                String name = sc.nextLine();
                System.out.print("Pickup: ");
                String p = sc.nextLine();
                System.out.print("Delivery: ");
                String d = sc.nextLine();
                System.out.print("Priority (1 urgent, 2 normal): ");
                int pr = Integer.parseInt(sc.nextLine());

                System.out.println(
                    om.createOrder(id, name, p, d, pr)
                    ? "Order created"
                    : "Failed"
                );
            }

            else if (c == 2) {
                for (Order o : om.getAllOrders())
                    System.out.println(o);
            }

            else if (c == 3) {
                for (Order o : om.getOrdersByStatus(Order.PENDING))
                    System.out.println(o);
            }

            else if (c == 4) {
                System.out.print("Rider ID: ");
                String r = sc.nextLine();
                System.out.println(
                    om.assignNextPending(r)
                    ? "Order assigned"
                    : "No pending orders"
                );
            }

            else if (c == 5) {
                System.out.print("Order ID: ");
                String id = sc.nextLine();
                System.out.println(
                    om.completeOrder(id)
                    ? "Order delivered"
                    : "Failed"
                );
            }

            else if (c == 6) {
                System.out.print("Order ID: ");
                String id = sc.nextLine();
                System.out.println(
                    om.cancelOrder(id)
                    ? "Order cancelled"
                    : "Failed"
                );
            }
        }

        sc.close();
    }
}

