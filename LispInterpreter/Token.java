
import java.io.*;
import java.lang.Character;
public class Token {
	
	public enum TokenType 
	{TK_LPAREN,TK_RPAREN,TK_DOT,TK_PLUS,TK_MINUS,TK_LIT_ATOM,TK_NUM_ATOM,TK_SPACE,TK_EOF,TK_ERR} ;
	
	TokenType tokenType;
	int tokenNum;
	String tokenLit;
		
	/*Constructors*/
	Token(){
		
	}
	Token(TokenType tt){
	this.tokenType = tt;
	}
	
	Token(TokenType tt,int atomNum){
		this.tokenType = tt;
		this.tokenNum = atomNum;
	}
	Token(TokenType tt,String atomLit){
		this.tokenType = tt;
		this.tokenLit = atomLit.toUpperCase();
	}
	
	public Token getToken(BufferedReader breader,Token t)throws IOException,LispException{
		t = this.nextToken(breader);
		return t;
	}
	
	private Token nextToken(BufferedReader breader)throws IOException,LispException{
		Token t;
		int state = 0;
		char c = ' ';
		int r;
		String atom = "";
		if((r = breader.read()) == -1){
			t = new Token(TokenType.TK_EOF,(char)-1);
			return t;
			
		}
		while(true){
			
			switch(state){
			case 0:
				c = (char)r;
				switch(c){
				case '(':
					state = 1;
					break;
				case ')':
					state = 2;
					break;
				case '.':
					state = 3;
					break;
				case '+':
					if ((r = breader.read()) != 1){
						 c = (char)r;
						 state = 0;
						// atom = "+";
					 }
					else { state = 0;}
					break;
				case '-':
					if ((r = breader.read()) != 1){
						 c = (char)r;
						 state = 0;
						 atom = "-";
					 }
					else { state = 0;}
					break;
				case ' ':			//space
				case ((char) 10): //newline
					state = 0;
					if ((r = breader.read()) != 1){
						 c = (char)r;
						 state = 0;
					 }
					 else state = 0;
					break;
				case ((char) -1):
					t = new Token(TokenType.TK_EOF,(char)-1);
				 	return t;
				 	
				default: 
					state = getNewState(state);
					
				}
				break;
			case 1:
				t = new Token(TokenType.TK_LPAREN,"(");
				return t;
				
			case 2: 
				t = new Token(TokenType.TK_RPAREN,")");
				return t;
				
			case 3:
				if ((r = breader.read()) != 1){
					 c = (char)r;
					 state = 0;
					 if(c == ' '){
						 t = new Token(TokenType.TK_DOT,". ");
							return t; 
					 }
					 else throw new LispException("Illegal Character "+c+" after DOT operator");
				 }
				else {
					throw new LispException("Error in Scanning");
				}
			case 4:
				if(Character.isLetter(c)){
					state = 5;
				}
				else if(Character.isDigit(c)){
					state = 6;
				}
				else {
					state = -1;
					throw new LispException("Scanning failed for char "+c);
				}
				break;
				
			case 5:
				if(Character.isLetterOrDigit(c)){
					 atom = atom + c;
					 breader.mark(1);
					 if ((r = breader.read()) != 1){
						 c = (char)r;
						 state = 5;
					 }
					 else {state = 0;}
				}
				else{
					if(( c == ' ') || (c == '\n')) {atom += ' '; }
				 	 if(( c == ' ') || (c == ')') || (c == '(') || (c == '\n'))  
				 		 {
				 		 breader.reset();
				 		 state = 0;
				 		return (new Token(TokenType.TK_LIT_ATOM,atom));
				 		
				 		 }
					 else throw new LispException("Illegal Character "+c+" after Literal atom"+atom);
				}
				break;
			case 6:
				if(Character.isDigit(c)){
					atom = atom + c;
					breader.mark(1);
					if ((r = breader.read()) != 1){
						 c = (char)r;
						 state = 6;
					 }
					 else {	 state = 0;	 }
					 }
					 
				
				else{
						if(( c == ' ') || (c == '\n')) {atom += ' '; }
					 	 if(( c == ' ') || (c == ')') || (c == '(') || (c == '\n'))  
					 		 {
					 		breader.reset();
					 		 state = 0;
					 		return (new Token(TokenType.TK_NUM_ATOM,atom));
					 		
					 		 }
						 else throw new LispException("Illegal Character "+c+" after numeric atom"+atom);
					}
				break;
				
				default:
					throw new LispException("ERROR in Scanning");
			}
		}
	}
	
	public int getNewState(int state){
		return 4;
	}
}


