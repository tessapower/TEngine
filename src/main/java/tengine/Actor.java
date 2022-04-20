package tengine;

import tengine.graphics.graphicsObjects.TGraphicObject;
import tengine.physics.PhysicsBody;
import tengine.physics.collisions.shapes.CollisionShape;
import tengine.world.World;

import java.awt.*;

public abstract class Actor {
    protected Point origin;
    protected PhysicsBody physicsBody;
    protected TGraphicObject graphicObject;
    protected World world;

    public Actor() {
        origin = new Point();
        physicsBody = new PhysicsBody();
        graphicObject = null;
        world = null;
    }

    public void setOrigin(Point origin) {
        this.origin = origin;

        if (graphicObject != null) {
            graphicObject.setOrigin(origin);
        }

//        if (this.physicsBody.collisionShape != null) {
//            physicsBody.collisionShape.setOrigin(origin);
//        }
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
        return physicsBody.collisionShape;
    }

    public void removeFromWorld() {
        world.remove(this);
    }

    public void addToWorld(World world) {
        this.world = world;
    }

    public void destroy() {
        graphicObject.removeFromParent();
        // TODO: Eventually include physicsBody.removeFromSystem()
        world = null;
    }

    public TGraphicObject graphic() {
        return graphicObject;
    }
}
