package tengine.graphics.components.sprites;

import tengine.graphics.components.TGraphicObject;
import tengine.graphics.context.GraphicsCtx;

import java.awt.*;
import java.io.InputStream;

/**
 * Represents a two-dimensional image that can be drawn to the screen.
 *
 * @author Tessa Power
 * @see AnimatedSprite
 */
public class Sprite extends TGraphicObject {
    protected Image image;

    /**
     * Constructs a <code>Sprite</code> from the given <code>InputStream</code> with the given
     * <code>Dimension</code>.
     */
    public Sprite(InputStream is, Dimension dimension) {
        super(dimension);
        image = ImageLoader.loadImage(is);
    }

    /**
     * Draw this <code>Sprite</code> to the screen using the given <code>GraphicsCtx</code>.
     */
    @Override
    protected void draw(GraphicsCtx graphicsCtx) {
        graphicsCtx.drawImage(image, dimension);
    }
}
