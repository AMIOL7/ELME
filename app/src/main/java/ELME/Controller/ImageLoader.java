package ELME.Controller;

import de.gurkenlabs.litiengine.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import static java.awt.Image.SCALE_FAST;

/**
 * this class does very basic image loading and provides correctly scaled instances of them
 *
 * @author Pap Szabolcs István
 */
public class ImageLoader {

    private static HashMap<String,BufferedImage> imgdb;

    public static void loadResources() throws IOException {
        imgdb = new HashMap<String,BufferedImage>();
        BufferedImage temp;
        temp = ImageIO.read(new File("assets/port/Input_empty.png"));
        imgdb.put("input/empty", temp);
        temp = ImageIO.read(new File("assets/port/Output_empty.png"));
        imgdb.put("output/empty", temp);
    }

    public static Image getImage(String str, int px) {
        return imgdb.get(str).getScaledInstance((int) (px * Game.world().camera().getZoom()), (int) (px * Game.world().camera().getZoom()), SCALE_FAST);
    }
}
