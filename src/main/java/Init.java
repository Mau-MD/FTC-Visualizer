package main.java;

import main.java.config.Config;
import main.java.display.Field;

import javax.swing.*;

public class Init {

    public static JFrame frame;
    public static Field field;

    public static void init() {
        frame = new JFrame("FTC Visualizer");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        field = new Field(Config.CANVAS_WIDTH, Config.CANVAS_HEIGHT);

        field.drawRobot(frame,Config.X_ROBOT_POSITION,Config.Y_ROBOT_POSITION, 0, 0, 0); // Need to draw an initial frame before setting it visible

        frame.setSize(Config.CANVAS_WIDTH, Config.CANVAS_HEIGHT);
        frame.setVisible(true);

    }

}
