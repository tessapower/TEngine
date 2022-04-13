package physics;

import physics.collisions.CollisionShape;
import physics.kinematics.Velocity;

public class PhysicsProperties {
    public Velocity velocity;
    public boolean movable;
    public boolean hasCollisions;
    public CollisionShape collisionShape;

    public PhysicsProperties() {
        this(new Velocity(), false, null, false);
    }

    public PhysicsProperties(Velocity velocity, boolean movable,
                             CollisionShape collisionShape, boolean hasCollisions) {
        this.velocity = velocity;
        this.movable = movable;
        this.collisionShape = collisionShape;
        this.hasCollisions = hasCollisions;
    }
}
