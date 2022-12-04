package tengine.geom;

import java.awt.geom.Point2D;

/**
 * Defines a point representing a location in (x, y) coordinate space.
 */
public class TPoint extends Point2D.Double {
    public TPoint(double x, double y) {
        super(x, y);
    }

    public TPoint() {
        super();
    }

    /**
     * Returns a new TPoint which is this TPoint translated by the given dx and dy.
     */
    public TPoint translate(double dx, double dy) {
        return new TPoint(this.x + dx, this.y + dy);
    }
}
