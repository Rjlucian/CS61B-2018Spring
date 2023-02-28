package lab14;

import lab14lib.Generator;

public class SawToothGenerator implements Generator {

    private final int period;
    private int state;

    public SawToothGenerator(int period) {
        this.period = period;
        this.state = 0;
    }


    @Override
    public double next() {
        double unNormalState = -1 * ((double) (++state % period) / period);
        return normalize(unNormalState);
    }

    private double normalize(double unmNormalState) {
        return -(unmNormalState + 0.5) * 2;
    }
}
