package tengine.graphics.components.sprites;

import tengine.graphics.components.TGraphicObject;
import tengine.graphics.context.GraphicsCtx;
import tengine.world.GridSquare;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.function.Consumer;

/**
 * A <code>Sprite</code>, but it's animated!
 *
 * @author Tessa Power
 * @see AnimatedSpriteBuilder
 * @see Sprite
 * @see SpriteSequence
 */
public class AnimatedSprite extends TGraphicObject {
    protected Image image;
    protected double elapsedSecs;
    protected int fps;
    protected SpriteSequence currentSequence;
    protected int currentFrame;
    protected Consumer<SpriteSequence> sequenceEnd = null;

    /**
     * An <code>AnimatedSprite</code> should not be constructed directly, rather use an
     * <code>AnimatedSpriteBuilder</code> to construct a new object.
     *
     * @see AnimatedSpriteBuilder
     */
    AnimatedSprite(InputStream is, Dimension frameDimension, int fps, SpriteSequence currentSequence) {
        super(frameDimension);
        image = ImageLoader.loadImage(is);
        this.fps = fps;
        this.currentSequence = currentSequence;
        currentFrame = 0;
        elapsedSecs = 0;
    }

    /**
     * Let this <code>AnimatedSprite</code> update since it was last update <code>dtSecs</code>
     * seconds ago.
     */
    @Override
    public void update(double dtSecs) {
        elapsedSecs += dtSecs;

        if (currentSequence.loops() || currentFrame != currentSequence.lastFrame()) {
            int framesToSkip = (int) (elapsedSecs / (1.0 / fps));
            if (!currentSequence.loops()) {
                framesToSkip = Math.min(framesToSkip, currentSequence.lastFrame() - currentFrame);
            }
            currentFrame = (currentFrame + framesToSkip) % currentSequence.numFrames();
        }

        // TODO: only notify once if not looping
        if (currentFrame == currentSequence.lastFrame() && sequenceEnd != null) {
            sequenceEnd.accept(currentSequence);
        }

        elapsedSecs %= (1.0 / fps);
    }

    /** Draws this <code>AnimatedSprite</code> to the screen using the given
     * <code>GraphicsCtx</code>.
     */
    @Override
    protected void draw(GraphicsCtx ctx) {
        GridSquare gridSquare = currentSequence.frames().get(currentFrame);
        int x = gridSquare.col() * dimension.width;
        int y = gridSquare.row() * dimension.height;

        Image frame = subImage(image, new Point(x, y), dimension);

        ctx.drawImage(frame, dimension);
    }

    /**
     * Sets the callback method which notifies the receiver every time the current
     * <code>SpriteSequence</code> ends.
     */
    public void setSequenceEndCallback(Consumer<SpriteSequence> onSequenceEnd) {
        sequenceEnd = onSequenceEnd;
    }

    /**
     * Retrieves the frame starting at the given <code>Point</code> with the given
     * <code>Dimension</code> from the sprite sheet for this <code>AnimatedSprite</code>.
     */
    Image subImage(Image source, Point point, Dimension dimension) {
        if (source == null) {
            System.err.println("Error: cannot extract a sub image from a null image.");

            return null;
        }

        BufferedImage buffered = (BufferedImage) source;

        return buffered.getSubimage(point.x, point.y, dimension.width, dimension.height);
    }

    /**
     * Resets the current <code>SpriteSequence</code> back to the first frame.
     */
    public void resetAnimation() {
        currentFrame = 0;
    }

}
