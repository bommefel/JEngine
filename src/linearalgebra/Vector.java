package linearalgebra;

import java.io.Serializable;

public class Vector implements Serializable {

    private static final long serialVersionUID = 2722207061281443072L;
    private final double x;
    private final double y;
    private final double z;
    private double w;
    public boolean calculationException = false;

    public Vector(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;

        // avoid division by zero
        if (this.w == 0) {
            this.w = 1;
        }
    }

    public double getX() {
        return x / w;
    }

    public double getY() {
        return y / w;
    }

    public double getZ() {
        return z / w;
    }

    public Vector add(Vector vector) {
        return new Vector(
                getX() + vector.getX(),
                getY() + vector.getY(),
                getZ() + vector.getZ(),
                1
        );
    }

    public Vector subtract(Vector vector) {
        return new Vector(
                getX() - vector.getX(),
                getY() - vector.getY(),
                getZ() - vector.getZ(),
                1
        );
    }

    public Vector invert() {
        return new Vector(
                -getX(),
                -getY(),
                -getZ(),
                1
        );
    }

    public Vector multiply(double factor) {
        return new Vector(
                factor * getX(),
                factor * getY(),
                factor * getZ(),
                1
        );
    }

    public Vector normalize() {
        double length = absoluteValue();

        return new Vector(
                getX() / length,
                getY() / length,
                getZ() / length,
                1
        );
    }

    public double scalarProduct(Vector vector) {
        return getX() * vector.getX() + getY() * vector.getY() + getZ() * vector.getZ();
    }

    public double absoluteValue() {
        return Math.sqrt(getX() * getX() + getY() * getY() + getZ() * getZ());
    }

    public String toString() {
        return "vector: " + getX() + " " + getY() + " " + getZ();
    }
}
