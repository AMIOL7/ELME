package Model.Nodes;

import java.util.Optional;

/**
 * Implements the logical OR gate.
 *
 * @author Viktor Bicskei
 *
 */
public class ORNode extends Model.Node {

    public ORNode() {
        super("OR", 2, 1);
    }

    public void evaluateImpl() {
        // SAFETY: It is safe to call .get() because prior to calling this method inputs are tested.
        if (inputs.stream().anyMatch(x -> x.getValue().get() == true)) {
            setAllOutputs(Optional.of(true));
        } else {
            setAllOutputs(Optional.of(false));
        }

    }
}
