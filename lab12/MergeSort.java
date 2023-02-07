import edu.princeton.cs.algs4.Queue;

import java.util.Random;

public class MergeSort {
    /**
     * Removes and returns the smallest item that is in q1 or q2.
     * The method assumes that both q1 and q2 are in sorted order, with the smallest item first. At
     * most one of q1 or q2 can be empty (but both cannot be empty).
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      The smallest item that is in q1 or q2.
     */
    private static <Item extends Comparable<Item>> Item getMin(
            Queue<Item> q1, Queue<Item> q2) {
        if (q1.isEmpty()) {
            return q2.dequeue();
        } else if (q2.isEmpty()) {
            return q1.dequeue();
        } else {
            // Peek at the minimum item in each queue (which will be at the front, since the
            // queues are sorted) to determine which is smaller.
            Item q1Min = q1.peek();
            Item q2Min = q2.peek();
            if (q1Min.compareTo(q2Min) <= 0) {
                // Make sure to call dequeue, so that the minimum item gets removed.
                return q1.dequeue();
            } else {
                return q2.dequeue();
            }
        }
    }

    /**
     * Returns a queue of queues that each contain one item from items.
     */
    private static <Item extends Comparable<Item>> Queue<Queue<Item>>
            makeSingleItemQueues(Queue<Item> items) {
        Queue<Queue<Item>> queue = new Queue<>();
        while (!items.isEmpty()) {
            Queue<Item> q = new Queue<>();
            q.enqueue(items.dequeue());
            queue.enqueue(q);
        }
        return queue;
    }

    /**
     * Returns a new queue that contains the items in q1 and q2 in sorted order.
     * This method should take time linear in the total number of items in q1 and q2.  After
     * running this method, q1 and q2 will be empty, and all of their items will be in the
     * returned queue.
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      A Queue containing all the q1 and q2 in sorted order, from least to
     *              greatest.
     *
     */
    private static <Item extends Comparable<Item>> Queue<Item> mergeSortedQueues(
            Queue<Item> q1, Queue<Item> q2) {
        Queue<Item> queue = new Queue<>();
        while (!q1.isEmpty() || !q2.isEmpty()) {
            queue.enqueue(getMin(q1, q2));
        }
        return queue;
    }

    /** Returns a Queue that contains the given items sorted from least to greatest. */
    public static <Item extends Comparable<Item>> Queue<Item> mergeSort(
            Queue<Item> items) {
        Queue<Queue<Item>> queue = makeSingleItemQueues(items);
        while (queue.size() >= 2) {
            Queue<Item> q1 = queue.dequeue();
            Queue<Item> q2 = queue.dequeue();
            queue.enqueue(mergeSortedQueues(q1, q2));
        }
        items = queue.dequeue();
        return items;
    }

    /**
     * @param nums num to be added into queue
     * @return queue consist of nums
     */
    private static Queue<Integer> of(int... nums) {
        Queue<Integer> queue = new Queue<>();
        for (int num : nums) {
            queue.enqueue(num);
        }
        return queue;
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
        Queue<Integer> queue = MergeSort.makeQueue(50);
        System.out.println(queue);
        queue = mergeSort(queue);
        System.out.println(queue);
        System.out.println(testSorted(queue));
    }
}
