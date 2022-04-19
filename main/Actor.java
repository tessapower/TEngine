package main;

import graphics.graphicsObjects.TGraphicObject;
import physics.PhysicsBody;
import physics.collisions.shapes.CollisionShape;
import world.World;

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

    public void setWorld(World world) {
        world.graphicsEngine().add(graphicObject);
        // TODO: Eventually include PhysicsEngine.add(physicsBody)
        this.world = world;
    }

    public void destroy() {
        graphicObject.removeFromParent();
        // TODO: Eventually include physicsBody.removeFromSystem()
        world = null;
    }
}
