package graphics;

import actors.Actor;
import engine.GameEngine;

import java.awt.*;

public class GraphicsEngine {
    private final GraphicsCtx graphicsCtx;
    private final TCompound canvas;
//    private final Color backgroundColor;

    public GraphicsEngine(GameEngine engine) {
        graphicsCtx = new TGraphicsCtx(engine);
        canvas = new TCompound(new Dimension(engine.windowWidth(), engine.windowHeight()));
//        backgroundColor = null;
    }

    public void update() {
        canvas.update(this.graphicsCtx);
    }

    //------------------------------------------------------------------------------------------------------ Actors --//

    public void add(Actor actor) {
        canvas.add(actor.sprite());
    }

    public void addAll(Actor... actors) {
        for (var actor : actors) {
            add(actor);
        }
    }

    public void remove(Actor actor) {
        // What we really want here is something like actor.removeFromParent();
        canvas.remove(actor.sprite());
    }

    public void removeAll() {
        canvas.removeAll();
    }
}
