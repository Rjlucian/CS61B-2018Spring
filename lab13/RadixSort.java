import edu.princeton.cs.algs4.Queue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        String[] sortedArr = new String[asciis.length];
        System.arraycopy(asciis, 0, sortedArr, 0, sortedArr.length);
        int length = findLongest(sortedArr);
        for (int i = length - 1; i >= 0; i--) {
            sortHelperLSD(sortedArr, i);
        }
        return sortedArr;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        int[] counts = new int[256];
        int[] starts = new int[256];
        Map<Integer, Queue<String>> map = new HashMap<>();
        updateCountsAndBuckets(asciis, counts, map, index);
        updateStarts(counts, starts);
        copyToSortedArr(asciis, counts, starts, map);
    }

    private static int findLongest(String[] arr) {
        int longest = 0;
        for (String e : arr) {
            longest = Math.max(e.length(), longest);
        }
        return longest;
    }

    private static void
        updateCountsAndBuckets(String[] arr, int[] counts, Map<Integer, Queue<String>> buckets, int index) {
        Arrays.fill(counts, 0);
        for (String str : arr) {
            int i = index >= str.length() ? 0 : (int) str.charAt(index);
            counts[i]++;
            if (buckets.get(i) == null) {
                buckets.put(i, new Queue<>());
            }
            buckets.get(i).enqueue(str);
        }

    }

    private static void
        updateStarts(int[] counts, int[] starts) {
        Arrays.fill(starts, 0);
        for (int i = 1; i < counts.length; i++) {
            starts[i] = counts[i - 1] + starts[i - 1];
        }
    }


    private static void
        copyToSortedArr(String[] arr, int[] counts, int[] starts, Map<Integer, Queue<String>> buckets) {
        for (int i : buckets.keySet()) {
            for (int j = 1; j <= counts[i]; j++) {
                arr[starts[i]] = buckets.get(i).dequeue();
                starts[i]++;
            }
        }
    }

    private static void printArr(String[] arr) {
        for (String str : arr) {
            System.out.print(str + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        String[] arr = {"a231", "32134b", "w3213d"};
        System.out.print("Origin: ");
        printArr(arr);
        String[] sortedArr = sort(arr);
        System.out.print("My sort: ");
        printArr(sortedArr);
        Arrays.sort(arr);
        System.out.print("Built-in sort: ");
        printArr(arr);

    }
}
