package ELME.Model.Nodes;

import java.util.Optional;

/**
 * Implements the logical AND gate.
 *
 * @author Viktor Bicskei
 *
 */
public class ANDNode extends ELME.Model.Node {

    public ANDNode() {
        super("AND", 2, 1);
    }

    @Override
    public void evaluateImpl() {
        // SAFETY: It is safe to call .get() because prior to calling this method inputs are tested.
        if (inputs.stream().allMatch(x -> x.getValue().get() == true)) {
            setAllOutputs(Optional.of(true));
        } else {
            setAllOutputs(Optional.of(false));
        }
    }
}
