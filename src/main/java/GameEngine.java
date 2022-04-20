package main.java;

import main.java.graphics.GraphicsEngine;
import main.java.graphics.context.GraphicsCtx;
import main.java.graphics.context.MasseyGraphicsCtx;
import main.java.physics.collisions.events.CollisionEvent;
import main.java.world.World;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
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
    JFrame mFrame;
    GamePanel mPanel;
    int mWidth, mHeight;
    Graphics2D mGraphics;
    boolean initialised = false;
    Stack<AffineTransform> mTransforms;

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
            mPanel.repaint();
        }
    });

//    Random mRandom = new Random();

    public GameEngine() {
        mTransforms = new Stack<>();

        // TODO: Reimplement the physics engine!
        //   physicsEngine = new PhysicsEngine()
        //   physicsEngine.setCollisionEventNotifier(this::onCollision)

        SwingUtilities.invokeLater(() -> setupWindow(new Dimension(500, 500)));
    }

    public static void createGame(GameEngine game, int framerate) {
        game.init();
        game.startGameLoop(framerate);
    }

    public static void createGame(GameEngine game) {
        createGame(game, 30);
    }

    public GraphicsEngine graphicsEngine() {
        return graphicsEngine;
    }

    protected static class GameTimer extends Timer {
        @Serial
        private static final long serialVersionUID = 1L;
        private int framerate;

        protected GameTimer(int framerate, ActionListener listener) {
            super(1000 / framerate, listener);
            this.framerate = framerate;
        }

        protected void setFramerate(int framerate) {
            if (framerate < 1)
                framerate = 1;
            this.framerate = framerate;

            int delay = 1000 / framerate;
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
        clearBackground(mWidth, mHeight);
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
        graphicsEngine = new GraphicsEngine(new Dimension(mWidth, mHeight));

        mFrame = new JFrame();
        mPanel = new GamePanel();

        mWidth = dimension.width;
        mHeight = dimension.height;

        mFrame.setSize(mWidth, mHeight);
        mFrame.setResizable(false);
        mFrame.setLocation(200, 200);
        mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mFrame.add(mPanel);
        mFrame.setVisible(true);

        mPanel.setDoubleBuffered(true);
        mPanel.addMouseListener(this);
        mPanel.addMouseMotionListener(this);

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
        Insets insets = mFrame.getInsets();
        mFrame.setSize(mWidth + insets.left + insets.right, mHeight + insets.top + insets.bottom);
    }

    public void setWindowProperties(Dimension dimension, String title) {
        SwingUtilities.invokeLater(() -> {
            // Resize the window (insets are just the borders that the Operating System puts on the board)
            Insets insets = mFrame.getInsets();
            mWidth = dimension.width;
            mHeight = dimension.height;
            mFrame.setSize(mWidth + insets.left + insets.right, mHeight + insets.top + insets.bottom);
            mPanel.setSize(mWidth, mHeight);
            mFrame.setTitle(title);
        });
    }

    public int windowWidth() {
        return mWidth;
    }

    public int windowHeight() {
        return mHeight;
    }

    // Initialise and start the game loop with the given framerate
    public void startGameLoop(int framerate) {
        initialised = true; // assume init has been called or won't be called

        timer.setFramerate(framerate);
        timer.setRepeats(true);

        // Main loop runs until program is closed
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
            mGraphics = (Graphics2D) graphics;

            // Reset all transforms
            mTransforms.clear();
            mTransforms.push(mGraphics.getTransform());

            // Rendering settings
            mGraphics.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));

            // Paint the game
            if (initialised) {
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
        mGraphics.setBackground(color);
    }

    /**
     * Clear the canvas and set the background color, if there is one.
     */
    public void clearBackground(int width, int height) {
        mGraphics.clearRect(0, 0, width, height);
    }

    /**
     * Change the drawing color to the specified color.
     */
    public void changeColor(Color color) {
        mGraphics.setColor(color);
    }

    /**
     * Draw a line from (x1, y2) to (x2, y2).
     */
    public void drawLine(double x1, double y1, double x2, double y2) {
        mGraphics.draw(new Line2D.Double(x1, y1, x2, y2));
    }

    /**
     * Draw a line from (x1, y2) to (x2, y2) with width l.
     */
    public void drawLine(double x1, double y1, double x2, double y2, double l) {
        // Set the stroke
        mGraphics.setStroke(new BasicStroke((float) l));

        mGraphics.draw(new Line2D.Double(x1, y1, x2, y2));

        // Reset the stroke
        mGraphics.setStroke(new BasicStroke(1.0f));
    }

    /**
     * Draw a line from <code>p1</code> to <code>p2</code>.
     */
    public void drawLine(Point p1, Point p2) {

        mGraphics.draw(new Line2D.Double(p1.x, p1.y, p2.x, p2.y));
    }

    /**
     * Draw a line from <code>p1</code> to <code>p2</code> with width l.
     */
    public void drawLine(Point p1, Point p2, double l) {
        // Set the stroke
        mGraphics.setStroke(new BasicStroke((float) l));

        mGraphics.draw(new Line2D.Double(p1.x, p1.y, p2.x, p2.y));

        // Reset the stroke
        mGraphics.setStroke(new BasicStroke(1.0f));
    }

    /**
     * Draw a rectangle at (x, y) with width and height (w, h).
     */
    public void drawRectangle(double x, double y, double w, double h) {
        mGraphics.draw(new Rectangle2D.Double(x, y, w, h));
    }

    /**
     * Draw a rectangle at (x, y) with width and height (w, h) with a line of width l.
     */
    public void drawRectangle(double x, double y, double w, double h, double l) {
        // Set the stroke
        mGraphics.setStroke(new BasicStroke((float) l));

        mGraphics.draw(new Rectangle2D.Double(x, y, w, h));

        // Reset the stroke
        mGraphics.setStroke(new BasicStroke(1.0f));
    }

    /**
     * Draw a filled rectangle at (x, y) with width and height (w, h).
     */
    public void drawSolidRectangle(double x, double y, double w, double h) {
        mGraphics.fill(new Rectangle2D.Double(x, y, w, h));
    }

    /**
     * Draw a circle at (x, y) with radius
     */
    public void drawCircle(double x, double y, double radius) {
        mGraphics.draw(new Ellipse2D.Double(x - radius, y - radius, radius * 2, radius * 2));
    }

    /**
     * Draw a circle at (x, y) with radius with a line of width l.
     */
    public void drawCircle(double x, double y, double radius, double l) {
        // Set the stroke
        mGraphics.setStroke(new BasicStroke((float) l));

        mGraphics.draw(new Ellipse2D.Double(x - radius, y - radius, radius * 2, radius * 2));

        // Reset the stroke
        mGraphics.setStroke(new BasicStroke(1.0f));
    }

    /**
     * Draw a filled circle at (x, y) with radius.
     */
    public void drawSolidCircle(double x, double y, double radius) {
        mGraphics.fill(new Ellipse2D.Double(x - radius, y - radius, radius * 2, radius * 2));
    }

    /**
     * Draw bold text on the screen at (x, y).
     */
    public void drawText(double x, double y, String s, Font font) {
        mGraphics.setFont(font);
        mGraphics.drawString(s, (int) x, (int) y);
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

        mGraphics.drawImage(image, (int) x, (int) y, null);
    }

    /**
     * Draw an image on the screen at position (x, y).
     */
    public void drawImage(Image image, double x, double y, double w, double h) {
        if (image == null) {
            System.err.println("Error: cannot draw null image.");
            return;
        }
        mGraphics.drawImage(image, (int) x, (int) y, (int) w, (int) h, null);
    }

    // ------------------------------------------------------------------------------------------------- Transforms --//

    /**
     * Save the current transform.
     */
    public void saveCurrentTransform() {
        mTransforms.push(mGraphics.getTransform());
    }

    /**
     * Restore the last transform.
     */
    public void restoreLastTransform() {
        // Set current transform to the top of the stack.
        mGraphics.setTransform(mTransforms.peek());

        // If there is more than one transform on the stack
        if (mTransforms.size() > 1) {
            // Pop a transform off the stack
            mTransforms.pop();
        }
    }

    /**
     * Translate the drawing context by (dx, dy).
     */
    public void translate(double dx, double dy) {
        mGraphics.translate(dx, dy);
    }

    /**
     * Rotate the drawing context by <code>thetaDegrees</code>.
     */
    public void rotate(double thetaDegrees) {
        mGraphics.rotate(Math.toRadians(thetaDegrees));
    }

    public void rotate(double thetaDegrees, int dx, int dy) {
        mGraphics.rotate(Math.toRadians(thetaDegrees), dx, dy);
    }

    /**
     * Scale the drawing context by (x, y)
     */
    public void scale(double x, double y) {
        mGraphics.scale(x, y);
    }

    /**
     * Shear the drawing context by (x, y)
     */
    public void shear(double x, double y) {
        mGraphics.shear(x, y);
    }

    //------------------------------------------------------------------------------------------------------- Audio --//

    public static class AudioClip {
        AudioFormat mFormat;

        byte[] mData;

        long mLength;

        Clip mLoopClip;

        public AudioClip(AudioInputStream stream) {
            mFormat = stream.getFormat();

            mLength = stream.getFrameLength() * mFormat.getFrameSize();

            mData = new byte[(int) mLength];

            try {
                stream.read(mData);
            } catch (Exception exception) {
                System.err.println("Error: reading audio file");

                System.exit(1);
            }

            mLoopClip = null;
        }

        public Clip getLoopClip() {
            return mLoopClip;
        }

        public void setLoopClip(Clip clip) {
            mLoopClip = clip;
        }

        public AudioFormat getAudioFormat() {
            return mFormat;
        }

        public byte[] getData() {
            return mData;
        }

        public long getBufferSize() {
            return mLength;
        }
    }

    // Load the AudioClip stored in the file specified by filename
    public AudioClip loadAudio(String filename) {
        try {
            File file = new File(filename);
            AudioInputStream audio = AudioSystem.getAudioInputStream(file);

            return new AudioClip(audio);
        } catch (Exception e) {
            System.err.println("Error: cannot open audio file " + filename);
        }

        return null;
    }

    // Play an AudioClip
    public void playAudio(AudioClip audioClip) {
        if (audioClip == null) {
            System.err.println("Error: audioClip is null");

            return;
        }

        try {
            Clip clip = AudioSystem.getClip();
            clip.open(audioClip.getAudioFormat(), audioClip.getData(), 0, (int) audioClip.getBufferSize());

            clip.start();
        } catch (Exception exception) {
            System.err.println("Error: playing audio clip");
        }
    }

    // Play an AudioClip with a volume in decibels
    public void playAudio(AudioClip audioClip, float volume) {
        if (audioClip == null) {
            System.err.println("Error: audioClip is null");

            return;
        }

        try {
            Clip clip = AudioSystem.getClip();
            clip.open(audioClip.getAudioFormat(), audioClip.getData(), 0, (int) audioClip.getBufferSize());

            FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            control.setValue(volume);

            clip.start();
        } catch (Exception exception) {
            System.err.println("Error: could not play audio clip");
        }
    }

    // Start playing an AudioClip on loop
    public void startAudioLoop(AudioClip audioClip) {
        if (audioClip == null) {
            System.err.println("Error: audioClip is null");

            return;
        }

        Clip clip = audioClip.getLoopClip();

        if (clip == null) {
            try {
                clip = AudioSystem.getClip();

                clip.open(audioClip.getAudioFormat(), audioClip.getData(), 0, (int) audioClip.getBufferSize());

                clip.loop(Clip.LOOP_CONTINUOUSLY);

                audioClip.setLoopClip(clip);
            } catch (Exception exception) {
                System.err.println("Error: could not play audio clip");
            }
        }

        // Set Frame Position to 0
        assert clip != null;
        clip.setFramePosition(0);

        clip.start();
    }

    // Start playing an AudioClip on loop with a volume in decibels
    public void startAudioLoop(AudioClip audioClip, float volume) {
        if (audioClip == null) {
            System.err.println("Error: audioClip is null");

            return;
        }

        Clip clip = audioClip.getLoopClip();

        if (clip == null) {
            try {
                clip = AudioSystem.getClip();
                clip.open(audioClip.getAudioFormat(), audioClip.getData(), 0, (int) audioClip.getBufferSize());

                FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                control.setValue(volume);

                clip.loop(Clip.LOOP_CONTINUOUSLY);
                audioClip.setLoopClip(clip);
            } catch (Exception exception) {
                System.err.println("Error: could not play audio clip");
            }
        }

        assert clip != null;
        clip.setFramePosition(0);

        clip.start();
    }

    // Stop an AudioClip playing
    public void stopAudioLoop(AudioClip audioClip) {
        Clip clip = audioClip.getLoopClip();

        if (clip != null) {
            clip.stop();
        }
    }

    //------------------------------------------------------------------------------------------------ Math Helpers --//

    // TODO: Remove this in favor of built in Math class, create a helper class for degrees <-> radians conversions
    // Return the length of a vector
    public double length(double x, double y) {
        // Calculate and return the sqrt
        return Math.sqrt(x * x + y * y);
    }

    // Return the distance between two points (x1,y1) and (x2,y2)
    public double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    // Convert an angle in radians to degrees
    public double toDegrees(double radians) {
        // Calculate and return the degrees
        return Math.toDegrees(radians);
    }

    // Convert an angle in degrees to radians
    public double toRadians(double degrees) {
        // Calculate and return the radians
        return Math.toRadians(degrees);
    }

    // Return the absolute value of the parameter
    public int abs(int value) {
        // Calculate and return abs
        return Math.abs(value);
    }

    // Return the cos of value
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
