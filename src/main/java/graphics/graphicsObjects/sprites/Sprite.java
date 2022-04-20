package main.java.graphics.graphicsObjects.sprites;

import main.java.graphics.context.GraphicsCtx;
import main.java.graphics.graphicsObjects.TGraphicObject;

import java.awt.*;
import java.io.InputStream;

public class Sprite extends TGraphicObject {
    protected Image image;

    public Sprite(InputStream is, Dimension dimension) {
        super(dimension);
        image = ImageLoader.loadImage(is);
    }

    @Override
    protected void draw(GraphicsCtx graphicsCtx) {
        graphicsCtx.drawImage(image, dimension);
    }
}
