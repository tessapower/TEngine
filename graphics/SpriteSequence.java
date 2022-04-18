package graphics;

import actors.GridSquare;

import java.util.List;

public record SpriteSequence(List<GridSquare> frames, boolean loops) {
    public int numFrames() {
        return frames.size();
    }
}
