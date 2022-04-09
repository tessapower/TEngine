package actors;

import collisions.CollisionPrimitive;
import graphics.TObject;

import java.awt.*;

public abstract class Actor {
    protected Point origin;
    protected CollisionPrimitive bounds;
    public TObject sprite;

    public Actor() {
        origin = new Point(0, 0);
        bounds = null;
        sprite = null;
    }

    public void setOrigin(Point origin) {
        this.origin = origin;

        if (sprite != null) {
            sprite.setOrigin(origin);
        }

        if (bounds != null) {
            bounds.setOrigin(origin);
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

    public CollisionPrimitive bounds() {
        return bounds;
    }
}
