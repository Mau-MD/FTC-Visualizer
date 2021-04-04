package main.java.simulator.math;

public class Functions {


    /**
     * Rule of three
     * @param first The first value is equal to `equal`
     * @param equals this value is equal to `first`
     * @param second The second value should be the same magnitude as `first`
     * @return the result of the rule of three
     */
    public static double scaleFactor(double first, double equals, double second) {
        return second * equals / first;
    }

    /**
     * Ensures certain value is within the range
     * @param value value to be clipped
     * @param min min value allowed
     * @param max max value allowed
     * @return value within the range
     */
    public static double clip(double value, double min, double max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }
}
