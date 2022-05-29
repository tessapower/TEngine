package tengine;

import tengine.geom.TPoint;
import tengine.graphics.components.TGraphicObject;
import tengine.physics.TPhysicsComponent;
import tengine.physics.collisions.shapes.CollisionShape;
import tengine.physics.kinematics.TVelocity;
import tengine.world.World;

public abstract class Actor {
    protected TPhysicsComponent physics = null;
    protected TGraphicObject graphic = null;
    protected TVelocity velocity = null;
    protected TPoint origin = new TPoint();
    protected World world = null;
    protected boolean destroyWhenOffScreen = false;
    protected boolean pendingDestroy = false;

    public Actor() {}

    /**
     * Use this method when you want to manually set the origin of the Actor and its components.
     */
    public void setOrigin(TPoint origin) {
        this.origin = origin;

        if (graphic != null) {
            graphic.setOrigin(origin);
        }

        if (physics != null) {
            physics.collisionShape().setOrigin(origin);
        }
    }

    public TPoint origin() {
        return origin;
    }

    public double x() {
        return origin.x;
    }

    public double y() {
        return origin.y;
    }

    public TVelocity velocity() {
        return velocity;
    }

    public void setVelocity(TVelocity velocity) {
        this.velocity = velocity;
    }

    public CollisionShape bounds() {
        return physics.collisionShape();
    }

    public void setDestroyWhenOffScreen(boolean b) {
        destroyWhenOffScreen = b;
    }

    public boolean shouldDestroyWhenOffScreen() {
        return destroyWhenOffScreen;
    }

    /**
     * Mark this Actor to be destroyed on the next update.
     */
    public void markPendingDestroy() {
       pendingDestroy = true;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    /**
     * Remove this Actor's graphic from anywhere it is displayed, and remove it from the assigned world if necessary.
     */
    public void destroy() {
        graphic.removeFromParent();
        if (world != null) {
            world.remove(this);
        }
        world = null;
    }

    public TGraphicObject graphic() {
        return graphic;
    }

    public TPhysicsComponent physics() {
        return physics;
    }
}
