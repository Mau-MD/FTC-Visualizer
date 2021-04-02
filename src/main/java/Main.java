package main.java;

import com.google.common.base.Stopwatch;
import main.java.config.Config;
import main.java.display.Field;
import main.java.hardware.DcMotor;
import main.java.hardware.MecanumDrive;
import main.java.math.Functions;
import main.java.math.Vector2d;

import javax.swing.*;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main (String[] args) throws InterruptedException {

        Init.init();

        MecanumDrive drive = new MecanumDrive(new DcMotor(), new DcMotor(), new DcMotor(), new DcMotor());

        while (true) {
            drive.left_front.setPower(1);
            drive.right_front.setPower(-1);
            drive.left_back.setPower(1);
            drive.right_back.setPower(-1);

            System.out.println("Robot Position " + drive.getX() + " " + drive.getY());
            System.out.println("Robot Heading " + drive.getAngularOrientation());
            drive.sleep(1);
        }

    }
}
