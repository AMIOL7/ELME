package ELME.ModelTests.NodeTests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import ELME.Model.Nodes.ORNode;
import java.util.Optional;

/**
 *
 * @author vismate
 */
public class ORNodeTest {

    @Test
    void create() {
        ORNode or1 = new ORNode();

        assertEquals(or1.getInputs().size(), 2);
        assertEquals(or1.getOutputs().size(), 1);
        assertEquals(or1.getTag(), "OR");
    }

    @Test
    void evaluation() {
        ORNode or1 = new ORNode();
        ORNode or2 = new ORNode();
        ORNode or3 = new ORNode();

        or3.getInputPort(0).connect(or1.getOutputPort(0));
        or3.getInputPort(1).connect(or2.getOutputPort(0));

        or1.getOutputPort(0).setValue(Optional.of(true));
        assertTrue(or3.getInputPort(0).getValue().get());

        or2.getOutputPort(0).setValue(Optional.of(false));
        assertFalse(or3.getInputPort(1).getValue().get());

        or3.evaluate();
        assertTrue(or3.getOutputPort(0).getValue().get());
    }
}
