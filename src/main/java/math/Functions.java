package main.java.math;

public class Functions {

    // 3.66 Metros
    // 3.66 metros - 600 px
    // 0.0061      - 1 px
    public static double scalePosition(double pos) {
        // Pixeles que me voy a mover
        return pos * 0.0061;
    }


    public static double scaleFactor(double first, double equals, double second) {
        return second * equals / first;
    }
}
