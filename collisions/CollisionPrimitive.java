package collisions;

import java.awt.*;

public interface CollisionPrimitive {
    boolean contains(Point p);

    boolean collides(CollisionPrimitive bounds);

    void setOrigin(Point point);
}
