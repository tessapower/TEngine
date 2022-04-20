package main.java.graphics.graphicsObjects.sprites;

import main.java.graphics.context.GraphicsCtx;
import main.java.graphics.graphicsObjects.TGraphicObject;
import main.java.world.GridSquare;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;

public class AnimatedSprite extends TGraphicObject {
    protected Image image;
    protected double elapsedSecs;
    protected int fps;
    protected SpriteSequence currentSequence;
    protected int currentFrame;

    protected AnimatedSprite(InputStream is, Dimension frameDimension, int fps, SpriteSequence currentSequence) {
        super(frameDimension);
        image = ImageLoader.loadImage(is);
        this.fps = fps;
        this.currentSequence = currentSequence;
        currentFrame = 0;
        elapsedSecs = 0;
    }

    @Override
    public void update(double dtSecs) {
        elapsedSecs += dtSecs;

        if (currentSequence.loops() || currentFrame != currentSequence.lastFrame()) {
            int framesToSkip = (int) (elapsedSecs / (1.0 / fps));
            currentFrame = (currentFrame + framesToSkip) % currentSequence.numFrames();
        }

        elapsedSecs %= (1.0 / fps);
    }

    @Override
    protected void draw(GraphicsCtx ctx) {
        GridSquare gridSquare = currentSequence.frames().get(currentFrame);
        int x = gridSquare.col() * dimension.width;
        int y = gridSquare.row() * dimension.height;

        Image frame = subImage(image, new Point(x, y), dimension);

        ctx.drawImage(frame, dimension);
    }

    Image subImage(Image source, Point point, Dimension dimension) {
        if (source == null) {
            System.err.println("Error: cannot extract a sub image from a null image.");

            return null;
        }

        BufferedImage buffered = (BufferedImage) source;

        return buffered.getSubimage(point.x, point.y, dimension.width, dimension.height);
    }
}
