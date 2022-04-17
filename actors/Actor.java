package actors;

import physics.collisions.CollisionShape;
import graphics.TObject;
import physics.PhysicsBody;

import java.awt.*;
import java.util.Objects;

public abstract class Actor {
    protected Point origin;
    protected PhysicsBody physicsBody;
    protected TObject sprite;

    public Actor() {
        physicsBody = new PhysicsBody();
        sprite = null;
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

    public TObject sprite() {
        return sprite;
    }

    public PhysicsBody physicsBody() {
        return physicsBody;
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
}
