package ELME.View;

import ELME.Model.Graph;
import ELME.Model.Node;
import ELME.Model.Nodes.*;
import de.gurkenlabs.litiengine.Game;

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

    Graph graph;
    ArrayList<Rectangle2D.Double> positions;

    /**
     * IMPORTANT: Only add empty graphs, otherwise there will be no position data with which to place nodes,
     * this won't be a problem later on, but for now please look out for that
     */

    public GraphLayoutContainer(Graph graph) {
        this.graph=graph;
        positions = new ArrayList<>();
    }

    public void InsertNode(Node node, Rectangle2D.Double location) {
        graph.getNodes().add(node);
        positions.add(location);
    }

    public void MoveNode(Node node, Point2D.Double location) throws Exception {
        int index = -1;
        for (int i = 0; i < graph.getNodes().size(); ++i)
            if (graph.getNodes().get(i).equals(node)) // Needs .equals() to be implemented in Node
                index = i;
        if (index < 0) throw new Exception("Exception: node not found in graph");
        MoveNode(1, location);
    }
    public void MoveNode(int index, Point2D.Double location) {
        double w = positions.get(index).width;
        double h = positions.get(index).height;
        positions.set(index, new Rectangle2D.Double(location.x, location.y, w, h));
    }

    public void DeleteNode(Node node) {
        
    }

    public void SaveLayout() {
        
    }

    public void LoadLayout() {
        
    }

    public void DrawLayout(final Graphics2D g) {
            for (int i=0; i<graph.getNodes().size(); ++i) {
                DrawNode(graph.getNodes().get(i), positions.get(i), g);
            }
    }

    private void DrawNode(Node node, Rectangle2D.Double pos, final Graphics2D g) {
        //Game.graphics().renderEntity();
        g.setColor(Color.WHITE);
        Game.graphics().renderShape(g, pos);
        g.setColor(Color.RED);
        Game.graphics().renderText(g, node.getTag()+ " node", pos.getCenterX(), pos.getCenterY());
        Image testImage = null;
        try {
            testImage = ImageIO.read(new File("app/assets/port/Input_empty.png"))
                    .getScaledInstance((int)(25* Game.world().camera().getZoom()), (int)(25*Game.world().camera().getZoom()), SCALE_FAST);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int numberOfInputs = node.getInputs().size();
        for (int i = 0; i < numberOfInputs; ++i) {
            Game.graphics().renderImage(g, testImage, pos.getMinX(), pos.getMinY()+pos.height*(i+1)/(numberOfInputs+2));
        }
        try {
            testImage = ImageIO.read(new File("app/assets/port/Output_empty.png"))
                    .getScaledInstance((int)(25* Game.world().camera().getZoom()), (int)(25*Game.world().camera().getZoom()), SCALE_FAST);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int numberOfOutputs = node.getOutputs().size();
        for (int i = 0; i < numberOfOutputs; ++i) {
            Game.graphics().renderImage(g, testImage, pos.getMaxX(), pos.getMinY()+pos.height*(i+1)/(numberOfOutputs+2));
        }
    }
    
    public void DrawCompactLayout() {
        
    }
}
