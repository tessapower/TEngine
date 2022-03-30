package engine;

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
import java.util.Random;
import java.util.Stack;

public abstract class GameEngine implements KeyListener, MouseListener, MouseMotionListener {
    JFrame mFrame;
    GamePanel mPanel;
    int mWidth, mHeight;
    Graphics2D mGraphics;
    boolean initialised = false;

    // Two variables to keep track of how much time has passed between frames
    long time = 0, oldTime = 0;
    // Main Loop of the game. Runs continuously and calls all the updates
    // of the game and tells the game to display a new frame.
    GameTimer timer = new GameTimer(30, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Determine the time step
            double passedTime = measureTime();
            double dt = passedTime / 1000.;

            // Update the Game
            update(dt);

            // Tell the Game to draw
            mPanel.repaint();
        }
    });

    // TODO: Move these into a TColors class
    @SuppressWarnings("unused")
    Color black = Color.BLACK;
    @SuppressWarnings("unused")
    Color orange = Color.ORANGE;
    @SuppressWarnings("unused")
    Color pink = Color.PINK;
    @SuppressWarnings("unused")
    Color red = Color.RED;
    @SuppressWarnings("unused")
    Color purple = new Color(128, 0, 128);
    @SuppressWarnings("unused")
    Color blue = Color.BLUE;
    @SuppressWarnings("unused")
    Color green = Color.GREEN;
    @SuppressWarnings("unused")
    Color yellow = Color.YELLOW;
    @SuppressWarnings("unused")
    Color white = Color.WHITE;

    // Stack of transforms
    Stack<AffineTransform> mTransforms;

    Random mRandom = null;

    public GameEngine() {
        // Create graphics transform stack
        mTransforms = new Stack<>();

        SwingUtilities.invokeLater(() -> setupWindow(500, 500, "Window"));
    }

    public static void createGame(GameEngine game, int framerate) {
        game.init();

        game.gameLoop(framerate);
    }

    public static void createGame(GameEngine game) {
        createGame(game, 30);
    }

    // Returns the time in milliseconds
    public long getTime() {
        return System.currentTimeMillis();
    }

    public void sleep(double ms) {
        try {
            Thread.sleep((long) ms);
        } catch (Exception e) {
            // Do Nothing
        }
    }

    // Returns the time passed since this function was last called.
    public long measureTime() {
        time = getTime();
        if (oldTime == 0) {
            oldTime = time;
        }
        long passed = time - oldTime;
        oldTime = time;
        return passed;
    }

    public void setupWindow(int width, int height, String title) {
        mFrame = new JFrame();
        mPanel = new GamePanel();

        mWidth = width;
        mHeight = height;

        mFrame.setSize(width, height);
        mFrame.setLocation(200, 200);
        mFrame.setTitle(title);
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

        // Resize the window (insets are just the boarders that the Operating System puts on the board)
        Insets insets = mFrame.getInsets();
        mFrame.setSize(width + insets.left + insets.right, height + insets.top + insets.bottom);
    }

    public void setWindowSize(final int width, final int height) {
        SwingUtilities.invokeLater(() -> {
            // Resize the window (insets are just the boarders that the Operating System puts on the board)
            Insets insets = mFrame.getInsets();
            mWidth = width;
            mHeight = height;
            mFrame.setSize(width + insets.left + insets.right, height + insets.top + insets.bottom);
            mPanel.setSize(width, height);
        });
    }

    // Return the width of the window
    public int width() {
        return mWidth;
    }

    // Return the height of the window
    public int height() {
        return mHeight;
    }

    // Initialises and starts the game loop with the given framerate.
    public void gameLoop(int framerate) {
        initialised = true; // assume init has been called or won't be called

        timer.setFramerate(framerate);
        timer.setRepeats(true);

        // Main loop runs until program is closed
        timer.start();
    }

    public void init() {
    }

    public abstract void update(double dt);

    public abstract void paintComponent();

    // Called whenever a key is pressed
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

    // Changes the background Color to the color c
    public void changeBackgroundColor(Color c) {
        // Set background colour
        mGraphics.setBackground(c);
    }

    // Changes the background Color to the color (red,green,blue)
    public void changeBackgroundColor(int red, int green, int blue) {
        // Set background colour
        mGraphics.setBackground(clampColor(red, green, blue));
    }

    private Color clampColor(int red, int green, int blue) {
        if (red < 0) {
            red = 0;
        }
        if (red > 255) {
            red = 255;
        }

        if (green < 0) {
            green = 0;
        }
        if (green > 255) {
            green = 255;
        }

        if (blue < 0) {
            blue = 0;
        }
        if (blue > 255) {
            blue = 255;
        }

        return new Color(red, green, blue);
    }

    // Clears the background, makes the whole window whatever the background color is
    public void clearBackground(int width, int height) {
        // Clear background
        mGraphics.clearRect(0, 0, width, height);
    }

    // Changes the drawing Color to the color c
    public void changeColor(Color c) {
        // Set colour
        mGraphics.setColor(c);
    }

    // Changes the drawing Color to the color (red,green,blue)
    public void changeColor(int red, int green, int blue) {
        // Set colour
        mGraphics.setColor(clampColor(red, green, blue));
    }

    // Draws a line from (x1,y2) to (x2,y2)
    public void drawLine(double x1, double y1, double x2, double y2) {
        // Draw a Line
        mGraphics.draw(new Line2D.Double(x1, y1, x2, y2));
    }

    // Draws a line from (x1,y2) to (x2,y2) with width l
    public void drawLine(double x1, double y1, double x2, double y2, double l) {
        // Set the stroke
        mGraphics.setStroke(new BasicStroke((float) l));

        // Draw a Line
        mGraphics.draw(new Line2D.Double(x1, y1, x2, y2));

        // Reset the stroke
        mGraphics.setStroke(new BasicStroke(1.0f));
    }

    // This function draws a rectangle at (x,y) with width and height (w,h)
    public void drawRectangle(double x, double y, double w, double h) {
        // Draw a Rectangle
        mGraphics.draw(new Rectangle2D.Double(x, y, w, h));
    }

    // This function draws a rectangle at (x,y) with width and height (w,h) with a line of width l
    public void drawRectangle(double x, double y, double w, double h, double l) {
        // Set the stroke
        mGraphics.setStroke(new BasicStroke((float) l));

        // Draw a Rectangle
        mGraphics.draw(new Rectangle2D.Double(x, y, w, h));

        // Reset the stroke
        mGraphics.setStroke(new BasicStroke(1.0f));
    }

    // This function fills in a rectangle at (x,y) with width and height (w,h)
    public void drawSolidRectangle(double x, double y, double w, double h) {
        // Fill a Rectangle
        mGraphics.fill(new Rectangle2D.Double(x, y, w, h));
    }

    // This function draws a circle at (x,y) with radius
    public void drawCircle(double x, double y, double radius) {
        // Draw a Circle
        mGraphics.draw(new Ellipse2D.Double(x - radius, y - radius, radius * 2, radius * 2));
    }

    // This function draws a circle at (x,y) with radius with a line of width l
    public void drawCircle(double x, double y, double radius, double l) {
        // Set the stroke
        mGraphics.setStroke(new BasicStroke((float) l));

        // Draw a Circle
        mGraphics.draw(new Ellipse2D.Double(x - radius, y - radius, radius * 2, radius * 2));

        // Reset the stroke
        mGraphics.setStroke(new BasicStroke(1.0f));
    }

    // This function draws a circle at (x,y) with radius
    public void drawSolidCircle(double x, double y, double radius) {
        // Fill a Circle
        mGraphics.fill(new Ellipse2D.Double(x - radius, y - radius, radius * 2, radius * 2));
    }

    // This function draws text on the screen at (x,y)
    public void drawText(double x, double y, String s) {
        // Draw text on the screen
        mGraphics.setFont(new Font("Arial", Font.PLAIN, 40));
        mGraphics.drawString(s, (int) x, (int) y);
    }

    // This function draws bold text on the screen at (x,y)
    public void drawBoldText(double x, double y, String s) {
        // Draw text on the screen
        mGraphics.setFont(new Font("Arial", Font.BOLD, 40));
        mGraphics.drawString(s, (int) x, (int) y);
    }

    // This function draws text on the screen at (x,y) with Font (font,size)
    public void drawText(double x, double y, String s, String font, int size) {
        // Draw text on the screen
        mGraphics.setFont(new Font(font, Font.PLAIN, size));
        mGraphics.drawString(s, (int) x, (int) y);
    }

    // This function draws bold text on the screen at (x,y) with Font (font,size)
    public void drawBoldText(double x, double y, String s, String font, int size) {
        // Draw text on the screen
        mGraphics.setFont(new Font(font, Font.BOLD, size));
        mGraphics.drawString(s, (int) x, (int) y);
    }

    // Loads an image from file
    public Image loadImage(String filename) {
        try {
            // Load and return Image
            return ImageIO.read(new File(filename));
        } catch (IOException e) {
            // Show Error Message
            System.out.println("Error: could not load image " + filename);
            System.exit(1);
        }

        // Return null
        return null;
    }

    // Loads a sub-image out of an image
    public Image subImage(Image source, int x, int y, int w, int h) {
        // Check if image is null
        if (source == null) {
            // Print Error message
            System.out.println("Error: cannot extract a subImage from a null image.\n");

            // Return null
            return null;
        }

        // Convert to a buffered image
        BufferedImage buffered = (BufferedImage) source;

        // Extract and return sub image
        return buffered.getSubimage(x, y, w, h);
    }

    // Draws an image on the screen at position (x,y)
    public void drawImage(Image image, double x, double y) {
        // Check if image is null
        if (image == null) {
            // Print Error message
            System.out.println("Error: cannot draw null image.\n");
            return;
        }

        // Draw image on screen at (x,y)
        mGraphics.drawImage(image, (int) x, (int) y, null);
    }

    // Draws an image on the screen at position (x,y)
    public void drawImage(Image image, double x, double y, double w, double h) {
        // Check if image is null
        if (image == null) {
            // Print Error message
            System.out.println("Error: cannot draw null image.\n");
            return;
        }
        // Draw image on screen at (x,y) with size (w,h)
        mGraphics.drawImage(image, (int) x, (int) y, (int) w, (int) h, null);
    }

    // Save the current transform
    public void saveCurrentTransform() {
        // Push transform onto the stack
        mTransforms.push(mGraphics.getTransform());
    }

    // Restores the last transform
    public void restoreLastTransform() {
        // Set current transform to the top of the stack.
        mGraphics.setTransform(mTransforms.peek());

        // If there is more than one transform on the stack
        if (mTransforms.size() > 1) {
            // Pop a transform off the stack
            mTransforms.pop();
        }
    }

    // This function translates the drawing context by (x,y)
    public void translate(double x, double y) {
        mGraphics.translate(x, y);
    }

    // This function rotates the drawing context by a degrees
    public void rotate(double a) {
        mGraphics.rotate(Math.toRadians(a));
    }

    // This function scales the drawing context by (x,y)
    public void scale(double x, double y) {
        mGraphics.scale(x, y);
    }

    // This function shears the drawing context by (x,y)
    public void shear(double x, double y) {
        mGraphics.shear(x, y);
    }

    // Loads the AudioClip stored in the file specified by filename
    public AudioClip loadAudio(String filename) {
        try {
            // Open File
            File file = new File(filename);

            // Open Audio Input Stream
            AudioInputStream audio = AudioSystem.getAudioInputStream(file);

            // Create and return Audio Clip
            return new AudioClip(audio);
        } catch (Exception e) {
            // Catch Exception
            System.out.println("Error: cannot open Audio File " + filename + "\n");
        }

        return null;
    }

    // Plays an AudioClip
    public void playAudio(AudioClip audioClip) {
        // Check audioClip for null
        if (audioClip == null) {
            // Print error message
            System.out.println("Error: audioClip is null\n");

            return;
        }

        try {
            Clip clip = AudioSystem.getClip();

            clip.open(audioClip.getAudioFormat(), audioClip.getData(), 0, (int) audioClip.getBufferSize());

            clip.start();
        } catch (Exception exception) {
            System.out.println("Error playing Audio Clip\n");
        }
    }

    // Plays an AudioClip with a volume in decibels
    public void playAudio(AudioClip audioClip, float volume) {
        if (audioClip == null) {
            System.out.println("Error: audioClip is null\n");

            return;
        }

        try {
            Clip clip = AudioSystem.getClip();

            clip.open(audioClip.getAudioFormat(), audioClip.getData(), 0, (int) audioClip.getBufferSize());

            FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

            control.setValue(volume);

            clip.start();
        } catch (Exception exception) {
            System.out.println("Error: could not play Audio Clip\n");
        }
    }

    // Starts playing an AudioClip on loop
    public void startAudioLoop(AudioClip audioClip) {
        if (audioClip == null) {
            System.out.println("Error: audioClip is null\n");

            return;
        }

        Clip clip = audioClip.getLoopClip();

        // Create Loop Clip if necessary
        if (clip == null) {
            try {
                clip = AudioSystem.getClip();

                clip.open(audioClip.getAudioFormat(), audioClip.getData(), 0, (int) audioClip.getBufferSize());

                clip.loop(Clip.LOOP_CONTINUOUSLY);

                audioClip.setLoopClip(clip);
            } catch (Exception exception) {
                System.out.println("Error: could not play Audio Clip\n");
            }
        }

        // Set Frame Position to 0
        assert clip != null;
        clip.setFramePosition(0);

        clip.start();
    }

    // Starts playing an AudioClip on loop with a volume in decibels
    public void startAudioLoop(AudioClip audioClip, float volume) {
        // Check audioClip for null
        if (audioClip == null) {
            System.out.println("Error: audioClip is null\n");

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
                System.out.println("Error: could not play Audio Clip\n");
            }
        }

        // Set Frame Position to 0
        assert clip != null;
        clip.setFramePosition(0);

        // Start Audio Clip playing
        clip.start();
    }

    // Stops an AudioClip playing
    public void stopAudioLoop(AudioClip audioClip) {
        Clip clip = audioClip.getLoopClip();

        if (clip != null) {
            clip.stop();
        }
    }

    // TODO: Remove this in favor of built in Random support
    // Function that returns a random integer between 0 and max
    public int rand(int max) {
        if (mRandom == null) {
            mRandom = new Random();
        }

        double d = mRandom.nextDouble();

        // Convert to an integer in range [0, max) and return
        return (int) (d * max);
    }

    // Function that gives you a random number between 0 and max
    public float rand(float max) {
        if (mRandom == null) {
            mRandom = new Random();
        }

        float d = mRandom.nextFloat();

        return d * max;
    }

    // Function that gives you a random number between 0 and max
    public double rand(double max) {
        if (mRandom == null) {
            mRandom = new Random();
        }

        double value = mRandom.nextDouble();

        return value * max;
    }

    // TODO: Remove this in favor of build in Math class
    // Returns the largest integer that is less than or equal to the argument value.
    public int floor(double value) {
        // Calculate and return floor
        return (int) Math.floor(value);
    }

    // Returns the smallest integer that is greater than or equal
    // to the argument value.
    public int ceil(double value) {
        // Calculate and return ceil
        return (int) Math.ceil(value);
    }

    // Rounds the argument value to the closest integer.
    public int round(double value) {
        // Calculate and return round
        return (int) Math.round(value);
    }

    // Returns the square root of the parameter
    public double sqrt(double value) {
        // Calculate and return the sqrt
        return Math.sqrt(value);
    }

    // Returns the length of a vector
    public double length(double x, double y) {
        // Calculate and return the sqrt
        return Math.sqrt(x * x + y * y);
    }

    // Returns the distance between two points (x1,y1) and (x2,y2)
    public double distance(double x1, double y1, double x2, double y2) {
        // Calculate and return the distance
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    // Converts an angle in radians to degrees
    public double toDegrees(double radians) {
        // Calculate and return the degrees
        return Math.toDegrees(radians);
    }

    // Converts an angle in degrees to radians
    public double toRadians(double degrees) {
        // Calculate and return the radians
        return Math.toRadians(degrees);
    }

    // Returns the absolute value of the parameter
    public int abs(int value) {
        // Calculate and return abs
        return Math.abs(value);
    }

    // Returns the absolute value of the parameter
    public float abs(float value) {
        // Calculate and return abs
        return Math.abs(value);
    }

    // Returns the absolute value of the parameter
    public double abs(double value) {
        // Calculate and return abs
        return Math.abs(value);
    }

    // Returns the cos of value
    public double cos(double value) {
        // Calculate and return cos
        return Math.cos(Math.toRadians(value));
    }

    // Returns the acos of value
    public double acos(double value) {
        // Calculate and return acos
        return Math.toDegrees(Math.acos(value));
    }

    // Returns the sin of value
    public double sin(double value) {
        // Calculate and return sin
        return Math.sin(Math.toRadians(value));
    }

    // Returns the asin of value
    public double asin(double value) {
        // Calculate and return asin
        return Math.toDegrees(Math.asin(value));
    }

    // Returns the tan of value
    public double tan(double value) {
        // Calculate and return tan
        return Math.tan(Math.toRadians(value));
    }

    // Returns the atan of value
    public double atan(double value) {
        // Calculate and return atan
        return Math.toDegrees(Math.atan(value));
    }

    // Returns the atan2 of value
    public double atan2(double x, double y) {
        // Calculate and return atan2
        return Math.toDegrees(Math.atan2(x, y));
    }

    protected static class GameTimer extends Timer {
        @Serial
        private static final long serialVersionUID = 1L;
        private int framerate;

        protected GameTimer(int framerate, ActionListener listener) {
            super(1000 / framerate, listener);
            this.framerate = framerate;
        }

        @SuppressWarnings("unused")
        protected int getFramerate() {
            return framerate;
        }

        protected void setFramerate(int framerate) {
            if (framerate < 1)
                framerate = 1;
            this.framerate = framerate;

            int delay = 1000 / framerate;
            setInitialDelay(0);
            setDelay(delay);
        }
    }

    protected class GamePanel extends JPanel {
        @Serial
        private static final long serialVersionUID = 1L;

        // This gets called any time the Operating System tells the program to paint itself
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
                GameEngine.this.paintComponent();
            }
        }
    }

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
                System.out.println("Error reading Audio File\n");

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
}
