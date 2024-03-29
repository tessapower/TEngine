package tengine.graphics.components;

import tengine.graphics.context.GraphicsCtx;

import java.awt.*;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * A compound graphical object that can be drawn to the screen. Use to group together multiple
 * <code>TGraphicObject</code>s, including <code>TLabel</code>s and <code>AnimatedSprites</code>.
 * Any transformations applied to a TGraphicCompound will be propagated to its children as well.
 * Children are drawn in insertion order.
 * 
 * @author Tessa Power
 * @see TGraphicObject
 */
public class TGraphicCompound extends TGraphicObject {
    // Note that this implementation is not thread-safe, and is only a quick work-around to support
    // drawing children in a specific order (similar to z-indexing) without changing the TGraphicObject
    // API itself. Children will be drawn in order of insertion into the set.
    private final Set<TGraphicObject> children = new LinkedHashSet<>();

    /**
     * Construct a new <code>TGraphicCompound</code> with the given <code>Dimension</code>.
     */
    public TGraphicCompound(Dimension dimension) {
        super(dimension);
    }

    /**
     * A list of the children that this <code>TGraphicCompound</code> contains.
     */
    public Set<TGraphicObject> children() {
        return Collections.unmodifiableSet(children);
    }

    /**
     * The number of children that this <code>TGraphicCompound</code> contains.
     */
    public int numChildren() {
        return children.size();
    }

    /**
     * Add the given <code>TGraphicObject</code> to this <code>TGraphicCompound</code>.
     */
    public void add(TGraphicObject obj) {
        if (this == obj) {
            throw new IllegalStateException("parent cannot be child of itself");
        }

        obj.setParent(this);
        children.add(obj);
    }

    /**
     * All the list of <code>TGraphicObject</code>s to this <code>TGraphicCompound</code>.
     */
    public void addAll(TGraphicObject... objects) {
        for (var obj : objects) {
            add(obj);
        }
    }

    /**
     * Remove the given <code>TGraphicObject</code> from this <code>TGraphicCompound</code>. Does
     *  nothing if the given object is not one of the children.
     */
    public void remove(TGraphicObject obj) {
        if (children.contains(obj)) {
            obj.setParent(null);
            children.remove(obj);
        }
    }

    /**
     * Remove all children from this <code>TGraphicCompound</code>.
     */
    public void removeAll() {
        children.forEach(child -> child.setParent(null));
        children.clear();
    }

    /**
     * Allow the children of this <code>TGraphicCompound</code> to update since they were last
     * update <code>dtSecs</code> ago.
     */
    @Override
    public void update(double dtSecs) {
        for (TGraphicObject child : children) {
            child.update(dtSecs);
        }
    }

    /**
     * Applies the transforms for this <code>TGraphicCompound</code> and then calls
     * <code>paint</code> on all of its children. Children are drawn in the order they were added
     *  to this <code>TGraphicCompound</code>.
     */
    @Override
    public void paint(GraphicsCtx ctx) {
        ctx.pushCurrentTransform();

        ctx.applyTransforms(translation, rotation, scale);
        for (TGraphicObject child : children) {
            child.paint(ctx);
        }

        ctx.popTransform();
    }

    /**
     * WARNING: Do not call this method on a <code>TGraphicCompound</code>, instead call
     * <code>paint()</code>.
     * 
     * @see TGraphicCompound#paint(GraphicsCtx)
     */
    @Override
    protected void draw(GraphicsCtx ctx) {
        // No-op
    }
}
