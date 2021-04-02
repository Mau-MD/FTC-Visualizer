package main.java.config;

public class Config {

    public static final int FRAMES_PER_SECOND = 60;

    public static final int CANVAS_WIDTH = 600;
    public static final int CANVAS_HEIGHT = 600;

    public static final int X_ROBOT_POSITION = 300; // PX
    public static final int Y_ROBOT_POSITION = 300; // PX

    public static final double ODOMETER_TICKS = 1000;
    public static final double ODOMETER_WHEEL_DIAMETER_CM = 5;
    public static final double ODOMETER_CIRCUMFERENCE_M = (ODOMETER_WHEEL_DIAMETER_CM * Math.PI) / 100.0;

    public static final double DRIVE_MOTOR_TICKS = 1000;
    public static final double DRIVE_MOTOR_DIAMETER_CM = 10;
    public static final double DRIVE_MOTOR_CIRCUMFERENCE_M = (DRIVE_MOTOR_DIAMETER_CM * Math.PI) / 100.0;

    public static final double VELOCITY_MAX = 0.5; // m/s
    public static final double HEADING_PER_SECOND_MAX = 180;

    // Hacer una escala
    // 3.66 Metros
    // 3.66 metros - 600 px
    // 0.0061      - 1 px

}
