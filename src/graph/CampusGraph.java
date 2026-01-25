package graph;

import ds.Node;
import ds.MyHashMap;
import ds.MyLinkedList;
import ds.MyPriorityQueue;
import ds.OrderComparator;

public class CampusGraph {
    private MyHashMap<String, MyLinkedList<Edge>> adjList = new MyHashMap<>(20);

    public void addLocation(String loc) {
        if (adjList.get(loc) == null) {
            adjList.put(loc, new MyLinkedList<Edge>());
            System.out.println("Location added: " + loc);
        } else {
            System.out.println("Location already exists.");
        }
    }

    public void addRoute(String from, String to, int dist) {
        if (adjList.get(from) == null || adjList.get(to) == null) {
            System.out.println("Error: Locations do not exist.");
            return;
        }
        adjList.get(from).insertAtHead(new Edge(to, dist));
        adjList.get(to).insertAtHead(new Edge(from, dist));
        System.out.println("Route added: " + from + " <-> " + to);
    }

    public void displayGraph() {
        MyLinkedList<String> keys = adjList.keySet();
        Node<String> curr = keys.head;
        while (curr != null) {
            System.out.print(curr.data + " -> ");
            adjList.get(curr.data).display();
            System.out.println();
            curr = curr.nextNode;
        }
    }

    public double getShortestPath(String start, String target) {
        MyHashMap<String, Integer> dists = new MyHashMap<>(20);
        MyHashMap<String, String> prev = new MyHashMap<>(20);

        OrderComparator<NodeDist> comp = new OrderComparator<NodeDist>() {
            @Override
            public int compare(NodeDist a, NodeDist b) {
                return Integer.compare(a.dist, b.dist);
            }
        };

        MyPriorityQueue<NodeDist> pq = new MyPriorityQueue<>(comp);

        if (adjList.get(start) == null || adjList.get(target) == null) {
            System.err.println("Error: One or both locations do not exist in the map.");
            return -1;
        }

        Node<String> locNode = adjList.keySet().head;
        while (locNode != null) {
            dists.put(locNode.data, Integer.MAX_VALUE);
            locNode = locNode.nextNode;
        }

        dists.put(start, 0);
        pq.add(new NodeDist(start, 0));

        while (!pq.isEmpty()) {
            NodeDist top = pq.poll();
            if (top.node.equals(target))
                break;

            Node<Edge> edgeNode = adjList.get(top.node).head;
            while (edgeNode != null) {
                int newDist = (int) (dists.get(top.node) + edgeNode.data.distanceKm);
                if (newDist < dists.get(edgeNode.data.destination)) {
                    dists.put(edgeNode.data.destination, newDist);
                    prev.put(edgeNode.data.destination, top.node);
                    pq.add(new NodeDist(edgeNode.data.destination, newDist));
                }
                edgeNode = edgeNode.nextNode;
            }
        }

        if (dists.get(target) == Integer.MAX_VALUE) {
            System.out.println("No path found.");
            return -1;
        } else {
            System.out.println("Distance: " + dists.get(target) + " km");
            System.out.print("Path: ");
            printPath(prev, target);
            System.out.println();
            return dists.get(target);
        }
    }

    private void printPath(MyHashMap<String, String> prev, String curr) {
        if (curr == null)
            return;
        printPath(prev, prev.get(curr));
        System.out.print(curr + " ");
    }
}
