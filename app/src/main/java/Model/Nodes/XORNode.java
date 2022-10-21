package Model.Nodes;

/**
 * Implements the logical XOR gate. The gate is true if exactly 1 input is true.
 * 
 * @author Viktor Bicskei
 *
 */
public class XORNode extends Model.Node {
	
	public XORNode() {
		super("XOR",2,1);
	}
	public void evaluateImpl() {
		if(inputs.stream().filter(x -> x.getValue() == true).count() == 1) {
			setAllOutputs(true);
		}
		else {
			setAllOutputs(false);
		}
	}
}
