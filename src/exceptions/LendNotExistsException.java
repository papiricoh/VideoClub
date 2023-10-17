package exceptions;

public class LendNotExistsException extends VideoClubException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6624391622675811338L;

	public LendNotExistsException(String msg) {
		super(msg);
	}
}
