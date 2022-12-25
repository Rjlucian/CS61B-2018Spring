public class ArrayDeque<T> {
    private static final double MINISUSAGE = 0.25;
    private T[] items;
    private int size;
    private int capacity;
    private int newLast;
    private int newFirst;

    private double getUsage() {
        return (double) size / capacity;
    }

    private void expand() {
        T[] newItems = (T[]) new Object[capacity * 2];
        int startIndex = newFirst == capacity - 1 ? 0 : newFirst + 1;
        for (int i = 0; i < size; i++) {
            int index = (startIndex + i) % capacity;
            newItems[i] = items[index];
        }
        items = newItems;
        capacity *= 2;
        newFirst = capacity - 1;
        newLast = size;
    }

    private void shrink() {
        T[] newItems = (T[]) new Object[capacity / 2];
        int startIndex = newFirst == capacity ? 0 : newFirst + 1;
        for (int i = 0; i < size; i++) {
            int index = (startIndex + i) % capacity;
            newItems[i] = items[index];
        }
        items = newItems;
        capacity /= 2;
        newFirst = capacity - 1;
        newLast = size;
    }
    public ArrayDeque() {
        size = 0;
        capacity = 8;
        items = (T[]) new Object[capacity];
        newFirst = 4;
        newLast = 5;
    }

    public void addFirst(T item) {
        if (size == capacity) {
            expand();
        }
        size++;
        items[newFirst] = item;
        if (newFirst == 0) {
            newFirst = capacity - 1;
        } else {
            newFirst--;
        }
    }

    public void addLast(T item) {
        if (size == capacity) {
            expand();
        }
        size++;
        items[newLast] = item;
        if (newLast == capacity - 1) {
            newLast = 0;
        } else {
            newLast++;
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return this.size;
    }

    public void printDeque() {
        int startIndex = (newFirst + 1) % capacity;
        for (int i = 0; i < size; i++) {
            int index = (startIndex + i) % capacity;
            System.out.print(items[index] + " ");
        }
        System.out.println();
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        size--;
        int index = newFirst == capacity - 1 ? 0 : newFirst + 1;
        T item = items[index];
        items[index] = null;
        newFirst = index;
        if (capacity >= 16 && getUsage() < MINISUSAGE) {
            shrink();
        }
        return item;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        size--;
        int index = newLast == 0 ? capacity - 1 : newLast - 1;
        T item = items[index];
        items[index] = null;
        newLast = index;
        if (capacity >= 16 && getUsage() < MINISUSAGE) {
            shrink();
        }
        return item;
    }

}



