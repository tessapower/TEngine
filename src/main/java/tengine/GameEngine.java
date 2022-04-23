package tengine;

import tengine.graphics.GraphicsEngine;
import tengine.graphics.context.GraphicsCtx;
import tengine.graphics.context.MasseyGraphicsCtx;
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
import java.util.Stack;

public abstract class GameEngine implements KeyListener, MouseListener, MouseMotionListener {
    private static final Dimension DEFAULT_WINDOW_DIMENSION = new Dimension(500, 500);
    private static final int DEFAULT_FRAMERATE = 30;

    JFrame jFrame;
    GamePanel gamePanel;
    int width, height;
    Graphics2D graphics2D;
    boolean initialized = false;
    Stack<AffineTransform> transforms;

    GraphicsEngine graphicsEngine;
    // TODO: Reimplement the physics engine!
    //   PhysicsEngine physicsEngine;

    long lastUpdateMillis = 0;
    // Main Loop of the game. Runs continuously and calls all the updates
    // of the game and tells the game to display a new frame.
    GameTimer timer = new GameTimer(30, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            long now = System.currentTimeMillis();
            if (lastUpdateMillis == 0) {
                lastUpdateMillis = now;
            }
            double elapsedSecs = (now - lastUpdateMillis) / 1000.0;
            lastUpdateMillis = now;

            // Update the Game
            update(elapsedSecs);

            // Tell the Game to draw
            gamePanel.repaint();
        }
    });

//    Random mRandom = new Random();

    public GameEngine() {
        transforms = new Stack<>();

        // TODO: Reimplement the physics engine!
        //   physicsEngine = new PhysicsEngine()
        //   physicsEngine.setCollisionEventNotifier(this::onCollision)

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

        protected int framerate() {
            return framerate;
        }
    }


    //------------------------------------------------------------------------------------- World Loading/Unloading --//

    public void loadWorld(World world) {
        graphicsEngine.add(world.canvas());
        // TODO: Physics counterpart
    }

    public void unloadWorld(World world) {
        world.canvas().removeFromParent();
        // TODO: Physics counterpart
    }

    //------------------------------------------------------------------------------------------------ Tick Methods --//

    public void update(double dtMillis) {
        //        physicsEngine.update(dtMillis);
        graphicsEngine.update(dtMillis);
    }

    public void paint(GraphicsCtx ctx) {
        clearBackground(width, height);
        graphicsEngine.paint(ctx);
    }

    public void sleep(double ms) {
        try {
            Thread.sleep((long) ms);
        } catch (Exception e) {
            // Do Nothing
        }
    }

    //------------------------------------------------------------------------------------------------------ Window --//

    private void setupWindow(Dimension dimension) {
        // TODO: Eventually replace with graphicsEngine = new GraphicsEngine(Graphics2D)
        graphicsEngine = new GraphicsEngine(dimension);

        jFrame = new JFrame();
        gamePanel = new GamePanel();

        width = dimension.width;
        height = dimension.height;

        jFrame.setSize(width, height);
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
                    return false; // do not consume the event
            }
        });

        // Resize the window (insets are just the borders that the Operating System puts on the board)
        Insets insets = jFrame.getInsets();
        jFrame.setSize(width + insets.left + insets.right, height + insets.top + insets.bottom);
    }

    public void setWindowProperties(Dimension dimension, String title) {
        SwingUtilities.invokeLater(() -> {
            // Resize the window (insets are just the borders that the Operating System puts on the board)
            Insets insets = jFrame.getInsets();
            width = dimension.width;
            height = dimension.height;
            jFrame.setSize(width + insets.left + insets.right, height + insets.top + insets.bottom);
            gamePanel.setSize(width, height);
            jFrame.setTitle(title);

            // TODO: Set the GraphicsEngine canvas
        });
    }

    public int windowWidth() {
        return width;
    }

    public int windowHeight() {
        return height;
    }

    // Initialise and start the game loop with the given framerate
    public void startGameLoop(int framerate) {
        initialized = true; // assume init has been called or won't be called

        timer.setFramerate(framerate);
        timer.setRepeats(true);

        timer.start();
    }

    //------------------------------------------------------------------------------ Methods that can be overridden --//

    public void init() {
    }

    public void keyPressed(KeyEvent event) {
    }

    public void keyReleased(KeyEvent event) {
    }

    public void keyTyped(KeyEvent event) {
    }

    public void mouseClicked(MouseEvent event) {
    }

    public void mousePressed(MouseEvent event) {
    }

    public void mouseReleased(MouseEvent event) {
    }

    public void mouseEntered(MouseEvent event) {
    }

    public void mouseExited(MouseEvent event) {
    }

    public void mouseMoved(MouseEvent event) {
    }

    public void mouseDragged(MouseEvent event) {
    }

    public void onCollision(CollisionEvent e) {
    }

    //----------------------------------------------------------------------------------------------------- Drawing --//

    protected class GamePanel extends JPanel {
        @Serial
        private static final long serialVersionUID = 1L;

        // Called each time the OS instructs the program to paint itself
        public void paintComponent(Graphics graphics) {
            // Get the graphics object
            graphics2D = (Graphics2D) graphics;

            // Reset all transforms
            transforms.clear();
            transforms.push(graphics2D.getTransform());

            // Rendering settings
            graphics2D.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));

            // Paint the game
            if (initialized) {
                // TODO: We should not be wrapping the entire engine,
                //  we should only be wrapping (Java) Graphics context
                //  Step 2: Introduce a Java Graphics Context which implements my
                //  Graphics Context and wraps the passed Graphics object
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
        // Set the stroke
        graphics2D.setStroke(new BasicStroke((float) l));

        graphics2D.draw(new Line2D.Double(x1, y1, x2, y2));

        // Reset the stroke
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
        // Set the stroke
        graphics2D.setStroke(new BasicStroke((float) l));

        graphics2D.draw(new Line2D.Double(p1.x, p1.y, p2.x, p2.y));

        // Reset the stroke
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
        // Set the stroke
        graphics2D.setStroke(new BasicStroke((float) l));

        graphics2D.draw(new Rectangle2D.Double(x, y, w, h));

        // Reset the stroke
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
        // Set current transform to the top of the stack.
        graphics2D.setTransform(transforms.peek());

        // If there is more than one transform on the stack
        if (transforms.size() > 1) {
            // Pop a transform off the stack
            transforms.pop();
        }
    }

    /**
     * Translate the drawing context by (dx, dy).
     */
    public void translate(double dx, double dy) {
        graphics2D.translate(dx, dy);
    }

    /**
     * Rotate the drawing context by <code>thetaDegrees</code>.
     */
    public void rotate(double thetaDegrees) {
        graphics2D.rotate(Math.toRadians(thetaDegrees));
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

    //------------------------------------------------------------------------------------------------ Math Helpers --//

    // TODO: Remove these functions in favor of built in Math class,
    //  create a helper class for degrees <-> radians conversions

    /**
     * Return the length of a vector.
     */
    public double length(double x, double y) {
        // Calculate and return the sqrt
        return Math.sqrt(x * x + y * y);
    }

    /**
     * Return the distance between two points (x1, y1) and (x2, y2).
     */
    public double distance(Point p1, Point p2) {
        return p1.distance(p2);
    }

    /**
     * Convert an angle in radians to degrees.
     */
    public double toDegrees(double radians) {
        // Calculate and return the degrees
        return Math.toDegrees(radians);
    }

    /**
     * Convert an angle in degrees to radians.
     */
    public double toRadians(double degrees) {
        // Calculate and return the radians
        return Math.toRadians(degrees);
    }

    /**
     * Return the cos of value
     */
    public double cos(double value) {
        // Calculate and return cos
        return Math.cos(Math.toRadians(value));
    }

    // Return the acos of value
    public double acos(double value) {
        // Calculate and return acos
        return Math.toDegrees(Math.acos(value));
    }

    // Return the sin of value
    public double sin(double value) {
        // Calculate and return sin
        return Math.sin(Math.toRadians(value));
    }

    // Return the asin of value
    public double asin(double value) {
        // Calculate and return asin
        return Math.toDegrees(Math.asin(value));
    }

    // Return the tan of value
    public double tan(double value) {
        // Calculate and return tan
        return Math.tan(Math.toRadians(value));
    }

    // Return the atan of value
    public double atan(double value) {
        // Calculate and return atan
        return Math.toDegrees(Math.atan(value));
    }

    // Return the atan2 of value
    public double atan2(double x, double y) {
        // Calculate and return atan2
        return Math.toDegrees(Math.atan2(x, y));
    }
}
