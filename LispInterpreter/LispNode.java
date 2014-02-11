
public class LispNode {
	 LispNode left;
	 LispNode right;
	 String value;
	 int counter = 0;
	 public LispNode(String value){
		 this.left = null;
		 this.right = null;
		 this.value = value;
	 }
	 
	 public enum NodeType {RIGHT,LEFT,DUMMYROOT};
	  LispNode root;
	public void initLispBinTree(){
		root = null;
	}
	
	public LispNode readTree(LispNode node){
		//To be accessed by Eval
		return node;
	}
	
	public void deleteTree(LispNode node){
		//After Eval is done, Delete the tree
	}

	public void insert(LispNode node, String value,NodeType place) {
	    if (node==null) {
	      node = new LispNode(value);
	    }
	    else {
	    	switch(place){
	    	case LEFT:
	    		node.left = new LispNode(value);
	    		break;
	    	case RIGHT:
	    		node.right = new LispNode(value);
	    		break;
	    	case DUMMYROOT:
	    		if(!node.value.equals("Root")){ 
	    			node.value = value;
	    			}
	    		else{
	    		node = new LispNode(value);}
	    		break;
	    	default:
	    		System.out.println("Not a valid Node");
	    	}
	    }
	    	
	  } 
	
	/**
	 Prints the node values in the "inorder" order.
	 Uses a recursive helper to do the traversal.
	*/

	public void printTree(LispNode node) {
		 if (node == null) {return;}
	 System.out.print(node.value + "  ");
	 printTree(node.left);
	 printTree(node.right);
	} 
}
