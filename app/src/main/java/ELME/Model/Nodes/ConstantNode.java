package ELME.Model.Nodes;

import ELME.Model.Node;
import java.util.Optional;

/**
 * Toggle-able constant output node
 *
 * @author vismate
 */
public class ConstantNode extends Node {

    private boolean active;

    public ConstantNode() {
        super("CONSTANT", 0, 1);
        this.active = false;
    }

    @Override
    protected void evaluateImpl() {
        this.setAllOutputs(Optional.of(this.active));
    }

    public void setValue(boolean value) {
        this.active = value;
    }

    /**
     * Toggles value. Turns active into inactive and vice versa
     */
    public void toggle() {
        this.active = !this.active;
    }
}
