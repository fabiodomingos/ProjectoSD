package sdstore.presentationserver.exception;

public class PresenterArgumentException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String command;
	private String invalidArgument;
	
	public PresenterArgumentException(String command, String invalidArgument) {
		super();
		this.command = command;
		this.invalidArgument = invalidArgument;
	}
	
	public String getCommand(){
		return this.command;
	}
	
	public String getInvalidArgument(){
		return this.invalidArgument;
	}
	
}
