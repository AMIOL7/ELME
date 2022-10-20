package Model.Nodes;

/**
 * Implements the logical AND gate.
 * 
 * @author victh
 *
 */
public class ANDNode extends Model.Node {

	public ANDNode(String tag) {
		super(tag);
	}

	public void evaluate() {
		if (inputs.stream().allMatch(x -> x.getValue() == true)) {
			setOutputs(true);
		}
		else {
			setOutputs(false);
		}
	}
}
