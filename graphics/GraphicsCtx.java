package graphics;

import java.awt.*;

public interface GraphicsCtx {
    void drawRect(Dimension dimension, Color color);
    void drawCircle(Dimension dimension, Color color);
    void drawFilledRect(Dimension dimension, Color color);
    void drawFilledCircle(Dimension dimension, Color color);
    void drawImage(Image image);
    void drawImage(Image image, Dimension dimension);
    void setTransforms(TTransform... transforms);
    void pushCurrentTransform();
    void popTransform();
    void rotate(double thetaDegrees);
    void translate(int dx, int dy);
    void scale(double xScaleFactor, double yScaleFactor);
}
