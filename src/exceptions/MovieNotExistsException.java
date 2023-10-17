package exceptions;

public class MovieNotExistsException extends VideoClubException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8368832765628892306L;

	public MovieNotExistsException(String msg) {
		super(msg);
	}

}
