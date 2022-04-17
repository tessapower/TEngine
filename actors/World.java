package actors;

import graphics.GraphicsEngine;

import java.util.ArrayList;
import java.util.List;

public class World {
    List<Actor> actors;
    GraphicsEngine graphicsEngine;

    public World(GraphicsEngine graphicsEngine) {
        actors = new ArrayList<>();
        this.graphicsEngine = graphicsEngine;
    }

    public void add(Actor actor) {
        actors.add(actor);
        actor.setWorld(this, graphicsEngine);
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
}
