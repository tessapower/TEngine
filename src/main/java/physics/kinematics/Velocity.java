package main.java.physics.kinematics;

public record Velocity(int dx, int dy) {
    public Velocity() {
        this(0, 0);
    }

    public Velocity(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }
}
