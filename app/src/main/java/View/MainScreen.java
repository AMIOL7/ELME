/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.gui.screens.GameScreen;
import de.gurkenlabs.litiengine.*;
import de.gurkenlabs.litiengine.graphics.*;
import de.gurkenlabs.litiengine.gui.*;
import de.gurkenlabs.litiengine.gui.screens.*;
import de.gurkenlabs.litiengine.input.IMouse;
import de.gurkenlabs.litiengine.input.Input;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import static java.awt.BasicStroke.*;


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
        drawGUIElements(g);
        //g.setColor(Color.RED);
        //Game.graphics().renderText(g, "Test text", 100, 100);

        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("./Resources/img.png"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Game.graphics().renderImage(g, img, 0, 0);
    }

    /**
     * @author pszi
     */
    protected void drawGUIElements(final Graphics2D g) {
        g.setColor(new Color(0x404040));
        Game.graphics().renderShape(g, new Rectangle(-400, -250, 800, 500));
        g.setColor(new Color(0xB0B0B0));
        Game.graphics().renderText(g, "LOGO", -305, -150);
        Game.graphics().renderShape(g, new Rectangle(-320, -110, 60, 180));
        g.setColor(new Color(0xF0F0F0));
        Game.graphics().renderShape(g, new Rectangle(-160, -100, 320, 200));
        Game.graphics().renderShape(g, new Rectangle(-250, -168, 500, 25));
        g.setColor(new Color(0x246AFF));
        Game.graphics().renderOutline(g, new Rectangle(-320, -110, 60, 180), new BasicStroke(3, CAP_ROUND, JOIN_ROUND));
        Game.graphics().renderText(g, "Operations Menu", -316, -100);
        Game.graphics().renderText(g, "(floating,", -308, -92);
        Game.graphics().renderText(g, "translucent)", -313, -84);
        g.setColor(new Color(0x008000));
        Game.graphics().renderOutline(g, new Rectangle(-250, -168, 500, 25), new BasicStroke(3, CAP_ROUND, JOIN_ROUND));
        Game.graphics().renderText(g, "Toolbar", -236, -158);
    }
}