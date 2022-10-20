package Model;

import java.util.ArrayList;

/**
 * Represents a composition of {@link Model.Node Nodes}. Notice that Graph
 * itself inherits from {@link Model.Node Node}, therefore subgraphs (nested
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
        this.isCollapsible = isCollapsible;
    }

    @Override
    public void evaluate() {
        nodes.forEach(node -> node.evaluate());
    }

}
