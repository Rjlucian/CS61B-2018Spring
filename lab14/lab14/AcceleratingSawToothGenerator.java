package lab14;

import lab14lib.Generator;

public class AcceleratingSawToothGenerator implements Generator {

    private int period;
    private int state;
    private double factor;

    public AcceleratingSawToothGenerator(int period, double factor) {
        this.period = period;
        this.factor = factor;
        this.state = 0;

    }
    @Override
    public double next() {
        double unNormalState =  -1 * ((double) (++state % period) / period);
        if (state % period == 0) {
            period = (int) (period * factor);
            state = 0;
        }
        return normalize(unNormalState);
    }

    private double normalize(double unNormalState) {
        return -(unNormalState + 0.5) * 2;
    }
}
