package physics.collisions.events;

import physics.PhysicsBody;
import physics.kinematics.Vector;

import java.awt.*;
import java.util.Objects;

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

    public void setImpactVector(Vector impactVector) {
        this.impactVector = impactVector;
    }

    public Vector impactVector() {
        return impactVector;
    }

    public void setImpactSide(Point impactSide) {
        this.impactSide = impactSide;
    }

    public Point impactSide() {
        return impactSide;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CollisionEvent other)) return false;
        return movingObj == other.movingObj
                && staticObj == other.staticObj
                && Objects.equals(impactVector, other.impactVector)
                && Objects.equals(impactSide, other.impactSide);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movingObj, staticObj, impactVector, impactSide);
    }
}
