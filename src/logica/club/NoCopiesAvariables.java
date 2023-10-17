package logica.club;

import exceptions.VideoClubException;

public class NoCopiesAvariables extends VideoClubException {

	private static final long serialVersionUID = -8814068151708331596L;

	public NoCopiesAvariables(String msg) {
		super(msg);
	}

}
