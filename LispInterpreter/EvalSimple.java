import java.util.*;

public class EvalSimple {

	/*SET1: Evaluation rules for QUOTE,CAR,CONS,CDR,EQ */
	/*SET2: Evaluation rules for COND, */
	/*T, NIL, CAR, CDR, CONS, ATOM, EQ, NULL, INT, PLUS, MINUS, TIMES, QUOTIENT, 
	REMAINDER, LESS, GREATER, COND, QUOTE, DEFUN*/
	Map<String,Stack<LispNode> > aList = new HashMap<String,Stack<LispNode>>();
	Map<String,Stack<LispNode> > dList = new HashMap<String,Stack<LispNode>>();
	
	public Boolean isAtom(LispNode exp){
		if(exp == null) return false; 
		if((exp.left == null) && (exp.right == null)){
		//	System.out.println("isAtom"+exp.value.trim());
			return true;
		}
		else return false;
	}
	
	public Boolean isEq(String expValue, String value){
	if(expValue.trim().equals(value)) {
		return true;
		}
		else return false;
	}
	
	public Boolean isInt(LispNode exp){
		try{
	        Integer.parseInt(exp.value.trim());
	    } catch(NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
	
	public Boolean isNull(LispNode exp){
		if(exp.value.equals("NIL")) return true;
		
	    return false;
	}
	
	public LispNode car(LispNode exp) throws LispException{
		try{
		LispNode val = exp.left;
		return val;
		}catch(NullPointerException ne){
			throw new LispException(" Invalid use of DOTTED List");
		}
	}
	
	public LispNode cdr(LispNode exp) throws LispException{
		try{
		LispNode tail = exp.right;
		return tail;
		}catch(NullPointerException ne){
                        throw new LispException(" Too Few Arguments to CDR");
                }
	}
	
	public LispNode cons(LispNode exp1,LispNode exp2){
		
		LispNode consNode = new LispNode("Root");
		consNode.left = exp1;
		consNode.right = exp2;
		return consNode;
	}
	
	public LispNode EQ(LispNode exp1, LispNode exp2){
		if(!((isAtom(exp1)) && (isAtom(exp2)))){
			return (new LispNode("ERROR: Illegal Arguments for EQ - Not Atoms"));
		}
		Boolean result = isEq(exp1.value,exp2.value);
		if(result){
			return (new LispNode("T"));
		}
		return (new LispNode("NIL"));
	}
		
	public LispNode ATOM(LispNode exp1){
		if(! (isAtom(exp1))) {
			return (new LispNode("NIL"));
		}
		else{
			return (new LispNode("T"));
		}
	}
	
	public LispNode INT (LispNode exp) throws LispException{
			if(isInt(exp)){
			return (new LispNode("T"));
			}
			else return (new LispNode("NIL"));
	}
	
	public LispNode NULL (LispNode exp) throws LispException{
			if(isNull(exp)){
			return (new LispNode("T"));
			}
			
			else {
				return (new LispNode("NIL"));
			}
	}

	public Boolean isLess(String int1,String int2){
		if((Integer.parseInt(int1)) < (Integer.parseInt(int2))){
			return true;
		}
		return false;
	}
	
	public Boolean isGreater(String int1,String int2){
		if((Integer.parseInt(int1)) < (Integer.parseInt(int2))){
			return true;
		}
		return false;
	}
	
	public LispNode LESS (LispNode exp1, LispNode exp2)throws LispException{
		if(!((isAtom(exp1)) && (isAtom(exp2)))) {
			throw new LispException("Illegal Arguments for LESS - Not Atoms");
		}
		if(!((isInt(exp1)) && (isInt(exp2)))){
			throw new LispException("Illegal Arguments for LESS - Not Integers");
		}
		if(isLess(exp1.value,exp2.value)){
			return (new LispNode("T"));
		}
		return (new LispNode("NIL"));
	}
	
	public LispNode GREATER (LispNode exp1, LispNode exp2)throws LispException{
		if(!((isAtom(exp1)) && (isAtom(exp2)))) {
			throw new LispException("Illegal Arguments for GREATER - Not Atoms");
		}
		if(!((isInt(exp1)) && (isInt(exp2)))){
			throw new LispException("Illegal Arguments for GREATER - Not Integers");
		}
		if(isGreater(exp1.value,exp2.value)){
			return (new LispNode("T"));
		}
		return (new LispNode("NIL"));
	}

	public Boolean isList(LispNode exp){
		if(isAtom(exp)){
			if(exp.value.equals("NIL")){
				return true;
			}
			else{
			return false;
			}
		}
		else{
			if(isList(exp.right)){
			return true;
			}
			else return false;
		}
	}

	public void printLispNode(LispNode exp){
		LispNode Dummy;
		if(isAtom(exp)){
		System.out.print(exp.value);}
		else{
			System.out.print("(");
			if(isList(exp)){
				if(exp.left.value.equals("SExpression")){
					printLispNode(exp.left);
				}
				else{
				System.out.print(exp.left.value);
				}
				Dummy = exp.right;
				while((Dummy.right != null) && (Dummy.left != null) && !(Dummy.left.value.equals("SExpression"))){
				System.out.print(" "+Dummy.left.value);
				Dummy = Dummy.right;
			}
				if((Dummy.left != null) && (Dummy.left.value.equals("SExpression")))
				{	 System.out.print(" ");
					printLispNode(Dummy.left);
					if((Dummy.right != null) && (Dummy.right.value.equals("SExpression"))){
					 System.out.print(" ");
					printLispNode(Dummy.right);}
				}
			}
			else{
				
				printLispNode(exp.left);
				System.out.print(" . ");
				printLispNode(exp.right);
			}
			System.out.print(")");
		}
	}
	
	public Boolean checkError(LispNode exp,int noOfArgs) throws LispException{
		try{
		switch(noOfArgs){
		case 1: 
			if(cdr(exp).value.equals("NIL")) return true;
			break;
		case 2:
			if(cdr(cdr(exp)).value.equals("NIL")) return true;
			break;
		case 3:
			if(cdr(cdr(cdr(exp))).value.equals("NIL")) return true;
			break;
		case 4:
			if(cdr(cdr(cdr(cdr(exp)))).value.equals("NIL")) return true;
			break;
		}
		
		return false;
		}catch(NullPointerException npe){
			throw new LispException("Too few Arguments for the function");
		}
	}
	
	public LispNode eval(LispNode exp, Map<String,Stack<LispNode> > aList, Map<String,Stack<LispNode>> dList) throws LispException{
		if(isAtom(exp)){	//exp is atom
			if(isEq(exp.value,"T")) return (new LispNode("T"));
			if(isEq(exp.value,"NIL")) return (new LispNode("NIL"));
			if(isInt(exp))    {
				return (new LispNode(exp.value.trim()));
				}	
			if(aList.containsKey(exp.value.trim())) {
				return getVal(exp.value, aList);
				}
			else
				{
				
				throw new LispException("Unbound variable!"+exp.value);
			}	
		}
		else{	
			//exp is List
			if(isAtom(car(exp))){
				if(isEq(car(exp).value,"QUOTE")){ 
					if(!checkError(cdr(exp),1)) { throw new LispException("Too Many Arguments in cdr[exp] for QUOTE");}
					LispNode returnvalue = car(cdr(exp));
					return returnvalue;
				}
				if(isEq(car(exp).value,"COND")){ 
					LispNode returnvalue = evcon(cdr(exp),aList,this.dList);
					return returnvalue;
				}
				if(isEq(car(exp).value,"DEFUN")){ 
					if(!checkError(cdr(exp),3)) { throw new LispException("Too Many Arguments in cdr[exp] for DEFUN");}
					LispNode f = car(cdr(exp));
					LispNode paramList = car(cdr(cdr(exp)));
					LispNode body = car(cdr(cdr(cdr(exp))));
					LispNode returnvalue = new LispNode("Root");
					returnvalue.left = f;
					returnvalue.right = new LispNode("SExpression");
					returnvalue.right.left = paramList;
					returnvalue.right.right = body;
					
					Stack<LispNode> y = new Stack<LispNode>();
					if(this.dList == null){
						y.push(returnvalue);
					}
					else 
						{
							if(this.dList.containsKey(f.value)){
								y = this.dList.get(f.value);
							}
							y.push(returnvalue);
						}
					this.dList.put(f.value, y);
					if(this.dList == null){ System.out.println("DLIST!");}
					return car(returnvalue);
				}
				else{
					LispNode applyNode = apply(car(exp),evlist(cdr(exp),aList,this.dList),aList,this.dList);
					return applyNode;
				}
			}			
		}
	
	return (new LispNode("Tfrom eval"));
}

public LispNode evcon(LispNode x, Map<String,Stack<LispNode> > aList, Map<String, Stack<LispNode>> dList) throws LispException{
	if((isAtom(x)) &&(NULL(x).value.equals("T"))){
		throw new LispException("NULL in evcon!");
	}
	LispNode evalReturn = eval((car(car(x))),aList,dList);
	if(isEq(evalReturn.value,"T")) {
		return(eval(car(cdr(car(x))),aList,dList));
	}
	return(evcon(cdr(x),aList,dList));
}
	
public LispNode evlist(LispNode x, Map<String,Stack<LispNode> > aList, Map<String, Stack<LispNode>> dList) throws LispException{
	if(x == null) return (new LispNode("NIL"));	
	if((isAtom(x)) &&(NULL(x).value.equals("T"))){
		return (new LispNode("NIL"));
	}
	
	return (cons(eval(car(x),aList,dList),evlist(cdr(x),aList,dList)));
}
	
public LispNode apply(LispNode f, LispNode x, Map<String,Stack<LispNode> > aList, Map<String, Stack<LispNode>> dList) throws LispException{
	if(isAtom(f)){
		String fValue = f.value;
		if(isEq(fValue,"CAR")){
			//should not be an atom
			if(isAtom(car(x))) throw new LispException("Argument should be a list in CDR");
			LispNode returnvalue = car(car(x));
			return returnvalue;
		}
		if(isEq(fValue,"CDR")){
			//should not be an atom
			if(isAtom(car(x))) throw new LispException("Argument should be a list in CDR");
			LispNode returnvalue = cdr(car(x));
			return returnvalue;
		}
		if(isEq(fValue,"EQ")){
			//should not be an atom
			if(!checkError(x,2)) { throw new LispException("Too Many Arguments in cdr[x] for EQ");}
			LispNode returnvalue = EQ(car(x),(car(cdr(x))));
			return returnvalue;
		}
		if(isEq(fValue,"INT")){
			//should not be an atom
			if(!checkError(x,1)) { throw new LispException("Too Many Arguments in cdr[x] for INT");}
			LispNode returnvalue = INT(car(x));
			return returnvalue;
		}
		if(isEq(fValue,"ATOM")){
			//should not be an atom
			if(!checkError(x,1)) { throw new LispException("Too Many Arguments in cdr[x] for ATOM");}
			LispNode returnvalue = ATOM(car(x));
			return returnvalue;
		}
		if(isEq(fValue,"CONS")){
			//should not be an atom
			if(!checkError(x,2)) { throw new LispException("Too Many Arguments in cdr[x] for CONS");}
			LispNode returnvalue = cons(car(x),(car(cdr(x))));
			return returnvalue;
		}
		if(isEq(fValue,"PLUS")) {
			if(!checkError(x,2)) { throw new LispException("Too Many Arguments in cdr[x] for PLUS");}
			int sum = 0;
			if(!(isInt(car(x)) && isInt(car(cdr(x))))) { throw new LispException("Arguments not Integers");}
			sum += Integer.parseInt((car(x)).value.trim());
			sum += Integer.parseInt((car(cdr(x))).value.trim());
			return (new LispNode(Integer.toString(sum)));
		}
		if(isEq(fValue,"MINUS")) {
			if(!checkError(x,2)) { throw new LispException("Too Many Arguments in cdr[exp] for MINUS");}
			int sum = 0;
			if(!(isInt(car(x)) && isInt(car(cdr(x))))) { throw new LispException("Arguments not Integers");}
			sum = Integer.parseInt((car(x)).value.trim());
			sum -= Integer.parseInt((car(cdr(x))).value.trim());
			return (new LispNode(Integer.toString(sum)));
		}
		if(isEq(fValue,"TIMES")) {
			if(!checkError(x,2)) { throw new LispException("Too Many Arguments in cdr[x] for TIMES");}
			if(!(isInt(car(x)) && isInt(car(cdr(x))))) { throw new LispException("Arguments not Integers");}
			int sum = 0;
			sum = Integer.parseInt((car(x)).value.trim());
			sum *= Integer.parseInt((car(cdr(x))).value.trim());
			return (new LispNode(Integer.toString(sum)));
		}
		if(isEq(fValue,"QUOTIENT")) {
			if(!checkError(x,2)) { throw new LispException("Too Many Arguments in cdr[exp] for QUOTIENT");}
			if(!(isInt(car(x)) && isInt(car(cdr(x))))) { throw new LispException("Arguments not Integers");}
			int sum = 0;
			sum = Integer.parseInt((car(x)).value.trim());
			sum /= Integer.parseInt((car(cdr(x))).value.trim());
			return (new LispNode(Integer.toString(sum)));
		}
		if(isEq(fValue,"REMAINDER")) {
			if(!checkError(x,2)) { throw new LispException("Too Many Arguments in cdr[exp] for REMAINDER");}
			if(!(isInt(car(x)) && isInt(car(cdr(x))))) { throw new LispException("Arguments not Integers");}
			int sum = 0;
			sum = Integer.parseInt((car(x)).value.trim());
			sum %= Integer.parseInt((car(cdr(x))).value.trim());
			return (new LispNode(Integer.toString(sum)));
		}

		if(!(dList == null) && dList.containsKey(fValue)){
		return (eval(cdr(cdr(getVal(fValue,dList))),addPairs(car(cdr(getVal(fValue,dList))),x,aList),dList));
		}
	}
	else{	//f is a list
		return (new LispNode("Implement defun"));
	}
	throw new LispException("ERROR: in apply!");
}
	public Map<String,Stack<LispNode>> addPairs(LispNode xList,LispNode yList,Map<String,Stack<LispNode>> z) throws LispException{
		if(!checkSize(xList,yList)) throw new LispException("Mismatch: number of actuals and formals");
		Stack<String> key = new Stack <String>();
		Stack<String> value = new Stack <String>();
			key = nextNode(xList,key);
		 value = nextNode(yList,value);
		 
		Iterator<String> itX = key.iterator();
		Iterator<String> itY = value.iterator();
		while((itX.hasNext()) && (itY.hasNext())){
			Stack<LispNode> stk = new Stack<LispNode>();
			String keyItem = itX.next();
			String valueItem = itY.next();
			if(z.containsKey(keyItem))stk = z.get(keyItem);
			stk.push(new LispNode(valueItem));
			z.put(keyItem, stk);
			
			
		}
		return z;
	}
	
	public Boolean checkSize(LispNode exp1,LispNode exp2){
		if(isEq(Integer.toString(getNode(exp1,0)),Integer.toString(getNode(exp2,0))))
		return true;
		return false;
	}
	
	public int getNode(LispNode exp1,int size){
		if(exp1 == null) return 0;
		size++;
		getNode(exp1.left,size);
		getNode(exp1.right,size);
		return size;
	}
	
	public Stack<String> nextNode(LispNode x,Stack<String> list){
		 if(x == null) return list;
		 if(((x.left == null) && (x.right == null)) &&(!x.value.equals("NIL"))) {list.push(x.value);}
		 nextNode(x.left,list);
		 nextNode(x.right,list);
		return list;
	}
	public LispNode getVal (String x, Map<String,Stack<LispNode>>gList){
		LispNode y = gList.get(x).lastElement();
		return y;
	}
}
