package ELME.Model.Nodes;

import java.util.Optional;

public class ONNode extends ELME.Model.Node {

	public  ONNode(){
		super("On",0,1);
	}

	@Override
	protected void evaluateImpl() {
		setAllOutputs(Optional.of(true));		
	}

}
