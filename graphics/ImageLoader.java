package graphics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

class ImageLoader {
    static Image loadImage(String filename) {
        try {
            return ImageIO.read(new File(filename));
        } catch (IOException e) {
            System.out.println("Error: could not load image " + filename);
            System.exit(1);
        }

        return null;
    }
}
