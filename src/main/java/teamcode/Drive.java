package main.java.teamcode;

import main.java.simulator.display.Field;
import main.java.simulator.display.Telemetry;
import main.java.simulator.hardware.DcMotor;
import main.java.simulator.hardware.MecanumDrive;
import main.java.simulator.math.Pose2d;

import javax.swing.*;

// yeah don't touch this
public class Drive {

    public static JFrame frame;
    public static Field field;
    public static Telemetry telemetry;
    static MecanumDrive drive = new MecanumDrive(new DcMotor(), new DcMotor(), new DcMotor(), new DcMotor());

    /**
     * Draws initial frame (background and robot), also initializes the telemetry class
     */
    public static void init() {
        frame = new JFrame("FTC Visualizer");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        field = new Field(Config.CANVAS_WIDTH, Config.CANVAS_HEIGHT);

        field.drawRobot(frame,new Pose2d(Config.X_ROBOT_POSITION, Config.Y_ROBOT_POSITION, Config.INITIAL_ROBOT_HEADING)); // Need to draw an initial frame before setting it visible

        frame.setSize(Config.CANVAS_WIDTH, Config.CANVAS_HEIGHT);
        frame.setVisible(true);

        telemetry = new Telemetry(field);

        System.out.println("Field Image by: u/Strong_Pineapple");
    }

    public static JFrame getFrame() {
        return frame;
    }

    public static Field getField() {
        return field;
    }

}
