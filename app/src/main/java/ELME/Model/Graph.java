package ELME.Model;

import java.util.ArrayList;

/**
 * Represents a composition of {@link ELME.Model.Node Nodes}. Notice that Graph
 * itself inherits from {@link ELME.Model.Node Node}, therefore subgraphs (nested
 * graphs) are permitted.
 *
 * @author Máté Visnyár
 */
public class Graph extends Node {

    private ArrayList<Node> nodes;
    private final boolean isCollapsible;
    private boolean isCollapsed;

    /**
     * Construct an Graph with a tag and whether the graph is collapsible
     *
     * @param tag Name of port
     * @param isCollapsible Whether graph should be collapsible
     */
    public Graph(String tag, boolean isCollapsible) {
        super(tag);
        nodes = new ArrayList<>(); //##//
        this.isCollapsible = isCollapsible;
    }

    @Override
    public void evaluateImpl() {
        nodes.forEach(node -> node.evaluate());
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<Node> nodes) {
        this.nodes = nodes;
    }

    public boolean isIsCollapsed() {
        return isCollapsed;
    }

    public void setIsCollapsed(boolean isCollapsed) {
        this.isCollapsed = isCollapsed;
    }

    
}
