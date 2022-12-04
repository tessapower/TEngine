package tengine.physics;

import tengine.Actor;
import tengine.physics.collisions.shapes.CollisionRect;

/**
 * A builder for a <code>TPhysicsComponent</code>. Use this to create an
 * <code>TPhysicsComponent</code> in a simple and readable way. E.g.
 * <br />
 * <pre>
 * var boxPhysicsBuilder = new TPhysicsComponentBuilder();
 * boxPhysicsBuilder.actor(box)
 *                  .isStatic(true)                   // Optional: default = true
 *                  .collisionShape(boxCollisionRect) // Optional: default = null
 *                  .hasCollisions(true);             // Optional: default = false;
 *
 * var boxPhysics = boxPhysicsBuilder.build();
 * </pre>
 */
public class TPhysicsComponentBuilder {
    private Actor actor;
    private boolean isStatic = true;
    private CollisionRect collisionShape = null;
    private boolean hasCollisions = false;

    public TPhysicsComponentBuilder actor(Actor actor) {
        this.actor = actor;

        return this;
    }

    public TPhysicsComponentBuilder isStatic(boolean isStatic) {
        this.isStatic = isStatic;

        return this;
    }

    public TPhysicsComponentBuilder collisionShape(CollisionRect collisionShape) {
        this.collisionShape = collisionShape;

        return this;
    }

    public TPhysicsComponentBuilder hasCollisions(boolean hasCollisions) {
        this.hasCollisions = hasCollisions;

        return this;
    }

    public TPhysicsComponent build() {
        return new TPhysicsComponent(actor, isStatic, collisionShape, hasCollisions);
    }
}
