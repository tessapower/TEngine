package graphics;

import java.awt.*;
import java.util.Objects;

abstract public class TShape extends TGraphicObject {
    public Color outlineColor;
    public Color fillColor;
    public boolean isFilled;

    public TShape(Dimension dimension) {
        super(dimension);
        outlineColor = Color.BLACK;
        fillColor = null;
        isFilled = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof TShape other))
            return false;

        return this.dimension == other.dimension
                && this.rotation == other.rotation
                && this.translation == other.translation
                && this.scale == other.scale
                && this.outlineColor == other.outlineColor
                && this.isFilled == other.isFilled
                && this.fillColor == other.fillColor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dimension, rotation, translation, scale, outlineColor, fillColor, isFilled);
    }
}
