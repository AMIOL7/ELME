package ELME.Model.Nodes;

import java.util.Optional;

/**
 * @author vismate
 */
public class NOTNode extends ELME.Model.Node {

    public NOTNode() {
        super("NOT", 1, 1);
    }

    public void evaluateImpl() {
        // SAFETY: It is safe to call .get() because prior to calling this method inputs are tested.
        outputs.get(0).setValue(Optional.of(!inputs.get(0).getValue().get()));
    }
}
