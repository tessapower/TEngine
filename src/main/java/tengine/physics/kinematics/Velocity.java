package tengine.physics.kinematics;

public record Velocity(double speed, Vector direction) {
    public Velocity() {
        this(0, new Vector(1, 0));
    }
}
