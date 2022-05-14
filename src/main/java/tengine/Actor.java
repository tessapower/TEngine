package tengine;

import tengine.graphics.components.TGraphicObject;
import tengine.physics.PhysicsComponent;
import tengine.physics.collisions.shapes.CollisionShape;
import tengine.world.World;

import java.awt.*;

public abstract class Actor {
    protected Point origin;
    protected PhysicsComponent physics;
    protected TGraphicObject graphic;
    protected World world;

    public Actor() {
        origin = new Point();
        physics = new PhysicsComponent();
        graphic = null;
        world = null;
    }

    public void setOrigin(Point origin) {
        this.origin = origin;

        if (graphic != null) {
            graphic.setOrigin(origin);
        }

        if (physics.collisionShape() != null) {
            physics.setOrigin(origin);
        }
    }


    public Point origin() {
        return origin;
    }

    public int x() {
        return origin.x;
    }

    public int y() {
        return origin.y;
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
}
