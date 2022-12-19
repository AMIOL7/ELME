package ELME.Model;

import java.util.ArrayList;
import java.util.Arrays;

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
     * @return not connected InputPorts in the order of increasing dependency
     */
    public ArrayList<InputPort> getFreeInputPorts(){
    	
    	
    	//Creates a row, counting the dependencies of each Node individually
    	int[] dependencyCount = new int[nodes.size()];
    	Arrays.setAll(dependencyCount, x -> 0);
        ArrayList<Node> sortedNodes = new ArrayList<>();
    	int i=0;
        for(Node node:nodes) {
        	for(Node ent:nodes) {
        		if(!node.equals(ent)) {
        			if(node.transitiveDependencyOf(ent)) {
        				dependencyCount[i]++;
        			}
        		}
        	}
        	i++;
        }
        
        int max = -1;
        
        for(int cur:dependencyCount) {
        	max = Math.max(max, cur);
        }
        
        //Adds the nodes to a new ArrayList by increasing dependencies, thus making it sorted by the dependencies
        for(i=0;i<=max;i++) {
        	for(int j=0;i<nodes.size();i++) {
        		if(dependencyCount[j]==i) {
        			sortedNodes.add(nodes.get(j));
        		}
        	}
        }
    	
        //Collects the not connected ports
    	ArrayList<InputPort> inputs = new ArrayList<>();
    	for(Node ent:sortedNodes) {
    		for(InputPort port:ent.inputs) {
    			if(!port.isConnected()) {
    				inputs.add(port);
    			}
    		}
    	}
    	
    	return inputs;
    }
    
    /**
     * @return not connected OutputPorts in the order of increasing dependency
     */
    public ArrayList<OutputPort> getFreeOutputPorts(){
    	
    	//Creates a row, counting the dependencies of each Node individually
    	int[] dependencyCount = new int[nodes.size()];
    	Arrays.setAll(dependencyCount, x -> 0);
        ArrayList<Node> sortedNodes = new ArrayList<>();
    	int i=0;
        for(Node node:nodes) {
        	for(Node ent:nodes) {
        		if(!node.equals(ent)) {
        			if(node.transitiveDependencyOf(ent)) {
        				dependencyCount[i]++;
        			}
        		}
        	}
        	i++;
        }
        
        int max = -1;
        
        for(int cur:dependencyCount) {
        	max = Math.max(max, cur);
        }
        
        //Adds the nodes to a new ArrayList by increasing dependencies, thus making it sorted by the dependencies
        for(i=0;i<=max;i++) {
        	for(int j=0;i<nodes.size();i++) {
        		if(dependencyCount[j]==i) {
        			sortedNodes.add(nodes.get(j));
        		}
        	}
        }
    	
    	
    	
    	
    	ArrayList<OutputPort> outputs = new ArrayList<>();
    	for(Node ent:sortedNodes) {
    		outputs.addAll(ent.outputs);
    	}
    	
    	for(Node ent:sortedNodes) {
    		for(InputPort input:ent.inputs) {
    			outputs.remove(input.getConnectedPort());
    		}
    	}
    	
    	return outputs;
    }
    
}
