package ELME.ModelTests.NodeTests;

import ELME.Model.Nodes.ANDNode;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import ELME.Model.Nodes.XORNode;
import java.util.Optional;

/**
 *
 * @author vismate
 */
public class XORNodeTest {

    @Test
    void create() {
        XORNode xor1 = new XORNode();

        assertEquals(xor1.getInputs().size(), 2);
        assertEquals(xor1.getOutputs().size(), 1);
        assertEquals(xor1.getTag(), "XOR");
    }

    @Test
    void evaluation() {
        ANDNode and1 = new ANDNode();
        ANDNode and2 = new ANDNode();
        XORNode xor1 = new XORNode();

        xor1.getInputPort(0).connect(and1.getOutputPort(0));
        xor1.getInputPort(1).connect(and2.getOutputPort(0));

        and1.getOutputPort(0).setValue(Optional.of(false));
        and2.getOutputPort(0).setValue(Optional.of(false));

        xor1.evaluate();
        assertFalse(xor1.getOutputPort(0).getValue().get());

        and1.getOutputPort(0).setValue(Optional.of(true));
        xor1.evaluate();
        assertTrue(xor1.getOutputPort(0).getValue().get());

        and2.getOutputPort(0).setValue(Optional.of(true));
        xor1.evaluate();
        assertFalse(xor1.getOutputPort(0).getValue().get());

    }

}
