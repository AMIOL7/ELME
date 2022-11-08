
package ELME.View;

import ELME.Model.Graph;
import ELME.Model.Node;
import ELME.Model.Nodes.ANDNode;
import de.gurkenlabs.litiengine.gui.screens.GameScreen;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * This class is responsible fo rendering the main screen of the game
 *
 * @author Shamanayev Andrey
 * @author Pap Szabolcs István
 */
public class MainScreen extends GameScreen {

    public GraphLayoutContainer graphVisuals;
    private SideMenu sideMenu;
    private Toolbar toolbar;
    public MainScreen() {
        super("TEST");
        graphVisuals = new GraphLayoutContainer(new Graph("WORKING_GRAPH", false));
    }
    
    protected void initializeComponents() {
        sideMenu = new SideMenu(0, 200, 200, 100, 3, 2, "NOT", "AND", "OR", "XOR", "ODD", "more...");
        toolbar = new Toolbar(350, 0, 400, 40,1, 3, "File", "Options", "Help");
        getComponents().add(sideMenu);
        getComponents().add(toolbar);

    }
    @Override
    public void prepare() {
        sideMenu.setEnabled(true);
        toolbar.setEnabled(true);
        super.prepare();
    }


    @Override
    public void render(final Graphics2D g) {
        //drawGUIElements(g);
        graphVisuals.DrawLayout(g);
        super.render(g);
        /*g.setColor(Color.RED);
        Game.graphics().renderText(g, "Test text", 100, 100);

        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("./Resources/img.png"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Game.graphics().renderImage(g, img, 0, 0);*/
    }
/*
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
     */
}
