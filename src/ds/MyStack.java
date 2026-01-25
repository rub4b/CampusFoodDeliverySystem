package ds;

public class MyStack<T> {
    private Object[] elements;
    private int size;

    public MyStack() {
        elements = new Object[10];
        size = 0;
    }

    public void push(T value) {
        if (size == elements.length) {
            grow();
        }
        elements[size++] = value;
    }

    @SuppressWarnings("unchecked")
    public T pop() {
        if (isEmpty())
            return null;
        T val = (T) elements[--size];
        elements[size] = null;
        return val;
    }

    @SuppressWarnings("unchecked")
    public T peek() {
        if (isEmpty())
            return null;
        return (T) elements[size - 1];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void grow() {
        Object[] newArr = new Object[elements.length * 2];
        System.arraycopy(elements, 0, newArr, 0, elements.length);
        elements = newArr;
    }
}
