package tengine.physics;

import tengine.physics.collisions.shapes.CollisionRect;
import tengine.physics.kinematics.Velocity;

public class PhysicsComponent {
    public PhysicsEngine system;
    public Velocity velocity;
    public boolean isStatic;
    public boolean hasCollisions;
    // TODO: Generalise to use a CollisionShape
    public CollisionRect collisionShape;

    public PhysicsComponent() {
        this(new Velocity(), true, null, false);
    }

    public PhysicsComponent(Velocity velocity, boolean isStatic,
                            CollisionRect collisionShape, boolean hasCollisions) {
        this.velocity = velocity;
        this.isStatic = isStatic;
        this.collisionShape = collisionShape;
        this.hasCollisions = hasCollisions;
    }

    void setSystem(PhysicsEngine system) {
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
}
