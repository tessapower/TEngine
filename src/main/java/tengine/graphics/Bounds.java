package tengine.graphics;

import tengine.geom.TPoint;

import java.awt.*;

public class Bounds {
    protected TPoint origin;
    protected Dimension dimension;

    public Bounds() {
        this(new TPoint(0, 0), new Dimension(0, 0));
    }

    public Bounds(TPoint origin) {
        this(origin, new Dimension(0, 0));
    }

    public Bounds(Dimension dimension) {
        this(new TPoint(0, 0), dimension);
    }

    public Bounds(TPoint origin, Dimension dimension) {
        this.origin = origin;
        this.dimension = dimension;
    }
}
