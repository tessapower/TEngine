package tengine.physics;

import tengine.Actor;
import tengine.physics.collisions.shapes.CollisionRect;
import tengine.physics.collisions.shapes.CollisionShape;

public class TPhysicsComponent {
    private final Actor actor;

    private CollisionRect collisionShape;
    private PhysicsEngine system;
    private boolean isStatic;
    private boolean hasCollisions;

    public TPhysicsComponent(Actor actor) {
        this(actor, true, null, false);
    }

    public TPhysicsComponent(Actor actor, boolean isStatic, CollisionRect collisionShape, boolean hasCollisions) {
        this.actor = actor;
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
        // Actor will take care of updating the origin of the graphic and collision shape
        actor.setOrigin(actor.origin().translate(
            actor.velocity().dx() * dtMillis,
            actor.velocity().dy() * dtMillis
        ));
    }

    public CollisionShape collisionShape() {
        return collisionShape;
    }

    public void setCollisionShape(CollisionRect collisionShape) {
        this.collisionShape = collisionShape;
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
