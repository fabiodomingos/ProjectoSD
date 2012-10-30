package sdstore.presentationserver.exception;

public class PresenterCommandException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String invalidCommand;

	public PresenterCommandException(String invalidCommand) {
		super();
		this.invalidCommand = invalidCommand;
	}

	public String getInvalidCommand() {
		return invalidCommand;
	}

	public void setInvalidCommand(String invalidCommand) {
		this.invalidCommand = invalidCommand;
	}
	

}
