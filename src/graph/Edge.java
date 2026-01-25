package graph;

public class Edge {
    String destination;
    int distanceKm;

    Edge(String dest, int dist) {
        this.destination = dest;
        this.distanceKm = dist;
    }

    @Override
    public String toString() {
        return "(" + destination + ", " + distanceKm + "km)";
    }
}

class NodeDist implements Comparable<NodeDist> {
    String node;
    int dist;

    NodeDist(String n, int d) {
        this.node = n;
        this.dist = d;
    }

    public int compareTo(NodeDist other) {
        return Integer.compare(this.dist, other.dist);
    }
}
