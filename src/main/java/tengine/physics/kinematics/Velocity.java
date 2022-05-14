package tengine.physics.kinematics;

public class Velocity {
    private double speed = 0;
    private Vector direction;

    public Velocity() {
        this(0, new Vector(0, 0));
    }

    public Velocity (double speed, Vector direction) {
        this.speed = speed;
        this.direction = direction;
    }

    public void invertDirection() {
        direction = new Vector(-1 * direction.x(), -1 * direction.y());
    }

    public void invertXDirection() {
        direction = new Vector(-1 * direction.x(), direction.y());
    }

    public void invertYDirection() {
        direction = new Vector(direction.x(), -1 * direction.y());
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double speed() {
        return speed;
    }

    public void setDirection(Vector direction) {
        this.direction = direction;
    }

    public Vector direction() {
        return direction;
    }

    public double dx() {
        return direction.x();
    }

    public double dy() {
        return direction.y();
    }
}
