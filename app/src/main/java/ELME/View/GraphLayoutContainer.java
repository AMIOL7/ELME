package ELME.View;

import ELME.Controller.ConnectionStatus;
import ELME.Controller.ImageLoader;
import ELME.Controller.LinkInfo;
import ELME.Controller.LogicEntity;
import ELME.Model.Graph;
import ELME.Model.Node;

import ELME.Model.Nodes.*;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.input.Input;

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
            Color linkColor = null;
            for (int i = entities.size() - 1; i >= 0; --i) {
                LogicEntity entity = entities.get(i);
                LinkInfo[] info = entity.getLinks();
                for (int j = 0; j < info.length; ++j) {
                    linkColor = switch (entity.getPortStatus(true, j)) {
                        case DISCONNECTED ->  new Color(85, 85, 85, 160);
                        case POSITIVE -> new Color(100, 255, 60, 160);
                        case NEGATIVE -> new Color(255, 60, 60, 160);
                    };
                    g.setColor(linkColor);
                    if (info[j] != null)
                        drawLink(g, info[j], entity, j);
                }
            }
            for (int i = entities.size() - 1; i >= 0; --i) {
                LogicEntity temp = entities.get(i);
                drawNode(g, temp, new Rectangle2D.Double(temp.getX(), temp.getY(), temp.getWidth(), temp.getHeight()));
            }
        }
    }

    private void drawNode(final Graphics2D g, LogicEntity entity, Rectangle2D.Double pos) {
        int alpha = displayBoundingBoxes ? 225 : 60;
        if (entity.getNode() instanceof LightNode) {
            Color signalColor = switch (entity.getPortStatus(true, 0)) {
                case DISCONNECTED -> new Color(120, 120, 120, 255);
                case POSITIVE -> new Color(100, 255, 60, 255);
                case NEGATIVE -> new Color(255, 60, 60, 255);
            };
            g.setColor(signalColor);
        } else g.setColor(new Color(240, 240, 240, alpha));
        Game.graphics().renderShape(g, pos);
        g.setColor(new Color(60, 60, 60, alpha));
        Game.graphics().renderOutline(g, entity.getMoveBoundingBox(), new BasicStroke(3, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
        g.setColor(new Color(140, 140, 140, alpha));
        Game.graphics().renderShape(g, entity.getResizeBoundingBox());
        g.setColor(new Color(240, 0, 0, alpha));
        Game.graphics().renderShape(g, entity.getCloseBoundingBox());
        g.setColor(new Color(25, 25, 255, alpha));
        Game.graphics().renderText(g, entity.getNode().getTag() + " node", pos.getMinX() + 5, pos.getMinY() + 5);
        g.setColor(Color.WHITE);
        Game.graphics().renderText(g, "X", pos.getMaxX() - 3, pos.getMinY() + 4);
        int numberOfInputs = entity.getNode().getInputs().size();
        String str = "";
        for (int i = 0; i < numberOfInputs; ++i) {
            str = switch (entity.getPortStatus(true, i)) {
                case POSITIVE -> "input/positive";
                case NEGATIVE -> "input/negative";
                case DISCONNECTED -> "input/empty";
            };
            Game.graphics().renderImage(g, ImageLoader.getImage(str, 25),
                    pos.getMinX(), pos.getMinY() + pos.height * (i + 1) / (numberOfInputs + 1));
        }
        int numberOfOutputs = entity.getNode().getOutputs().size();
        for (int i = 0; i < numberOfOutputs; ++i) {
            str = switch (entity.getPortStatus(false, i)) {
                case POSITIVE -> "output/positive";
                case NEGATIVE -> "output/negative";
                case DISCONNECTED -> "output/empty";
            };
            Game.graphics().renderImage(g, ImageLoader.getImage(str, 25),
                    pos.getMaxX() - 8, pos.getMinY() + pos.height * (i + 1) / (numberOfOutputs + 1));
        }
        g.setColor(new Color(45, 75, 225, Math.max(alpha - 150, 0)));
        for (Ellipse2D circle : entity.getInputPortsBoundingBoxes())
            Game.graphics().renderShape(g, circle);
        for (Ellipse2D circle : entity.getOutputPortsBoundingBoxes())
            Game.graphics().renderShape(g, circle);
    }

    private void drawLine(final Graphics2D g, Line2D line) {
        int numOfDots = (int) (line.getP1().distance(line.getP2()) / 5.0);
        Ellipse2D.Double dot;
        for (int i = 1; i < numOfDots; ++i) {
            dot = new Ellipse2D.Double(line.getX1() + (line.getX2() - line.getX1()) / numOfDots * i - 3,
                    line.getY1() + (line.getY2() - line.getY1()) / numOfDots * i - 3, 6, 6);
            Game.graphics().renderShape(g, dot);
        }
    }

    private void drawLink(final Graphics2D g, LinkInfo start, LogicEntity endEntity, int endInputNumber) {
        drawLine(g, new Line2D.Double(
                start.entity().getOutputPortsBoundingBoxes()[start.number()].getCenterX(),
                start.entity().getOutputPortsBoundingBoxes()[start.number()].getCenterY(),
                endEntity.getInputPortsBoundingBoxes()[endInputNumber].getCenterX(),
                endEntity.getInputPortsBoundingBoxes()[endInputNumber].getCenterY()));
    }

    public void drawCompactLayout() {
        
    }

    public void drawDragLink(final Graphics2D g, LogicEntity entity, int portIndex) {
        g.setColor(new Color(127, 127, 127, 127));
        drawLine(g, new Line2D.Double(
                entity.getOutputPortsBoundingBoxes()[portIndex].getCenterX(),
                entity.getOutputPortsBoundingBoxes()[portIndex].getCenterY(),
                Input.mouse().getMapLocation().getX(),
                Input.mouse().getMapLocation().getY()));
    }

    public void toggleDisplayBoundingBoxes() {
        displayBoundingBoxes = !displayBoundingBoxes;
    }

    public LinkedList<LogicEntity> getEntities() {
        return entities;
    }

}
