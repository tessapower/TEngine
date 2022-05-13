package tengine.physics;

import tengine.physics.collisions.detection.CollisionDetector;
import tengine.physics.collisions.events.CollisionEventNotifier;

import java.util.LinkedHashSet;
import java.util.Set;

public class PhysicsEngine {
    CollisionDetector collisionDetector;
    Set<PhysicsComponent> physicsComponents;
    CollisionEventNotifier collisionEventNotifier;

    public PhysicsEngine() {
        collisionDetector = new CollisionDetector();
        physicsComponents = new LinkedHashSet<>();
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

    public void add(PhysicsComponent component) {
        component.setSystem(this);
        physicsComponents.add(component);
    }

    public void addAll(PhysicsComponent... components) {
        for (var body : components) {
            add(body);
        }
    }

    public void remove(PhysicsComponent component) {
        if (physicsComponents.contains(component)) {
            component.setSystem(null);
            physicsComponents.remove(component);
        }
    }

    public void removeAll() {
        physicsComponents.forEach(body -> body.setSystem(null));
        physicsComponents.clear();
    }
}
