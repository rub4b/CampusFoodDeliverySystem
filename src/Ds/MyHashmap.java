/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ds;

/**
 *
 * @author ASUS
 */
public class MyHashmap<K, V> {

    private static class Entry<K, V> {
        K key;
        V value;

        Entry(K k, V v) {
            key = k;
            value = v;
        }
    }

    private MyArrayList<Entry<K, V>> entries;

    public MyHashmap() {
        entries = new MyArrayList<>();
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    public void put(K key, V value) {
        int idx = indexOfKey(key);
        if (idx == -1) {
            entries.add(new Entry<>(key, value));
        } else {
            entries.set(idx, new Entry<>(key, value));
        }
    }

    public V get(K key) {
        int idx = indexOfKey(key);
        if (idx == -1) return null;
        Entry<K, V> e = entries.get(idx);
        return e == null ? null : e.value;
    }

    private int indexOfKey(K key) {
        for (int i = 0; i < entries.size(); i++) {
            Entry<K, V> e = entries.get(i);
            if (e != null && equalsKey(e.key, key)) {
                return i;
            }
        }
        return -1;
    }

    private boolean equalsKey(K a, K b) {
        if (a == null && b == null) return true;
        if (a == null || b == null) return false;
        return a.equals(b);
    }
}
