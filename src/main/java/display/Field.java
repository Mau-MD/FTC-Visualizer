package main.java.display;

import main.java.config.Config;
import main.java.math.Pose2d;
import org.checkerframework.checker.units.qual.C;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Field extends JPanel {

    int width;
    int height;
    Pose2d robot;
    private List<drawnStrings> strings;


    /**
     * Initialize the Field Class
     * @param width Width of the canvas
     * @param height Height of the canvas
     */
    public Field(int width, int height) {
        strings = new ArrayList<>();
        this.width = width;
        this.height = height;
    }



    /**
     * This function erases the whole canvas and then redraws the robot with the updated position
     * @param graphic default graphics
     */
    public void paintComponent(Graphics graphic) {

        graphic.clearRect(0, 0, width, height);

        super.paintComponent(graphic);

        Graphics2D main_graphics = (Graphics2D) graphic;

        main_graphics.setColor(Color.WHITE);
        main_graphics.setStroke(new BasicStroke(2));

        int ROBOT_SIZE = 50;
        Rectangle robot_outline =  new Rectangle((int) robot.x, (int) robot.y, ROBOT_SIZE, ROBOT_SIZE);
        Path2D.Double robotPath = new Path2D.Double();

        robotPath.append(robot_outline, false);

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
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("src/main/java/resources/field600px.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }

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

    }

    /**
     * This function updates a list of strings that need to be draw based on a HashMap
     * @param data hashmap containing all the telemetry data
     */
    public void updateTelemetry(HashMap<String, Double> data) {

        Font font = new Font(Font.SANS_SERIF, Font.BOLD, Config.FONT_SIZE);

        strings.clear();

        int i = 0;

        if (data.isEmpty())return; // Me trying to solve bugs

        for (Map.Entry<String, Double> entry : data.entrySet()) {

            String text = entry.getKey();
            Double value = entry.getValue();

            Rectangle centerRect = new Rectangle(0,i * Config.FONT_SIZE, Config.CANVAS_WIDTH, Config.FONT_SIZE );

            String finalText = text + ": " + value;
            drawCenteredString(finalText, centerRect, font);
            i+=1;
        }
    }


    /**
     * This function draws a centered string (lol) based on a rectangle
     * Definitely not stolen from stack overflow
     */
    public void drawCenteredString(String text,  Rectangle rect, Font font) {

        Canvas c = new Canvas(); // I love u Canvas
        FontMetrics metrics = c.getFontMetrics(font);

        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();

        strings.add(new drawnStrings(text, x, y)); // Add it to the telemetry list
    }

    /**
     * This function draws the robot based on a frame and the robot current pose
     * @param frame current frame
     * @param robot Pose2D containing robot info
     */
    public void drawRobot(JFrame frame, Pose2d robot)
    {
        this.robot = robot;
        frame.add(this);
        frame.repaint();
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
