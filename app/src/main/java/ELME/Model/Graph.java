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
        this.isCollapsible = isCollapsible;
        this.nodes = new ArrayList<>();
        this.isCollapsed = false;
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

    public boolean isCollapsed() {
        return isCollapsed;
    }

    public void setIsCollapsed(boolean isCollapsed) {
        this.isCollapsed = isCollapsed;
    }

    /**
     * @return not connected InputPorts
     */
    public ArrayList<InputPort> getInputPorts(){
    	ArrayList<InputPort> inputs = new ArrayList<>();
    	for(Node ent:nodes) {
    		for(InputPort port:ent.inputs) {
    			if(!port.isConnected()) {
    				inputs.add(port);
    			}
    		}
    	}
    	
    	return inputs;
    }
    
    /**
     * @return not connected OutputPorts
     */
    public ArrayList<OutputPort> getOutputPorts(){
    	ArrayList<OutputPort> outputs = new ArrayList<>();
    	for(Node ent:nodes) {
    		outputs.addAll(ent.outputs);
    	}
    	
    	for(Node ent:nodes) {
    		for(InputPort input:ent.inputs) {
    			outputs.remove(input.getConnectedPort());
    		}
    	}
    	
    	return outputs;
    }
    
}
