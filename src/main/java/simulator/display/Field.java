package main.java.simulator.display;

import main.java.simulator.hardware.ElapsedTime;
import main.java.teamcode.Config;
import main.java.simulator.math.Pose2d;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.List;

public class Field extends JPanel {

    int width;
    int height;
    Pose2d robot;
    private List<drawnStrings> strings;
    private Font font;
    FontMetrics metrics;
    BufferedImage image = null;
    long paintTime;

    /**
     * Initialize the Field Class
     * @param width Width of the canvas
     * @param height Height of the canvas
     */
    public Field(int width, int height) {
        strings = new ArrayList<>();
        this.width = width;
        this.height = height;
        font = new Font(Font.SANS_SERIF, Font.BOLD, Config.FONT_SIZE);
        Canvas c = new Canvas(); // I love u Canvas

        metrics = c.getFontMetrics(font);
        try {
            image = ImageIO.read(new File("src/main/java/simulator/resources/field600px.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * This function erases the whole canvas and then redraws the robot with the updated position
     * @param graphic default graphics
     */
    public void paintComponent(Graphics graphic) {

        ElapsedTime time = new ElapsedTime();
        time.reset();

        graphic.clearRect(0, 0, width, height);

        super.paintComponent(graphic);

        Graphics2D main_graphics = (Graphics2D) graphic;

        main_graphics.setColor(Color.WHITE);
        main_graphics.setStroke(new BasicStroke(2));

        int ROBOT_SIZE = 50;

        Path2D.Double robotPath = new Path2D.Double();

        // Robot Main Body
        Rectangle robot_outline =  new Rectangle((int) robot.x, (int) robot.y, ROBOT_SIZE, ROBOT_SIZE);

        // Robot wheels
        Rectangle left_front_wheel = new Rectangle((int) robot.x - ROBOT_SIZE / 6, (int) robot.y, ROBOT_SIZE / 6, ROBOT_SIZE / 4);
        Rectangle left_back_wheel = new Rectangle((int) robot.x - ROBOT_SIZE / 6, (int) robot.y + ROBOT_SIZE - ROBOT_SIZE / 4, ROBOT_SIZE / 6, ROBOT_SIZE / 4);
        Rectangle right_front_wheel = new Rectangle((int) robot.x + ROBOT_SIZE, (int) robot.y, ROBOT_SIZE / 6, ROBOT_SIZE / 4);
        Rectangle right_back_wheel = new Rectangle((int) robot.x + ROBOT_SIZE , (int) robot.y + ROBOT_SIZE - ROBOT_SIZE / 4, ROBOT_SIZE / 6, ROBOT_SIZE / 4);


        robotPath.append(robot_outline, false);
        robotPath.append(left_front_wheel, false);
        robotPath.append(left_back_wheel,false);
        robotPath.append(right_front_wheel, false);
        robotPath.append(right_back_wheel,false);

        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(robot.theta),
                (2 * robot.x + ROBOT_SIZE) / 2.0,
                (2 * robot.y + ROBOT_SIZE) / 2.0);

        robotPath.transform(transform);

        Rectangle robot_heading =  new Rectangle((int) ((2 * robot.x + ROBOT_SIZE) / 2), (int) robot.y, 2, ROBOT_SIZE/2);
        Path2D.Double headingPath = new Path2D.Double();

        headingPath.append(robot_heading, false);
        headingPath.transform(transform);

        // Background


        main_graphics.drawImage(image,0,0,null);
        main_graphics.draw(robotPath);
        main_graphics.setColor(Color.RED);
        main_graphics.draw(headingPath);
        main_graphics.setColor(Color.white);


        // Draw Telemetry
        if (strings.isEmpty())return;
        for (drawnStrings string : strings) {
            main_graphics.drawString(string.string, (int) string.x, (int) string.y);
        }

        paintTime = time.milliseconds();
    }

    /**
     * This function updates a list of strings that need to be draw based on a HashMap
     * @param data hashmap containing all the telemetry data
     */
    public void updateTelemetry(HashMap<String, Double> data) {

        if (!strings.isEmpty())strings.clear();

        int i = 0;

        if (data.isEmpty())return; // Me trying to solve bugs

        for (Map.Entry<String, Double> entry : data.entrySet()) {

            String text = entry.getKey();
            double value = BigDecimal.valueOf(entry.getValue()).setScale(2, RoundingMode.HALF_UP).doubleValue();

            Rectangle centerRect = new Rectangle(0,i * Config.FONT_SIZE * 2, Config.CANVAS_WIDTH, Config.FONT_SIZE * 2);

            String finalText = text + ": " + value;
            drawCenteredString(finalText, centerRect);
            i+=1;
        }
    }


    /**
     * This function draws a centered string (lol) based on a rectangle
     * Definitely not stolen from stack overflow
     */
    public void drawCenteredString(String text,  Rectangle rect) {


        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();

        strings.add(new drawnStrings(text, x, y)); // Add it to the telemetry list
    }

    /**
     * This function draws the robot based on a frame and the robot current pose
     * @param frame current frame
     * @param robot Pose2D containing robot info
     */
    public long drawRobot(JFrame frame, Pose2d robot)
    {
        this.robot = robot;
        frame.add(this);
        frame.repaint();

        return paintTime;
    }
}

class drawnStrings {
    String string;
    double x;
    double y;

    /**
     * Object that stores the data string and the coordinate the string will be at
     * @param string data
     * @param x coordinate
     * @param y coordinate
     */
    public drawnStrings(String string, double x, double y) {
        this.string = string;
        this.x = x;
        this.y = y;
    }
}
