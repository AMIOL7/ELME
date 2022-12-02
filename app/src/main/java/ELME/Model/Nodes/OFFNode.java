package ELME.Model.Nodes;

import java.util.Optional;

public class OFFNode extends ELME.Model.Node{

	public OFFNode() {
		super("OFF",0,1);
	}

	@Override
	protected void evaluateImpl() {
		setAllOutputs(Optional.of(false));
	}

}
