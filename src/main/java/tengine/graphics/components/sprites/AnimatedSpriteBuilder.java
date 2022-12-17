package tengine.graphics.components.sprites;

import java.awt.*;
import java.io.InputStream;

/**
 * A builder for an <code>AnimatedSprite</code>. Use this to create an
 * <code>AnimatedSprite</code> in a simple and readable way. E.g.
 * <br />
 * <pre>
 * var monsterBuilder = new AnimatedSpriteBuilder();
 * monsterBuilder.inputStream(monsterInputStream)
 *               .frameDimension(monsterDim)
 *               .fps(25)
 *               .currentSequence(patrolling);
 *
 * var monster = monsterBuilder.build();
 * </pre>
 *
 * @author Tessa Power
 * @see AnimatedSprite
 * @see SpriteSequence
 */
public class AnimatedSpriteBuilder {
    private InputStream is;
    private Dimension frameDimension;
    private int fps;
    private SpriteSequence currentSequence;

    /**
     * The <code>InputStream</code> to use to build an <code>AnimatedSprite</code>.
     */
    public AnimatedSpriteBuilder inputStream(InputStream is) {
        this.is = is;

        return this;
    }

    /**
     * The <code>Dimension</code> of the frames of the <code>AnimatedSprite</code>.
     */
    public AnimatedSpriteBuilder frameDimension(Dimension frameDimension) {
        this.frameDimension = frameDimension;

        return this;
    }

    /**
     * The frames per second for the <code>AnimatedSprite</code>.
     */
    public AnimatedSpriteBuilder fps(int fps) {
        this.fps = fps;

        return this;
    }

    /**
     * The current <code>SpriteSequence</code> for the <code>AnimatedSprite</code>.
     */
    public AnimatedSpriteBuilder currentSequence(SpriteSequence currentSequence) {
        this.currentSequence = currentSequence;

        return this;
    }

    /**
     * Constructs a new <code>AnimatedSprite</code>.
     *
     * @throws IllegalArgumentException if any of the constructor parameters are not initialized.
     */
    public AnimatedSprite build() {
        if (is == null) {
            throw new IllegalArgumentException("InputStream cannot be null");
        }

        if (frameDimension == null) {
            throw new IllegalArgumentException("Frame dimension cannot be null");
        }

        if (fps <= 0) {
            throw new IllegalArgumentException("FPS must be a positive, non-zero integer");
        }

        if (currentSequence == null) {
            throw new IllegalArgumentException("SpriteSequence cannot be null");
        }

        return new AnimatedSprite(is, frameDimension, fps, currentSequence);
    }
}
