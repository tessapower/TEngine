package tengine.physics.collisions.detection;

import tengine.physics.PhysicsComponent;
import tengine.physics.collisions.shapes.CollisionCircle;
import tengine.physics.collisions.shapes.CollisionRect;
import tengine.physics.collisions.shapes.CollisionShape;

public class BroadPhaseDetector {
    public static boolean detect(PhysicsComponent p1, PhysicsComponent p2) {
        // TODO: implement broad phase collision detection (overlapping objects)
        // switch over of collision shapes

        return false;
    }

    // Generic vs. Generic
    private static boolean collides(CollisionShape a, CollisionShape b) {
        return false;
    }

    // Rect vs. Generic
    private static boolean collides(CollisionRect a, CollisionShape b) {
        return false;
    }

    // Circle vs. Generic
    private static boolean collides(CollisionCircle a, CollisionShape b) {
        return false;
    }

    // Rect vs. Rect
    private static boolean collides(CollisionRect a, CollisionRect b) {
        return false;
    }

    // Rect vs. Circle
    private static boolean collides(CollisionRect a, CollisionCircle b) {
        return false;
    }

    // Circle vs. Circle
    private static boolean collides(CollisionCircle a, CollisionCircle b) {
        return false;
    }
}
