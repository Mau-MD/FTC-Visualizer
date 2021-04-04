package main.java;

import main.java.hardware.DcMotor;
import main.java.hardware.ElapsedTime;

public class Main extends Drive {

    public static void main (String[] args) throws InterruptedException {

        init();

        // Code goes here -------------------------------------------------------------------------------
        // Remember to change configurations in config.Config

        ElapsedTime time = new ElapsedTime(); // The same reliable FTC Elapsed Time!
        time.reset();

        drive.left_front.setDirection(DcMotor.Direction.FORWARD); // You can even invert directions!

        // Best Method in FTC to "sleep"
        while (time.milliseconds() < 5000) {

            drive.left_front.setPower(1); // Same as FTC
            drive.right_front.setPower(1);
            drive.left_back.setPower(1);
            drive.right_back.setPower(1);

            telemetry.addData("Heading", drive.getAngularOrientation()); // Get robots heading
            telemetry.addData("x", drive.getX()); // Get relative x position in ticks (odometry)
            telemetry.addData("y", drive.getY()); // Get relative y position in ticks (odometry)
            telemetry.addData("Current Time", time.milliseconds()); // Elapsed time!

            telemetry.update(); // Don't forget to call update after adding telemetry ;)

            /*
             * Even if you are using the ElapsedTime method, you need to include sleep(0) to
             * update the robot's position on the field.
             */
            drive.sleep(0);

        }

        idle(); // You can create functions too!! (See below)

        drive.sleep(2000); // You can use the normal sleep() too!

        drive.left_front.setPower(-1);
        drive.right_front.setPower(1);
        drive.left_back.setPower(1);
        drive.right_back.setPower(-1);

        drive.sleep(3000);

        idle();

        drive.sleep(2000);

        time.reset();
        while (time.milliseconds() < 2000) { // Let's go backwards!

            drive.left_front.setPower(-1);
            drive.right_front.setPower(-1);
            drive.left_back.setPower(-1);
            drive.right_back.setPower(-1);

            telemetry.addData("Left Front Position", drive.left_front.getCurrentPosition()); // Encoders!
            telemetry.update();

            drive.sleep(0); // Remember to update!
        }

        idle();

        drive.sleep(2000);

        time.reset();
        while (time.milliseconds() < 2000) { // Let's turn around

            drive.left_front.setPower(-1);
            drive.right_front.setPower(1);
            drive.left_back.setPower(-1);
            drive.right_back.setPower(1);

            telemetry.addData("Heading", drive.getAngularOrientation());
            telemetry.update();

            drive.sleep(0); // Remember to update!
        }

        idle();

    }

    public static void idle() {
        drive.left_front.setPower(0);
        drive.right_front.setPower(0);
        drive.left_back.setPower(0);
        drive.right_back.setPower(0);
    }
}
