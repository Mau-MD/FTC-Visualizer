package main.java.math;

public class Vector2d {

    public double x;
    public double y;

    public Vector2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector3d crossProduct(Vector2d first_vector, Vector2d second_vector) {
        return new Vector3d(0, 0, first_vector.x * second_vector.y - first_vector.y * second_vector.x);
    }

    public static double getMagnitude(Vector2d vector) {
        return Math.sqrt((vector.x * vector.x)+(vector.y * vector.y));
    }

    public Vector2d rotateVector(double theta) {
        double new_x = Math.cos(theta) * x - Math.sin(theta) * y;
        double new_y = Math.sin(theta) * x + Math.cos(theta) * y;
        return new Vector2d(new_x,new_y);
    }

    public double getAngle() {
        return Math.atan2(y,x);
    }

    public static Vector2d rotateVector(Vector2d vector, double theta) {
        double x = vector.x * Math.cos(Math.toRadians(theta)) - vector.y * Math.sin(Math.toRadians(theta));
        double y = vector.x * Math.sin(Math.toRadians(theta)) + vector.y * Math.cos(Math.toRadians(theta));
        return new Vector2d(x,y);
    }
}
