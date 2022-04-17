package physics;

import physics.collisions.CollisionRect;
import physics.kinematics.Velocity;

import java.util.Objects;

public class PhysicsBody {
    public PhysicsEngine system;
    public Velocity velocity;
    public boolean isStatic;
    public boolean hasCollisions;
    // TODO: Generalise to use a CollisionShape
    public CollisionRect collisionShape;

    public PhysicsBody() {
        this(new Velocity(), true, null, false);
    }

    public PhysicsBody(Velocity velocity, boolean isStatic,
                       CollisionRect collisionShape, boolean hasCollisions) {
        this.velocity = velocity;
        this.isStatic = isStatic;
        this.collisionShape = collisionShape;
        this.hasCollisions = hasCollisions;
    }

    void setSystem(PhysicsEngine system) {
        this.system = system;
    }

    public void removeFromSystem() {
        // TODO: implement this when finishing PhysicsEngine
        throw new RuntimeException("implement me!");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof PhysicsBody other))
            return false;
        return isStatic == other.isStatic
                && hasCollisions == other.hasCollisions
                && velocity.equals(other.velocity)
                && collisionShape == other.collisionShape;
    }

    @Override
    public int hashCode() {
        return Objects.hash(velocity, isStatic, hasCollisions, collisionShape);
    }
}
