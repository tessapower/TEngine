package tengine.physics.collisions.detection;

import tengine.Actor;
import tengine.physics.TPhysicsComponent;
import tengine.physics.collisions.events.CollisionEvent;

import java.util.*;

public class CollisionDetector {
     public Collection<CollisionEvent> detectCollisions(List<Actor> actors) {
        Map<Actor, CollisionEvent> collisions = new HashMap<>();

        for (var actor : actors) {
            TPhysicsComponent physics = actor.physics();
            if (physics.hasCollisions()) {
                for (var other : actors) {
                    if (actor == other) continue;
                    // broad phase collision detection
                }
            }
        }

        return Collections.unmodifiableCollection(collisions.values());
     }

}
