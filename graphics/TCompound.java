package graphics;

import java.awt.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class TCompound extends TObject {
    private final Set<TObject> children;

    public TCompound(Dimension dimension) {
        super(dimension);
        children = new HashSet<>();
    }

    public Set<TObject> children() {
        return Collections.unmodifiableSet(children);
    }

    public int numChildren() {
        return children.size();
    }

    public void add(TObject obj) {
        if (this == obj) {
            throw new IllegalStateException("parent cannot be child of itself");
        }

        obj.setParent(this);
        children.add(obj);
    }

    public void addAll(TObject... objects) {
        for (var obj : objects) {
            add(obj);
        }
    }

    public void remove(TObject obj) {
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
    public void paint(GraphicsCtx ctx) {
        if (!children.isEmpty()) {
            for (TObject child : children) {
                child.update(ctx);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TCompound other)) return false;

        return children.equals(other.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(children);
    }
}
