package physics;

import actors.Actor;
import physics.collisions.CollisionDetector;
import physics.collisions.CollisionEvent;
import physics.collisions.CollisionEventNotifier;
import physics.kinematics.Velocity;

import java.awt.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class PhysicsEngine {
    CollisionDetector collisionDetector;
    Set<Actor> actors;
    CollisionEventNotifier collisionEventNotifier;

    public PhysicsEngine() {
        collisionDetector = new CollisionDetector();
        actors = new HashSet<>();
    }

    public void update() {
        // Step 1: Move everything that can move
        for (var actor : actors) {
            if (actor.physicsBody().isStatic) {
                Point origin = actor.origin();
                Velocity velocity = actor.physicsBody().velocity;
                origin.translate(velocity.dx(), velocity.dy());
                actor.setOrigin(origin);
            }
        }

        // Step 2: Detect collisions
         Collection<CollisionEvent> collisions = collisionDetector.detectCollisions(actors);

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

    public void add(Actor actor) {
        actors.add(actor);
    }

    public void addAll(Actor... actors) {
        for (var actor : actors) {
            add(actor);
        }
    }

    public void remove(Actor actor) {
        actors.remove(actor);
    }

    public void removeAll() {
        actors.clear();
    }
}
