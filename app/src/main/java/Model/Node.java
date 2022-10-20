package Model;

import java.util.ArrayList;

/**
 * A node represents a unit of computation.
 *
 * @author Máté Visnyár
 */
public abstract class Node {

    private String tag;
    protected ArrayList<InputPort> inputs;
    protected ArrayList<OutputPort> outputs;

    /**
     * Construct a Node with a tag
     *
     * @param tag Name of node
     */
    public Node(String tag) {
        this.tag = tag;
        this.inputs = new ArrayList<>();
        this.outputs = new ArrayList<>();
    }

    /**
     * Evaluates the node and updates the value of
     * {@link Model.OutputPort OutputPorts}.
     */
    public abstract void evaluate();
    
    /**
     * Sets the value of all the "OutputPorts" in "outputs"
     * @author Viktor Bicskei
     * @param value
     */
    public void setOutputs(boolean value) {
    	outputs.stream().forEach(x -> x.setValue(value));
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

}
