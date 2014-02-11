
public class LispException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String errorString = null;
	public LispException(String errorString){
        this.errorString = errorString;
    }

    public String toString(){
        return errorString ;
    }

}
