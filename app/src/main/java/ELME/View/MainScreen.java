
package ELME.View;

import ELME.Controller.LogicEntity;
import ELME.Model.Graph;
import de.gurkenlabs.litiengine.gui.ImageComponent;
import de.gurkenlabs.litiengine.gui.screens.GameScreen;
import de.gurkenlabs.litiengine.input.Input;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * This class is responsible for rendering the main screen of the game
 *
 * @author Shamanayev Andrey
 * @author Pap Szabolcs István
 */
public class MainScreen extends GameScreen {

    public GraphLayoutContainer graphVisuals;
    private SideMenu sideMenu;
    private Toolbar toolbar;
    private ImageComponent background;

    private LogicEntity activeEntity;
    /**
     * functions :
     *  1 - moving
     *  2 - resizing
     *  3 - linking
     */
    private short entityInteraction;
    private int linkPortIndex;
    private Point2D.Double delta;
    public MainScreen() {
        super("TEST");
        graphVisuals = new GraphLayoutContainer(this, new Graph("WORKING_GRAPH", false));
        Input.mouse().onPressed(e -> {
            if (e.getButton() == MouseEvent.BUTTON1) {
                Point2D point = Input.mouse().getMapLocation();
                delta = new Point2D.Double();
                for (LogicEntity ent : graphVisuals.entities) {
                    if (ent.getBoundingBox().contains(point)) {
                        graphVisuals.moveToTop(ent);
                        if (ent.getCloseBoundingBox().contains(point))
                            graphVisuals.deleteNode(ent);
                        if (ent.getMoveBoundingBox().contains(point)) {
                            activeEntity = ent;
                            entityInteraction = 1;
                            delta.x = point.getX() - ent.getLocation().getX();
                            delta.y = point.getY() - ent.getLocation().getY();
                        }
                        if (ent.getResizeBoundingBox().contains(point)) {
                            activeEntity = ent;
                            entityInteraction = 2;

                        }
                        for (int i = 0; i < ent.getOutputPortsBoundingBoxes().length; ++i) {
                            if (ent.getOutputPortsBoundingBoxes()[i].contains(point)) {
                                activeEntity = ent;
                                linkPortIndex = i;
                                entityInteraction = 3;

                            }
                        }
                        return;
                    }
                }
            }
        });

        Input.mouse().onDragged(e -> {
            if (Input.mouse().isLeftButtonPressed()) {
                double x = Input.mouse().getMapLocation().getX();
                double y = Input.mouse().getMapLocation().getY();
                if (activeEntity != null) {
                    if (entityInteraction == 1 && delta != null)
                        activeEntity.relocate(x-delta.x, y-delta.y);
                    if (entityInteraction == 2)
                        activeEntity.resize(x, y);
                }
            }
        });

        Input.mouse().onReleased(e -> {
            if (e.getButton() == MouseEvent.BUTTON1) {
                for (LogicEntity ent : graphVisuals.entities) {
                    Point2D point = Input.mouse().getMapLocation();
                    for (int i = 0; i < ent.getInputPortsBoundingBoxes().length; ++i)
                        if (activeEntity != null && ent.getInputPortsBoundingBoxes()[i].contains(point) && entityInteraction == 3)
                            activeEntity.addLink(linkPortIndex, ent, i);
                }
                delta = null;
                activeEntity = null;
                linkPortIndex = -1;
            }
        });
    }

    protected void initializeComponents() {
        BufferedImage b;
        try { b = ImageIO.read(new File("assets/background.png")); }
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
        background.render(g);
        try { graphVisuals.drawLayout(g); } catch (IOException e)
        { throw new RuntimeException("image failed to load", e); }
        super.render(g);
    }

}
