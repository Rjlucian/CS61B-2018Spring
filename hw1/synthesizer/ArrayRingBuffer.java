package synthesizer;
import java.util.Iterator;

public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    private int first;
    private int last;
    private T[] items;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        this.first = 0;
        this.last = 0;
        this.fillCount = 0;
        this.capacity = capacity;
        this.items = (T[]) new Object[capacity];
    }

    @Override
    public int capacity() {
        return this.capacity;
    }

    @Override
    public int fillCount() {
        return this.fillCount;
    }

    @Override
    public void enqueue(T x) {
        if (isFull()) {
            throw new RuntimeException("Ring buffer overflow");
        }
        items[last] = x;
        last = (last + 1) % capacity;
        fillCount++;
    }

    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        T item = items[first];
        first = (first + 1) % capacity;
        fillCount--;
        return item;
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        return items[first];
    }

    @Override
    public Iterator<T> iterator() {
        return new BufferIterator();
    }

    private class BufferIterator implements Iterator<T> {
        private int offset;

        BufferIterator() {
            this.offset = 0;
        }

        @Override
        public boolean hasNext() {
            return offset <= fillCount() - 1;
        }

        @Override
        public T next() {
            int index = (first + offset) % capacity;
            T returnItem = items[index];
            offset++;
            return returnItem;
        }

    }
}
