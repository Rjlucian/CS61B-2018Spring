package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Ruoji Wang
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key, root);
    }

    private V getHelper(K key, Node p) {
        Node temp = p;
        while (temp != null) {
            int cp = temp.key.compareTo(key);
            if (cp == 0) {
                break;
            } else if (cp > 0) {
                temp = temp.left;
            } else {
                temp = temp.right;
            }
        }
        if (temp == null) {
            return null;
        } else {
            return temp.value;
        }
    }


    /** Inserts the pair (key, value)
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        size++;
        root = putHelper(key, value, root);
    }

    private Node putHelper(K key, V value, Node node) {
        if (node == null) {
            node = new Node(key, value);
        }
        int cp = node.key.compareTo(key);
        if (cp == 0) {
            node.value = value;
        } else if (cp > 0) {
            node.left = putHelper(key, value, node.left);
        } else {
            node.right = putHelper(key, value, node.right);
        }
        return node;
    }

    /** Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    /** Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        HashSet<K> set = new HashSet<>();
        keySetHelper(root, set);
        return set;

    }

    private void keySetHelper(Node node, HashSet<K> set) {
        if (node == null) {
            return;
        }
        set.add(node.key);
        keySetHelper(node.left, set);
        keySetHelper(node.right, set);
    }

    /**
     * @param node a tree's root
     * @return the leftest(minis) node of a binary tree
     */
    private Node findMin(Node node) {
        Node temp = node;
        while (temp.left != null) {
            temp = temp.left;
        }
        return temp;
    }
    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        V value = get(key);
        if (value == null) {
            return null;
        }
        size--;
        root = removeHelper(key, root);
        return value;
    }

    private Node removeHelper(K key, Node node) {
        if (node == null) {
            return null;
        }
        int cp = node.key.compareTo(key);
        if (cp > 0) {
            node.left = removeHelper(key, node.left);
        } else if (cp < 0) {
            node.right = removeHelper(key, node.right);
        } else {
            if (node.left != null && node.right != null) { // node has two children nodes
                Node temp = findMin(node.right);
                node.key = temp.key;
                node.value = temp.value;
                node.right = removeHelper(node.key, node.right);
            } else { // 0 or 1 child
                if (node.left != null) {
                    node = node.left;
                } else {
                    node = node.right;
                }
            }
        }
        return node;
    }

    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/

    @Override
    public V remove(K key, V value) {
        V temp = get(key);
        if (temp == null) {
            return null;
        } else {
            if (!temp.equals(value)) {
                return null;
            } else {
                size--;
                root = removeHelper(key, root);
                return value;
            }
        }
    }

    @Override
    public Iterator<K> iterator() {
        return new BSTMapIterator();
    }

    private class BSTMapIterator implements Iterator<K> {
        private List<K> list;

        private BSTMapIterator() {
            initList(root);
        }

        private void initList(Node node) {
            if (node == null) {
                return;
            }
            list.add(node.key);
            initList(node.left);
            initList(node.right);
        }

        @Override
        public boolean hasNext() {
            return list.size() != 0;
        }

        @Override
        public K next() {
            return list.get(0);
        }
    }
    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
            left = null;
            right = null;
        }
    }
}
