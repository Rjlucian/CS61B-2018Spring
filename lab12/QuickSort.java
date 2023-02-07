import edu.princeton.cs.algs4.Queue;

import java.util.Random;

public class QuickSort {
    /**
     * Returns a new queue that contains the given queues catenated together.
     * The items in q2 will be catenated after all of the items in q1.
     */
    private static <Item extends Comparable<Item>> Queue<Item>
        catenate(Queue<Item> q1, Queue<Item> q2) {
        Queue<Item> catenated = new Queue<>();
        for (Item item : q1) {
            catenated.enqueue(item);
        }
        for (Item item: q2) {
            catenated.enqueue(item);
        }
        return catenated;
    }

    /** Returns a random item from the given queue. */
    private static <Item extends Comparable<Item>> Item getRandomItem(Queue<Item> items) {
        int pivotIndex = (int) (Math.random() * items.size());
        Item pivot = null;
        // Walk through the queue to find the item at the given index.
        for (Item item : items) {
            if (pivotIndex == 0) {
                pivot = item;
                break;
            }
            pivotIndex--;
        }
        return pivot;
    }

    /**
     * Partitions the given unsorted queue by pivoting on the given item.
     *
     * @param unsorted  A Queue of unsorted items
     * @param pivot     The item to pivot on
     * @param less      An empty Queue. When the function completes, this queue will contain
     *                  all the items in unsorted that are less than the given pivot.
     * @param equal     An empty Queue. When the function completes, this queue will contain
     *                  all the items in unsorted that are equal to the given pivot.
     * @param greater   An empty Queue. When the function completes, this queue will contain
     *                  all the items in unsorted that are greater than the given pivot.
     */
    private static <Item extends Comparable<Item>> void partition(
            Queue<Item> unsorted, Item pivot,
            Queue<Item> less, Queue<Item> equal, Queue<Item> greater) {
        while (!unsorted.isEmpty()) {
            Item item = unsorted.dequeue();
            int cp = pivot.compareTo(item);
            if (cp > 0) {
                greater.enqueue(item);
            } else if (cp == 0) {
                equal.enqueue(item);
            } else {
                less.enqueue(item);
            }
        }
    }

    /** Returns a Queue that contains the given items sorted from least to greatest. */
    public static <Item extends Comparable<Item>> Queue<Item> quickSort(
            Queue<Item> items) {
        if (items.size() == 0 || items.size() == 1) {
            return items;
        }
        Queue<Item> less = new Queue<>();
        Queue<Item> equal = new Queue<>();
        Queue<Item> greater = new Queue<>();
        Item pivot = getRandomItem(items);
        partition(items, pivot, less, equal, greater);
        quickSort(less);
        quickSort(greater);
        for (Item item : greater) {
            items.enqueue(item);
        }
        for (Item item : equal) {
            items.enqueue(item);
        }
        for (Item item : less) {
            items.enqueue(item);
        }
        return items;
    }


    /**
     * @param size queue's size
     * @return A queue consisted by random numbers
     */
    private static Queue<Integer> makeQueue(int size) {
        Random random = new Random();
        Queue<Integer> queue = new Queue<>();
        for (int i = 1; i <= size; i++) {
            queue.enqueue(random.nextInt(100));
        }
        return queue;
    }

    private static boolean testSorted(Queue<Integer> queue) {
        while (queue.size() > 1) {
            int first = queue.dequeue();
            if (first > queue.peek()) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Queue<Integer> queue = makeQueue(30);
        System.out.println(queue);
        quickSort(queue);
        System.out.println(queue);
        System.out.println(testSorted(queue));
    }
}
