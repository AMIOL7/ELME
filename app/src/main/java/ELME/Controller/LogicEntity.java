package ELME.Controller;

import ELME.Model.Node;
import de.gurkenlabs.litiengine.entities.Entity;

import java.awt.geom.Rectangle2D;

public class LogicEntity extends Entity {
    Node node;
    Rectangle2D.Double moveBoundingBox, resizeBoundingBox;
    public LogicEntity(Node node, Rectangle2D.Double pos) {
        this.node = node;
        setLocation(pos.getX(), pos.getY());
        setSize(pos.getWidth(), pos.getHeight());
        moveBoundingBox = new Rectangle2D.Double(pos.getMinX(), pos.getMinY(), pos.getWidth(), 8);
        resizeBoundingBox = new Rectangle2D.Double(pos.getMaxX()-5, pos.getMaxY()-5, 5, 5);
    }

    public void KeepAttached(Rectangle2D.Double pos) {
        moveBoundingBox = new Rectangle2D.Double(pos.getMinX(), pos.getMinY(), pos.getWidth(), 8);
        resizeBoundingBox = new Rectangle2D.Double(pos.getMaxX()-5, pos.getMaxY()-5, 5, 5);
    }

    public Rectangle2D.Double getMoveBoundingBox() {
        return moveBoundingBox;
    }

    public void setMoveBoundingBox(Rectangle2D.Double moveBoundingBox) {
        this.moveBoundingBox = moveBoundingBox;
    }

    public Rectangle2D.Double getResizeBoundingBox() {
        return resizeBoundingBox;
    }

    public void setResizeBoundingBox(Rectangle2D.Double resizeBoundingBox) {
        this.resizeBoundingBox = resizeBoundingBox;
    }
}
