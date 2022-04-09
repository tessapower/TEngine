package actors;

import physics.kinematics.Velocity;

import java.awt.*;

public abstract class MovableActor extends Actor {
    protected Velocity velocity;
    protected Point lastPosition;

    MovableActor() {
        velocity = new Velocity();
        lastPosition = origin;
    }

    /**
     * Translates the actor, including its bounds and sprite, according to its velocity. Override this method if you
     * don't want the sprite or bounds to have the same origin as the actor.
     */
    public void move() {
        lastPosition = origin;
        origin.translate(velocity.dx(), velocity.dy());

        if (sprite != null) {
            sprite.setOrigin(origin);
        }
        if (bounds != null) {
            bounds.setOrigin(origin);
        }
    }

    public void setVelocity(Velocity velocity) {
        this.velocity = velocity;
    }
}
