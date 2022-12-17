package tengine;

import tengine.geom.TPoint;
import tengine.graphics.components.TGraphicObject;
import tengine.physics.TPhysicsComponent;
import tengine.physics.collisions.shapes.CollisionShape;
import tengine.physics.kinematics.TVelocity;
import tengine.world.World;

import java.util.Optional;

/**
 * An <code>Actor</code>, a.k.a. 'Game Object", "Game Entity", or "Node", is a general purpose
 * object that interacts with your game, and responds to player input or other
 * <code>Actor</code>s in the world. <code>Actor</code>s are modelled on an "Entity Component
 * System" (ECS). Currently, an <code>Actor</code> comprises a graphical component and a physical
 * component, but can be extended to support other types of components, e.g. AI or player data.
 *
 * @author Tessa Power
 * @see TPhysicsComponent
 * @see TGraphicObject
 */
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
     * Set the origin of this <code>Actor</code> and it's components..
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

    /**
     * The origin of this <code>Actor</code>.
     */
    public TPoint origin() {
        return origin;
    }

    /**
     * The x component of the origin of this <code>Actor</code>.
     */
    public double x() {
        return origin.x;
    }

    /**
     * The y component of the origin of this <code>Actor</code>.
     */
    public double y() {
        return origin.y;
    }

    /**
     * The velocity of this <code>Actor</code>.
     */
    public TVelocity velocity() {
        return velocity;
    }

    /**
     * Set the velocity of this <code>Actor</code>.
     */
    public void setVelocity(TVelocity velocity) {
        this.velocity = velocity;
    }

    /**
     * The <code>CollisionShape</code> which encloses this <code>Actor</code>.
     */
    public CollisionShape bounds() {
        return physics.collisionShape();
    }

    /**
     * Set whether this <code>Actor</code> should be destroyed when it goes offscreen.
     */
    public void destroyWhenOffScreen(boolean b) {
        destroyWhenOffScreen = b;
    }

    /**
     * Whether this <code>Actor</code> should be destroyed when it goes offscreen.
     */
    public boolean destroyWhenOffScreen() {
        return destroyWhenOffScreen;
    }

    /**
     * Mark this <code>Actor</code> to be destroyed on the next update.
     */
    public void markPendingDestroy() {
       pendingDestroy = true;
    }

    /**
     * <strong>WARNING</strong>: this method should not be called directly, instead call
     * <code>World::add(Actor)</code> and the <code>World</code> will take care of calling this 
     * method.
     * 
     * @see World#add(Actor)
     */
    public void setWorld(World world) {
        this.world = world;
    }

    /**
     * Destroys this <code>Actor</code> and removes it from the <code>World</code> it belongs to,
     * if any. <strong>WARNING</strong>: this method should not be called directly, instead call
     * <code>markPendingDestroy</code> and the <code>GameEngine</code> will take care of 
     * destroying this <code>Actor</code> on the next update.
     * 
     * @see Actor#markPendingDestroy
     */
    public void destroy() {
        graphic.removeFromParent();
        if (world != null) {
            world.remove(this);
        }
        setWorld(null);
    }

    /**
     * The graphical component for this <code>Actor</code>. Returns null if the graphic has not been
     * initialized.
     */
    public TGraphicObject graphic() {
        return graphic;
    }

    /**
     * The physical component for this <code>Actor</code>. Returns an optional, as an
     * <code>Actor</code> does not require a <code>TPhysicsComponent</code> before it can
     * reasonably be used.
     */
    public Optional<TPhysicsComponent> physics() {
        return Optional.ofNullable(physics);
    }
}
