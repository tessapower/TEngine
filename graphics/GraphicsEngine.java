package graphics;

import engine.GameEngine;

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

    public void update() {
        canvas.update(this.graphicsCtx);
    }

    //------------------------------------------------------------------------------------------------------ Actors --//

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
}
