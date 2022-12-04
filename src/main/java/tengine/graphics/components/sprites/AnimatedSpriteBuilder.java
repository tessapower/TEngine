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
 */
public class AnimatedSpriteBuilder {
    private InputStream is;
    private Dimension frameDimension;
    private int fps;
    private SpriteSequence currentSequence;

    public AnimatedSpriteBuilder inputStream(InputStream is) {
        this.is = is;
        return this;
    }

    public AnimatedSpriteBuilder frameDimension(Dimension frameDimension) {
        this.frameDimension = frameDimension;
        return this;
    }

    public AnimatedSpriteBuilder fps(int fps) {
        this.fps = fps;
        return this;
    }

    public AnimatedSpriteBuilder currentSequence(SpriteSequence currentSequence) {
        this.currentSequence = currentSequence;
        return this;
    }

    public AnimatedSprite build() {
        return new AnimatedSprite(is, frameDimension, fps, currentSequence);
    }
}
