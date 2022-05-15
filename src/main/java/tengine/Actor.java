package tengine;

import tengine.geom.TPoint;
import tengine.graphics.components.TGraphicObject;
import tengine.physics.TPhysicsComponent;
import tengine.physics.collisions.shapes.CollisionShape;
import tengine.physics.kinematics.TVelocity;
import tengine.world.World;

public abstract class Actor {
    protected TPhysicsComponent physics;
    protected TGraphicObject graphic;
    protected TVelocity velocity;
    protected TPoint origin;
    protected World world;

    public Actor() {
        origin = new Point();
        physics = new PhysicsComponent();
        graphic = null;
        velocity = null;
        origin = new TPoint();
        world = null;
    }

    /**
     * Use this method when you want to manually set the origin of the Actor and its components.
     */
    public void setOrigin(TPoint origin) {
        this.origin = origin;

        if (graphic != null) {
            graphic.setOrigin(origin);
        }

        if (physics.collisionShape() != null) {
            physics.setOrigin(origin);
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

    public void removeFromWorld() {
        world.remove(this);
    }

    public void addToWorld(World world) {
        this.world = world;
    }

    public void destroy() {
        graphic.removeFromParent();
        physics.removeFromSystem();
        world = null;
    }

    public TGraphicObject graphic() {
        return graphic;
    }

    public TPhysicsComponent physics() {
        return physics;
    }
}
