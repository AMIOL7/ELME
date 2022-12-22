package ELME.Model.Nodes;

import java.util.Optional;

/**
 * A terminating node that indicates whether it's input true or false.
 * @author Viktor Bicskei 
 */
public class LightNode extends ELME.Model.Node{
	boolean active = false;
	
	public LightNode() {
		super("Light",1,0);
	}

	@Override
	public void evaluate() {
		if(inputs.stream().anyMatch(x -> x.getValue().get() == true)) {
			active = true;
		}
		else {
			active = false;
		}
	}
	
	@Override
	protected void evaluateImpl() {
		
	}

    public boolean isActive() {
        return active;
    }   
}
