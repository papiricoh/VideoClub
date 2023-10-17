package interfaz;

import exceptions.VideoClubException;
import logica.club.VideoClub;
import logica.movie.Genre;
import logica.movie.Movie;
import logica.partners.Lend;
import logica.partners.Partner;

public class Main {

	public static void main(String[] args) {
		VideoClub vd = new VideoClub();
		
		Movie m1 = new Movie("lotr", "El Señor de los anillos: La comunidad del anillo", Genre.HIGH_FANTASY, "New Line Cinema", 2003);
		Movie m2 = new Movie("lrtt", "El Señor de los anillos: Las dos torres", Genre.HIGH_FANTASY, "New Line Cinema", 2006);
		Movie m3 = new Movie("lrrk", "El Señor de los anillos: El Retorno del Rey", Genre.HIGH_FANTASY, "New Line Cinema", 2008);
		
		try {
			vd.addMovie(m1, 4, 3);
			vd.addMovie(m2, 4, 6);
			vd.addMovie(m3, 12, 4);
		} catch (VideoClubException e) {
			System.err.println(e.getMessage());
		}
		
		try {
			System.out.println(vd.getFormattedCopiesOfMovie(m3));
		} catch (VideoClubException e) {
			System.err.println(e.getMessage());
		}
		
		Partner p1 = new Partner("KWKD", "Lopecin el grande");
		Partner p2 = new Partner("KW2D", "Robertin el travieso");
		
		try {
			vd.addPartner(p1);
			vd.addPartner(p2);
		} catch (VideoClubException e) {
			System.err.println(e.getMessage());
		}
		try {
			vd.addLend(p1, m1, true);
			vd.addLend(p2, m2, true);
			vd.addLend(p2, m2, false);
		} catch (VideoClubException e) {
			System.err.println(e.getMessage());
		}
		try {
			System.out.println(vd.getFormattedLendsByPartnerAndMovie(p1, m1));
		} catch (VideoClubException e) {
			System.err.println(e.getMessage());
		}
		try {
			System.out.println(vd.getFormattedCopiesOfMovie(m1));
		} catch (VideoClubException e) {
			System.err.println(e.getMessage());
		}

		try {
			vd.removeMovieByCode(m1.getCode());
		} catch (VideoClubException e) {
			System.err.println(e.getMessage());
		}

		try {
			System.out.println(vd.getFormattedCopiesOfMovie(m1));
		} catch (VideoClubException e) {
			System.err.println(e.getMessage()); //M1 not exists
		}
		try {
			System.out.println(vd.getFormattedLendsByPartnerAndMovie(p2, m2));
		} catch (VideoClubException e) {
			System.err.println(e.getMessage());
		}
		
		try {
			Lend l = vd.getLendByPartnerAndMovie(p2, m2);
			vd.removeLend(l.getCode()); //Success in removal
		} catch (VideoClubException e) {
			System.err.println(e.getMessage());
		}
		
		try {
			System.out.println(vd.getFormattedLendsByPartnerAndMovie(p2, m2)); //Check that remove OK
		} catch (VideoClubException e) {
			System.err.println(e.getMessage());
		}

		try {
			System.out.println(vd.getFormattedCopiesOfMovie(m2));
		} catch (VideoClubException e) {
			System.err.println(e.getMessage());
		}
		
		try {
			vd.removePartnerByCode(p2.getCode()); //Remove P2 
		} catch (VideoClubException e) {
			System.err.println(e.getMessage());
		}

		try {
			System.out.println(vd.getFormattedCopiesOfMovie(m2)); //Check if copies are Unrented
		} catch (VideoClubException e) {
			System.err.println(e.getMessage());
		}
		
		
		System.out.println("============================ FIN DE TESTS ============================");
	}

}
