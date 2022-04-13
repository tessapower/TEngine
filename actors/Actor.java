package actors;

import physics.collisions.CollisionShape;
import graphics.TObject;
import physics.PhysicsProperties;

import java.awt.*;

public abstract class Actor {
    protected Point origin;
    protected PhysicsProperties physicsProps;
    protected TObject sprite;

    public Actor() {
        physicsProps = new PhysicsProperties();
        sprite = null;
    }

    public void setOrigin(Point origin) {
        this.origin = origin;

        if (sprite != null) {
            sprite.setOrigin(origin);
        }

        if (this.physicsProps.collisionShape != null) {
            physicsProps.collisionShape.setOrigin(origin);
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
        return physicsProps.collisionShape;
    }

    public TObject sprite() {
        return sprite;
    }

    public PhysicsProperties physicsProps() {
        return physicsProps;
    }
}
