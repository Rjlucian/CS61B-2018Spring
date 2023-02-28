package lab14;

import lab14lib.Generator;

public class StrangeBitwiseGenerator implements Generator {

    private int period;
    private int state;

    public StrangeBitwiseGenerator(int period) {
        this.period = period;
        this.state = 0;
    }


    @Override
    public double next() {
        state += 1;
        int weirdState = state & (state >> 3) & (state >> 8) % period;
        double unNormalState = -1 * ((double) weirdState / period);
        return normalize(unNormalState);
    }

    private double normalize(double unNormalState) {
        return -(unNormalState + 0.5) * 2;
    }
}
