package ELME.ModelTests.NodeTests;

import org.junit.jupiter.api.Test;

import ELME.Model.Graph;
import ELME.Model.Node;
import ELME.Model.Nodes.ConstantNode;
import ELME.Model.Nodes.LightNode;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Optional;

public class LightNodeTest {

    @Test
    void create() {
        LightNode light = new LightNode();

        assertEquals(light.getInputs().size(), 1);
        assertEquals(light.getOutputs().size(), 0);
        assertEquals(light.getTag(), "Light");
    }

    @Test
    void evaluation() {
    	ConstantNode cn = new ConstantNode();
    	LightNode light = new LightNode();
    	
        light.getInputPort(0).connect(cn.getOutputPort(0));
        assertFalse(light.isActive());
        
        cn.toggle();
        assertTrue(cn.isActive());
        cn.evaluate();
        light.evaluate();
        
        assertTrue(light.isActive());
        
        cn.toggle();

        cn.evaluate();
        light.evaluate();
        assertFalse(light.isActive());
        
        

    }
}
