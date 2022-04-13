package collisions;

import actors.Actor;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CollisionEvent {
    private final Actor movingActor;
    private final Set<Actor> staticActors;
    private Vector collisionVector;

    public CollisionEvent(Actor movingActor, Actor staticActor) {
        this.collisionVector = null;
        this.movingActor = movingActor;
        staticActors = new HashSet<>();
        add(staticActor);
    }

    public void add(Actor... staticActors) {
        this.staticActors.addAll(List.of(staticActors));
        // calculate distance between moving actor origin and static actor origins
    }

    public Actor movingActor() {
        return movingActor;
    }

    public Set<Actor> staticActors() {
        return Collections.unmodifiableSet(staticActors);
    }

    public void setCollisionVector(Vector collisionVector) {
        this.collisionVector = collisionVector;
    }

    public Vector collisionVector() {
        return collisionVector;
    }
}
