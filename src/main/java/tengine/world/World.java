package tengine.world;

import tengine.Actor;
import tengine.graphics.components.TGraphicCompound;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class World {
    protected TGraphicCompound canvas;
    protected List<Actor> actors = new ArrayList<>();

    public World(Dimension dimension) {
        canvas = new TGraphicCompound(dimension);
    }

    public void add(Actor actor) {
        actor.addToWorld(this);
        actors.add(actor);
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

    public List<Actor> actors() {
        return actors;
    }
}
