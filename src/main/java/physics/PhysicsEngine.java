package main.java.physics;

import main.java.physics.collisions.detection.CollisionDetector;
import main.java.physics.collisions.events.CollisionEventNotifier;

import java.util.HashSet;
import java.util.Set;

public class PhysicsEngine {
    CollisionDetector collisionDetector;
    Set<PhysicsBody> physicsBodies;
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
//                Velocity velocity = actor.physicsBody().velocity;
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

    public void add(PhysicsBody physicsBody) {
        physicsBodies.add(physicsBody);
    }

    public void addAll(PhysicsBody... physicsBodies) {
        for (var body : physicsBodies) {
            add(body);
        }
    }

    public void remove(PhysicsBody physicsBody) {
        physicsBodies.remove(physicsBody);
    }

    public void removeAll() {
        physicsBodies.clear();
    }
}
