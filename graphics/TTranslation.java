package graphics;

public class TTranslation implements TTransform {
    public int dx;
    public int dy;

    public TTranslation(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public static TTranslation identity() {
        return new TTranslation(0, 0);
    }

    public void apply(GraphicsCtx ctx) {
        ctx.translate(dx, dy);
    }
}
