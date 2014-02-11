import java.io.*;
import java.util.*;

public class Interpreter {

	public static void main(String[] args)throws IOException,LispException{
	try{
		Boolean notEOF = true;
		Boolean isAtom = false;
		Boolean isSpace = false;
		Lexer l = new Lexer(new InputStreamReader(System.in));
		l.initLexer(new InputStreamReader(System.in));
		int countLParen = 0;
		int countRParen = 0;
		EvalSimple eval = new EvalSimple();
		while(notEOF){
			isAtom = false;
			isSpace = false;
			Parser p = new Parser();
			p = p.initTree();
			LispNode lnode = new LispNode("NotInitialised");
			 countLParen = 0;
			countRParen = 0;
		while(true){
		/*Lexer Part */
			Token t = l.getToken(l);
			if((t.tokenType == Token.TokenType.TK_EOF)){
				notEOF = false;
				break;
			}
			if(t.tokenType == Token.TokenType.TK_ERR){
				System.out.println(t.tokenLit);
				notEOF = false;
				break;
			}
		/*End of Lexer*/
		/*Build Tree*/
			if(t.tokenType == Token.TokenType.TK_LPAREN) countLParen++;
			if(t.tokenType == Token.TokenType.TK_RPAREN) countRParen++;
			if(countLParen == countRParen){
				if(t.tokenType == Token.TokenType.TK_SPACE) {continue; }
				if (countLParen > 0) break;
				else 
				{
					isAtom = true;
					lnode = new LispNode (t.tokenLit);
					break;
				}
			}
			lnode = p.addNode(t, p);
		
		}
		if(countLParen != countRParen){ System.out.println("ERROR: unmatched parenthesis");break;}
		if(notEOF || isAtom){
		
		LispNode result = eval.eval(lnode,new HashMap<String,Stack<LispNode>>(),eval.dList);
		eval.printLispNode(result);
		System.out.println();
		}
		
		}
		// call lexer
	
	}catch(LispException lispe){
		System.out.println("ERROR: "+lispe.toString());
	}
    }
}
