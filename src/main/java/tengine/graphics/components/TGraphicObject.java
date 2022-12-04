package tengine.graphics.components;

import tengine.geom.TPoint;
import tengine.graphics.context.GraphicsCtx;
import tengine.graphics.transforms.TRotation;
import tengine.graphics.transforms.TScale;
import tengine.graphics.transforms.TTranslation;

import java.awt.*;
import java.util.Optional;

/**
 * A generic graphical object that can be drawn to the screen.
 */
abstract public class TGraphicObject {
    protected Dimension dimension;

    protected final TRotation rotation = TRotation.identity();
    protected final TTranslation translation = TTranslation.identity();
    protected final TScale scale = TScale.identity();

    protected TGraphicCompound parent = null;

    public TGraphicObject(Dimension dimension) {
        this.dimension = dimension;
    }

    public Dimension dimension() {
        return new Dimension((int) (scale.xScaleFactor * dimension.width),
                (int) (scale.yScaleFactor * dimension.height));
    }

    public int width() {
        return (int) (scale.xScaleFactor * dimension.width);
    }

    public int height() {
        return (int) (scale.yScaleFactor * dimension.height);
    }

    public void setOrigin(TPoint origin) {
        translation.dx = (int) origin.x;
        translation.dy = (int) origin.y;
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

    public Point midPoint() {
        return new Point(dimension.width / 2, dimension.height / 2);
    }

    public void setRotation(double thetaDegrees) {
        rotation.thetaDegrees = thetaDegrees;
    }

    /**
     * Set the rotation of this <code>TGraphicObject</code> around the given <code>origin</code>.
     */
    public void setRotation(double thetaDegrees, Point origin) {
        rotation.thetaDegrees = thetaDegrees;
        rotation.origin = origin;
    }

    public void setScale(double scaleFactor) {
        scale.xScaleFactor = scaleFactor;
        scale.yScaleFactor = scaleFactor;
    }

    void setParent(TGraphicCompound parent) {
        this.parent = parent;
    }

    public Optional<TGraphicCompound> parent() {
        return Optional.ofNullable(parent);
    }

    /**
     * Remove this <code>TGraphicObject</code> from its parent <code>TGraphicCompound</code> if it has one.
     */
    public void removeFromParent() {
        if (parent != null) {
            parent.remove(this);
        }
    }

    public void paint(GraphicsCtx ctx) {
        ctx.pushCurrentTransform();

        ctx.applyTransforms(translation, rotation, scale);
        draw(ctx);

        ctx.popTransform();
    }

    /**
     * Override this method to specify how to draw this graphical object to the screen with the
     * given <code>GraphicsCtx</code>.
     */
    protected abstract void draw(GraphicsCtx ctx);

    /**
     * Give this <code>TGraphicObject</code> a chance to update since the last frame was updated
     * <code>dtSec</code> ago.
     */
    public void update(double dtSec) {
        // No-op
    }
}
