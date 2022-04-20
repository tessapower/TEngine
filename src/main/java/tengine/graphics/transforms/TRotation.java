package tengine.graphics.transforms;

import tengine.graphics.context.GraphicsCtx;

import java.util.Objects;

public class TRotation implements TTransform {
    public double thetaDegrees;
    private TTranslation translation;

    public TRotation(double thetaDegrees) {
        this.thetaDegrees = thetaDegrees;
    }

    public static TRotation identity() {
        return new TRotation(0);
    }

    @Override
    public void apply(GraphicsCtx ctx) {
        ctx.rotate(thetaDegrees, translation.dx, translation.dy);
    }

    @Override
    public boolean equals(Object o) {
        // TODO: compare negative rotations & mod super massive rotations?
        if (this == o) return true;
        if (!(o instanceof TRotation other)) return false;

        return this.thetaDegrees == other.thetaDegrees;
    }

    @Override
    public int hashCode() {
        return Objects.hash(thetaDegrees);
    }

    public void setTranslation(TTranslation translation) {
        this.translation = translation;
    }
}
