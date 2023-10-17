package exceptions;

public class MovieAlreadyExistsException extends VideoClubException {

	private static final long serialVersionUID = -4873669307709260636L;

	public MovieAlreadyExistsException(String msg) {
		super(msg);
	}
}
