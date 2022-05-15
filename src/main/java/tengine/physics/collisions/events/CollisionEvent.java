package tengine.physics.collisions.events;

import tengine.Actor;

public record CollisionEvent(Actor actorA, Actor actorB) {
}
