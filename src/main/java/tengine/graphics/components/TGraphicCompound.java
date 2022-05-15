package tengine.graphics.components;

import tengine.graphics.context.GraphicsCtx;

import java.awt.*;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class TGraphicCompound extends TGraphicObject {
    // Note that this implementation is not thread-safe, and is only a quick work-around to support
    // drawing children in a specific order (similar to z-indexing) without changing the TGraphicObject
    // API itself. Children will be drawn in order of insertion into the set.
    private final Set<TGraphicObject> children = new LinkedHashSet<>();

    public TGraphicCompound(Dimension dimension) {
        super(dimension);
    }

    public Set<TGraphicObject> children() {
        return Collections.unmodifiableSet(children);
    }

    public int numChildren() {
        return children.size();
    }

    public void add(TGraphicObject obj) {
        if (this == obj) {
            throw new IllegalStateException("parent cannot be child of itself");
        }

        obj.setParent(this);
        children.add(obj);
    }

    public void addAll(TGraphicObject... objects) {
        for (var obj : objects) {
            add(obj);
        }
    }

    public void remove(TGraphicObject obj) {
        if (children.contains(obj)) {
            obj.setParent(null);
            children.remove(obj);
        }
    }

    public void removeAll() {
        children.forEach(child -> child.setParent(null));
        children.clear();
    }

    @Override
    public void update(double dtMillis) {
        for (TGraphicObject child : children) {
            child.update(dtMillis);
        }
    }

    @Override
    public void paint(GraphicsCtx ctx) {
        ctx.pushCurrentTransform();

        ctx.applyTransforms(translation, rotation, scale);
        for (TGraphicObject child : children) {
            child.paint(ctx);
        }

        ctx.popTransform();
    }

    @Override
    protected void draw(GraphicsCtx ctx) {
        // No-op
    }
}
