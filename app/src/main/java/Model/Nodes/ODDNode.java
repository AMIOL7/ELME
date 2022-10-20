package Model.Nodes;

/**
 * ODDNode is the implementation of the 2nd type XORNode, where the node is true when
 * the number of true inputs is odd.
 * 
 * @author Viktor Bicskei
 */
public class ODDNode extends Model.Node {
	public ODDNode(String tag) {
		super(tag);
	}
	
	public void evaluate() {
		if (inputs.stream().filter(x -> x.getValue() == true).count()%2 == 0) {
			setOutputs(true);
		}
		else {
			setOutputs(false);
		}
			
	}
}
