package tengine.graphics.components.shapes;

import tengine.graphics.components.TGraphicObject;
import tengine.graphics.context.GraphicsCtx;

import java.awt.*;

/**
 * A graphical representation of an Oval.
 *
 * @author Tessa Power
 * @see TShape
 * @see TGraphicObject
 */
public class TOval extends TShape {
    public TOval() {
        this(new Dimension());
    }

    /**
     * Constructs a new <code>TOval</code> with given the <code>Dimension</code>.
     */
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
