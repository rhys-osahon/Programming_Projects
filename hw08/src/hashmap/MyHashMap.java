package hashmap;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

/**
 *  A hash table-backed Map implementation.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author Rhys Osahon
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    // You should probably define some more!
    private int elementsNum;
    private int bucketsNum;
    private double loadFactor;
    private final int INITIAL_CAPACITY = 16;
    private final double LOADFACTOR = 0.75;

    /** Constructors */
    public MyHashMap() {
        buckets = new Collection[INITIAL_CAPACITY];
        for (int i = 0; i < INITIAL_CAPACITY; i++) {
            buckets[i] = createBucket();
        }
        bucketsNum = INITIAL_CAPACITY;
        loadFactor = LOADFACTOR;
        elementsNum = 0;
    }

    public MyHashMap(int initialCapacity) {
        buckets = new Collection[initialCapacity];
        for (int i = 0; i < initialCapacity; i++) {
            buckets[i] = createBucket();
        }
        bucketsNum = initialCapacity;
        loadFactor = LOADFACTOR;
        elementsNum = 0;
    }

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor maximum load factor
     */
    public MyHashMap(int initialCapacity, double loadFactor) {
        buckets = new Collection[initialCapacity];
        for (int i = 0; i < initialCapacity; i++) {
            buckets[i] = createBucket();
        }
        bucketsNum = initialCapacity;
        this.loadFactor = loadFactor;
        elementsNum = 0;
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *  Note that this is referring to the hash table bucket itself,
     *  not the hash map itself.
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }
    // Your code won't compile until you do so!
    /**
     * Associates the specified value with the specified key in this map.
     * If the map already contains the specified key, replaces the key's mapping
     * with the value specified.
     *
     * @param key
     * @param value
     */
    @Override
    public void put(K key, V value) {
        int id = bucketId(key);
        Collection<Node> bucket = buckets[id];
        for (Node item: bucket) {
            if (item.key.equals(key)) {
                item.value = value;
                return;
            }
        }
        bucket.add(new Node(key, value));
        elementsNum++;
        if (elementsNum * 1.0 / bucketsNum > loadFactor) {
            resize();
        }
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     *
     * @param key
     */
    @Override
    public V get(K key) {
        int id = bucketId(key);
        Collection<Node> bucket = buckets[id];
        for (Node item: bucket) {
            if (item.key.equals(key)) {
                return item.value;
            }
        }
        return null;
    }

    /**
     * Returns whether this map contains a mapping for the specified key.
     *
     * @param key
     */
    @Override
    public boolean containsKey(K key) {
        int id = bucketId(key);
        Collection<Node> bucket = buckets[id];
        for (Node item: bucket) {
            if (item.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size() {
        return elementsNum;
    }

    /**
     * Removes every mapping from this map.
     */
    @Override
    public void clear() {
        for (int i = 0; i < bucketsNum; i++) {
            buckets[i] = createBucket();
        }
        elementsNum = 0;
    }

    /**
     * Returns a Set view of the keys contained in this map. Not required for this lab.
     * If you don't implement this, throw an UnsupportedOperationException.
     */
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    /**
     * Removes the mapping for the specified key from this map if present,
     * or null if there is no such mapping.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException.
     *
     * @param key
     */
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
    private int bucketId(K key) {
        int id = key.hashCode() % bucketsNum;
        if (id < 0) {
            id += bucketsNum;
        }
        return id;
    }
    private void resize() {
        int newBucketsNum = 2 * bucketsNum;
        MyHashMap newMap = new MyHashMap(newBucketsNum, loadFactor);
        for (Collection bucket: buckets) {
            Collection<Node> currentBucket = bucket;
            for (Node item: currentBucket) {
                newMap.put(item.key, item.value);
            }
        }
        this.buckets = newMap.buckets;
        this.bucketsNum = newMap.bucketsNum;
        this.elementsNum = newMap.elementsNum;
    }
}
