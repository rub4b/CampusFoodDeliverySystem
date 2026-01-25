package ds;

public class MyLinkedList<T> {
    public Node<T> head;
    private int size;

    public MyLinkedList() {
        head = null;
        size = 0;
    }

    public void insertAtHead(T data) {
        Node<T> newNode = new Node<>(data);
        newNode.nextNode = head;
        head = newNode;
        size++;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void display() {
        Node<T> curr = head;
        while (curr != null) {
            System.out.print(curr.data + " ");
            curr = curr.nextNode;
        }
    }
}