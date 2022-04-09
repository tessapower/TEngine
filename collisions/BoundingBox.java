package collisions;

import java.awt.*;

public class BoundingBox implements CollisionPrimitive {
    private Point origin;
    private final Dimension dimension;

    public BoundingBox(Point origin, Dimension dimension) {
        this.origin = origin;
        this.dimension = dimension;
    }

    public boolean contains(Point point) {
        return point.x >= origin.x && point.y >= origin.y && point.x < origin.x + dimension.width && point.y < origin.y + dimension.height;
    }

    @Override
    public boolean collides(CollisionPrimitive bounds) {
        if (bounds instanceof BoundingBox) {
            return rectRect((BoundingBox) bounds);
        }
        // else if...
        // Springboard into rectCircle
        // Springboard into rectRect
        // Springboard into rectPoint
        // Springboard into rectLine
        // etc...

        return false;
    }

    @Override
    public void setOrigin(Point point) {
        this.origin = point;
    }

    private boolean rectRect(BoundingBox other) {
        return other.contains(topCenter()) || other.contains(leftCenter()) || other.contains(rightCenter()) || other.contains(bottomCenter());
    }

    private Point topLeft() {
        return new Point(origin.x, origin.y);
    }

    private Point topCenter() {
        return new Point((int) (origin.x + dimension.width * 0.5), origin.y);
    }

    private Point topRight() {
        return new Point(origin.x + dimension.width, origin.y);
    }

    private Point leftCenter() {
        return new Point(origin.x, (int) (origin.y + dimension.height * 0.5));
    }

    private Point rightCenter() {
        return new Point(origin.x + dimension.width, origin.y);
    }

    private Point bottomLeft() {
        return new Point(origin.x, origin.y + dimension.height);
    }

    private Point bottomCenter() {
        return new Point((int) (origin.x + dimension.width * 0.5), origin.y + dimension.height);
    }

    private Point bottomRight() {
        return new Point(origin.x + dimension.width, origin.y + dimension.height);
    }
}