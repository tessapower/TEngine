package tengine;

import tengine.geom.TPoint;
import tengine.graphics.components.TGraphicObject;
import tengine.physics.TPhysicsComponent;
import tengine.physics.collisions.shapes.CollisionShape;
import tengine.physics.kinematics.TVelocity;
import tengine.world.World;

import java.util.Optional;

public abstract class Actor {
    protected TPhysicsComponent physics = null;
    protected TGraphicObject graphic = null;

    protected TVelocity velocity = null;
    protected TPoint origin = new TPoint();

    protected World world = null;

    protected boolean destroyWhenOffScreen = false;
    protected boolean pendingDestroy = false;

    /**
     * An <code>Actor</code> is a game object that the player can control or interact with.
     * By default, an actor does not have a graphical (<code>TGraphicsComponent</code>) or physical
     * (<code>TPhysicsComponent</code>) representation. These should be created and associated with
     * the <code>Actor</code> in the overriding class constructor before the <code>Actor</code> can
     * be used. You can also choose not to have a physical component, if it's not necessary.
     * Having a graphical component for an <code>Actor</code> is always necessary, however.
     */
    public Actor() {}

    /**
     * Use this method when you want to manually set the origin of the <code>Actor</code> and
     * its graphical and physical components.
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

    public void destroyWhenOffScreen(boolean b) {
        destroyWhenOffScreen = b;
    }

    public boolean destroyWhenOffScreen() {
        return destroyWhenOffScreen;
    }

    /**
     * Mark this <code>Actor</code> to be destroyed on the next update.
     */
    public void markPendingDestroy() {
       pendingDestroy = true;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    /**
     * Remove this <code>Actor</code>'s graphic from anywhere it is displayed, and remove it
     * from the world it's assigned to if necessary.
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
