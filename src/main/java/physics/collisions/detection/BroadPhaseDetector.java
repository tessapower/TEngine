package main.java.physics.collisions.detection;

import main.java.physics.PhysicsBody;
import main.java.physics.collisions.shapes.CollisionCircle;
import main.java.physics.collisions.shapes.CollisionRect;
import main.java.physics.collisions.shapes.CollisionShape;

public class BroadPhaseDetector {
    public static boolean detect(PhysicsBody p1, PhysicsBody p2) {
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
