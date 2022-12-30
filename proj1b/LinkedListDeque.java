public class LinkedListDeque<Item> implements Deque<Item>{
    private Node sentinel;
    private int size;

    public LinkedListDeque() {
        this.sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.pre = sentinel;
        size = 0;
    }

    @Override
    public void addFirst(Item item) {
        size++;
        Node oldFirst = sentinel.next;
        Node newFirst = new Node(item, sentinel, oldFirst);
        sentinel.next = newFirst;
        oldFirst.pre = newFirst;
    }

    @Override
    public void addLast(Item item) {
        size++;
        Node oldLast = sentinel.pre;
        Node newLast = new Node(item, oldLast, sentinel);
        sentinel.pre = newLast;
        oldLast.next = newLast;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void printDeque() {
        Node cur = sentinel.next;
        while (cur != sentinel) {
            System.out.print(cur.item + " ");
            cur = cur.next;
        }
        System.out.println();
    }

    @Override
    public Item removeFirst() {
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

    @Override
    public Item removeLast() {
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

    @Override
    public Item get(int index) {
        if (index < -1 || index >= size) {
            return null;
        }
        Node cur = sentinel;
        for (int i = -1; i < index; i++) {
            cur = cur.next;
        }
        return cur.item;
    }

    private Item help(Node node, int index, int cur) {
        if (index == cur) {
            return node.item;
        } else {
            return help(node.next, index, cur + 1);
        }

    }

    public Item getRecursive(int index) {
        if (index < -1 || index >= size) {
            return null;
        }
        return help(sentinel.next, index, 0);
    }

    private class Node {
        private Item item;
        private Node pre;
        private Node next;

        private Node(Item item, Node pre, Node next) {
            this.item = item;
            this.pre = pre;
            this.next = next;
        }
    }

}
