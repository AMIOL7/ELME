package Model.Nodes;

/**
 * Implements the logical XOR gate. The gate is true if exactly 1 input is true.
 * 
 * @author Viktor Bicskei
 *
 */
public class XORNode extends Model.Node {
	public XORNode(String tag) {
		super(tag);
	}
	public void evaluate() {
		if(inputs.stream().filter(x -> x.getValue() == true).count() == 1) {
			setOutputs(true);
		}
		else {
			setOutputs(false);
		}
	}
}
