package ELME.ModelTests.NodeTests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import ELME.Model.Nodes.ODDNode;
import ELME.Model.Nodes.ANDNode;
import ELME.Model.Nodes.ORNode;
import java.util.Optional;

/**
 *
 * @author vismate
 */
public class ODDNodeTest {

    @Test
    void create() {
        ODDNode odd1 = new ODDNode();

        assertEquals(odd1.getInputs().size(), 3);
        assertEquals(odd1.getOutputs().size(), 1);
        assertEquals(odd1.getTag(), "ODD");
    }

    @Test
    void evaluation() {
        ANDNode and1 = new ANDNode();
        ANDNode and2 = new ANDNode();
        ORNode or1 = new ORNode();
        ODDNode odd1 = new ODDNode();

        odd1.getInputPort(0).connect(and1.getOutputPort(0));
        odd1.getInputPort(1).connect(and2.getOutputPort(0));
        odd1.getInputPort(2).connect(or1.getOutputPort(0));

        and1.getOutputPort(0).setValue(Optional.of(false));
        and2.getOutputPort(0).setValue(Optional.of(false));
        or1.getOutputPort(0).setValue(Optional.of(false));

        odd1.evaluate();
        assertFalse(odd1.getOutputPort(0).getValue().get());

        and1.getOutputPort(0).setValue(Optional.of(true));
        odd1.evaluate();
        assertTrue(odd1.getOutputPort(0).getValue().get());

        and2.getOutputPort(0).setValue(Optional.of(true));
        odd1.evaluate();
        assertFalse(odd1.getOutputPort(0).getValue().get());

        or1.getOutputPort(0).setValue(Optional.of(true));
        odd1.evaluate();
        assertTrue(odd1.getOutputPort(0).getValue().get());

    }
}
