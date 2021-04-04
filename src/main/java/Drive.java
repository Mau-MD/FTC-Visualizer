package main.java;

import main.java.config.Config;
import main.java.display.Field;
import main.java.display.Telemetry;
import main.java.hardware.DcMotor;
import main.java.hardware.MecanumDrive;
import main.java.math.Pose2d;

import javax.swing.*;

public class Drive {

    public static JFrame frame;
    public static Field field;
    public static Telemetry telemetry;
    static MecanumDrive drive = new MecanumDrive(new DcMotor(), new DcMotor(), new DcMotor(), new DcMotor());


    public static void init() {
        frame = new JFrame("FTC Visualizer");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        field = new Field(Config.CANVAS_WIDTH, Config.CANVAS_HEIGHT);

        field.drawRobot(frame,new Pose2d(Config.X_ROBOT_POSITION, Config.Y_ROBOT_POSITION, Config.INITIAL_ROBOT_HEADING)); // Need to draw an initial frame before setting it visible

        frame.setSize(Config.CANVAS_WIDTH, Config.CANVAS_HEIGHT);
        frame.setVisible(true);

        telemetry = new Telemetry(field);
    }

    public static JFrame getFrame() {
        return frame;
    }

    public static Field getField() {
        return field;
    }

}
