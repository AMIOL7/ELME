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
import java.util.LinkedList;



/**
 * This class is what remembers and stores graphical information about the layout
 * of custom graphs, both current and saved.
 *
 * @author Pap Szabolcs István
 */

public class GraphLayoutContainer implements Serializable {

    MainScreen screen;
    Graph graph;
    LinkedList<LogicEntity> entities;
    boolean displayBoundingBoxes = true;

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

    /** needed only when placing a new node, otherwise would be redundant */
    public void moveNode(int index, double x, double y) { entities.get(index).relocate(x, y); }

    //public void resizeNode(int index, double x, double y) { entities.get(index).setSize(x, y); }

    public void deleteNode(LogicEntity entity) {
        entity.removeAllLinks();
        entities.remove(entity);
    }

    public void saveLayout() {
        
    }

    public void loadLayout() {
        
    }

    public void drawLayout(final Graphics2D g) throws IOException {
        if (entities.size() > 0) {
            g.setColor(new Color(60, 60, 60, 127));
            for (int i = entities.size() - 1; i >= 0; --i) {
                LogicEntity entity = entities.get(i);
                LinkInfo[] info = entity.getLinks();
                for (int j = 0; j < info.length; ++j)
                    if (info[j] != null)
                        drawLink(g, info[j], entity, j);
            }
            for (int i = entities.size() - 1; i >= 0; --i) {
                LogicEntity temp = entities.get(i);
                drawNode(g, temp, new Rectangle2D.Double(temp.getX(), temp.getY(), temp.getWidth(), temp.getHeight()));
            }
        }
    }

    private void drawNode(final Graphics2D g, LogicEntity entity, Rectangle2D.Double pos) {
        //Game.graphics().renderEntity(g, entity);
        /*if (displayBoundingBoxes) {
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
            g.setColor(new Color(240, 240, 0, 100));*/
        int alpha = displayBoundingBoxes ? 225 : 60;
        g.setColor(new Color(240, 240, 240, alpha));
        Game.graphics().renderShape(g, pos);
        g.setColor(new Color(60, 60, 60, alpha));
        Game.graphics().renderOutline(g, entity.getMoveBoundingBox(), new BasicStroke(3, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
        g.setColor(new Color(140, 140, 140, alpha));
        Game.graphics().renderShape(g, entity.getResizeBoundingBox());
        g.setColor(new Color(240, 0, 0, alpha));
        Game.graphics().renderShape(g, entity.getCloseBoundingBox());
        Game.graphics().renderText(g, entity.getNode().getTag() + " node", pos.getMinX() + 5, pos.getMinY() + 5);
        g.setColor(Color.WHITE);
        Game.graphics().renderText(g, "X", pos.getMaxX() - 3, pos.getMinY() + 4);
        int numberOfInputs = entity.getNode().getInputs().size();
        for (int i = 0; i < numberOfInputs; ++i)
            Game.graphics().renderImage(g, ImageLoader.getImage("input/empty", 25),
                    pos.getMinX(), pos.getMinY() + pos.height*(i+1)/(numberOfInputs+1));
        int numberOfOutputs = entity.getNode().getOutputs().size();
        for (int i = 0; i < numberOfOutputs; ++i)
            Game.graphics().renderImage(g, ImageLoader.getImage("output/empty", 25),
                    pos.getMaxX()-8, pos.getMinY()+pos.height*(i+1)/(numberOfOutputs+1));
        g.setColor(new Color(240, 240, 0, Math.max(alpha - 150, 0)));
            for (Ellipse2D circle : entity.getInputPortsBoundingBoxes())
                Game.graphics().renderShape(g, circle);
            for (Ellipse2D circle : entity.getOutputPortsBoundingBoxes())
                Game.graphics().renderShape(g, circle);
        //}
    }

    private void drawLink(final Graphics2D g, LinkInfo start, LogicEntity endEntity, int endInputNumber) {
        Line2D line = new Line2D.Double(
                start.entity().getOutputPortsBoundingBoxes()[start.number()].getCenterX(),
                start.entity().getOutputPortsBoundingBoxes()[start.number()].getCenterY(),
                endEntity.getInputPortsBoundingBoxes()[endInputNumber].getCenterX(),
                endEntity.getInputPortsBoundingBoxes()[endInputNumber].getCenterY());
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

    public LinkedList<LogicEntity> getEntities() {
        return entities;
    }
}
