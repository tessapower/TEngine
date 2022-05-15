package tengine.physics.collisions.detection;

import tengine.Actor;
import tengine.physics.collisions.events.CollisionEvent;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CollisionDetector {
     public Set<CollisionEvent> detectCollisions(List<Actor> actors) {
        Set<CollisionEvent> collisions = new HashSet<>();

        for (var actorA : actors) {
            if (actorA.physics().hasCollisions()) {
                for (var actorB : actors) {
                    if (actorA == actorB || !actorB.physics().hasCollisions()) continue;

                    if (BroadPhaseDetector.detect(actorA.physics(), actorB.physics())) {
                        if (actorA.hashCode() < actorB.hashCode()) {
                            collisions.add(new CollisionEvent(actorA, actorB));
                        } else {
                            collisions.add(new CollisionEvent(actorB, actorA));
                        }
                    }
                }
            }
        }

        return Collections.unmodifiableSet(collisions);
     }
}
