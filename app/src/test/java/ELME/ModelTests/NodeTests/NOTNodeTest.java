package ELME.ModelTests.NodeTests;

import ELME.Model.Nodes.ANDNode;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import ELME.Model.Nodes.NOTNode;
import java.util.Optional;

/**
 *
 * @author vismate
 */
public class NOTNodeTest {

    @Test
    void create() {
        NOTNode not = new NOTNode();

        assertEquals(not.getInputs().size(), 1);
        assertEquals(not.getOutputs().size(), 1);
        System.out.println(not.getTag());
        assertEquals(not.getTag(), "NOT");
    }

    @Test
    void evaluation() {
        ANDNode and = new ANDNode();
        NOTNode not = new NOTNode();

        not.getInputPort(0).connect(and.getOutputPort(0));
        and.getOutputPort(0).setValue(Optional.of(true));
        assertTrue(and.getOutputPort(0).getValue().get());
        not.evaluate();

        assertFalse(not.getOutputPort(0).getValue().get());

    }
}
