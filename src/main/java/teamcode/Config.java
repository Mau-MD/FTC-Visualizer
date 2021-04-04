package main.java.teamcode;

public class Config {

    public static final int FRAMES_PER_SECOND = 60;

    public static final int CANVAS_WIDTH = 600; // PX
    public static final int CANVAS_HEIGHT = 600; // PX

    public static final int FONT_SIZE = 10; // Telemetry Font Size

    public static final int X_ROBOT_POSITION = 300; // Starting Position PX
    public static final int Y_ROBOT_POSITION = 300; // Starting Position PX
    public static final int INITIAL_ROBOT_HEADING = 0; // Starting Heading PX

    // If you don't plan to test odometry, don't tweak these variables and do not call  getX() and getY() functions
    // As they try to simulate real robot odometry
    public static final double ODOMETER_TICKS = 1000; // Odometry Encoder Ticks per Revolution
    public static final double ODOMETER_WHEEL_DIAMETER_CM = 5;
    public static final double ODOMETER_CIRCUMFERENCE_M = (ODOMETER_WHEEL_DIAMETER_CM * Math.PI) / 100.0; // Don't change it

    // Same as above. These variables try to simulate built-in motor encoders. If you don't plan to use them, just
    // don't call motor.getCurrentPosition().
    public static final double DRIVE_MOTOR_TICKS = 1000; // Motor Ticks per Revolution
    public static final double DRIVE_MOTOR_DIAMETER_CM = 10;
    public static final double DRIVE_MOTOR_CIRCUMFERENCE_M = (DRIVE_MOTOR_DIAMETER_CM * Math.PI) / 100.0; // Don't change it

    public static final double VELOCITY_MAX = 0.5; // m/s
    public static final double HEADING_PER_SECOND_MAX = 180;

}
