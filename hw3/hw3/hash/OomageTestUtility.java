package hw3.hash;

import edu.princeton.cs.algs4.In;

import java.util.HashMap;
import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (Oomage o : oomages) {
            int bucketNum = o.hashCode() & 0x7FFFFFFF % M;
            if (!map.containsKey(bucketNum)) {
                map.put(bucketNum, 1);
            } else {
                map.put(bucketNum, map.get(bucketNum) + 1);
            }
        }
        for (int a : map.values()) {
            if (a <= oomages.size() / 50 || a >= oomages.size() / 2.5) {
                return false;
            }
        }
        return true;
    }
}
