package Model;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Represents a composition of {@link Model.Node Nodes}. Notice that Graph
 * itself inherits from {@link Model.Node Node}, therefore subgraphs (nested
 * graphs) are permitted.
 *
 * @author Máté Visnyár
 */
public class Graph extends Node {

    private ArrayList<Node> nodes;
    private boolean isCollapsable;
    private boolean isCollapsed;

    /**
     * Construct an Graph with a tag
     *
     * @param tag Name of port
     */
    public Graph(String tag) {
        super(tag);
    }

    @Override
    public void evaluate() {
        nodes.forEach(node -> node.evaluate());
    }

}
