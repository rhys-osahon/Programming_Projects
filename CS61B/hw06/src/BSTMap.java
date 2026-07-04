import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable, V> implements Map61B<K, V> {
    private class BSTNode {
        private K key;
        private V value;
        private BSTNode greaterNode;
        private BSTNode smallerNode;
        public BSTNode(K k, V v) {
            key = k;
            value = v;
        }
        public K getKey() {

            return key;
        }
        public V getValue() {

            return value;
        }
        public BSTNode getGreater() {

            return greaterNode;
        }
        public BSTNode getSmaller() {

            return smallerNode;
        }
        public void setValue(V v) {

            value = v;
        }
        public void setGreater(BSTNode g) {
            greaterNode = g;
        }
        public void setSmaller(BSTNode s) {
            smallerNode = s;
        }
        private BSTNode findParent(K k) {
            if (compareKeys(k) > 0) {
                if (greaterNode == null) {
                    return this;
                }
                return greaterNode.findParent(k);
            } else if (compareKeys(k) < 0) {
                if (smallerNode == null) {
                    return this;
                }
                return smallerNode.findParent(k);
            } else {
                return this;
            }
        }
        private int compareKeys(K k) {
            return key.compareTo(k);
        }
    }
    private BSTNode root;
    private int size;
    public BSTMap() {

        root = null;
        size = 0;
    }
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
        if (root == null) {
            root = new BSTNode(key, value);
            size++;
        } else {
            BSTNode parent = root.findParent(key);
            int compareNum = parent.compareKeys(key);
            if (compareNum == 0) {
                parent.setValue(value);
            } else if (compareNum > 0) {
                parent.setGreater(new BSTNode(key, value));
                size++;
            } else if (compareNum < 0) {
                parent.setSmaller(new BSTNode(key, value));
                size++;
            }
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
        if (root == null) {
            return null;
        }
        BSTNode parentOrMatch = root.findParent(key);
        int compareNum = parentOrMatch.compareKeys(key);
        if (compareNum == 0) {
            return parentOrMatch.getValue();
        } else {
            if (compareNum > 0) {
                if (parentOrMatch.getGreater() != null) {
                    if (parentOrMatch.getGreater().compareKeys(key) == 0) {
                        return parentOrMatch.getGreater().getValue();
                    }
                }
            } else {
                if (parentOrMatch.getSmaller() != null) {
                    if (parentOrMatch.getSmaller().compareKeys(key) == 0) {
                        return parentOrMatch.getSmaller().getValue();
                    }
                }
            }
            return null;
        }
    }

    /**
     * Returns whether this map contains a mapping for the specified key.
     *
     * @param key
     */
    @Override
    public boolean containsKey(K key) {
        if (root == null) {
            return false;
        }
        BSTNode parentOrMatch = root.findParent(key);
        int compareNum = parentOrMatch.compareKeys(key);
        if (compareNum == 0) {
            return true;
        } else {
            if (compareNum > 0) {
                if (parentOrMatch.getGreater() != null) {
                    if (parentOrMatch.getGreater().compareKeys(key) == 0) {
                        return true;
                    }
                }
            } else {
                if (parentOrMatch.getSmaller() != null) {
                    if (parentOrMatch.getSmaller().compareKeys(key) == 0) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Removes every mapping from this map.
     */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException.
     */
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    /**
     * Removes the mapping for the specified key from this map if present,
     * or null if there is no such mapping.
     * Not required for Lab 7. If you don't implement this, throw an
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
}
