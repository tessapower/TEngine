package graphics.graphicsObjects.sprites;

import graphics.graphicsObjects.TGraphicObject;

import java.awt.*;

public abstract class Sprite extends TGraphicObject {
    protected Image image;

    protected Sprite(String filename, Dimension dimension) {
        super(dimension);
        image = ImageLoader.loadImage(filename);
    }
}
