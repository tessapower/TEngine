package graphics;

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
}
