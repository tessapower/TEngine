package graphics;

import graphics.context.GraphicsCtx;
import graphics.graphicsObjects.TGraphicCompound;
import graphics.graphicsObjects.TGraphicObject;
import main.GameEngine;

import java.awt.*;

public class GraphicsEngine {
    private final GraphicsCtx graphicsCtx;
    private final TGraphicCompound canvas;
//    private final Color backgroundColor;

    public GraphicsEngine(GameEngine engine) {
        graphicsCtx = new MasseyGraphicsCtx(engine);
        canvas = new TGraphicCompound(new Dimension(engine.windowWidth(), engine.windowHeight()));
//        backgroundColor = null;
    }

    public void paint() {
        canvas.paint(this.graphicsCtx);
    }

    //--------------------------------------------------------------------------------------------- Graphic Objects --//

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

    public void update(double dtMillis) {
        canvas.update(dtMillis);
    }
}
