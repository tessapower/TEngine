package graphics;

import java.awt.*;

public class TOval extends TShape {
    public TOval() {
        this(new Dimension());
    }

    public TOval(Dimension dimension) {
        super(dimension);
    }

    @Override
    protected void paint(GraphicsCtx ctx) {
        if (isFilled) {
            ctx.drawFilledCircle(dimension, fillColor);
        } else {
            ctx.drawCircle(dimension, outlineColor);
        }
    }
}
