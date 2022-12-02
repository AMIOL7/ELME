package ELME.Model.Nodes;

import java.util.Optional;

public class LightNode extends ELME.Model.Node{
	boolean active = false;
	
	public LightNode() {
		super("Light",1,0);
	}

	@Override
	public void evaluate() {
		if(inputs.stream().anyMatch(x -> x.getValue() == Optional.of(true))) {
			active = true;
		}
		else {
			active = false;
		}
	}
	
	@Override
	protected void evaluateImpl() {
		
	}

}
