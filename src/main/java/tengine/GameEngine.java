package tengine;

import tengine.geom.TPoint;
import tengine.graphics.GraphicsEngine;
import tengine.graphics.context.GraphicsCtx;
import tengine.graphics.context.MasseyGraphicsCtx;
import tengine.physics.PhysicsEngine;
import tengine.physics.collisions.events.CollisionEvent;
import tengine.world.World;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serial;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public abstract class GameEngine implements KeyListener, MouseListener, MouseMotionListener {
    private static final Dimension DEFAULT_WINDOW_DIMENSION = new Dimension(500, 500);
    private static final int DEFAULT_FRAMERATE = 30;

    private JFrame jFrame;
    private GamePanel gamePanel;
    private Dimension dimension = DEFAULT_WINDOW_DIMENSION;
    private Graphics2D graphics2D;
    private boolean initialized = false;
    private final Stack<AffineTransform> transforms;

    private GraphicsEngine graphicsEngine;
    private final PhysicsEngine physicsEngine = new PhysicsEngine();
    private World activeWorld = null;
    private boolean isPaused = false;

    long lastUpdateMillis = 0;

    // Main Loop of the game
    GameTimer timer = new GameTimer(30, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            long now = System.currentTimeMillis();
            if (lastUpdateMillis == 0) {
                lastUpdateMillis = now;
            }
            double elapsedSec = (now - lastUpdateMillis) / 1000.0;
            lastUpdateMillis = now;

            update(elapsedSec);

            gamePanel.repaint();
        }
    });

    public GameEngine() {
        transforms = new Stack<>();

        physicsEngine.setCollisionEventNotifier(this::onCollision);

        SwingUtilities.invokeLater(() -> setupWindow(DEFAULT_WINDOW_DIMENSION));
    }

    public static void createGame(GameEngine game, int framerate) {
        game.init();
        game.startGameLoop(framerate);
    }

    public static void createGame(GameEngine game) {
        createGame(game, DEFAULT_FRAMERATE);
    }

    public GraphicsEngine graphicsEngine() {
        return graphicsEngine;
    }

    public PhysicsEngine physicsEngine() {
        return physicsEngine;
    }

    protected static class GameTimer extends Timer {
        @Serial
        private static final long serialVersionUID = 1L;
        private static final int MIN_FRAMERATE = 1;
        private static final int ONE_SECOND = 1000;
        private int framerate;

        protected GameTimer(int framerate, ActionListener listener) {
            super(1000 / framerate, listener);
            this.framerate = framerate;
        }

        protected void setFramerate(int framerate) {
            if (framerate < MIN_FRAMERATE) framerate = MIN_FRAMERATE;
            this.framerate = framerate;

            int delay = ONE_SECOND / framerate;
            setInitialDelay(0);
            setDelay(delay);
        }
    }

    //------------------------------------------------------------------------------------- World Loading/Unloading --//

    public void loadWorld(World world) {
        if (activeWorld != null) {
            unloadWorld();
        }
        activeWorld = world;
        graphicsEngine.add(world.canvas());
    }

    public void unloadWorld() {
        activeWorld = null;
    }

    //------------------------------------------------------------------------------------------------ Tick Methods --//

    public void update(double dtSec) {
        if (activeWorld != null && !isPaused) {
            List<Actor> actors = new ArrayList<>(activeWorld.actors());
            for (Iterator<Actor> iterator = actors.iterator(); iterator.hasNext(); ) {
                Actor actor = iterator.next();
                if (actor.destroyWhenOffScreen && !isOnScreen(actor)) {
                    actor.markPendingDestroy();
                }

                if (actor.pendingDestroy) {
                    iterator.remove();
                    actor.destroy();
                    continue;
                }


                var physics = actor.physics();
                physics.ifPresent(p -> p.update(physicsEngine, dtSec));
            }

            physicsEngine.processCollisions(actors, dtSec);
        }

        // Allow graphical objects, e.g. AnimatedSprite, to make time-based updates if necessary
        graphicsEngine.update(dtSec);
    }

    public void paint(GraphicsCtx ctx) {
        clearBackground(dimension.width, dimension.height);
        graphicsEngine.paint(ctx);
    }

    //------------------------------------------------------------------------------------------------------ Window --//

    private void setupWindow(Dimension dimension) {
        // TODO: Eventually construct GraphicsEngine by passing the Graphics2D context.
        graphicsEngine = new GraphicsEngine(dimension);

        jFrame = new JFrame();
        gamePanel = new GamePanel();

        this.dimension = dimension;

        jFrame.setSize(this.dimension.width, this.dimension.height);
        jFrame.setResizable(false);
        jFrame.setLocation(200, 200);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.add(gamePanel);
        jFrame.setVisible(true);

        gamePanel.setDoubleBuffered(true);
        gamePanel.addMouseListener(this);
        gamePanel.addMouseMotionListener(this);

        // Register a key event dispatcher to get a turn in handling all
        // key events, independent of which component currently has the focus
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
            switch (e.getID()) {
                case KeyEvent.KEY_PRESSED:
                    GameEngine.this.keyPressed(e);
                    return false;
                case KeyEvent.KEY_RELEASED:
                    GameEngine.this.keyReleased(e);
                    return false;
                case KeyEvent.KEY_TYPED:
                    GameEngine.this.keyTyped(e);
                    return false;
                default:
                    // do not consume the event
                    return false;
            }
        });

        Insets insets = jFrame.getInsets();
        jFrame.setSize(dimension.width + insets.left + insets.right, dimension.height + insets.top + insets.bottom);
    }

    public int windowWidth() {
        return dimension.width;
    }

    public int windowHeight() {
        return dimension.height;
    }

    public void startGameLoop(int framerate) {
        initialized = true;

        timer.setFramerate(framerate);
        timer.setRepeats(true);

        timer.start();
    }

    private boolean isOnScreen(Actor actor) {
        TPoint actorPos = actor.origin();
        Dimension actorSize = actor.graphic().dimension();
        return actorPos.x >= 0 && actorPos.x + actorSize.width <= windowWidth()
            && actorPos.y >= 0 && actorPos.y + actorSize.height <= windowHeight();
    }

    //----------------------------------------------------------------------------------------------- Game Settings --//

    public void setWindowProperties(Dimension dimension, String title) {
        SwingUtilities.invokeLater(() -> {
            Insets insets = jFrame.getInsets();
            this.dimension = dimension;
            jFrame.setSize(dimension.width + insets.left + insets.right, dimension.height + insets.top + insets.bottom);
            gamePanel.setSize(dimension.width, dimension.height);
            jFrame.setTitle(title);

            // TODO: Set the size of the GraphicsEngine canvas
        });
    }

    public void setPaused(boolean isPaused) {
        this.isPaused = isPaused;
    }

    //------------------------------------------------------------------------------ Methods that can be overridden --//

    public void init() {
    }

    @Override
    public void keyPressed(KeyEvent event) {
    }

    @Override
    public void keyReleased(KeyEvent event) {
    }

    @Override
    public void keyTyped(KeyEvent event) {
    }

    @Override
    public void mouseClicked(MouseEvent event) {
    }

    @Override
    public void mousePressed(MouseEvent event) {
    }

    @Override
    public void mouseReleased(MouseEvent event) {
    }

    @Override
    public void mouseEntered(MouseEvent event) {
    }

    @Override
    public void mouseExited(MouseEvent event) {
    }

    @Override
    public void mouseMoved(MouseEvent event) {
    }

    @Override
    public void mouseDragged(MouseEvent event) {
    }

    public void onCollision(CollisionEvent e) {
    }

    //----------------------------------------------------------------------------------------------------- Drawing --//

    protected class GamePanel extends JPanel {
        @Serial
        private static final long serialVersionUID = 1L;

        @Override
        public void paintComponent(Graphics graphics) {
            graphics2D = (Graphics2D) graphics;

            transforms.clear();
            transforms.push(graphics2D.getTransform());

            graphics2D.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));

            if (initialized) {
                // TODO: We should not be wrapping the entire engine, we should only be wrapping (Java) Graphics context
                //  Step 2: Introduce a Java Graphics Context which implements
                //  my Graphics Context and wraps the passed Graphics object
                //  Step 3: Remove Massey GameEngine
                GraphicsCtx ctx = new MasseyGraphicsCtx(GameEngine.this);
                GameEngine.this.paint(ctx);
            }
        }
    }

    /**
     * Change the background color of the canvas to the specified color.
     */
    public void changeBackgroundColor(Color color) {
        graphics2D.setBackground(color);
    }

    /**
     * Clear the canvas and set the background color, if there is one.
     */
    public void clearBackground(int width, int height) {
        graphics2D.clearRect(0, 0, width, height);
    }

    /**
     * Change the drawing color to the specified color.
     */
    public void changeColor(Color color) {
        graphics2D.setColor(color);
    }

    /**
     * Draw a line from (x1, y2) to (x2, y2).
     */
    public void drawLine(double x1, double y1, double x2, double y2) {
        graphics2D.draw(new Line2D.Double(x1, y1, x2, y2));
    }

    /**
     * Draw a line from (x1, y2) to (x2, y2) with width l.
     */
    public void drawLine(double x1, double y1, double x2, double y2, double l) {
        graphics2D.setStroke(new BasicStroke((float) l));

        graphics2D.draw(new Line2D.Double(x1, y1, x2, y2));

        graphics2D.setStroke(new BasicStroke(1.0f));
    }

    /**
     * Draw a line from <code>p1</code> to <code>p2</code>.
     */
    public void drawLine(Point p1, Point p2) {
        graphics2D.draw(new Line2D.Double(p1.x, p1.y, p2.x, p2.y));
    }

    /**
     * Draw a line from <code>p1</code> to <code>p2</code> with width l.
     */
    public void drawLine(Point p1, Point p2, double l) {
        graphics2D.setStroke(new BasicStroke((float) l));

        graphics2D.draw(new Line2D.Double(p1.x, p1.y, p2.x, p2.y));

        graphics2D.setStroke(new BasicStroke(1.0f));
    }

    /**
     * Draw a rectangle at (x, y) with width and height (w, h).
     */
    public void drawRectangle(double x, double y, double w, double h) {
        graphics2D.draw(new Rectangle2D.Double(x, y, w, h));
    }

    /**
     * Draw a rectangle at (x, y) with width and height (w, h) with a line of width l.
     */
    public void drawRectangle(double x, double y, double w, double h, double l) {
        graphics2D.setStroke(new BasicStroke((float) l));

        graphics2D.draw(new Rectangle2D.Double(x, y, w, h));

        graphics2D.setStroke(new BasicStroke(1.0f));
    }

    /**
     * Draw a filled rectangle at (x, y) with width and height (w, h).
     */
    public void drawSolidRectangle(double x, double y, double w, double h) {
        graphics2D.fill(new Rectangle2D.Double(x, y, w, h));
    }

    /**
     * Draw a circle at (x, y) with radius
     */
    public void drawCircle(double x, double y, double radius) {
        graphics2D.draw(new Ellipse2D.Double(x - radius, y - radius, radius * 2, radius * 2));
    }

    /**
     * Draw a circle at (x, y) with radius with a line of width l.
     */
    public void drawCircle(double x, double y, double radius, double l) {
        // Set the stroke
        graphics2D.setStroke(new BasicStroke((float) l));

        graphics2D.draw(new Ellipse2D.Double(x - radius, y - radius, radius * 2, radius * 2));

        // Reset the stroke
        graphics2D.setStroke(new BasicStroke(1.0f));
    }

    /**
     * Draw a filled circle at (x, y) with radius.
     */
    public void drawSolidCircle(double x, double y, double radius) {
        graphics2D.fill(new Ellipse2D.Double(x - radius, y - radius, radius * 2, radius * 2));
    }

    /**
     * Draw bold text on the screen at (x, y).
     */
    public void drawText(double x, double y, String s, Font font) {
        graphics2D.setFont(font);
        graphics2D.drawString(s, (int) x, (int) y);
    }

    /**
     * Load an image from file.
     */
    public Image loadImage(String filename) {
        try {
            return ImageIO.read(new File(filename));
        } catch (IOException e) {
            System.err.println("Error: could not load image " + filename);
            System.exit(1);
        }

        return null;
    }

    /**
     * Load a sub-image out of an image.
     */
    public Image subImage(Image source, int x, int y, int w, int h) {
        if (source == null) {
            System.err.println("Error: cannot extract a sub image from a null image.");

            return null;
        }

        BufferedImage buffered = (BufferedImage) source;

        return buffered.getSubimage(x, y, w, h);
    }

    /**
     * Draw an image on the screen at position (x, y).
     */
    public void drawImage(Image image, double x, double y) {
        if (image == null) {
            System.err.println("Error: cannot draw null image.");
            return;
        }

        graphics2D.drawImage(image, (int) x, (int) y, null);
    }

    /**
     * Draw an image on the screen at position (x, y).
     */
    public void drawImage(Image image, double x, double y, double w, double h) {
        if (image == null) {
            System.err.println("Error: cannot draw null image.");
            return;
        }
        graphics2D.drawImage(image, (int) x, (int) y, (int) w, (int) h, null);
    }

    // ------------------------------------------------------------------------------------------------- Transforms --//

    /**
     * Save the current transform.
     */
    public void saveCurrentTransform() {
        transforms.push(graphics2D.getTransform());
    }

    /**
     * Restore the last transform.
     */
    public void restoreLastTransform() {
        graphics2D.setTransform(transforms.peek());

        if (transforms.size() > 1) {
            transforms.pop();
        }
    }

    /**
     * Translate the drawing context by (dx, dy).
     */
    public void translate(double dx, double dy) {
        graphics2D.translate(dx, dy);
    }

    public void rotate(double thetaDegrees, int dx, int dy) {
        graphics2D.rotate(Math.toRadians(thetaDegrees), dx, dy);
    }

    /**
     * Scale the drawing context by (x, y)
     */
    public void scale(double x, double y) {
        graphics2D.scale(x, y);
    }

    /**
     * Shear the drawing context by (x, y)
     */
    public void shear(double x, double y) {
        graphics2D.shear(x, y);
    }
}
