package graphics;

import java.awt.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class TGraphicCompound extends TGraphicObject {
    private final Set<TGraphicObject> children;

    public TGraphicCompound(Dimension dimension) {
        super(dimension);
        children = new HashSet<>();
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

    // TODO: This is confusing, update and paint aren't really the right names for what's going on.
    //   We should find language that expresses applying the transforms and drawing the shape on the canvas
    //   while in it's current context
    @Override
    public void paint(MasseyGraphicsCtx ctx) {
        if (!children.isEmpty()) {
            for (TGraphicObject child : children) {
                child.update(ctx);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TGraphicCompound other)) return false;

        return children.equals(other.children);
    }
}
