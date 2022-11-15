package ELME.View;

import ELME.Controller.ImageLoader;
import ELME.Controller.LogicEntity;
import ELME.Model.Graph;
import ELME.Model.Node;

import de.gurkenlabs.litiengine.Game;

import javax.security.auth.login.LoginContext;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;



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
    LinkedList<LogicEntity> entities;

    public LogicEntity activeEntityMove, activeEntityResize;

    /**
     * IMPORTANT: Only add empty graphs, otherwise there will be no position data with which to place nodes,
     * this won't be a problem later on, but for now please look out for that
     */

    public GraphLayoutContainer(MainScreen scr, Graph graph) {
        screen=scr;
        this.graph=graph;
        entities = new LinkedList<>();
    }

    public void moveToTop(LogicEntity entity) {
        entities.remove(entity);
        entities.addFirst(entity);
    }

    public void insertNode(Node node, Rectangle2D.Double location) {
        entities.addFirst(new LogicEntity(node, location));
        graph.getNodes().add(node);
    }

    public void moveNode(LogicEntity entity, double x, double y) throws Exception {
        int index = -1;
        for (int i = 0; i < entities.size(); ++i)
            if (entities.get(i).equals(entity)) // Needs .equals() to be implemented in Node
                index = i;
        if (index < 0) throw new Exception("Node not found in graph");
        moveNode(index, x, y);
    }
    public void moveNode(int index, double x, double y) {
        entities.get(index).relocate(x, y);
    }

    public void resizeNode(int index, double x, double y) {
        entities.get(index).setSize(x, y);
    }

    public void deleteNode(Node node) {
        
    }

    public void saveLayout() {
        
    }

    public void loadLayout() {
        
    }

    public void drawLayout(final Graphics2D g) throws IOException {
        if (entities.size() > 0)
            for (int i=entities.size()-1; i>=0; --i) {
                LogicEntity temp = entities.get(i);
                drawNode(temp, new Rectangle2D.Double(temp.getX(), temp.getY(), temp.getWidth(), temp.getHeight()), g);
                g.setColor(Color.DARK_GRAY);
                Game.graphics().renderOutline(g, temp.getMoveBoundingBox(), new BasicStroke(3, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
                g.setColor(Color.LIGHT_GRAY);
                Game.graphics().renderShape(g, temp.getResizeBoundingBox());
            }
    }

    private void drawNode(LogicEntity entity, Rectangle2D.Double pos, final Graphics2D g) {
        Game.graphics().renderEntity(g, entity);
        g.setColor(Color.WHITE);
        Game.graphics().renderShape(g, pos);
        g.setColor(Color.RED);
        Game.graphics().renderText(g, entity.getNode().getTag() + " node", pos.getMinX()+5, pos.getMinY()+5);
        int numberOfInputs = entity.getNode().getInputs().size();
        for (int i = 0; i < numberOfInputs; ++i)
            Game.graphics().renderImage(g, ImageLoader.getImage("input/empty", 25),
                    pos.getMinX()-5, pos.getMinY() + pos.height*(i+1)/(numberOfInputs+1));
        int numberOfOutputs = entity.getNode().getOutputs().size();
        for (int i = 0; i < numberOfOutputs; ++i)
            Game.graphics().renderImage(g, ImageLoader.getImage("output/empty", 25),
                    pos.getMaxX()-5, pos.getMinY()+pos.height*(i+1)/(numberOfOutputs+1));
    }
    
    public void drawCompactLayout() {
        
    }
}
