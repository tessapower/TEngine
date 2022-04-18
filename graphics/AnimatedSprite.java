package graphics;

import actors.GridSquare;

import java.awt.*;
import java.awt.image.BufferedImage;

public class AnimatedSprite extends TGraphicObject {
    protected Image image;
    protected double elapsedSecs;
    protected int fps;
    protected SpriteSequence currentSequence;
    protected int currentFrame;

    protected AnimatedSprite(String filename, Dimension frameDimension, int fps, SpriteSequence currentSequence) {
        super(frameDimension);
        image = ImageLoader.loadImage(filename);
        this.fps = fps;
        this.currentSequence = currentSequence;
        currentFrame = 0;
        elapsedSecs = 0;
    }

    @Override
    public void update(double dtSecs) {
        elapsedSecs += dtSecs;

        int framesToSkip = (int) (elapsedSecs / (1.0 / fps));

        currentFrame = (currentFrame + framesToSkip) % currentSequence.numFrames();
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
