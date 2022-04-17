package graphics;

import engine.GameEngine;

import java.awt.*;

public class GraphicsEngine {
    private final GraphicsCtx graphicsCtx;
    private final TCompound canvas;
//    private final Color backgroundColor;

    public GraphicsEngine(GameEngine engine) {
        graphicsCtx = new MasseyGraphicsCtx(engine);
        canvas = new TCompound(new Dimension(engine.windowWidth(), engine.windowHeight()));
//        backgroundColor = null;
    }

    public void update() {
        canvas.update(this.graphicsCtx);
    }

    //------------------------------------------------------------------------------------------------------ Actors --//

    public void add(TObject tObject) {
        canvas.add(tObject);
    }

    public void addAll(TObject... tObjects) {
        for (var tObject : tObjects) {
            add(tObject);
        }
    }

    public void remove(TObject tObject) {
        tObject.removeFromParent();
    }

    public void removeAll() {
        canvas.removeAll();
    }
}
