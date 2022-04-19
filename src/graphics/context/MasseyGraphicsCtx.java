package graphics.context;

import graphics.transforms.TTransform;
import main.GameEngine;

import java.awt.*;

// Wraps and adapts the Massey GameEngine to conform to our GraphicsCtx interface.
// Later this can be thrown away, and we can simply use the built-in Graphics2D library.
public class MasseyGraphicsCtx implements GraphicsCtx {
    GameEngine masseyCtx;

    public MasseyGraphicsCtx(GameEngine masseyCtx) {
        this.masseyCtx = masseyCtx;
    }

    //------------------------------------------------------------------------------------------ Drawing Primitives --//

    @Override
    public void drawRect(Dimension dimension, Color color) {
        masseyCtx.changeColor(color);
        masseyCtx.drawRectangle(0, 0, dimension.width, dimension.height);
    }

    @Override
    public void drawCircle(Dimension dimension, Color color) {
        masseyCtx.changeColor(color);
        // TODO: See note in drawFilledCircle
        masseyCtx.drawCircle(dimension.width / 2.0, dimension.height / 2.0, dimension.width * 0.5);
    }

    @Override
    public void drawFilledRect(Dimension dimension, Color color) {
        masseyCtx.changeColor(color);
        masseyCtx.drawSolidRectangle(0, 0, dimension.width, dimension.height);
    }

    @Override
    public void drawFilledCircle(Dimension dimension, Color color) {
        masseyCtx.changeColor(color);
        // The Massey GameEngine strangely treats the center of the circle as the origin for drawing a circle,
        // which is odd as all other shapes consider the top left as the origin. A rect with the same origin
        // and dimensions as a circle would not enclose the circle.
        // TODO: Set drawing coordinates back to 0, 0 when replacing Massey GameEngine!
        masseyCtx.drawSolidCircle(dimension.width / 2.0, dimension.height / 2.0, dimension.width * 0.5);
    }

    //------------------------------------------------------------------------------------------------ Drawing Text --//

    @Override
    public void drawText(Point origin, String text, Font font, Color color) {
        masseyCtx.changeColor(color);
        masseyCtx.drawText(origin.x, origin.y, text, font);
    }

    //---------------------------------------------------------------------------------------------- Drawing Images --//

    @Override
    public void drawImage(Image image) {
        masseyCtx.drawImage(image, 0, 0);
    }

    @Override
    public void drawImage(Image image, Dimension dimension) {
        masseyCtx.drawImage(image, 0, 0, dimension.width, dimension.height);
    }

    //-------------------------------------------------------------------------------------------------- Transforms --//
    @Override
    public void setTransforms(TTransform... transforms) {
        for (var t : transforms) {
            t.apply(this);
        }
    }

    @Override
    public void pushCurrentTransform() {
        masseyCtx.saveCurrentTransform();
    }

    @Override
    public void popTransform() {
        masseyCtx.restoreLastTransform();
    }

    @Override
    public void rotate(double thetaDegrees) {
        masseyCtx.rotate(thetaDegrees);
    }

    @Override
    public void rotate(double thetaDegrees, int dx, int dy) {
        masseyCtx.rotate(thetaDegrees, dx, dy);
    }

    @Override
    public void translate(int dx, int dy) {
        masseyCtx.translate(dx, dy);
    }

    @Override
    public void scale(double xScaleFactor, double yScaleFactor) {
        masseyCtx.scale(xScaleFactor, yScaleFactor);
    }
}
