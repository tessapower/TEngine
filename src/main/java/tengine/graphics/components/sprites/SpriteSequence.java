package tengine.graphics.components.sprites;

import tengine.world.GridSquare;

import java.util.List;

/**
 * A sequence of images used together with an <code>AnimatedSprite</code>. A
 * <code>SpriteSequence</code> lets you easily refer to and swap out different sequences.
 *
 * @author Tessa Power
 * @see AnimatedSprite
 */
public record SpriteSequence(String id, List<GridSquare> frames, boolean loops) {
    public int numFrames() {
        return frames.size();
    }
    public int lastFrame() {
        return numFrames() - 1;
    }
}
