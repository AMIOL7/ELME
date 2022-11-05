package ELME.Model.Nodes;

import java.util.Optional;

/**
 * ODDNode is the implementation of the 2nd type XORNode, where the node is true
 * when the number of true inputs is odd.
 *
 * @author Viktor Bicskei
 */
public class ODDNode extends ELME.Model.Node {

    public ODDNode() {
        super("ODD", 3, 1);
    }

    public void evaluateImpl() {
        // SAFETY: It is safe to call .get() because prior to calling this method inputs are tested.
        if (inputs.stream().filter(x -> x.getValue().get() == true).count() % 2 == 0) {
            setAllOutputs(Optional.of(true));
        } else {
            setAllOutputs(Optional.of(false));
        }

    }
}
