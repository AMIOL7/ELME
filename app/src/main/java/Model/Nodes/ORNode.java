package Model.Nodes;

/**
 * Implements the logical OR gate.
 * 
<<<<<<< HEAD
 * @author Viktor Bicskei
=======
 * @author victh
>>>>>>> branch 'basic-nodes' of https://github.com/AMIOL7/ELME.git
 *
 */

public class ORNode extends Model.Node {
	public ORNode(String tag) {
		super(tag);
	}
	
	public void evaluate() {
		if (inputs.stream().anyMatch(x -> x.getValue() == true)) {
			setOutputs(true);
			//outputs.stream().forEach(x -> x.setValue(true));
		}
		else {
			setOutputs(false);
			//outputs.stream().forEach(x -> x.setValue(false));
		}
			
	}
}
