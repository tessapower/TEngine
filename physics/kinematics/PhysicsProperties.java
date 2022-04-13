package physics.kinematics;

import collisions.CollisionPrimitive;

import java.awt.*;

public class PhysicsProperties {
    public Point origin;
    public Velocity velocity;
    public boolean movable;
    public boolean hasCollisions;
    public CollisionPrimitive bounds;

    public PhysicsProperties() {
        this(new Point(), new Velocity(), false, false, null);
    }

    public PhysicsProperties(Point origin, Velocity velocity,
                             boolean movable, boolean hasCollisions,
                             CollisionPrimitive bounds) {
        this.origin = origin;
        this.velocity = velocity;
        this.movable = movable;
        this.hasCollisions = hasCollisions;
        this.bounds = bounds;
    }
}
