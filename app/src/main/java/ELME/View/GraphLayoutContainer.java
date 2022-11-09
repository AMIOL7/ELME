package ELME.View;

import ELME.Controller.LogicEntity;
import ELME.Model.Graph;
import ELME.Model.Node;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.resources.Resources;
import de.gurkenlabs.litiengine.resources.TextureAtlas;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import static java.awt.Image.*;

/**
 * This class is what remembers and stores graphical information about the layout
 * of custom graphs, both current and saved.
 *
 * (may end up being moved to the controller)
 *
 * @author Pap Szabolcs István
 */

public class GraphLayoutContainer implements Serializable {

    MainScreen screen;
    Graph graph;
    ArrayList<LogicEntity> entities;

    public LogicEntity activeEntityMove, activeEntityResize;

    /**
     * IMPORTANT: Only add empty graphs, otherwise there will be no position data with which to place nodes,
     * this won't be a problem later on, but for now please look out for that
     */

    public GraphLayoutContainer(MainScreen scr, Graph graph) {
        screen=scr;
        this.graph=graph;
        entities = new ArrayList<>();
    }

    public void InsertNode(Node node, Rectangle2D.Double location) {
        entities.add(new LogicEntity(node, location));
        graph.getNodes().add(node);
    }

    public void MoveNode(Node node, double x, double y) throws Exception {
        int index = -1;
        for (int i = 0; i < graph.getNodes().size(); ++i)
            if (graph.getNodes().get(i).equals(node)) // Needs .equals() to be implemented in Node
                index = i;
        if (index < 0) throw new Exception("Node not found in graph");
        MoveNode(1, x, y);
    }
    public void MoveNode(int index, double x, double y) {
        LogicEntity ent = entities.get(index);
        ent.setLocation(x, y);
        ent.setMoveBoundingBox(new Rectangle2D.Double(x,y, ent.getWidth(), 8));
        ent.setResizeBoundingBox(new Rectangle2D.Double(x+ent.getWidth()-5, y+ent.getHeight()-5, 5, 5));
    }

    public void ResizeNode(int index, double x, double y) {
        entities.get(index).setSize(x, y);
    }

    public void DeleteNode(Node node) {
        
    }

    public void SaveLayout() {
        
    }

    public void LoadLayout() {
        
    }

    public void DrawLayout(final Graphics2D g) throws IOException {
            for (int i=0; i<graph.getNodes().size(); ++i) {
                LogicEntity temp = entities.get(i);
                DrawNode(graph.getNodes().get(i), new Rectangle2D.Double(temp.getX(), temp.getY(), temp.getWidth(), temp.getHeight()), g);
                g.setColor(Color.DARK_GRAY);
                Game.graphics().renderOutline(g, temp.getMoveBoundingBox(), new BasicStroke(3, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
                g.setColor(Color.LIGHT_GRAY);
                Game.graphics().renderShape(g, temp.getResizeBoundingBox());
            }
    }

    private void DrawNode(Node node, Rectangle2D.Double pos, final Graphics2D g) throws IOException {
        //Game.graphics().renderEntity();
        g.setColor(Color.WHITE);
        Game.graphics().renderShape(g, pos);
        g.setColor(Color.RED);
        Game.graphics().renderText(g, node.getTag() + " node", pos.getMinX()+5, pos.getMinY()+5);
        Image testImage = null;

        testImage = ImageIO.read(new File("app/assets/port/Input_empty.png"))
                .getScaledInstance((int) (25 * Game.world().camera().getZoom()), (int) (25 * Game.world().camera().getZoom()), SCALE_FAST);
        int numberOfInputs = node.getInputs().size();
        for (int i = 0; i < numberOfInputs; ++i)
            Game.graphics().renderImage(g, testImage, pos.getMinX(), pos.getMinY() + pos.height * (i + 1) / (numberOfInputs + 2));

        testImage = ImageIO.read(new File("app/assets/port/Output_empty.png"))
                .getScaledInstance((int) (25 * Game.world().camera().getZoom()), (int) (25 * Game.world().camera().getZoom()), SCALE_FAST);
        int numberOfOutputs = node.getOutputs().size();
        for (int i = 0; i < numberOfOutputs; ++i)
            Game.graphics().renderImage(g, testImage, pos.getMaxX(), pos.getMinY() + pos.height * (i + 1) / (numberOfOutputs + 2));
    }
    
    public void DrawCompactLayout() {
        
    }
}
