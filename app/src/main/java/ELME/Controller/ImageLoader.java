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
        temp = ImageIO.read(new File("assets/port/Input_no_signal.png"));
        imgdb.put("input/empty", temp);
        temp = ImageIO.read(new File("assets/port/Output_no_signal.png"));
        imgdb.put("output/empty", temp);
        temp = ImageIO.read(new File("assets/port/Input_positive.png"));
        imgdb.put("input/positive", temp);
        temp = ImageIO.read(new File("assets/port/Output_positive.png"));
        imgdb.put("output/positive", temp);
        temp = ImageIO.read(new File("assets/port/Input_negative.png"));
        imgdb.put("input/negative", temp);
        temp = ImageIO.read(new File("assets/port/Output_negative.png"));
        imgdb.put("output/negative", temp);
    }

    public static Image getImage(String str, int px) {
        return imgdb.get(str).getScaledInstance((int) (px * Game.world().camera().getZoom()), (int) (px * Game.world().camera().getZoom()), SCALE_FAST);
    }
}
