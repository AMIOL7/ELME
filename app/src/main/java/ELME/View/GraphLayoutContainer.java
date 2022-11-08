package ELME.View;

import ELME.Model.Graph;
import ELME.Model.Node;
import ELME.Model.Nodes.ANDNode;
import de.gurkenlabs.litiengine.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
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
    }

    public void InsertNode(Node node, Point2D location) {
        
    }

    public void DeleteNode(Node node) {
        
    }

    public void SaveLayout() {
        
    }

    public void LoadLayout() {
        
    }

    public void DrawLayout(final Graphics2D g) {
        //for (int i=0; i<graph.getNodes().size(); ++i) { // NEED getNodes()
        //    DrawNode(graph.getNodes().get(i), positions.get(i), g); // NEED getNodes()
        //}
        DrawNode(new ANDNode(),new Rectangle2D.Double(50,50,10,10), g);
    }

    private void DrawNode(Node node, Rectangle2D.Double pos, final Graphics2D g) {
        //Game.graphics().renderEntity();
        g.setColor(Color.WHITE);
        Game.graphics().renderShape(g, pos);
        Image testImage = null;
        try {
            testImage = ImageIO.read(new File("app/assets/port/Input_empty.png"))
                    .getScaledInstance((int)(25* Game.world().camera().getZoom()), (int)(25*Game.world().camera().getZoom()), SCALE_FAST);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Game.graphics().renderImage(g, testImage, pos.getMinX(), pos.getCenterY()+pos.getHeight()/4);
        Game.graphics().renderImage(g, testImage, pos.getMinX(), pos.getCenterY()-pos.getHeight()/4);
    }
    
    public void DrawCompactLayout() {
        
    }
}
