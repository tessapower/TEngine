package world;

import graphics.graphicsObjects.TGraphicCompound;
import main.Actor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class World {
    protected TGraphicCompound canvas;
    // TODO: Add PhysicsCompound
    protected List<Actor> actors;

    public World(Dimension dimension) {
        canvas = new TGraphicCompound(dimension);
        actors = new ArrayList<>();
    }

    public void add(Actor actor) {
        actors.add(actor);
        actor.addToWorld(this);
        canvas.add(actor.graphic());
    }

    public void add(Actor... actors) {
        for (var actor : actors) {
            add(actor);
        }
    }

    public void add(List<Actor> actors) {
        actors.forEach(this::add);
    }

    public void remove(Actor actor) {
        actors.remove(actor);
        actor.destroy();
    }

    public TGraphicCompound canvas() {
        return canvas;
    }
}
