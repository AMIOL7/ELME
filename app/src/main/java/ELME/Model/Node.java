package ELME.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.Stack;

/**
 * A node represents a unit of computation.
 *
 * @author Máté Visnyár
 */
public abstract class Node {

    private String tag;
    protected ArrayList<InputPort> inputs;
    protected ArrayList<OutputPort> outputs;
    protected HashMap<Node, Integer> dependents;

    /**
     * Construct a Node with a tag
     *
     * @param tag Name of node
     */
    public Node(String tag) {
        this.tag = tag;
        this.inputs = new ArrayList<>();
        this.outputs = new ArrayList<>();
        this.dependents = new HashMap<>();
    }

    /**
     * Constructs a built-in Node with specific number of Inputs and Outputs
     *
     * @param tag
     * @param numberOfInputs
     * @param numberOfOutputs
     */
    public Node(String tag, int numberOfInputs, int numberOfOutputs) {
        this(tag);
        createInputs(numberOfInputs);
        createOutputs(numberOfOutputs);
    }

    private void createInputs(int numberOfInputs) {
        for (int i = 1; i <= numberOfInputs; i++) {
            inputs.add(new InputPort(("In" + i), this));
        }
    }

    private void createOutputs(int numberOfOutputs) {
        for (int i = 1; i <= numberOfOutputs; i++) {
            outputs.add(new OutputPort(("Out" + i), this));
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
     * Adds dependent, if not already added.
     *
     * @param dependent dependent
     */
    public void addDependent(Node dependent) {
        if (dependents.computeIfPresent(dependent, (key, val) -> val + 1) == null) {
            dependents.put(dependent, 1);
        }

    }

    /**
     * Removes dependent if it's multiplicity is 1.
     *
     * @param dependent dependent
     */
    public void removeDependent(Node dependent) {
        dependents.computeIfPresent(dependent, (key, val) -> val > 1 ? val - 1 : null);
    }

    /**
     * Evaluates node and updates all dependent nodes in the same manner.
     */
    public void triggerDownlineUpdate() {
        evaluate();
        dependents.keySet().forEach((dependent) -> dependent.triggerDownlineUpdate());
    }

    /**
     * Returns true if node is inside of a cycle
     *
     * @return true if node is in a cycle
     */
    public boolean inCycle() {
        Stack<Node> stack = new Stack<>();
        stack.push(this);

        while (!stack.isEmpty()) {
            for (Node dependent : stack.pop().dependents.keySet()) {
                if (this == dependent) {
                    return true;
                }
                stack.push(dependent);
            }
        }

        return false;
    }

    /**
     * Returns true if node is (either directly or transitively) depends on the
     * result of this node
     *
     * @param node node
     * @return true if node is a depends on this one
     */
    public boolean transitiveDependencyOf(Node node) {
        for (Node d : dependents.keySet()) {
            if (d == node || d.transitiveDependencyOf(node)) {
                return true;
            }

        }

        return false;
    }

    /**
     * Check whether node is a direct dependent of this node.
     *
     * Node A is a direct dependency of Node B if and only if, at least one
     * output port of Node A connects to at least one input port of Node B.
     *
     * @param node node
     * @return true, if node is a direct dependent
     */
    public boolean directDependencyOf(Node node) {
        return dependents.containsKey(node);
    }

    /**
     * Sets the value of all the "OutputPorts" in "outputs"
     *
     * @author Viktor Bicskei
     * @param value
     */
    public void setAllOutputs(Optional<Boolean> value) {
        outputs.stream().forEach(x -> x.setValue(value));
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
