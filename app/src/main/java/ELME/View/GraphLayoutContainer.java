package ELME.View;

import ELME.Controller.ImageLoader;
import ELME.Controller.LinkInfo;
import ELME.Controller.LogicEntity;
import ELME.Model.Graph;
import ELME.Model.Node;

import de.gurkenlabs.litiengine.Game;

import java.awt.*;
import java.awt.geom.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
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
    public LogicEntity activeEntityMove, activeEntityResize, activeEntityLink;
    public int linkPortIndex;
    private boolean displayBoundingBoxes = true;

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
    public void moveNode(int index, double x, double y) { entities.get(index).relocate(x, y); }

    //public void resizeNode(int index, double x, double y) { entities.get(index).setSize(x, y); }

    public void deleteNode(LogicEntity entity) {
        for (int i = 0; i < entity.getNode().getInputs().size(); ++i)
            entity.removeLink(i);
        entities.remove(entity);
    }
    /*public void linkNodes(LogicEntity from, LogicEntity into, int index) {
        into.getNode().getInputs().get(index).connect(from.getNode().getOutputs().get(linkPortIndex));
    }*/
    public void resetActivity() {
        activeEntityMove = null;
        activeEntityResize = null;
        activeEntityLink = null;
        linkPortIndex = -1;
    }

    public void saveLayout() {
        
    }

    public void loadLayout() {
        
    }

    public void drawLayout(final Graphics2D g) throws IOException {
        if (entities.size() > 0) {
            g.setColor(new Color(60, 60, 60, 127));
            for (int i = entities.size() - 1; i >= 0; --i) {
                LogicEntity le = entities.get(i);
                if (le.getLinks() != null) {
                    ArrayList<LinkInfo>[] li = le.getLinks();
                    for (int j = 0; j < le.getNode().getOutputs().size(); ++j) {
                        if (le.getLinks()[j] != null) {
                            LinkInfo out = new LinkInfo(le, j);
                            for (LinkInfo in : le.getLinks()[j]) {
                                drawLink(g, out, in);
                            }
                        }
                    }
                }
            }
            for (int i = entities.size() - 1; i >= 0; --i) {
                LogicEntity temp = entities.get(i);
                drawNode(g, temp, new Rectangle2D.Double(temp.getX(), temp.getY(), temp.getWidth(), temp.getHeight()));
            }
        }
    }

    private void drawNode(final Graphics2D g, LogicEntity entity, Rectangle2D.Double pos) {
        Game.graphics().renderEntity(g, entity);
        int numberOfInputs = entity.getNode().getInputs().size();
        for (int i = 0; i < numberOfInputs; ++i)
            Game.graphics().renderImage(g, ImageLoader.getImage("input/empty", 25),
                    pos.getMinX(), pos.getMinY() + pos.height*(i+1)/(numberOfInputs+1));
        int numberOfOutputs = entity.getNode().getOutputs().size();
        for (int i = 0; i < numberOfOutputs; ++i)
            Game.graphics().renderImage(g, ImageLoader.getImage("output/empty", 25),
                    pos.getMaxX()-8, pos.getMinY()+pos.height*(i+1)/(numberOfOutputs+1));
        if (displayBoundingBoxes) {
            g.setColor(new Color(240, 240, 240, 100));
            Game.graphics().renderShape(g, pos);
            g.setColor(new Color(60, 60, 60, 100));
            Game.graphics().renderOutline(g, entity.getMoveBoundingBox(), new BasicStroke(3, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
            g.setColor(new Color(140, 140, 140, 100));
            Game.graphics().renderShape(g, entity.getResizeBoundingBox());
            g.setColor(new Color(240, 0, 0, 100));
            Game.graphics().renderShape(g, entity.getCloseBoundingBox());
            Game.graphics().renderText(g, entity.getNode().getTag() + " node", pos.getMinX() + 5, pos.getMinY() + 5);
            g.setColor(Color.WHITE);
            Game.graphics().renderText(g, "X", pos.getMaxX() - 3, pos.getMinY() + 4);
            g.setColor(new Color(240, 240, 0, 100));
            for (Ellipse2D circle : entity.getInputPortsBoundingBoxes())
                Game.graphics().renderShape(g, circle);
            for (Ellipse2D circle : entity.getOutputPortsBoundingBoxes())
                Game.graphics().renderShape(g, circle);
        }
    }

    private void drawLink(final Graphics2D g, LinkInfo start, LinkInfo end) {
        Line2D line = new Line2D.Double(
                start.entity().getOutputPortsBoundingBoxes()[start.number()].getCenterX(),
                start.entity().getOutputPortsBoundingBoxes()[start.number()].getCenterY(),
                end.entity().getInputPortsBoundingBoxes()[end.number()].getCenterX(),
                end.entity().getInputPortsBoundingBoxes()[end.number()].getCenterY());
        int numOfDots = (int) (line.getP1().distance(line.getP2()) / 5.0);
        Ellipse2D.Double dot;
        for (int i = 1; i < numOfDots; ++i) {
            dot = new Ellipse2D.Double(line.getX1() + (line.getX2() - line.getX1()) / numOfDots * i - 3,
                    line.getY1() + (line.getY2() - line.getY1()) / numOfDots * i - 3, 6, 6);
            Game.graphics().renderShape(g, dot);
        }
    }

    public void drawCompactLayout() {
        
    }

    public void toggleDisplayBoundingBoxes() {
        displayBoundingBoxes = !displayBoundingBoxes;
    }
}
