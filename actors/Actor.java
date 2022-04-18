package actors;

import graphics.GraphicsEngine;
import graphics.TGraphicObject;
import physics.PhysicsBody;
import physics.collisions.CollisionShape;

import java.awt.*;
import java.util.Objects;

public abstract class Actor {
    protected Point origin;
    protected PhysicsBody physicsBody;
    protected TGraphicObject sprite;
    protected World world;

    public Actor() {
        origin = new Point();
        physicsBody = new PhysicsBody();
        sprite = null;
        world = null;
    }

    public void setOrigin(Point origin) {
        this.origin = origin;

        if (sprite != null) {
            sprite.setOrigin(origin);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Actor other)) return false;
        return Objects.equals(origin, other.origin) && Objects.equals(physicsBody, other.physicsBody) && Objects.equals(sprite, other.sprite);
    }

    @Override
    public int hashCode() {
        return Objects.hash(origin, physicsBody, sprite);
    }

    public void removeFromWorld() {
        world.remove(this);
    }

    void setWorld(World world, GraphicsEngine graphicsEngine) {
        graphicsEngine.add(sprite);
        // TODO: Eventually include PhysicsEngine.add(physicsBody)
        this.world = world;
    }

    void destroy() {
        sprite.removeFromParent();
        // TODO: Eventually include physicsBody.removeFromSystem()
        world = null;
    }
}
