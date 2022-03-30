package engine;

import graphics.TTransform;

import java.awt.*;

public class TGContext implements GraphicsCtx {
    GameEngine engine;
    public TGContext(GameEngine engine) {
        this.engine = engine;
    }

    @Override
    public void drawRect(Dimension dimension, Color color) {
        engine.changeColor(color);

        engine.drawRectangle(0, 0, dimension.width, dimension.height);

        // reset color to default (black)
        engine.changeColor(Color.BLACK);
    }

    @Override
    public void drawCircle(Dimension dimension, Color color) {
        engine.changeColor(color);

        engine.drawCircle(0, 0, dimension.width * 0.5);

        engine.changeColor(Color.BLACK);
    }

    @Override
    public void drawFilledRect(Dimension dimension, Color color) {
        engine.changeColor(color);
        // get parent transforms

        // apply own transforms
        engine.drawSolidRectangle(0, 0, dimension.width, dimension.height);

        // reset color to default (black)
        engine.changeColor(Color.BLACK);
    }

    @Override
    public void drawFilledCircle(Dimension dimension, Color color) {
        engine.changeColor(color);

        engine.drawSolidCircle(0, 0, dimension.width * 0.5);

        engine.changeColor(Color.BLACK);
    }

    @Override
    public void setTransforms(TTransform... transforms) {
        for (var t : transforms) {
            t.apply(this);
        }
    }

    @Override
    public void pushCurrentTransform() {
        engine.saveCurrentTransform();
    }

    @Override
    public void popTransform() {
        engine.restoreLastTransform();
    }

    @Override
    public void rotate(double thetaDegrees) {
        engine.rotate(thetaDegrees);
    }

    @Override
    public void translate(int dx, int dy) {
        engine.translate(dx, dy);
    }

    @Override
    public void scale(double xScaleFactor, double yScaleFactor) {
        engine.scale(xScaleFactor, yScaleFactor);
    }
}
