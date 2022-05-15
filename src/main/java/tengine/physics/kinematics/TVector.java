package tengine.physics.kinematics;

public class TVector {
    private double x;
    private double y;

    public TVector() {
        this(0, 0);
    }

    public TVector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }

    public TVector normalize() {
        return new TVector((x / magnitude()), (y / magnitude()));
    }
}
