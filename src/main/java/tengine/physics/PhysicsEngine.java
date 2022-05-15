package tengine.physics;

import tengine.physics.collisions.detection.CollisionDetector;
import tengine.physics.collisions.events.CollisionEventNotifier;

import java.util.ArrayList;
import java.util.List;

public class PhysicsEngine {
    CollisionDetector collisionDetector = new CollisionDetector();
    List<TPhysicsComponent> components = new ArrayList<>();
    CollisionEventNotifier collisionEventNotifier = null;

    public PhysicsEngine() {}

    public void update(double dtMillis) {
        for (var component : components) {
           component.update(dtMillis);
        }

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

    public void add(TPhysicsComponent component) {
        components.add(component);
    }

    public void addAll(TPhysicsComponent... components) {
        for (var component : components) {
            add(component);
        }
    }

    public void addAll(List<TPhysicsComponent> components) {
        components.forEach(this::add);
    }

    public void remove(TPhysicsComponent component) {
        if (components.contains(component)) {
            component.setSystem(null);
            components.remove(component);
        }
    }

    public void removeAll() {
        components.forEach(component -> component.setSystem(null));
        components.clear();
    }
}
