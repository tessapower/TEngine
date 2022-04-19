package graphics.graphicsObjects.sprites;

import graphics.context.GraphicsCtx;
import graphics.graphicsObjects.TGraphicObject;

import java.awt.*;

public class Sprite extends TGraphicObject {
    protected Image image;

    public Sprite(String filename, Dimension dimension) {
        super(dimension);
        image = ImageLoader.loadImage(filename);
    }

    @Override
    protected void draw(GraphicsCtx graphicsCtx) {
        graphicsCtx.drawImage(image, dimension);
    }
}
