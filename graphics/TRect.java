package graphics;

import java.awt.*;

public class TRect extends TShape {
    public TRect() {
        this(new Dimension());
    }

    public TRect(Dimension dimension) {
        super(dimension);
    }

    @Override
    protected void paint(MasseyGraphicsCtx ctx) {
        if (isFilled) {
            ctx.drawFilledRect(dimension, fillColor);
        } else {
            ctx.drawRect(dimension, outlineColor);
        }
    }
}
