package ELME.ModelTests;

import ELME.Model.Graph;
import ELME.Model.Node;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import ELME.Model.Nodes.*;
import ELME.Model.OutputPort;
import java.util.ArrayList;
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

    @Test
    void dependency() {
        ANDNode not1 = new ANDNode();
        NOTNode not2 = new NOTNode();
        NOTNode not3 = new NOTNode();

        /**
         * not1 - not2 - not3
         */
        not2.getInputPort(0).connect(not1.getOutputPort(0));
        not3.getInputPort(0).connect(not2.getOutputPort(0));

        assertTrue(not1.directDependencyOf(not2));
        assertTrue(not1.transitiveDependencyOf(not2));

        assertFalse(not1.directDependencyOf(not3));
        assertTrue(not1.transitiveDependencyOf(not3));

        assertFalse(not1.directDependencyOf(not1));
        assertFalse(not1.transitiveDependencyOf(not1));

        assertFalse(not2.directDependencyOf(not1));
        assertFalse(not2.transitiveDependencyOf(not1));
    }

    @Test
    void graphEvaluation() {
        ANDNode and = new ANDNode();
        NOTNode not = new NOTNode();
        OutputPort o1 = new OutputPort("a", null);
        OutputPort o2 = new OutputPort("b", null);

        o1.setValue(Optional.of(true));
        o2.setValue(Optional.of(false));

        and.getInputPort(0).connect(o1);
        and.getInputPort(1).connect(not.getOutputPort(0));

        not.getInputPort(0).connect(o2);

        ArrayList<Node> nodes = new ArrayList<>();
        nodes.add(and);
        nodes.add(not);

        Graph g = new Graph("g", true);
        g.setNodes(nodes);

        g.evaluate();
        assertTrue(g.getFreeOutputPorts().get(0).getValue().get());
    }
}
