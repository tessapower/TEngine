package tengine.physics;

import tengine.physics.collisions.shapes.CollisionRect;
import tengine.physics.collisions.shapes.CollisionShape;
import tengine.physics.kinematics.Velocity;

import java.awt.*;

public class PhysicsComponent {
    private PhysicsEngine system;
    private Velocity velocity;
    private boolean isStatic;
    private boolean hasCollisions;
    private final CollisionRect collisionShape;

    public PhysicsComponent() {
        this(new Velocity(), true, null, false);
    }

    public PhysicsComponent(Velocity velocity, boolean isStatic, CollisionRect collisionShape, boolean hasCollisions) {
        this.velocity = velocity;
        this.isStatic = isStatic;
        this.collisionShape = collisionShape;
        this.hasCollisions = hasCollisions;
    }

    public void setSystem(PhysicsEngine system) {
        this.system = system;
    }

    public PhysicsEngine system() {
        return system;
    }

    public void removeFromSystem() {
        if (system != null) {
            system.remove(this);
        }
    }

    public void update(double dtMillis) {
        Point origin = collisionShape.origin();
        origin.translate((int)(velocity.dx() * dtMillis), (int)(velocity.dy() * dtMillis));
        collisionShape.setOrigin(origin);
    }

    public CollisionShape collisionShape() {
        return collisionShape;
    }

    public void setOrigin(Point origin) {
        collisionShape.setOrigin(origin);
    }

    public void setVelocity(Velocity velocity) {
        this.velocity = velocity;
    }

    public Velocity velocity() {
        return this.velocity;
    }

    public void setStatic(boolean b) {
        isStatic = b;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public void setHasCollisions(boolean b) {
        this.hasCollisions = b;
    }

    public boolean hasCollisions() {
        return hasCollisions;
    }
}
