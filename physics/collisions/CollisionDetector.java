package physics.collisions;

import actors.Actor;

import java.util.*;

public class CollisionDetector {
    public CollisionDetector() {
    }

    public boolean collides(Actor a, Actor b) {
        CollisionShape bounds = a.bounds();
        return bounds.collides(b.bounds());
    }

    public boolean collides(CollisionShape a, CollisionShape b) {
        return a.collides(b);
    }

    // TODO: Write tests for detecting collisions
     public Collection<CollisionEvent> detectCollisions(Set<Actor> actors) {
        Map<Actor, CollisionEvent> collisions = new HashMap<>();

        actors.forEach(actor -> {
            if (actor.physicsProps().movable) {
                // Check for collisions with every other non-moving actor that has collisions enabled
                actors.forEach(otherActor -> {
                    if (actor != otherActor
                            && !otherActor.physicsProps().movable && otherActor.physicsProps().hasCollisions) {
                        Vector collidedSide = actor.bounds().collisionSide(otherActor.bounds());
                        if (collidedSide != null) {
                            // Create a new collision event or add to existing one for this actor
                            if (collisions.containsKey(actor)) {
                                collisions.get(actor).add(otherActor);
                            } else {
                                collisions.put(actor, new CollisionEvent(actor, otherActor));
                            }

                            // TODO: if this staticActor is the closest collided actor, determine and
                            //  set collisionVector
                        }
                    }
                });
            }
        });

        return Collections.unmodifiableCollection(collisions.values());
     }
}
