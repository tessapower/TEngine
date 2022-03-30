package graphics;

import engine.GraphicsCtx;

import java.awt.*;

abstract public class TShape extends TObject {
    Color outlineColor;
    Color fillColor;
    boolean isFilled;

    public TShape(Dimension dimension) {
        super(dimension);
        outlineColor = Color.BLACK;
        fillColor = null;
        isFilled = false;
    }

    abstract protected void paint(GraphicsCtx ctx);
}
