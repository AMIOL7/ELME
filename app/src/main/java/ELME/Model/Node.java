package ELME.Model;

import java.util.ArrayList;
import java.util.Optional;

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
     * Constructs a built-in Node with specific number of Inputs and Outputs
     *
     * @param tag
     * @param numberOfInputs
     * @param numberOfOutputs
     */
    public Node(String tag, int numberOfInputs, int numberOfOutputs) {
        this.tag = tag;

        this.inputs = new ArrayList<>();
        createInputs(numberOfInputs);

        this.outputs = new ArrayList<>();
        createOutputs(numberOfOutputs);

    }

    private void createInputs(int numberOfInputs) {
        for (int i = 1; i <= numberOfInputs; i++) {
            inputs.add(new InputPort(("In" + i)));
        }
    }

    private void createOutputs(int numberOfOutputs) {
        for (int i = 1; i <= numberOfOutputs; i++) {
            outputs.add(new OutputPort(("Out" + i)));
        }
    }

    /**
     *
     * Evaluates the node and updates the value of
     * {@link ELME.Model.OutputPort OutputPorts}.
     */
    public void evaluate() {
        if (inputs.stream().anyMatch(x -> x.getValue().isEmpty())) {
            setAllOutputs(Optional.empty());
        } else {
            evaluateImpl();
        }
    }

    /**
     * Evaluates the node and updates the value of
     * {@link ELME.Model.OutputPort OutputPorts}.
     */
    protected abstract void evaluateImpl();

    /**
     * Sets the value of all the "OutputPorts" in "outputs"
     *
     * @author Viktor Bicskei
     * @param b
     */
    public void setAllOutputs(Optional<Boolean> b) {
        outputs.stream().forEach(x -> x.setValue(b));
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
    
    public InputPort getInputPort(int num) {
        return inputs.get(num);
    }

    public ArrayList<InputPort> getInputs() {
        return inputs;
    }

    public OutputPort getOutputPort(int num) {
        return outputs.get(num);
    }

    public ArrayList<OutputPort> getOutputs() {
        return outputs;
    }

    public void setInputs(ArrayList<InputPort> inputs) {
        this.inputs = inputs;
    }

    public void setOutputs(ArrayList<OutputPort> outputs) {
        this.outputs = outputs;
    }

}
