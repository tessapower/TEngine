package tengine.physics.kinematics;

/**
 * Represents a velocity with a direction vector and a speed. The magnitude of the x and y
 * components in the direction vector do not influence the speed.
 */
public class TVelocity {
    private double speed;
    private TVector direction;

    public TVelocity() {
        this(0, new TVector(0, 0));
    }

    public TVelocity(double speed, TVector direction) {
        this.speed = speed;
        this.direction = direction;
    }

    public void invertDirection() {
        invertXDirection();
        invertYDirection();
    }

    public void invertXDirection() {
        direction.setX(direction.x() * -1);
    }

    public void invertYDirection() {
        direction.setY(direction.y() * -1);
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double speed() {
        return speed;
    }

    public void setDirectionX(double directionX) {
        direction.setX(directionX);
    }

    public void setDirectionY(double directionY) {
        direction.setY(directionY);
    }

    public void setDirection(TVector direction) {
        this.direction = direction;
    }

    public TVector direction() {
        return direction;
    }

    /**
     * Returns the x component of the velocity.
     */
    public double dx() {
        return direction.x() * speed;
    }

    /**
     * Returns the y component of the velocity.
     */
    public double dy() {
        return direction.y() * speed;
    }
}
