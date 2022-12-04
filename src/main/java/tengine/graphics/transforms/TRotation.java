package tengine.graphics.transforms;

import tengine.geom.TPoint;

import java.util.Objects;

public class TRotation {
    public double thetaDegrees;
    public TPoint origin;

    /**
     * Rotate <code>thetaDegrees</code> around <code>rotationOrigin</code>.
     */
    public TRotation(double thetaDegrees, TPoint rotationOrigin) {
        this.thetaDegrees = thetaDegrees;
        this.origin = rotationOrigin;
    }

    public static TRotation identity() {
        return new TRotation(0, new TPoint(0, 0));
    }

    @Override
    public boolean equals(Object o) {
        // TODO: compare negative rotations & mod super massive rotations?
        if (this == o) return true;
        if (!(o instanceof TRotation other)) return false;

        return this.thetaDegrees == other.thetaDegrees && origin == other.origin;
    }

    @Override
    public int hashCode() {
        return Objects.hash(thetaDegrees, origin);
    }
}
