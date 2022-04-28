package tengine.physics;

import tengine.physics.collisions.detection.CollisionDetector;
import tengine.physics.collisions.events.CollisionEventNotifier;

import java.util.HashSet;
import java.util.Set;

public class PhysicsEngine {
    CollisionDetector collisionDetector;
    Set<PhysicsComponent> physicsBodies;
    CollisionEventNotifier collisionEventNotifier;

    public PhysicsEngine() {
        collisionDetector = new CollisionDetector();
        physicsBodies = new HashSet<>();
    }

    public void update() {
        throw new RuntimeException("implement me!");
        // Step 1: Move everything that can move
//        for (var body : physicsBodies) {
//            if (body.isStatic) {
//                Point origin = body.origin;
//                Velocity velocity = actor.physicsComponent().velocity;
//                origin.translate(velocity.dx(), velocity.dy());
//                actor.setOrigin(origin);
//            }
//        }

        // Step 2: Detect collisions
//         Collection<CollisionEvent> collisions = collisionDetector.detectCollisions(actors);

        // Step 3: Resolve collisions
        // resolveCollisions(collisions);

        // Step 4: Notify collisions
//        if (collisionEventNotifier != null) {
//             collisions.forEach(event -> collisionEventNotifier.notifyCollision(event));
//        }
    }

    public void setCollisionEventNotifier(CollisionEventNotifier eventNotifier) {
        collisionEventNotifier = eventNotifier;
    }

    public void add(PhysicsComponent physicsComponent) {
        physicsBodies.add(physicsComponent);
    }

    public void addAll(PhysicsComponent... physicsBodies) {
        for (var body : physicsBodies) {
            add(body);
        }
    }

    public void remove(PhysicsComponent physicsComponent) {
        physicsBodies.remove(physicsComponent);
    }

    public void removeAll() {
        physicsBodies.clear();
    }
}
