package main.java.simulator.hardware;

import com.google.common.base.Stopwatch;
import main.java.teamcode.Drive;
import main.java.simulator.math.Functions;
import main.java.simulator.math.Pose2d;
import main.java.simulator.math.Vector2d;
import main.java.teamcode.Config;

import java.util.concurrent.TimeUnit;

public class MecanumDrive {

    public DcMotor left_front;
    public DcMotor right_front;
    public DcMotor left_back;
    public DcMotor right_back;

    private DcMotor[] motors = new DcMotor[4];

    public Pose2d robot = new Pose2d(0,0, 0);
    public Vector2d velocity = new Vector2d(0,0);
    public double rotation_velocity = 0;

    private double distanceMoved = 0;



    public MecanumDrive(DcMotor left_front, DcMotor right_front, DcMotor left_back, DcMotor right_back) {
        this.left_front = left_front;
        this.right_front = right_front;
        this.left_back = left_back;
        this.right_back = right_back;
    }


    /**
     * Updates robot's linear and rotational velocity based on the current power of the four motors. It also updates
     * robot heading and position
     */
    public void update() {

        double[] velocities = new double[4];

        velocities[0] = Functions.clip(left_front.power, -1, 1);
        velocities[1] = Functions.clip(right_front.power, -1, 1);
        velocities[2] = Functions.clip(left_back.power, -1, 1);
        velocities[3] = Functions.clip(right_back.power, -1, 1);

        motors[0] = left_front;
        motors[1] = right_front;
        motors[2] = left_back;
        motors[3] = right_back;

        velocity = Vector2d.rotateVector(getRobotVelocity(velocities), robot.theta);
        rotation_velocity = getRobotRotationVelocity(velocities);


        double real_x_velocity = Functions.scaleFactor(4.0, Config.VELOCITY_MAX, velocity.x);
        double real_y_velocity = Functions.scaleFactor(4.0, Config.VELOCITY_MAX, velocity.y);
        double real_theta_velocity = Functions.scaleFactor(4.00, Config.HEADING_PER_SECOND_MAX, rotation_velocity);

        updateVariables(real_x_velocity, real_y_velocity, real_theta_velocity);
        updateRobotPosition();


    }

    public Vector2d getRobotVelocity(double[] velocities) {
        double y_velocity = velocities[0] + velocities[1] + velocities[2] + velocities[3];
        double x_velocity = velocities[0] - velocities[1] - velocities[2] + velocities[3];
        return new Vector2d(x_velocity, -y_velocity);
    }

    public double getRobotRotationVelocity(double[] velocities) {
        return velocities[0] - velocities[1] + velocities[2] - velocities[3];
    }

    public void updateVariables(double x_velocity, double y_velocity, double rotation_velocity) {


        double lastX = robot.x;
        double lastY = robot.y;

        robot.x += (x_velocity / Config.FRAMES_PER_SECOND);
        robot.y += (y_velocity / Config.FRAMES_PER_SECOND);

        robot.theta += (rotation_velocity / Config.FRAMES_PER_SECOND);
        if (robot.theta < 0)robot.theta += 360;
        robot.theta %= 360;

        //System.out.println("Frame: " + currentFrame + "  Robot X: " + robot.x + " Robot Y: " + robot.y);
        velocity.x = x_velocity;
        velocity.y = y_velocity;

        distanceMoved = Vector2d.getMagnitude(new Vector2d(robot.x - lastX, robot.y - lastY));
    }

    public void sleep(int milliseconds) throws InterruptedException {
        Stopwatch elapsedTime = Stopwatch.createStarted();

        if (milliseconds == -1) {
            while (true) {
                update();
                Drive.getField().drawRobot(Drive.getFrame(), new Pose2d(Config.X_ROBOT_POSITION + (int) Functions.scaleFactor(3.66,600,robot.x), Config.Y_ROBOT_POSITION + (int)Functions.scaleFactor(3.66,600,robot.y), (int)robot.theta));
                Thread.sleep(1000 / (Config.FRAMES_PER_SECOND));
            }
        }
        if (milliseconds == 0) {
            update();
            Drive.getField().drawRobot(Drive.getFrame(), new Pose2d(Config.X_ROBOT_POSITION + (int) Functions.scaleFactor(3.66,600,robot.x), Config.Y_ROBOT_POSITION + (int)Functions.scaleFactor(3.66,600,robot.y), (int)robot.theta));
            Thread.sleep(1000 / (Config.FRAMES_PER_SECOND));
        }
        while (elapsedTime.elapsed(TimeUnit.MILLISECONDS) < milliseconds) {
            update();
            Drive.getField().drawRobot(Drive.getFrame(), new Pose2d(Config.X_ROBOT_POSITION + (int) Functions.scaleFactor(3.66,600,robot.x), Config.Y_ROBOT_POSITION + (int)Functions.scaleFactor(3.66,600,robot.y), (int)robot.theta));
            Thread.sleep(1000 / (Config.FRAMES_PER_SECOND));
        }
    }

    private void updateRobotPosition() {
        for (int i=0;i<4;i++) {
            double contrib = motors[i].getPower() * distanceMoved;
            motors[i].setPosition(motors[i].getCurrentPosition() + Functions.scaleFactor(Config.DRIVE_MOTOR_CIRCUMFERENCE_M, Config.DRIVE_MOTOR_TICKS, contrib));
        }
    }


    // Util Functions

    public double getX() {
        return Functions.scaleFactor(Config.ODOMETER_CIRCUMFERENCE_M, Config.ODOMETER_TICKS, robot.x);
    }

    public double getY() {
        return -Functions.scaleFactor(Config.ODOMETER_CIRCUMFERENCE_M, Config.ODOMETER_TICKS, robot.y);
    }

    public double getAngularOrientation() {
        if (robot.theta >= 180) {
            return 360 - robot.theta;
        }
        else return -robot.theta;
    }





    /*

    Changed to Velocity-Based Calculations instead of force based

    public void update(double time) {

        System.out.println("Time " + time);
        Vector2d motors[] = new Vector2d[4];

        motors[0] = getComponents(left_front.power * force_max, motor.LEFT_FRONT);
        motors[1] = getComponents(right_front.power * force_max, motor.RIGHT_FRONT);
        motors[2] = getComponents(left_back.power * force_max, motor.LEFT_BACK);
        motors[3] = getComponents(right_back.power * force_max, motor.RIGHT_BACK);

        robotVector = getRobotComponents(motors);

        // X Component

        double new_x_accel = getAcceleration(Config.ROBOT_MASS, robotVector.x);
        double new_x_vel = getVelocity(current_x_acceleration, time, current_x_velocity);
        double new_x_position = getPosition(current_x_acceleration, current_x_velocity, time, current_x);

        double new_y_accel = getAcceleration(Config.ROBOT_MASS, robotVector.y );
        double new_y_vel = getVelocity(current_y_acceleration, time, current_y_velocity);
        double new_y_position = getPosition(current_y_acceleration, current_y_velocity, time, current_y);

        current_x_acceleration = new_x_accel;
        current_x_velocity= new_x_vel;
        current_x = new_x_position;

        current_y_acceleration = new_y_accel;
        current_y_velocity= new_y_vel;
        current_y = new_y_position;

        System.out.println("Y Position: " +  new_y_position);
        current_acceleration = Vector2d.getMagnitude(new Vector2d(current_x_acceleration, current_y_acceleration));
        current_velocity = Vector2d.getMagnitude(new Vector2d(current_x_velocity, current_y_velocity));

    }

    // El angulo lo calculo con el angulo relativo
    private Vector2d getComponents(double force, motor motor) {

        double forceAngle = 0;

        double x_direction = 1, y_direction = 1;
        switch (motor) {
            case RIGHT_FRONT: x_direction = 1; y_direction = 1; break;
            case LEFT_FRONT: x_direction = -1; y_direction = 1; break;
            case RIGHT_BACK: x_direction = -1; y_direction = 1; break;
            case LEFT_BACK:x_direction = 1; y_direction = 1; break;
        }

        double real_angle = forceAngle + (current_heading - 90);
        if (real_angle < 0) real_angle += 360;

        // System.out.println("Angle:" + real_angle + " Force: " + force);
        double x_component = force * Math.cos(45);
        double y_component = force * Math.sin(45);
        //System.out.println("X_component " + x_component + " y component: " + y_component);

        return new Vector2d(x_direction * x_component, y_direction * y_component);
    }

    private Vector2d getRobotComponents(Vector2d[] components) {

        double x_component = 0, y_component = 0;

        for (int i = 0;i < 4; i++)
        {
            x_component += components[i].x;
            y_component += components[i].y;
            //System.out.println("2. X_component " + x_component + " y component: " + y_component);

        }

        double normalizer = (Config.VELOCITY_MAX - current_velocity) / Config.VELOCITY_MAX ;

        return new Vector2d(x_component * normalizer, y_component * normalizer);

    }

    private double getAcceleration(double mass, double force) {
        return force / mass;
    }

    private double getVelocity(double acceleration, double time, double velocity) {
        return acceleration * time + velocity;
    }

    private double getPosition(double acceleration, double velocity, double time, double position) {
        return time * velocity + position;
    }
    // El timepo es individual por componente. Si este es cero, se reinicia
    */

}
