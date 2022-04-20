package main.java.graphics.graphicsObjects.shapes;

import main.java.graphics.graphicsObjects.TGraphicObject;

import java.awt.*;

abstract public class TShape extends TGraphicObject {
    public Color outlineColor;
    public Color fillColor;
    public boolean isFilled;

    public TShape(Dimension dimension) {
        super(dimension);
        outlineColor = Color.BLACK;
        fillColor = null;
        isFilled = false;
    }
}
