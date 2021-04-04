package main.java.hardware;

import com.google.common.base.Stopwatch;

import java.util.concurrent.TimeUnit;

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
