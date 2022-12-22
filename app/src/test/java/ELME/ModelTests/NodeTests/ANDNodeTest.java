package ELME.ModelTests.NodeTests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import ELME.Model.Nodes.ANDNode;
import java.util.Optional;

/**
 *
 * @author vismate
 */
public class ANDNodeTest {

    @Test
    void create() {
        ANDNode and1 = new ANDNode();

        assertEquals(and1.getInputs().size(), 2);
        assertEquals(and1.getOutputs().size(), 1);
        assertEquals(and1.getTag(), "AND");
    }

    @Test
    void evaluation() {
    	//Checking for all true inputs
        ANDNode and1 = new ANDNode();
        ANDNode and2 = new ANDNode();
        ANDNode and3 = new ANDNode();

        and3.getInputPort(0).connect(and1.getOutputPort(0));
        and3.getInputPort(1).connect(and2.getOutputPort(0));

        and1.getOutputPort(0).setValue(Optional.of(true));
        and2.getOutputPort(0).setValue(Optional.of(true));
        assertTrue(and3.getInputPort(0).getValue().get());

        and3.evaluate();

        assertTrue(and3.getOutputPort(0).getValue().get());

      //Changing 1 true input to false.

        and1.getOutputPort(0).setValue(Optional.of(false));
        assertFalse(and3.getInputPort(0).getValue().get());
        
        and3.evaluate();
        assertFalse(and3.getOutputPort(0).getValue().get());
    }
}
