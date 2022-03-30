package graphics;

import engine.GraphicsCtx;

import java.awt.*;

abstract public class TShape extends TObject {
    public Color outlineColor;
    public Color fillColor;
    public boolean isFilled;

    public TShape(Dimension dimension) {
        super(dimension);
        outlineColor = Color.BLACK;
        fillColor = null;
        isFilled = false;
    }

    abstract protected void paint(GraphicsCtx ctx);
}
