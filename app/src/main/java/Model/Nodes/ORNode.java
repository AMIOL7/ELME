package Model.Nodes;

/**
 * Implements the logical OR gate.
 * 
 * @author victh
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
