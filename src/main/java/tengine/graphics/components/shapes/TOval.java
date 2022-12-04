package tengine.graphics.components.shapes;

import tengine.graphics.context.GraphicsCtx;

import java.awt.*;

/**
 * A graphical representation of an Oval.
 */
public class TOval extends TShape {
    public TOval() {
        this(new Dimension());
    }

    public TOval(Dimension dimension) {
        super(dimension);
    }

    /**
     * Draw this <code>TOval</code> using the given <code>GraphicsCtx</code>.
     */
    @Override
    protected void draw(GraphicsCtx ctx) {
        if (isFilled) {
            ctx.drawFilledCircle(dimension, fillColor);
        } else {
            ctx.drawCircle(dimension, outlineColor);
        }
    }
}
