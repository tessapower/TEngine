package world;

import graphics.GraphicsEngine;
import main.Actor;
import main.GameEngine;

import java.util.ArrayList;
import java.util.List;

public class World {
    protected List<Actor> actors;
    protected GameEngine engine;

    public World(GameEngine engine) {
        actors = new ArrayList<>();
        this.engine = engine;
    }

    public GraphicsEngine graphicsEngine() {
        return engine.graphicsEngine();
    }

    public void add(Actor actor) {
        actors.add(actor);
        actor.setWorld(this);
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
