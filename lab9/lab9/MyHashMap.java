package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  @author Your name here
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    private static final int DEFAULT_SIZE = 16;
    private static final double MAX_LF = 0.75;

    private ArrayMap<K, V>[] buckets;
    private int size;

    private int loadFactor() {
        return size / buckets.length;
    }

    public MyHashMap() {
        buckets = new ArrayMap[DEFAULT_SIZE];
        this.clear();
    }

    /* Removes all the mappings from this map. */
    @Override
    public void clear() {
        this.size = 0;
        for (int i = 0; i < this.buckets.length; i += 1) {
            this.buckets[i] = new ArrayMap<>();
        }
    }

    /** Computes the hash function of the given key. Consists of
     *  computing the hashcode, followed by modding by the number of buckets.
     *  To handle negative numbers properly, uses floorMod instead of %.
     */
    private int hash(K key) {
        if (key == null) {
            return 0;
        }

        int numBuckets = buckets.length;
        return Math.floorMod(key.hashCode(), numBuckets);
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        int hCode = hash(key);
        return buckets[hCode].get(key);
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {

        if (!containsKey(key)) {
            size++;
        }
        int hCode = hash(key);
        buckets[hCode].put(key, value);

        if (loadFactor() > loadFactor()) {
            ArrayMap<K, V>[] newBuckets =  new ArrayMap[buckets.length * 2];
            System.arraycopy(buckets, 0, newBuckets, 0, size);
            buckets = newBuckets;
        }
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /**
     * Returns a Set view of the keys contained in this map.
     */
    @Override
    public Set<K> keySet() {
        HashSet<K> set = new HashSet<>();
        for (ArrayMap<K, V> bucket : buckets) {
            for (int j = 0; j < bucket.size; j++) {
                set.addAll(bucket.keySet());
            }
        }
        return set;
    }

    /**
     * Removes the mapping for the specified key from this map if exists.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    @Override
    public V remove(K key) {
        for (int i = 0; i < buckets.length; i++) {
            for (int j = 0; j < buckets[i].size; j++) {
                V value = buckets[i].remove(key);
                if (value != null) {
                    return value;
                }
            }
        }
        return null;
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for this lab. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        for (int i = 0; i < buckets.length; i++) {
            for (int j = 0; j < buckets[i].size; j++) {
                V v = buckets[i].remove(key, value);
                if (v != null) {
                    return value;
                }
            }
        }
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }
}
