package ELME;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import ELME.Model.Nodes.*;
import java.util.Optional;

class AppTest {

    @Test
    void ANDNodeCreateTest() {
        ANDNode and1 = new ANDNode();

        assertEquals(and1.getInputs().size(), 2);
        assertEquals(and1.getOutputs().size(), 1);
        assertEquals(and1.getTag(), "AND");
    }

    @Test
    void ODDNodeCreateTest() {
        ODDNode odd1 = new ODDNode();

        assertEquals(odd1.getInputs().size(), 3);
        assertEquals(odd1.getOutputs().size(), 1);
        assertEquals(odd1.getTag(), "ODD");
    }

    @Test
    void ORNodeCreateTest() {
        ORNode or1 = new ORNode();

        assertEquals(or1.getInputs().size(), 2);
        assertEquals(or1.getOutputs().size(), 1);
        assertEquals(or1.getTag(), "OR");
    }

    @Test
    void XORNodeCreateTest() {
        XORNode xor1 = new XORNode();

        assertEquals(xor1.getInputs().size(), 2);
        assertEquals(xor1.getOutputs().size(), 1);
        assertEquals(xor1.getTag(), "XOR");
    }

    @Test
    void ANDNodeConnectTest() {
        ANDNode node1 = new ANDNode();
        ANDNode node2 = new ANDNode();

        assertFalse(node1.getInputPort(0).isConnected());
        assertFalse(node1.getInputPort(1).isConnected());
        assertFalse(node2.getInputPort(0).isConnected());
        assertFalse(node2.getInputPort(1).isConnected());

        node2.getInputPort(0).connect(node1.getOutputPort(0));
        assertTrue(node2.getInputPort(0).isConnected());

        node2.getInputPort(0).disconnect();
        assertFalse(node2.getInputPort(0).isConnected());
    }

    @Test
    void ODDNodeConnectTest() {
        ANDNode andNode1 = new ANDNode();
        ANDNode andNode2 = new ANDNode();
        ORNode orNode1 = new ORNode();
        ODDNode oddNode1 = new ODDNode();

        oddNode1.getInputPort(0).connect(andNode1.getOutputPort(0));
        oddNode1.getInputPort(1).connect(andNode2.getOutputPort(0));

        assertFalse(oddNode1.getInputs().stream().allMatch(x -> x.isConnected()));

        oddNode1.getInputPort(2).connect(orNode1.getOutputPort(0));
        assertTrue(oddNode1.getInputs().stream().allMatch(x -> x.isConnected()));
    }

    @Test
    void NodeConnectTest() {
        ANDNode andNode1 = new ANDNode();
        ANDNode andNode2 = new ANDNode();

        assertFalse(andNode1.getInputPort(0).isConnected());
        assertFalse(andNode1.getInputPort(1).isConnected());
        assertFalse(andNode2.getInputPort(0).isConnected());
        assertFalse(andNode2.getInputPort(1).isConnected());

        andNode2.getInputPort(0).connect(andNode1.getOutputPort(0));
        assertTrue(andNode2.getInputPort(0).isConnected());

        andNode2.getInputPort(0).disconnect();
        assertFalse(andNode2.getInputPort(0).isConnected());

        andNode2.getInputPort(0).connect(andNode1.getOutputPort(0));
        assertTrue(andNode2.getInputPort(0).isConnected());

        assertEquals(andNode2.getInputPort(0).getConnectedPort(), andNode1.getOutputPort(0));

        ORNode orNode1 = new ORNode();
        orNode1.getInputPort(0).connect(andNode1.getOutputPort(0));
        orNode1.getInputPort(1).connect(andNode2.getOutputPort(0));

        assertTrue(orNode1.getInputs().stream().allMatch(x -> x.isConnected()));

    }

    @Test
    void NodeNotConnected() {
        ANDNode and1 = new ANDNode();

        assertEquals(and1.getInputPort(0).getValue(), Optional.empty());

    }

    @Test
    void EmptyValue() {
        ANDNode and1 = new ANDNode();
        ANDNode and2 = new ANDNode();

        assertEquals(and1.getInputPort(0).getValue(), Optional.empty());

        and1.getInputPort(0).connect(and2.getOutputPort(0));
        assertEquals(and1.getInputPort(0).getValue(), Optional.empty());
    }

    @Test
    void ANDevaluation() {
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

    }

    @Test
    void ORevaluation() {
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

    @Test
    void ODDevaluation() {
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

    @Test
    void XORevaluation() {
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
