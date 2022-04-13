package actors;

import collisions.CollisionPrimitive;
import graphics.TObject;
import physics.kinematics.PhysicsProperties;

import java.awt.*;

public abstract class Actor {
    protected PhysicsProperties physicsProps;
    public TObject sprite;

    public Actor() {
        physicsProps = new PhysicsProperties();
        sprite = null;
    }

    public void setOrigin(Point origin) {
        this.physicsProps.origin = origin;

        if (sprite != null) {
            sprite.setOrigin(origin);
        }

        if (this.physicsProps.bounds != null) {
            physicsProps.bounds.setOrigin(origin);
        }
    }

    public Point origin() {
        return physicsProps.origin;
    }

    public int x() {
        return physicsProps.origin.x;
    }

    public int y() {
        return physicsProps.origin.y;
    }

    public CollisionPrimitive bounds() {
        return physicsProps.bounds;
    }

    public PhysicsProperties physicsProps() {
        return physicsProps;
    }
}
