package graphics;

import java.util.Objects;

public class TRotation implements TTransform {
    double thetaDegrees;

    public TRotation(double thetaDegrees) {
        this.thetaDegrees = thetaDegrees;
    }

    public static TRotation identity() {
        return new TRotation(0);
    }

    @Override
    public void apply(GraphicsCtx ctx) {
        ctx.rotate(thetaDegrees);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof TRotation other))
            return false;

        return this.thetaDegrees == other.thetaDegrees;
    }

    @Override
    public int hashCode() {
        return Objects.hash(thetaDegrees);
    }
}
