package Model.Nodes;

/**
 * ODDNode is the implementation of the 2nd type XORNode, where the node is true when
 * the number of true inputs is odd.
 * 
 * @author Viktor Bicskei
 */
public class ODDNode extends Model.Node {
	
	public ODDNode() {
		super("ODD",3,1);
	}
	
	public void evaluateImpl() {
		if (inputs.stream().filter(x -> x.getValue() == true).count()%2 == 0) {
			setAllOutputs(true);
		}
		else {
			setAllOutputs(false);
		}
			
	}
}
