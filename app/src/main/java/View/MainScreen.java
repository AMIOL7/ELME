/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.gui.screens.GameScreen;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 *
 * @author andru
 */
public class MainScreen extends GameScreen {
    
    public MainScreen() {
        super("TEST");
    }

    @Override
    public void render(final Graphics2D g) {
        super.render(g);
        g.setColor(Color.RED);
        Game.graphics().renderText(g, "Test text", 100, 100);
        
        
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("./Resources/img.png"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        Game.graphics().renderImage(g, img, 0, 0);
    }
}
