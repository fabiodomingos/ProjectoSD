package sdstore.presentationserver.exception;

public class PresenterArgumentCountException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String command;
	private Integer invalidArg;
	
	public PresenterArgumentCountException(String command, Integer invalidArg) {
		this.command = command;
		this.invalidArg = invalidArg;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public Integer getInvalidArg() {
		return invalidArg;
	}

	public void setInvalidArg(Integer invalidArg) {
		this.invalidArg = invalidArg;
	}
	
}
