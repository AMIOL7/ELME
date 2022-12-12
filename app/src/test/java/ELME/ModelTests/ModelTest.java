package ELME.ModelTests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import ELME.Model.Nodes.*;
import java.util.Optional;

class AppTest {

    @Test
    void ANDNodeConnectTest() {
        ANDNode node1 = new ANDNode();
        ANDNode node2 = new ANDNode();

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
    void inCycleTest() {
        ANDNode n1 = new ANDNode();
        ANDNode n2 = new ANDNode();
        ANDNode n3 = new ANDNode();

        // In the beginning there are no connections, so there are no cycles
        assertFalse(n1.inCycle());
        assertFalse(n2.inCycle());
        assertFalse(n3.inCycle());

        // After chaining one node after another there still should not be a cycle
        n2.getInputPort(0).connect(n1.getOutputPort(0));

        assertFalse(n1.inCycle());
        assertFalse(n2.inCycle());
        assertFalse(n3.inCycle());

        // Chaining in the third should still not result in a cycle
        n3.getInputPort(0).connect(n2.getOutputPort(0));

        assertFalse(n1.inCycle());
        assertFalse(n2.inCycle());
        assertFalse(n3.inCycle());

        // After connecting the third node's output to the first's input all 3 nodes should be in a (the same) cycle
        n1.getInputPort(0).connect(n3.getOutputPort(0));

        assertTrue(n1.inCycle());
        assertTrue(n2.inCycle());
        assertTrue(n3.inCycle());

        // Undiong the previous connection shpuld return us back to a cycle-less state
        n1.getInputPort(0).disconnect();

        assertFalse(n1.inCycle());
        assertFalse(n2.inCycle());
        assertFalse(n3.inCycle());

    }
    
    @Test
    void downlineUpdate() {
        ANDNode and1 = new ANDNode(); // root node, just to have an output port.
        ANDNode and2 = new ANDNode();
        ANDNode and3 = new ANDNode();
        ANDNode and4 = new ANDNode();

        /**
         * '-' one connection
         * '=' two connection (two input ports)
         * this makes: '-=' a split
         * 
         *                   -------= and3 -= and4
         *                  /      /
         * and1(always true) -= and2 
         *                      ^
         *                      | 
         *           trigger update from here
         * 
         * Bad for a logic circuit, good for test down-line update propagation
        **/
        
        and4.getInputPort(0).connect(and3.getOutputPort(0));
        and4.getInputPort(1).connect(and3.getOutputPort(0));
        
        and3.getInputPort(0).connect(and1.getOutputPort(0));
        and3.getInputPort(1).connect(and2.getOutputPort(0));
        
        and2.getInputPort(0).connect(and1.getOutputPort(0));
        and2.getInputPort(1).connect(and1.getOutputPort(0));
       
        and1.getOutputPort(0).setValue(Optional.of(true));
        and2.triggerDownlineUpdate();

        assertTrue(and4.getOutputPort(0).getValue().get());
    }
}
