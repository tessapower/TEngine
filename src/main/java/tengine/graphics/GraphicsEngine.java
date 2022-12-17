package tengine.graphics;

import tengine.graphics.components.TGraphicCompound;
import tengine.graphics.components.TGraphicObject;
import tengine.graphics.context.GraphicsCtx;

import java.awt.*;

public class GraphicsEngine {
    private final TGraphicCompound canvas;

    public GraphicsEngine(Dimension dimension) {
        canvas = new TGraphicCompound(dimension);
    }

    public void paint(GraphicsCtx ctx) {
        canvas.paint(ctx);
    }

    public void add(TGraphicObject tGraphicObject) {
        canvas.add(tGraphicObject);
    }

    public void addAll(TGraphicObject... tGraphicObjects) {
        for (var tObject : tGraphicObjects) {
            add(tObject);
        }
    }

    public void remove(TGraphicObject tGraphicObject) {
        tGraphicObject.removeFromParent();
    }

    public void removeAll() {
        canvas.removeAll();
    }

    public void update(double dtSecs) {
        canvas.update(dtSecs);
    }
}
