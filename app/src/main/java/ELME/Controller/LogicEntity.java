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

    public void relocate(double x, double y) {
        setLocation(x, y);
        keepAttached(x, y, getWidth(), getHeight());
    }

    public void resize(double w, double h) {
        double x = getX();
        double y = getY();
        setSize(w-x, h-y);
        keepAttached(x, y, w-x, h-y);
    }

    private void keepAttached(double x, double y, double w, double h) {
        moveBoundingBox = new Rectangle2D.Double(x, y, w, 8);
        resizeBoundingBox = new Rectangle2D.Double(x+w-5, y+h-5, 5, 5);
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

    public void setResizeBoundingBox(Rectangle2D.Double resizeBoundingBox) { this.resizeBoundingBox = resizeBoundingBox; }
}
