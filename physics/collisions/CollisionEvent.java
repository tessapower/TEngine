package physics.collisions;

import physics.PhysicsBody;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CollisionEvent {
    private final PhysicsBody movingObj;
    private final PhysicsBody staticObj;
    private Vector impactVector;
    private Point impactSide;

    public CollisionEvent(PhysicsBody p1, PhysicsBody p2) {
        this(p1, p2, new Vector(), new Point());
    }

    public CollisionEvent(PhysicsBody p1, PhysicsBody p2, Vector impactVector, Point impactSide) {
        movingObj = p1;
        staticObj = p2;
        this.impactVector = impactVector;
        this.impactSide = impactSide;
    }

    public PhysicsBody movingObj() {
        return movingObj;
    }

    public PhysicsBody staticObj() {
        return staticObj;
    }

    public void setCollisionVector(Vector collisionVector) {
        this.collisionVector = collisionVector;
    }

    public Vector collisionVector() {
        return collisionVector;
    }
}
