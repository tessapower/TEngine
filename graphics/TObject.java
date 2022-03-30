package graphics;

import engine.GraphicsCtx;
import java.awt.*;

abstract public class TObject {
    protected Dimension dimension;
    private TRotation rotation;
    private TTranslation translation;
    private TScale scale;

    public TObject(Dimension dimension) {
        this.dimension = dimension;
        rotation = TRotation.identity();
        translation = TTranslation.identity();
        scale = TScale.identity();
    }

    public Dimension getDimension() {
        return dimension;
    }

    public void setOrigin(Point origin) {
        translation.dx = origin.x;
        translation.dy = origin.y;
    }

    public int x() {
        return translation.dx;
    }

    public int y() {
        return translation.dy;
    }

    public Point getOrigin() {
        return new Point(translation.dx, translation.dy);
    }

    public void setRotation(double thetaDegrees) {
        rotation.thetaDegrees = thetaDegrees;
    }

    public void setScale(double scaleFactor) {
        scale.xScaleFactor = scaleFactor;
        scale.yScaleFactor = scaleFactor;
    }

    public final void update(GraphicsCtx ctx) {
        ctx.pushCurrentTransform();
        ctx.setTransforms(rotation, translation, scale);
        paint(ctx);
        ctx.popTransform();
    }

    protected abstract void paint(GraphicsCtx ctx);
}
