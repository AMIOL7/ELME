package ELME.Controller;

import ELME.Model.Node;
import ELME.Model.Nodes.*;
import ELME.View.MainScreen;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.Entity;

import java.awt.geom.*;

/**
 * this class stores the information about the in-world positioning of the "node"
 *
 * @author Pap Szabolcs István
 */
public class LogicEntity extends Entity {
    Node node;
    Rectangle2D.Double moveBoundingBox, resizeBoundingBox, closeBoundingBox;
    Ellipse2D.Double[] inputPortsBoundingBoxes, outputPortsBoundingBoxes;

    LinkInfo[] links;
    public LogicEntity(Node node, Rectangle2D.Double pos) {
        this.node = node;
        links = new LinkInfo[node.getInputs().size()];
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
        for (int i = 0; i < outputPortsBoundingBoxes.length; ++i) {
            outputPortsBoundingBoxes[i] = new Ellipse2D.Double(pos.getMaxX()-8, pos.getMinY()+pos.height*(i+1)/(outputPortsBoundingBoxes.length+1), 6, 6);
        }
    }

    public void relocate(double x, double y) {
        setLocation(x, y);
        keepAttached(x, y, getWidth(), getHeight());
    }

    /**
     * this function, aside from the trivial action, ensures there is a minimal size
     * for the entity
     */
    public void resize(double w, double h) {
        double x = getX();
        double y = getY();
        double w_new = Math.max(w-x, 30);
        double h_new = Math.max(h-y, 40);
            setSize(w_new, h_new);
            keepAttached(x, y, w_new, h_new);
    }

    public void addLink(int outputNum, LogicEntity in, int inputNum) {
        if (in == this) return;
        in.links[inputNum] = new LinkInfo(this, outputNum);
        in.node.getInputPort(inputNum).connect(node.getOutputPort(outputNum));

    }

    public void toggleSwitch() {
        System.out.println(node.getOutputPort(0).getValue().get());
        ((ConstantNode) node).toggle();
        System.out.println(node.getOutputPort(0).getValue().get());

    }

    public void removeLink(int inputNum) {
        node.getInputPort(inputNum).disconnect();
        links[inputNum] = null;
    }

    public void removeAllLinks() {
        for (int i = 0; i < links.length; ++i) {
            if (links[i] != null)
                removeLink(i);
        }
        MainScreen s = (MainScreen) (Game.screens().current());
        for (LogicEntity le : s.graphVisuals.getEntities())
            for (int i = 0; i < le.links.length; ++i)
                if (le.links[i] != null)
                    if (le.links[i].entity() == this)
                        le.removeLink(i);
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

    public ConnectionStatus getPortStatus(boolean isInput, int portNumber) {
        if (isInput)
            if (node.getInputs().get(portNumber).getValue().isPresent())
                if (node.getInputs().get(portNumber).getValue().get())
                    return ConnectionStatus.POSITIVE;
                else return ConnectionStatus.NEGATIVE;
            else return ConnectionStatus.DISCONNECTED;
        else
            if (node.getOutputs().get(portNumber).getValue().isPresent())
                if (node.getOutputs().get(portNumber).getValue().get())
                    return ConnectionStatus.POSITIVE;
                else return ConnectionStatus.NEGATIVE;
            else return ConnectionStatus.DISCONNECTED;
    }

    public Node getNode() { return node; }
    public LinkInfo[] getLinks() { return links; }
    public Rectangle2D.Double getMoveBoundingBox() { return moveBoundingBox; }
    public Rectangle2D.Double getResizeBoundingBox() { return resizeBoundingBox; }
    public Rectangle2D.Double getCloseBoundingBox() { return closeBoundingBox; }
    public Ellipse2D.Double[] getInputPortsBoundingBoxes() { return inputPortsBoundingBoxes; }
    public Ellipse2D.Double[] getOutputPortsBoundingBoxes() { return outputPortsBoundingBoxes; }
}
