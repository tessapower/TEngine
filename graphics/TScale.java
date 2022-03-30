package graphics;

import engine.GraphicsCtx;

public class TScale implements TTransform {
    public double xScaleFactor;
    public double yScaleFactor;

    public TScale(double xScaleFactor, double yScaleFactor) {
        this.xScaleFactor = xScaleFactor;
        this.yScaleFactor = yScaleFactor;
    }

    public static TScale identity() {
        return new TScale(1, 1);
    }

    @Override
    public void apply(GraphicsCtx ctx) {
        ctx.scale(xScaleFactor, yScaleFactor);
    }
}
