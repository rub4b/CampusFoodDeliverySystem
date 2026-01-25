package ds;

public class DataStructureTest {
    public static void main(String[] args) {
        testHashMap();
        testPriorityQueue();
    }

    private static void testHashMap() {
        System.out.println("Testing MyHashMap...");
        MyHashMap<String, Integer> map = new MyHashMap<>(10);
        map.put("A", 1);
        map.put("B", 2);
        map.put("C", 3);
        map.put("A", 10); // Update

        if (map.get("A") == 10 && map.get("B") == 2 && map.get("C") == 3) {
            System.out.println("  Get/Put: PASSED");
        } else {
            System.out.println("  Get/Put: FAILED");
        }

        if (map.containsKey("B") && !map.containsKey("D")) {
            System.out.println("  ContainsKey: PASSED");
        } else {
            System.out.println("  ContainsKey: FAILED");
        }

        MyLinkedList<String> keys = map.keySet();
        int count = 0;
        Node<String> curr = keys.head;
        while (curr != null) {
            count++;
            curr = curr.nextNode;
        }
        if (count == 3) {
            System.out.println("  KeySet: PASSED");
        } else {
            System.out.println("  KeySet: FAILED (count=" + count + ")");
        }
    }

    private static void testPriorityQueue() {
        System.out.println("Testing MyPriorityQueue...");
        OrderComparator<Integer> intComp = new OrderComparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return a.compareTo(b);
            }
        };

        MyPriorityQueue<Integer> pq = new MyPriorityQueue<>(intComp);
        pq.add(5);
        pq.add(1);
        pq.add(10);
        pq.add(3);

        boolean passed = true;
        if (pq.poll() != 1)
            passed = false;
        if (pq.poll() != 3)
            passed = false;
        if (pq.poll() != 5)
            passed = false;
        if (pq.poll() != 10)
            passed = false;

        if (passed && pq.isEmpty()) {
            System.out.println("  Poll/Order: PASSED");
        } else {
            System.out.println("  Poll/Order: FAILED");
        }
    }
}
