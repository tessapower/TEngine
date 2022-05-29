package tengine.physics;

import tengine.Actor;
import tengine.physics.collisions.detection.CollisionDetector;
import tengine.physics.collisions.events.CollisionEvent;
import tengine.physics.collisions.events.CollisionEventNotifier;

import java.util.List;
import java.util.Set;

public class PhysicsEngine {
    private final CollisionDetector collisionDetector = new CollisionDetector();
    private CollisionEventNotifier collisionEventNotifier = null;

    public PhysicsEngine() {}

    public void processCollisions(List<Actor> actors, double dtSec) {
        // Step 2: Detect collisions
         Set<CollisionEvent> collisions = collisionDetector.detectCollisions(actors);

        // Step 3: Resolve collisions
        // resolveCollisions(collisions);

        // Step 4: Notify collisions
        if (collisionEventNotifier != null) {
             collisions.forEach(event -> collisionEventNotifier.notifyCollision(event));
        }
    }

    public void setCollisionEventNotifier(CollisionEventNotifier eventNotifier) {
        collisionEventNotifier = eventNotifier;
    }
}
