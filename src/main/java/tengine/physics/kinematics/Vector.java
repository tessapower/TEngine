package tengine.physics.kinematics;

public record Vector(double x, double y) {
    public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }

    public Vector normalize() {
        return new Vector((x / magnitude()), (y / magnitude()));
    }
}
