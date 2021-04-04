package main.java.simulator.hardware;

public class DcMotor {

    public double power = 0;
    private double position = 0;
    int reversed = 1;
    boolean brake = false;

    public enum Direction{
        REVERSE,
        FORWARD
    }

    public enum ZeroPowerBehavior {
        BRAKE,
        FLOAT
    }

    /**
     * Set the power's motor
     * @param power power
     */
    public void setPower(double power) {
        this.power = reversed * power;
    }

    /**
     *
     * @return current power of the motor
     */
    public double getPower() {
        return power;
    }

    /**
     * Sets the direction of the motor
     * @param direction REVERSE / FORWARD
     */
    public void setDirection(Direction direction) {
        if (direction == Direction.REVERSE) {
            reversed = -1;
        }
        else {
            reversed = 1;
        }
    }

    /**
     * Sets the behavior when the motors are stopped
     * @param behavior BRAKE / FLOAT
     */
    public void setZeroPowerBehavior(ZeroPowerBehavior behavior) {
        if (behavior == ZeroPowerBehavior.BRAKE) {
            brake = true;
        }
        else {
            brake = false;
        }
    }

    /**
     * Simulates how an encoder should work
     * @param position current position of the motor
     */
    protected void setPosition(double position) {
        this.position = position;
    }

    public double getCurrentPosition() {
        return position;
    }
}
