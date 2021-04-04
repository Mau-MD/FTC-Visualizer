package main.java.simulator.math;

public class Vector2d {

    public double x;
    public double y;

    public Vector2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the cross product of two 2d vectors
     * @param first_vector
     * @param second_vector
     * @return a vector containing the cross product
     */
    public Vector3d crossProduct(Vector2d first_vector, Vector2d second_vector) {
        return new Vector3d(0, 0, first_vector.x * second_vector.y - first_vector.y * second_vector.x);
    }

    /**
     * Returns the magnitude of a 2d vector
     * @param vector 2d vector
     * @return magnitude
     */
    public static double getMagnitude(Vector2d vector) {
        return Math.sqrt((vector.x * vector.x)+(vector.y * vector.y));
    }

    /**
     * Returns the angle of the vector
     * @return angle in radians
     */
    public double getAngle() {
        return Math.atan2(y,x);
    }

    /**
     * Rotates the vector given an angle in degrees
     * @param vector vector to be rotated
     * @param theta angle to be rotated in degrees
     * @return rotated vector
     */
    public static Vector2d rotateVector(Vector2d vector, double theta) {
        double x = vector.x * Math.cos(Math.toRadians(theta)) - vector.y * Math.sin(Math.toRadians(theta));
        double y = vector.x * Math.sin(Math.toRadians(theta)) + vector.y * Math.cos(Math.toRadians(theta));
        return new Vector2d(x,y);
    }
}
