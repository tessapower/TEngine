package physics.collisions;

import java.awt.*;

public class CollisionRect implements CollisionShape {
    Point origin;
    Dimension dimension;

    public CollisionRect(Point origin, Dimension dimension) {
        this.origin = origin;
        this.dimension = dimension;
    }

    @Override
    public void setOrigin(Point origin) {
        this.origin = origin;
    }
}
