package physics.collisions;

import java.awt.*;

public class CollisionRect implements CollisionShape {
    protected Point origin;
    protected Dimension dimension;

    public CollisionRect(Point origin, Dimension dimension) {
        this.origin = origin;
        this.dimension = dimension;
    }

    @Override
    public Dimension dimension() {
        return dimension;
    }

    @Override
    public Point origin() {
        return origin;
    }

    @Override
    public void setOrigin(Point origin) {
        this.origin = origin;
    }

    @Override
    public boolean contains(Point point) {
        return point.x >= origin.x
                && point.y >= origin.y
                && point.x < origin.x + dimension.width
                && point.y < origin.y + dimension.height;
    }

    @Override
    public Point center() {
        return new Point((origin.x + dimension.width / 2), (origin.y + dimension.height / 2));
    }
}
