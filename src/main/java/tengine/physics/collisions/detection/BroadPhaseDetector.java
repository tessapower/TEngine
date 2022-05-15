package tengine.physics.collisions.detection;

import tengine.geom.TPoint;
import tengine.physics.TPhysicsComponent;
import tengine.physics.collisions.shapes.CollisionRect;

import java.awt.*;

public class BroadPhaseDetector {
    public static boolean detect(TPhysicsComponent physicsComponentA, TPhysicsComponent physicsComponentB) {
        CollisionRect rectA = physicsComponentA.collisionShape();
        TPoint rectAOrigin = rectA.origin();
        Dimension rectASize = rectA.dimension();

        CollisionRect rectB = physicsComponentB.collisionShape();
        TPoint rectBOrigin = rectB.origin();
        Dimension rectBSize = rectB.dimension();

        return !(rectAOrigin.x + rectASize.width < rectBOrigin.x      // rectA is to the left of rectB
                || rectAOrigin.x > rectBOrigin.x + rectBSize.width    // rectA is to the right of rectB
                || rectAOrigin.y + rectASize.height < rectBOrigin.y   // rectA is above rectB
                || rectAOrigin.y > rectBOrigin.y + rectBSize.height); // rectA is below rectB
    }
}
