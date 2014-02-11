import java.io.*;


public class Lexer {
	InputStreamReader filenameInput;
	BufferedReader breader;
	final int BUFFER_LENGTH = 1024;
	Lexer(InputStreamReader filenameInput){
		this.filenameInput = filenameInput;		
	}
	

	public void initLexer(InputStreamReader input) throws IOException,LispException{
	
	/** Reading File and printing char by char ***/
	try{
	breader = new BufferedReader(input);
	}catch(Exception ioexception){
		throw new LispException("Invalid FileOperation"+ioexception.toString());
	}
	/** End Of: Reading File and printing char by char ****/
	
	
}
	Boolean hasSpace = false;
	public Token getToken(Lexer l) throws IOException,LispException{
		   Token t = new Token();
		    
		    	if(hasSpace){
		    		t = new Token(Token.TokenType.TK_SPACE," ");
		    		hasSpace = false;
		    		return t;
		    	}
		    	t = t.getToken(l.breader,t);
		    	
		    	if((t.tokenType.equals(Token.TokenType.TK_EOF)) || (t.tokenType.equals(Token.TokenType.TK_ERR))){
		    		return t;
		    	}
		    	if(t.tokenLit.charAt(t.tokenLit.length() - 1) == ' '){
		    		hasSpace = true;
		    	}
		    return t;
	}
	
}
