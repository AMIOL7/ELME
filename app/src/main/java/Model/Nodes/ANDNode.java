package Model.Nodes;

/**
 * Implements the logical AND gate.
 * 
 * @author Viktor Bicskei
 *
 */
public class ANDNode extends Model.Node {

	
	public ANDNode() {
		super("AND",2,1);
	}

	public void evaluateImpl() {
		if (inputs.stream().allMatch(x -> x.getValue() == true)) {
			setAllOutputs(true);
		}
		else {
			setAllOutputs(false);
		}
	}
}
