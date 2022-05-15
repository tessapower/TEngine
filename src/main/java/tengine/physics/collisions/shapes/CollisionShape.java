package tengine.physics.collisions.shapes;

import tengine.geom.TPoint;

import java.awt.*;

public interface CollisionShape {
    Dimension dimension();
    TPoint origin();
    void setOrigin(TPoint origin);
    boolean contains(TPoint point);
    TPoint midpoint();
    boolean equals(Object o);
    int hashCode();
}
