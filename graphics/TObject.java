package graphics;

import java.awt.*;

abstract public class TObject {
    protected Dimension dimension;
    protected final TRotation rotation;
    protected final TTranslation translation;
    protected final TScale scale;
    protected TCompound parent;

    public TObject(Dimension dimension) {
        this.dimension = dimension;
        rotation = TRotation.identity();
        translation = TTranslation.identity();
        scale = TScale.identity();
        parent = null;
    }

    public Dimension dimension() {
        return dimension;
    }

    public int width() {
        return dimension.width;
    }

    public int height() {
        return dimension.height;
    }

    public void setOrigin(Point origin) {
        translation.dx = origin.x;
        translation.dy = origin.y;
    }

    public Point origin() {
        return new Point(translation.dx, translation.dy);
    }

    public int x() {
        return translation.dx;
    }

    public int y() {
        return translation.dy;
    }

    public void setRotation(double thetaDegrees) {
        rotation.thetaDegrees = thetaDegrees;
    }

    public void setScale(double scaleFactor) {
        scale.xScaleFactor = scaleFactor;
        scale.yScaleFactor = scaleFactor;
    }

    void setParent(TCompound parent) {
        this.parent = parent;
    }

    public TCompound parent() {
        return parent;
    }

    public void removeFromParent() {
        if (parent != null) {
            parent.remove(this);
        }
    }

    public final void update(GraphicsCtx ctx) {
        ctx.pushCurrentTransform();
        ctx.setTransforms(rotation, translation, scale);
        paint(ctx);
        ctx.popTransform();
    }

    protected abstract void paint(GraphicsCtx ctx);

    public abstract boolean equals(Object o);
    public abstract int hashCode();
}
