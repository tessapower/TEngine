package tengine.graphics.components.shapes;

import tengine.graphics.components.TGraphicObject;
import tengine.graphics.context.GraphicsCtx;

import java.awt.*;

/**
 * A graphical representation of a Rectangle.
 *
 * @author Tessa Power
 * @see TShape
 * @see TGraphicObject
 */
public class TRect extends TShape {
    public TRect() {
        this(new Dimension());
    }

    /**
     * Constructs a new <code>TRect</code> with given the <code>Dimension</code>.
     */
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
