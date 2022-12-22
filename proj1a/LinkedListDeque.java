public class LinkedListDeque<T> {
    private Node sentinel;
    private int size;

    public LinkedListDeque() {
        this.sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.pre = sentinel;
        size = 0;
    }
    public void addFirst(T item) {
        size++;
        Node oldFirst = sentinel.next;
        Node newFirst = new Node(item, sentinel, oldFirst);
        sentinel.next = newFirst;
        oldFirst.pre = newFirst;
    }

    public void addLast(T item) {
        size++;
        Node oldLast = sentinel.pre;
        Node newLast = new Node(item, oldLast, sentinel);
        sentinel.pre = newLast;
        oldLast.next = newLast;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size() {
        return this.size;
    }

    public void printDeque() {
        Node cur = sentinel.next;
        while (cur != sentinel) {
            System.out.print(cur.item + " ");
            cur = cur.next;
        }
        System.out.println();
    }

    public T removeFirst() {
        if (this.size == 0) {
            return null;
        }
        size--;
        Node oldFirst = sentinel.next;
        Node newFirst = oldFirst.next;
        oldFirst.pre = null;
        oldFirst.next = null;
        sentinel.next = newFirst;
        newFirst.pre = sentinel;
        return oldFirst.item;
    }

    public T removeLast() {
        if (this.size == 0) {
            return null;
        }
        size--;
        Node oldLast = sentinel.pre;
        Node newLast = oldLast.pre;
        oldLast.pre = null;
        oldLast.next = null;
        sentinel.pre = newLast;
        newLast.next = sentinel;
        return oldLast.item;
    }

    public T get(int index) {
        if (index < -1 || index >= size) {
            return null;
        }
        Node cur = sentinel;
        for (int i = -1; i < index; i++) {
            cur = cur.next;
        }
        return cur.item;
    }

    private T help(Node node, int index, int cur) {
        if (index == cur) {
            return node.item;
        } else {
            return help(node.next, index, cur + 1);
        }

    }

    public T getRecursive(int index) {
        if (index < -1 || index >= size) {
            return null;
        }
        return help(sentinel.next, index, 0);
    }
    private class Node {
        private T item;
        private Node pre;
        private Node next;

        private Node(T item, Node pre, Node next) {
            this.item = item;
            this.pre = pre;
            this.next = next;
        }
    }

}
