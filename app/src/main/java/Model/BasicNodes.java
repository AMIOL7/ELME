package Model;

/**
 * Implementing the Simplest of Gates.
 * 
 * Every XYNode is derived from the abstract class Node.
 * 
 * @author Viktor Bicskei
 *
 */

public class BasicNodes {

	public class ANDNode extends Node{
		
		public ANDNode(String tag) {
			super(tag);
		}

		public void evaluate() {
			if (inputs.stream().allMatch(x -> x.getValue() == true)) {
				outputs.stream().forEach(x -> x.setValue(true));
			}
			else {
				outputs.stream().forEach(x -> x.setValue(false));
			}
		}
	}
	
	public class ORNode extends Node{
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

	/**
	 * XORNode is true when exactly 1 input is true
	 */
	public class XORNode extends Node{
		public XORNode(String tag) {
			super(tag);
		}
		public void evaluate() {
			if(inputs.stream().filter(x -> x.getValue() == true).count() == 1) {
				setOutputs(true);
				//outputs.stream().forEach(x -> x.setValue(true));
			}
			else {
				setOutputs(false);
				//outputs.stream().forEach(x -> x.setValue(false));
			}
		}
	}
	
	/**
	 * ODDNode is the implementation of the 2nd type XORNode, where the node is true when
	 * the number of true inputs is odd.
	 */
	public class ODDNode extends Node{
		public ODDNode(String tag) {
			super(tag);
		}
		
		public void evaluate() {
			if (inputs.stream().filter(x -> x.getValue() == true).count()%2 == 0) {
				setOutputs(true);
				//outputs.stream().forEach(x -> x.setValue(true));
			}
			else {
				setOutputs(false);
				//outputs.stream().forEach(x -> x.setValue(false));
			}
				
		}
	}
	
}
