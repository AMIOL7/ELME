package Model.Nodes;

/**
 * Implements the logical OR gate.
 * 
 * @author Viktor Bicskei
 *
 */

public class ORNode extends Model.Node {
	
	public ORNode() {
		super("OR",2,1);
	}
	
	public void evaluateImpl() {
		if (inputs.stream().anyMatch(x -> x.getValue() == true)) {
			setAllOutputs(true);
		}
		else {
			setAllOutputs(false);
		}
			
	}
}
