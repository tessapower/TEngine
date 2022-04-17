package graphics;

import java.awt.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class TCompound extends TObject {
    private final Set<TObject> children;

    public TCompound(Dimension dimension) {
        super(dimension);
        children = new HashSet<>();
    }

    public boolean add(TObject obj) {
        if (this == obj) {
            throw new IllegalStateException("parent cannot be child of itself.");
        }

        obj.setParent(this);
        return children.add(obj);
    }

    public void addAll(TObject... objects) {
        for (var obj : objects) {
            add(obj);
        }
    }

    public boolean remove(TObject obj) {
        return children.remove(obj);
    }

    public void removeAll() {
        children.clear();
    }

    public int numChildren() {
        return children.size();
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
