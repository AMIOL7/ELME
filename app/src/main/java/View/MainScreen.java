/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.gui.screens.GameScreen;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * This class is responsible fo rendering the main screen of the game
 *
 * @author Shamanayev Andrey
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
    }
}
