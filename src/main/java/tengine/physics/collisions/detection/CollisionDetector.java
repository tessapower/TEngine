package tengine.physics.collisions.detection;

import tengine.Actor;
import tengine.physics.collisions.events.CollisionEvent;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CollisionDetector {
    // This currently only implements Broad Phase Collision Detection
     public Set<CollisionEvent> detectCollisions(List<Actor> actors) {
        Set<CollisionEvent> collisions = new HashSet<>();

        for (var actorA : actors) {
            var aPhysics = actorA.physics();
            if (aPhysics.isPresent()) {
                var p = aPhysics.get();
                if (p.hasCollisions()) {
                    for (var actorB : actors) {
                        var bPhysics = actorB.physics();
                        if (actorA == actorB || bPhysics.isEmpty() || !bPhysics.get().hasCollisions())
                            continue;

                        if (BroadPhaseDetector.detect(aPhysics.get(), bPhysics.get())) {
                            if (actorA.hashCode() < actorB.hashCode()) {
                                collisions.add(new CollisionEvent(actorA, actorB));
                            } else {
                                collisions.add(new CollisionEvent(actorB, actorA));
                            }
                        }
                    }
                }
            }
        }

        return Collections.unmodifiableSet(collisions);
     }
}
