import org.junit.Test;
import static org.junit.Assert.*;

public class TestArrayDequeGold {
    @Test
    public void test() {
        String str = "";
        StudentArrayDeque<Integer> sad = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> ad = new ArrayDequeSolution<>();
        Integer numSad;
        Integer numAd;
        for (int i = 0; i < 7; i++) {
            double rm = StdRandom.uniform();
            if (rm < 0.5) {
                sad.addFirst(i);
                ad.addFirst(i);
                str = str + "addFirst(" + i + ")\n";
            } else {
                sad.addLast(i);
                ad.addLast(i);
                str = str + "addLast(" + i + ")\n";
            }
        }
        for (int i = 0; i < 7; i++) {
            double rm = StdRandom.uniform();
            if (rm < 0.5) {
                numSad = sad.removeFirst();
                numAd = ad.removeFirst();
                str = str + "removeFirst()\n";
                assertEquals(str, numSad, numAd);
            } else {
                numSad = sad.removeLast();
                numAd = ad.removeLast();
                str = str + "removerLast()\n";
                assertEquals(str, numSad, numAd);
            }
        }
    }

}
