package ds;

public class Node<T> {
    public T data;
    public Node<T> nextNode;

    public Node(T data) {
        this.data = data;
        this.nextNode = null;
    }

    @Override
    public String toString() {
        return data != null ? data.toString() : "null";
    }
}
