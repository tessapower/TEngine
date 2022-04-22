package tengine.graphics.graphicsObjects.sprites;

import tengine.world.GridSquare;

import java.util.List;

public record SpriteSequence(List<GridSquare> frames, boolean loops) {
    public int numFrames() {
        return frames.size();
    }
    public int lastFrame() {
        return numFrames() - 1;
    }
}
