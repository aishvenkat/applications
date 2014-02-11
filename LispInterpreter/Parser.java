import java.util.*;

public class Parser {
Boolean hasRight = false;
Boolean hasLeft = false;
Boolean foundSpace = false;
Boolean wasLiteral = false;
Boolean foundDot = false;
int dotCounter = 0;
LispNode root; 
LispNode currentNode;
String val = "SExpression";
int parenCounter = 0;
int counter = 0;
List<LispNode> currentList = new ArrayList<LispNode>();
public Parser (){
	this.root = new LispNode("Root");
	this.currentNode = this.root;
}
public Parser initTree(){
	Parser p = new Parser();
	currentNode = this.root;
	return p;
}

public void getVal(){
	if((currentNode.value.equals("Root"))){
		val = "Root";
	}
	else{
		val = "SExpression";
	}
}
LispNode current = null;

public LispNode addNode(Token t, Parser p) throws LispException{
	
	switch(t.tokenType){
	case TK_LPAREN:
		dotCounter = 0;
		getVal();
		currentNode.insert(currentNode, val, LispNode.NodeType.DUMMYROOT);
		currentNode.insert(currentNode, "NIL", LispNode.NodeType.RIGHT);
		if(!currentNode.value.equals("Root")){
			if(foundDot){
				foundDot = false;
				currentList.add(parenCounter++,currentNode.right);
				
			}
			else{
				currentNode.insert(currentNode, "SExpression", LispNode.NodeType.LEFT);
				currentList.add(parenCounter++,currentNode.right);
				currentNode = currentNode.left;
			}
			currentNode.insert(currentNode, "NIL" , LispNode.NodeType.RIGHT);
		}
		if(foundSpace) foundSpace = false;
		wasLiteral = false;
		break;
	case TK_SPACE:
		foundSpace = true;
		break;
	case TK_RPAREN:
		if((currentList.size()> 0) && (parenCounter > 0))
			currentNode = currentList.get(--parenCounter);
		break;
	case TK_LIT_ATOM:
	case TK_NUM_ATOM:
		if((!foundDot) && (foundSpace)&&(wasLiteral)){
			currentNode.insert(currentNode, "SExpression" , LispNode.NodeType.DUMMYROOT);
			currentNode.insert(currentNode, "NIL" , LispNode.NodeType.RIGHT);
		}
		if(foundDot){
			if(dotCounter > 1) throw new LispException("Invalid Sexpression");
			if(currentList.size() == 0) { throw new LispException("Invalid Function Name");}
			currentNode = currentList.get((parenCounter));
			currentNode.insert(currentNode, t.tokenLit.trim() , LispNode.NodeType.RIGHT);
			foundDot = false;
		}
		else{
				currentNode.insert(currentNode, t.tokenLit.trim() , LispNode.NodeType.LEFT);
				if(parenCounter > 0){
				currentList.add((parenCounter),currentNode);
				}
				currentNode = currentNode.right;
			}
		wasLiteral = true;
		break;
	case TK_DOT:
		foundSpace = false;
		foundDot = true;
		dotCounter++;
		if((currentList.size()> 0) && (parenCounter > 0)){
		}
			
		break;
	}
	
	return root;
}
public LispNode addNode3(Token t, Parser p){
	
	switch(t.tokenType){
	case TK_LPAREN:
		getVal();
    	currentNode.insert(currentNode, val , LispNode.NodeType.DUMMYROOT);
     	currentNode.insert(currentNode, "NIL" , LispNode.NodeType.RIGHT);
		/*Storing state for new parenthesis */  
		if(!currentNode.value.equals("Root")){
			if(foundDot){
				 currentNode.insert(currentNode, "Sexpression" , LispNode.NodeType.RIGHT);
				  
				  currentNode = currentNode.right;
				  counter++;
				  foundDot = false;
			}
			else{
			 currentNode.insert(currentNode, "Sexpression" , LispNode.NodeType.LEFT);
			  currentNode.insert(currentNode, "NIL" , LispNode.NodeType.RIGHT);
			  current = currentNode;
			  currentList.add(parenCounter,currentNode.right);
			  parenCounter++;
			  currentNode = currentNode.left;
			  
			  counter++;
			}
			  currentNode.insert(currentNode, "NIL" , LispNode.NodeType.RIGHT);
			  counter++;
		}
		
		if(foundSpace) foundSpace = false;
		wasLiteral = false;
		break;
	case TK_SPACE:
		foundSpace = true;
		break;
	case TK_RPAREN:
		if(currentList.size()> 0)
		currentNode = currentList.remove(parenCounter--);
		foundSpace = false;
		wasLiteral = false;
		break;
	case TK_LIT_ATOM:
	case TK_NUM_ATOM:
		if(foundSpace && wasLiteral){
			currentNode.insert(currentNode, "SExpression" , LispNode.NodeType.DUMMYROOT);
			currentNode.insert(currentNode, "NIL" , LispNode.NodeType.RIGHT);
			counter++;
		}
		if(foundDot){
		currentNode.insert(currentNode, t.tokenLit , LispNode.NodeType.RIGHT);
		foundDot = false;
		}
		else{
			currentNode.insert(currentNode, t.tokenLit , LispNode.NodeType.LEFT);
			current = currentNode;
			currentList.add(parenCounter,current);
			currentNode = currentNode.right;
		}
		wasLiteral = true;
		break;
	case TK_DOT:
		foundSpace = false;
		foundDot = true;
		break;
	}
	return root;
}

public void printTree(LispNode current){
	current.printTree(current);
	}
}
