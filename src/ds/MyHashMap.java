package ds;

public class MyHashMap<K, V> {
    private static class Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private Entry<K, V>[] buckets;
    private int capacity;
    private int size;

    @SuppressWarnings("unchecked")
    public MyHashMap(int capacity) {
        this.capacity = capacity;
        this.buckets = new Entry[capacity];
        this.size = 0;
    }

    public MyHashMap() {
        this(16);
    }

    private int getBucketIndex(K key) {
        if (key == null)
            return 0;
        int hash = key.hashCode();
        return (hash < 0 ? -hash : hash) % capacity;
    }

    public void put(K key, V value) {
        int index = getBucketIndex(key);
        Entry<K, V> head = buckets[index];
        while (head != null) {
            if (equalsKey(head.key, key)) {
                head.value = value;
                return;
            }
            head = head.next;
        }
        Entry<K, V> newEntry = new Entry<>(key, value);
        newEntry.next = buckets[index];
        buckets[index] = newEntry;
        size++;
    }

    public V get(K key) {
        int index = getBucketIndex(key);
        Entry<K, V> head = buckets[index];
        while (head != null) {
            if (equalsKey(head.key, key))
                return head.value;
            head = head.next;
        }
        return null;
    }

    public boolean containsKey(K key) {
        int index = getBucketIndex(key);
        Entry<K, V> head = buckets[index];
        while (head != null) {
            if (equalsKey(head.key, key))
                return true;
            head = head.next;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public MyLinkedList<K> keySet() {
        MyLinkedList<K> keys = new MyLinkedList<>();
        for (int i = 0; i < capacity; i++) {
            Entry<K, V> head = buckets[i];
            while (head != null) {
                keys.insertAtHead(head.key);
                head = head.next;
            }
        }
        return keys;
    }

    private boolean equalsKey(K a, K b) {
        if (a == null && b == null)
            return true;
        if (a == null || b == null)
            return false;
        return a.equals(b);
    }
}