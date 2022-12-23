package ELME.ModelTests.NodeTests;

import org.junit.jupiter.api.Test;

import ELME.Model.Nodes.ConstantNode;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

public class ConstantNodeTest {

    @Test
    void create() {
        ConstantNode cn = new ConstantNode();

        assertEquals(cn.getInputs().size(), 0);
        assertEquals(cn.getOutputs().size(), 1);
        assertEquals(cn.getTag(), "CONSTANT");
        assertFalse(cn.isActive());
    }
    
    @Test
    void toggle() {
    	ConstantNode cn = new ConstantNode();
        
        assertFalse(cn.isActive());
        
        cn.toggle();

        assertTrue(cn.isActive());
        
        cn.toggle();

        assertFalse(cn.isActive());
        
        cn.toggle();

        assertTrue(cn.isActive());
    }

    @Test
    void setValue() {
    	ConstantNode cn = new ConstantNode();
        
        assertFalse(cn.isActive());
        
        cn.setValue(true);

        assertTrue(cn.isActive());
        
        cn.setValue(true);

        assertTrue(cn.isActive());
        
        cn.setValue(false);

        assertFalse(cn.isActive());
    }
    
    @Test
    void evaluation() {
        ConstantNode cn = new ConstantNode();
        
        cn.evaluate();
        assertFalse(cn.getOutputPort(0).getValue().get());
        
        cn.toggle();

        cn.evaluate();
        assertTrue(cn.getOutputPort(0).getValue().get());
    }
}
