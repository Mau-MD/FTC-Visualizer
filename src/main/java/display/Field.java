package main.java.display;

import main.java.math.Pose2d;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Field extends JPanel {

    int width;
    int height;
    Pose2d robot_position;
    double velocity;
    double acceleration;

    /**
     * Initialize the Field Class
     * @param width Width of the canvas
     * @param height Height of the canvas
     */
    public Field(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Set the coordinates to let the program know where to draw the robot
     * @param robot_position current x,y and heading position
     */
    public void setPose3D(Pose2d robot_position)
    {
        this.robot_position = robot_position;
    }

    public void setVelocity(double velocity) { this.velocity = velocity;}

    public void setAcceleration(double acceleration) { this.acceleration = acceleration; }

    public void setTelemetry(Pose2d robot_position, double velocity,  double x, double y) {
        this.robot_position = robot_position;
        this.velocity = velocity;
    }

    /**
     * Erase all the canvas and then draws the outline of the robot
     * @param graphic default
     */
    public void paintComponent(Graphics graphic) {

        graphic.clearRect(0, 0, width, height);

        super.paintComponent(graphic);
        this.setBackground(Color.BLACK);

        Graphics2D main_graphics = (Graphics2D) graphic;
        main_graphics.setColor(Color.WHITE);
        main_graphics.setStroke(new BasicStroke(1));

        int ROBOT_SIZE = 50;
        Rectangle robot_outline =  new Rectangle((int) robot_position.x, (int) robot_position.y, ROBOT_SIZE, ROBOT_SIZE);
        Rectangle robot_heading =  new Rectangle((int) ((2 * robot_position.x + ROBOT_SIZE) / 2), (int) robot_position.y, 2, ROBOT_SIZE/2);

        Path2D.Double path = new Path2D.Double();
        path.append(robot_outline, false);
        path.append(robot_heading, false);

        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(robot_position.theta),
                (2 * robot_position.x + ROBOT_SIZE) / 2.0,
                (2 * robot_position.y + ROBOT_SIZE) / 2.0);

        path.transform(transform);


        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("resources/field600px.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }

        main_graphics.draw(path);
        main_graphics.drawString("Velocity " + velocity, 0, 30);
        main_graphics.drawString("Heading " + robot_position.theta, 0, 60);
        main_graphics.drawString("x: " + robot_position.x + " y: " + robot_position.y, 0, 90);


       // System.out.println("Velocity: " + velocity + " Acceleration: " + acceleration + "\n");
        /*
        main_graphics.rotate(Math.toRadians(robot_position.heading), robot_position.x, robot_position.y);
        main_graphics.setColor(Color.WHITE);
        main_graphics.setStroke(new BasicStroke(1));

        main_graphics.drawRect(robot_position.x, robot_position.y, ROBOT_SIZE, ROBOT_SIZE);

        */
    }

    /**
     * Draws a robot based on x and y coordinates
     * @param frame JFrame to draw on
     * @param x left-front x position of the robot
     * @param y left-front y position of the robot
     * @param heading current heading IN DEGREES
     */
    public void drawRobot(JFrame frame, int x, int y, int heading, double acceleration, double velocity)
    {
        setPose3D(new Pose2d(x, y, heading));
        setVelocity(velocity);
        frame.add(this);
        frame.repaint();
    }
}
