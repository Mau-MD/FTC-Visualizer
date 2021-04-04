package main.java.simulator.hardware;

/**
 * Simulates how FTC's Elapsed time works by using system time.
 */
public class ElapsedTime {

    long startingTime;

    public ElapsedTime() {
        startingTime = System.currentTimeMillis();
    }

    public long milliseconds() {
        return System.currentTimeMillis() - startingTime;
    }

    public double seconds() {
        return (System.currentTimeMillis() - startingTime) / 1000.0;
    }

    public void reset() {
        startingTime = System.currentTimeMillis();
    }
}
