package physics;

import physics.collisions.CollisionShape;
import physics.kinematics.Velocity;

import java.awt.*;

public class PhysicsProperties {
    public Point origin;
    public Velocity velocity;
    public boolean movable;
    public boolean hasCollisions;
    public CollisionShape bounds;

    public PhysicsProperties() {
        this(new Point(), new Velocity(), false, false, null);
    }

    public PhysicsProperties(Point origin, Velocity velocity,
                             boolean movable, boolean hasCollisions,
                             CollisionShape bounds) {
        this.origin = origin;
        this.velocity = velocity;
        this.movable = movable;
        this.hasCollisions = hasCollisions;
        this.bounds = bounds;
    }
}
