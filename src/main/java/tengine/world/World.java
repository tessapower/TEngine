package tengine.world;

import tengine.Actor;
import tengine.graphics.components.TGraphicCompound;
import tengine.physics.TPhysicsComponent;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class World {
    protected TGraphicCompound canvas;
    protected List<TPhysicsComponent> physicsComponents = new ArrayList<>();
    protected List<Actor> actors = new ArrayList<>();

    public World(Dimension dimension) {
        canvas = new TGraphicCompound(dimension);
    }

    public void add(Actor actor) {
        actor.addToWorld(this);
        actors.add(actor);
        canvas.add(actor.graphic());
        physicsComponents.add(actor.physics());
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
        physicsComponents.remove(actor.physics());
        actor.destroy();
    }

    public TGraphicCompound canvas() {
        return canvas;
    }

    public List<TPhysicsComponent> physicsComponents() {
        return Collections.unmodifiableList(physicsComponents);
    }

    public List<Actor> actors() {
        return actors;
    }
}
