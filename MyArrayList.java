/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ds;

/**
 *
 * @author ASUS
 */
public class MyArrayList<T> {

    private Object[] data;
    private int size;

    public MyArrayList() {
        data = new Object[10];
        size = 0;
    }

    public int size() {
        return size;
    }

    public void add(T value) {
        if (size == data.length) {
            grow();
        }
        data[size] = value;
        size++;
    }

    public T get(int index) {
        if (index < 0 || index >= size) return null;
        return (T) data[index];
    }

    public void set(int index, T value) {
        if (index < 0 || index >= size) return;
        data[index] = value;
    }

    private void grow() {
        Object[] newData = new Object[data.length * 2];
        for (int i = 0; i < data.length; i++) {
            newData[i] = data[i];
        }
        data = newData;
    }
}

