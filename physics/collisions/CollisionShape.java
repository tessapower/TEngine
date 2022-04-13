package physics.collisions;

import java.awt.*;

public interface CollisionShape {
    boolean contains(Point p);
    boolean collides(CollisionShape bounds);
    void setOrigin(Point point);
}
