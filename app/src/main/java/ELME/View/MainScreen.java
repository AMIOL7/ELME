
package ELME.View;

import ELME.Controller.LogicEntity;
import ELME.Model.Graph;
import de.gurkenlabs.litiengine.gui.ImageComponent;
import de.gurkenlabs.litiengine.gui.screens.GameScreen;
import de.gurkenlabs.litiengine.input.Input;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
    ImageComponent background;
    public MainScreen() {
        super("TEST");
        graphVisuals = new GraphLayoutContainer(this, new Graph("WORKING_GRAPH", false));

        Input.mouse().onPressed(e -> {
            if (e.getButton() == MouseEvent.BUTTON1)
                for (LogicEntity ent : graphVisuals.entities) {
                    if (ent.getMoveBoundingBox().contains(Input.mouse().getMapLocation().getX(), Input.mouse().getMapLocation().getY()))
                        graphVisuals.activeEntityMove = ent;
                    if (ent.getResizeBoundingBox().contains(Input.mouse().getMapLocation().getX(), Input.mouse().getMapLocation().getY()))
                        graphVisuals.activeEntityResize = ent;
                }
        });

        Input.mouse().onDragged(e -> {
            if (Input.mouse().isLeftButtonPressed()) {
                double x = Input.mouse().getMapLocation().getX();
                double y = Input.mouse().getMapLocation().getY();
                if (graphVisuals.activeEntityMove != null) {
                    graphVisuals.activeEntityMove.setLocation(x, y);
                    graphVisuals.activeEntityMove.KeepAttached(new Rectangle2D.Double(x, y, graphVisuals.activeEntityMove.getWidth(), graphVisuals.activeEntityMove.getHeight()));
                }
                if (graphVisuals.activeEntityResize != null) {
                    graphVisuals.activeEntityResize.setWidth(x - graphVisuals.activeEntityResize.getX());
                    graphVisuals.activeEntityResize.setHeight(y - graphVisuals.activeEntityResize.getY());
                    double w = graphVisuals.activeEntityResize.getWidth();
                    double h = graphVisuals.activeEntityResize.getHeight();
                    graphVisuals.activeEntityResize.KeepAttached(new Rectangle2D.Double(x-w ,y-h , w, h));
                }
            }
        });

        Input.mouse().onReleased(e -> {
            if (e.getButton() == MouseEvent.BUTTON1) {
                graphVisuals.activeEntityMove = null;
                graphVisuals.activeEntityResize = null;
            }
        });
    }

    private void selectClickedComponent(MouseEvent e) {

    }

    protected void initializeComponents() {
        BufferedImage b = null;
        try { b = ImageIO.read(new File("app/assets/background.png")); }
        catch (IOException e) { throw new RuntimeException(e); }
        sideMenu = new SideMenu(0, 200, 200, 100, 3, 2, "NOT", "AND", "OR", "XOR", "ODD", "more...");
        toolbar = new Toolbar(350, 0, 400, 40,1, 3, "File", "Options", "Help");
        background = new ImageComponent(0, 0, 1920, 1080, b);
        getComponents().add(sideMenu);
        getComponents().add(toolbar);

    }
    @Override
    public void prepare() {
        background.setVisible(true);
        sideMenu.setEnabled(true);
        toolbar.setEnabled(true);
        super.prepare();
    }


    @Override
    public void render(final Graphics2D g) {
        //drawGUIElements(g);
        background.render(g);
        try { graphVisuals.DrawLayout(g); } catch (IOException e)
        { throw new RuntimeException("image failed to load", e); }
        super.render(g);
    }

}
