/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ds;

/**
 *
 * @author ASUS
 */

public class MyPriorityQueue<T> {

    private Object[] heap;
    private int size;
    private OrderComparator<T> comp;

    public MyPriorityQueue(OrderComparator<T> comp) {
        this.comp = comp;
        heap = new Object[10];
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void add(T value) {
        if (size == heap.length) {
            grow();
        }
        heap[size] = value;
        siftUp(size);
        size++;
    }

    public T peek() {
        if (size == 0) return null;
        return (T) heap[0];
    }

    public T poll() {
        if (size == 0) return null;

        T top = (T) heap[0];
        size--;
        heap[0] = heap[size];
        heap[size] = null;

        siftDown(0);
        return top;
    }

    private void siftUp(int idx) {
        while (idx > 0) {
            int parent = (idx - 1) / 2;

            T cur = (T) heap[idx];
            T par = (T) heap[parent];

            if (comp.compare(cur, par) >= 0) break;

            heap[idx] = par;
            heap[parent] = cur;

            idx = parent;
        }
    }

    private void siftDown(int idx) {
        while (true) {
            int left = idx * 2 + 1;
            int right = idx * 2 + 2;
            int smallest = idx;

            if (left < size) {
                T a = (T) heap[left];
                T b = (T) heap[smallest];
                if (comp.compare(a, b) < 0) smallest = left;
            }

            if (right < size) {
                T a = (T) heap[right];
                T b = (T) heap[smallest];
                if (comp.compare(a, b) < 0) smallest = right;
            }

            if (smallest == idx) break;

            Object tmp = heap[idx];
            heap[idx] = heap[smallest];
            heap[smallest] = tmp;

            idx = smallest;
        }
    }

    private void grow() {
        Object[] newHeap = new Object[heap.length * 2];
        for (int i = 0; i < heap.length; i++) {
            newHeap[i] = heap[i];
        }
        heap = newHeap;
    }
}