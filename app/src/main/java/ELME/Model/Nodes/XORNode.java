package ELME.Model.Nodes;

import java.util.Optional;

/**
 * Implements the logical XOR gate. The gate is true if exactly 1 input is true.
 *
 * @author Viktor Bicskei
 *
 */
public class XORNode extends ELME.Model.Node {

    public XORNode() {
        super("XOR", 2, 1);
    }

    public void evaluateImpl() {
        // SAFETY: It is safe to call .get() because prior to calling this method inputs are tested.
        if (inputs.stream().filter(x -> x.getValue().get() == true).count() == 1) {
            setAllOutputs(Optional.of(true));
        } else {
            setAllOutputs(Optional.of(false));
        }
    }
}
