package tengine.graphics.components.shapes;

import tengine.graphics.context.GraphicsCtx;

import java.awt.*;

/**
 * A graphical representation of a Rectangle.
 */
public class TRect extends TShape {
    public TRect() {
        this(new Dimension());
    }

    public TRect(Dimension dimension) {
        super(dimension);
    }

    /**
     * Draw this <code>TRect</code> using the given <code>GraphicsCtx</code>.
     */
    @Override
    protected void draw(GraphicsCtx ctx) {
        if (isFilled) {
            ctx.drawFilledRect(dimension, fillColor);
        } else {
            ctx.drawRect(dimension, outlineColor);
        }
    }
}
