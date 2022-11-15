package ELME.Controller;

import ELME.Model.Node;
import de.gurkenlabs.litiengine.entities.Entity;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class LogicEntity extends Entity {
    Node node;
    Rectangle2D.Double moveBoundingBox, resizeBoundingBox, closeBoundingBox;
    Ellipse2D.Double[] inputPortsBoundingBoxes, outputPortsBoundingBoxes;

    public LogicEntity(Node node, Rectangle2D.Double pos) {
        this.node = node;
        setLocation(pos.getX(), pos.getY());
        setSize(pos.getWidth(), pos.getHeight());
        moveBoundingBox = new Rectangle2D.Double(pos.getMinX(), pos.getMinY(), pos.getWidth()-5, 8);
        resizeBoundingBox = new Rectangle2D.Double(pos.getMaxX()-5, pos.getMaxY()-5, 5, 5);
        closeBoundingBox = new Rectangle2D.Double(pos.getMaxX()-5, pos.getMinY(), 5, 5);
        inputPortsBoundingBoxes = new Ellipse2D.Double[node.getInputs().size()];
        for (int i = 0; i < inputPortsBoundingBoxes.length; ++i) {
            inputPortsBoundingBoxes[i] = new Ellipse2D.Double(pos.getMinX()-4, pos.getMinY()+pos.height*(i+1)/(inputPortsBoundingBoxes.length+1), 6, 6);
        }
        outputPortsBoundingBoxes = new Ellipse2D.Double[node.getOutputs().size()];
        for (int i = 0; i < outputPortsBoundingBoxes.length; ++i) {
            outputPortsBoundingBoxes[i] = new Ellipse2D.Double(pos.getMaxX()-4, pos.getMinY()+pos.height*(i+1)/(outputPortsBoundingBoxes.length+1), 6, 6);
        }
    }

    public void relocate(double x, double y) {

        setLocation(x, y);
        keepAttached(x, y, getWidth(), getHeight());
    }

    public void resize(double w, double h) {
        double x = getX();
        double y = getY();
        double w_new = w-x >= 30 ? w-x : 30;
        double h_new = h-y >= 40 ? h-y : 40;
            setSize(w_new, h_new);
            keepAttached(x, y, w_new, h_new);
    }

    private void keepAttached(double x, double y, double w, double h) {
        moveBoundingBox = new Rectangle2D.Double(x, y, w-5, 8);
        resizeBoundingBox = new Rectangle2D.Double(x+w-5, y+h-5, 5, 5);
        closeBoundingBox = new Rectangle2D.Double(x+w-5, y, 5, 5);
        for (int i = 0; i < inputPortsBoundingBoxes.length; ++i) {
            inputPortsBoundingBoxes[i] = new Ellipse2D.Double(x-4, y+h*(i+1)/(inputPortsBoundingBoxes.length+1), 6, 6);
        }
        for (int i = 0; i < outputPortsBoundingBoxes.length; ++i) {
            outputPortsBoundingBoxes[i] = new Ellipse2D.Double(x+w-4, y+h*(i+1)/(outputPortsBoundingBoxes.length+1), 6, 6);
        }
    }

   public Node getNode() { return node; }
    public Rectangle2D.Double getMoveBoundingBox() { return moveBoundingBox; }

    public void setMoveBoundingBox(Rectangle2D.Double moveBoundingBox) { this.moveBoundingBox = moveBoundingBox; }

    public Rectangle2D.Double getResizeBoundingBox() { return resizeBoundingBox; }

    public void setResizeBoundingBox(Rectangle2D.Double resizeBoundingBox) { this.resizeBoundingBox = resizeBoundingBox; }

    public Rectangle2D.Double getCloseBoundingBox() { return closeBoundingBox; }

    public void setCloseBoundingBox(Rectangle2D.Double closeBoundingBox) { this.closeBoundingBox = closeBoundingBox; }

    public Ellipse2D.Double[] getInputPortsBoundingBoxes() {
        return inputPortsBoundingBoxes;
    }

    public void setInputPortsBoundingBoxes(Ellipse2D.Double[] inputPortsBoundingBoxes) {
        this.inputPortsBoundingBoxes = inputPortsBoundingBoxes;
    }

    public Ellipse2D.Double[] getOutputPortsBoundingBoxes() {
        return outputPortsBoundingBoxes;
    }

    public void setOutputPortsBoundingBoxes(Ellipse2D.Double[] outputPortsBoundingBoxes) {
        this.outputPortsBoundingBoxes = outputPortsBoundingBoxes;
    }
}
