package tengine.geom;

import java.awt.geom.Point2D;

public class TPoint extends Point2D.Double {
    public TPoint(double x, double y) {
        super(x, y);
    }

    public TPoint() {
        super();
    }

    public TPoint translate(double dx, double dy) {
        return new TPoint(this.x + dx, this.y + dy);
    }
}
