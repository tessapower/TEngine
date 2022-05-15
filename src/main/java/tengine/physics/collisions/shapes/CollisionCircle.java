package tengine.physics.collisions.shapes;

import tengine.geom.TPoint;

import java.awt.*;

public class CollisionCircle implements CollisionShape {
    protected TPoint origin;
    protected final Dimension dimension;

    public CollisionCircle(TPoint origin, Dimension dimension) {
        this.origin = origin;
        this.dimension = dimension;
    }

    public int radius() {
        return dimension.width / 2;
    }

    public double x() {
       return origin.x;
    }

    public double y() {
        return origin.y;
    }

    @Override
    public Dimension dimension() {
        return dimension;
    }

    @Override
    public TPoint origin() {
        return origin;
    }

    @Override
    public void setOrigin(TPoint origin) {
        this.origin = origin;
    }

    public boolean contains(TPoint point) {
        return midpoint().distance(point) >= radius();
    }

    @Override
    public TPoint midpoint() {
        return new TPoint((origin.x + radius()), (origin.y + radius()));
    }
}
