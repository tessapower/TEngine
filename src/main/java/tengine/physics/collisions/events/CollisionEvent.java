package tengine.physics.collisions.events;

import tengine.geom.TPoint;
import tengine.physics.TPhysicsComponent;
import tengine.physics.kinematics.TVector;

import java.util.Objects;

public class CollisionEvent {
    private final TPhysicsComponent movingObj;
    private final TPhysicsComponent staticObj;
    private TVector impactVector;
    private TPoint impactSide;

    public CollisionEvent(TPhysicsComponent p1, TPhysicsComponent p2) {
        this(p1, p2, new TVector(), new TPoint());
    }

    public CollisionEvent(TPhysicsComponent p1, TPhysicsComponent p2, TVector impactVector, TPoint impactSide) {
        movingObj = p1;
        staticObj = p2;
        this.impactVector = impactVector;
        this.impactSide = impactSide;
    }

    public TPhysicsComponent movingObj() {
        return movingObj;
    }

    public TPhysicsComponent staticObj() {
        return staticObj;
    }

    public void setImpactVector(TVector impactVector) {
        this.impactVector = impactVector;
    }

    public TVector impactVector() {
        return impactVector;
    }

    public void setImpactSide(TPoint impactSide) {
        this.impactSide = impactSide;
    }

    public TPoint impactSide() {
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
