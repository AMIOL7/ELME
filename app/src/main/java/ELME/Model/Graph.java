package ELME.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Stack;

/**
 * Represents a composition of {@link ELME.Model.Node Nodes}. Notice that Graph
 * itself inherits from {@link ELME.Model.Node Node}, therefore subgraphs
 * (nested graphs) are permitted.
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
        sortTopologically();
        nodes.forEach((n) -> n.evaluate());
    }

    private void sortTopologically() {
        Stack<Node> stack = new Stack<>();
        HashSet<Node> visited = new HashSet<>();

        for (Node n : nodes) {
            dfsVisit(n, stack, visited);
        }

        nodes.clear();
        while (!stack.isEmpty()) {
            nodes.add(stack.pop());
        }
    }

    private void dfsVisit(Node current, Stack<Node> stack, HashSet<Node> visited) {
        visited.add(current);
        for (Node v : current.dependents.keySet()) {
            if (visited.contains(v)) {
                // cycle found
            } else {
                dfsVisit(v, stack, visited);
            }
        }
        stack.push(current);
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
    public ArrayList<InputPort> getFreeInputPorts() {

        //Creates a row, counting the dependencies of each Node individually
        int[] dependencyCount = new int[nodes.size()];
        Arrays.setAll(dependencyCount, x -> 0);
        ArrayList<Node> sortedNodes = new ArrayList<>();
        int i = 0;
        for (Node node : nodes) {
            for (Node ent : nodes) {
                if (!node.equals(ent)) {
                    if (node.transitiveDependencyOf(ent)) {
                        dependencyCount[i]++;
                    }
                }
            }
            i++;
        }

        int max = -1;

        for (int cur : dependencyCount) {
            max = Math.max(max, cur);
        }
        
        for(i=0;i<dependencyCount.length;i++) {
        	dependencyCount[i]=max-dependencyCount[i];
        }
        
        //Adds the nodes to a new ArrayList by increasing dependencies, thus making it sorted by the dependencies
        for (i = 0; i <= max; i++) {
            for (int j = 0; j < nodes.size(); j++) {
                if (dependencyCount[j] == i) {
                    sortedNodes.add(nodes.get(j));
                }
            }
        }

        //Collects the not connected ports
        ArrayList<InputPort> inputs = new ArrayList<>();
        for (Node node : sortedNodes) {
            for (InputPort port : node.getInputs()) {
                if (!port.isConnected()) {
                    inputs.add(port);
                }
            }
        }

        return inputs;
    }

    /**
     * @return not connected OutputPorts in the order of increasing dependency
     */
    public ArrayList<OutputPort> getFreeOutputPorts() {

        //Creates a row, counting the dependencies of each Node individually
        int[] dependencyCount = new int[nodes.size()];
        Arrays.setAll(dependencyCount, x -> 0);
        ArrayList<Node> sortedNodes = new ArrayList<>();
        int i = 0;
        for (Node node : nodes) {
            for (Node ent : nodes) {
                if (!node.equals(ent)) {
                    if (node.transitiveDependencyOf(ent)) {
                        dependencyCount[i]++;
                    }
                }
            }
            i++;
        }

        int max = -1;

        for (int cur : dependencyCount) {
            max = Math.max(max, cur);
        }

        for(i=0;i<dependencyCount.length;i++) {
        	dependencyCount[i]=max-dependencyCount[i];
        }
        
        //Adds the nodes to a new ArrayList by increasing dependencies, thus making it sorted by the dependencies
        for (i = 0; i <= max; i++) {
            for (int j = 0; j < nodes.size(); j++) {
                if (dependencyCount[j] == i) {
                    sortedNodes.add(nodes.get(j));
                }
            }
        }
        
        ArrayList<OutputPort> outputs = new ArrayList<>();
        for (Node node : sortedNodes) {
            outputs.addAll(node.outputs);
        }

        for (Node node : sortedNodes) {
            for (InputPort port : node.inputs) {
                outputs.remove(port.getConnectedPort());
            }
        }

        return outputs;
    }

    private Exception Exception(String cycle_found_in_node_graph) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
