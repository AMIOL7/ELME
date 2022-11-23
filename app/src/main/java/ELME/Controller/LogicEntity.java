package ELME.Controller;

import ELME.Model.Node;
import de.gurkenlabs.litiengine.entities.Entity;

import java.awt.geom.*;
import java.util.ArrayList;

public class LogicEntity extends Entity {
    Node node;
    Rectangle2D.Double moveBoundingBox, resizeBoundingBox, closeBoundingBox;
    Ellipse2D.Double[] inputPortsBoundingBoxes, outputPortsBoundingBoxes;

    ArrayList<LinkInfo>[] links;
    public LogicEntity(Node node, Rectangle2D.Double pos) {
        this.node = node;
        setLocation(pos.getX(), pos.getY());
        setSize(pos.getWidth(), pos.getHeight());
        moveBoundingBox = new Rectangle2D.Double(pos.getMinX(), pos.getMinY(), pos.getWidth()-5, 8);
        resizeBoundingBox = new Rectangle2D.Double(pos.getMaxX()-5, pos.getMaxY()-5, 5, 5);
        closeBoundingBox = new Rectangle2D.Double(pos.getMaxX()-5, pos.getMinY(), 5, 5);
        inputPortsBoundingBoxes = new Ellipse2D.Double[node.getInputs().size()];
        for (int i = 0; i < inputPortsBoundingBoxes.length; ++i) {
            inputPortsBoundingBoxes[i] = new Ellipse2D.Double(pos.getMinX(), pos.getMinY()+pos.height*(i+1)/(inputPortsBoundingBoxes.length+1), 6, 6);
        }
        outputPortsBoundingBoxes = new Ellipse2D.Double[node.getOutputs().size()];
        links = new ArrayList[node.getOutputs().size()];
        for (int i = 0; i < outputPortsBoundingBoxes.length; ++i) {
            outputPortsBoundingBoxes[i] = new Ellipse2D.Double(pos.getMaxX()-8, pos.getMinY()+pos.height*(i+1)/(outputPortsBoundingBoxes.length+1), 6, 6);
            //links[i] = new ArrayList<>();
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

    public void addLink(int outputNum, LogicEntity into, int inputNum) {
        if (into == this) return;
        if (links[outputNum] == null)
            links[outputNum] = new ArrayList<>();
        links[outputNum].add(new LinkInfo(into, inputNum));
        into.node.getInputs().get(inputNum).connect(node.getOutputs().get(outputNum));
    }

    public void removeLink(int inputNum) {
        node.getInputs().get(inputNum).disconnect();
    }

    public void removeAllLinks() {

    }

    private void keepAttached(double x, double y, double w, double h) {
        moveBoundingBox = new Rectangle2D.Double(x, y, w-5, 8);
        resizeBoundingBox = new Rectangle2D.Double(x+w-5, y+h-5, 5, 5);
        closeBoundingBox = new Rectangle2D.Double(x+w-5, y, 5, 5);
        for (int i = 0; i < inputPortsBoundingBoxes.length; ++i) {
            inputPortsBoundingBoxes[i] = new Ellipse2D.Double(x, y+h*(i+1)/(inputPortsBoundingBoxes.length+1), 8, 8);
        }
        for (int i = 0; i < outputPortsBoundingBoxes.length; ++i) {
            outputPortsBoundingBoxes[i] = new Ellipse2D.Double(x+w-8, y+h*(i+1)/(outputPortsBoundingBoxes.length+1), 8, 8);
        }
    }

    public Node getNode() { return node; }
    public ArrayList<LinkInfo>[] getLinks() { return links; }
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
