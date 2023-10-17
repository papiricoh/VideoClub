package logica.club;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import exceptions.LendNotExistsException;
import exceptions.MovieAlreadyExistsException;
import exceptions.MovieNotExistsException;
import exceptions.PartnerException;
import exceptions.VideoClubException;
import logica.movie.Copy;
import logica.movie.Dvd;
import logica.movie.Movie;
import logica.movie.Tape;
import logica.partners.Lend;
import logica.partners.Partner;

public class VideoClub {
	private Set<Partner> partners;
	private Map<String, Copy> copies;
	private List<Lend> lends;
	private List<Movie> movies;
	

	public VideoClub() {
		this.partners = new TreeSet<>();
		this.copies = new HashMap<>();
		this.lends = new ArrayList<>();
		this.movies = new ArrayList<>();
	}
	
	public VideoClub(Set<Partner> partners,
			Map<String, Copy> copies, List<Lend> lends, List<Movie> movies) {
		
		this.partners = partners != null ? partners : new TreeSet<>();
		this.copies = copies != null ? copies : new HashMap<>();
		this.lends = lends != null ? lends : new ArrayList<>();
		this.movies = movies != null ? movies : new ArrayList<>();
	}
	
	public void addLend(Partner partner, Movie movie, boolean is_tape) 
			throws PartnerException, MovieNotExistsException, NoCopiesAvariables {
		
		if(!partnerExists(partner)) {
			throw new PartnerException("Partner " + partner.getName() + " does not exists in Videoclub");
		}else if(!movieExists(movie)) {
			throw new MovieNotExistsException("Movie "+ movie.getTitle() + " not exists in Videoclub");
		}
		Copy copy = is_tape ? getFreeTape(movie) : getFreeDvd(movie);
		if(copy == null) {
			throw new NoCopiesAvariables("No avariable copies of " + movie.getTitle());
		}
		String code = generateLendCode();
		this.lends.add(new Lend(code, partner.getCode(), copy));
	}

	public void removeMovieByCode(String code) throws VideoClubException {
		Iterator<Movie> movieIterator = this.movies.iterator();
	    
	    while (movieIterator.hasNext()) {
	        Movie m = movieIterator.next();
	        if (m.getCode().equals(code)) {
	            Iterator<Map.Entry<String, Copy>> copyIterator = this.copies.entrySet().iterator();

	            while (copyIterator.hasNext()) {
	                Map.Entry<String, Copy> entry = copyIterator.next();
	                Copy c = entry.getValue();

	                if (c.getMovie_code().equals(m.getCode())) {
	                    Iterator<Lend> lendIterator = this.lends.iterator();
	                    
	                    while (lendIterator.hasNext()) {
	                        Lend l = lendIterator.next();
	                        
	                        if (l.getCopy_code().equals(c.getCode())) {
	                            lendIterator.remove();
	                        }
	                    }
	                    copyIterator.remove();
	                }
	            }
	            movieIterator.remove();
	            return;
	        }
	    }
		throw new MovieNotExistsException("Movie do not exists in Videoclub, Code: " + code);
	}

	public void removeLend(String code) throws VideoClubException {
		for (Lend l : this.lends) {
			if(l.getCode().equals(code)) {
				for (Copy c : this.copies.values()) {
					if(c.getCode().equals(l.getCopy_code())) {
						c.unlend(); //Unlends
					}
				}
				this.lends.remove(l);
				return;
			}
		}
		throw new LendNotExistsException("Lend with code: " + code + " not exists");
	}
	
	public void removePartnerByCode(String code) throws VideoClubException {
	    Iterator<Partner> partnerIterator = this.partners.iterator();

	    while (partnerIterator.hasNext()) {
	        Partner p = partnerIterator.next();

	        if (p.getCode().equals(code)) {
	            partnerIterator.remove();

	            Iterator<Lend> lendIterator = this.lends.iterator();
	            while (lendIterator.hasNext()) {
	                Lend lend = lendIterator.next();
	                
	                if (lend.getPartner_code().equals(code)) {
	                    getCopyByCode(lend.getCopy_code()).unlend(); //Unlends the copy
	                    lendIterator.remove();
	                }
	            }
	            return;
	        }
	    }
	    throw new PartnerException("Partner with code: " + code + " not exists");
	}
	
	
	private Copy getCopyByCode(String copy_code) {
		for (String c : this.copies.keySet()) {
			if(c.equals(copy_code)) {
				return this.copies.get(c);
			}
		}
		return null;
	}

	private Tape getFreeTape(Movie movie) {
		for (Copy c : this.copies.values()) {
			if(c instanceof Tape && c.getMovie_code().equals(movie.getCode()) && c.isAvariable()) {
				return (Tape) c;
			}
		}
		return null;
	}
	
	private Dvd getFreeDvd(Movie movie) {
		for (Copy c : this.copies.values()) {
			if(c instanceof Dvd && c.getMovie_code().equals(movie.getCode()) && c.isAvariable()) {
				return (Dvd) c;
			}
		}
		return null;
	}
	
	private String generateLendCode() {
		String code = generateCode();
		for (Lend lend : lends) {
			if(lend.getCode().equals(code)) {
				return generateLendCode();
			}
		}
		return code;
	}

	public void addPartner(Partner partner) throws PartnerException {
		if(partnerExists(partner)) {
			throw new PartnerException("Partner " + partner.getName() + " not exists");
		}
		this.partners.add(partner);
	}
	
	private boolean partnerExists(Partner partner) {
		for (Partner p : partners) {
			if(p.getCode().equals(partner.getCode())) {
				return true;
			}
		}
		return false;
	}

	public void addMovie(Movie movie, int dvd_copies, int tape_copies) throws MovieAlreadyExistsException {
		if(movieExists(movie)) {
			throw new MovieAlreadyExistsException("This movie already exists");
		}
		this.movies.add(movie);
		addDvds(movie.getCode(), dvd_copies);
		addTapes(movie.getCode(), tape_copies);
	}
	
	private void addTapes(String code, int tape_copies) {
		for (int i = 0; i < tape_copies; i++) {
			String copy_code = generateCopyCode();
			this.copies.put(copy_code, new Tape(copy_code, code));
		}
	}

	private void addDvds(String code, int dvd_copies) {
		for (int i = 0; i < dvd_copies; i++) {
			String copy_code = generateCopyCode();
			this.copies.put(copy_code, new Dvd(copy_code, code));
		}
	}
	

	private String generateCopyCode() {
		String generated_code = generateCode();
		for (String c : this.copies.keySet()) {
			if(c.equals(generated_code)) {
				return generateCopyCode();
			}
		}
		return generated_code;
	}

	private boolean movieExists(Movie movie) {
		for (Movie m : movies) {
			if(m.getCode().equals(movie.getCode())) {
				return true;
			}
		}
		return false;
	}
	
	private String generateCode() {
		final int LENGTH = 8;
		String returned_string = "";
		for (int i = 0; i < LENGTH; i++) {
			int rnd = (int) (Math.random() * 52); // A random name a-Z
		    char base = (rnd < 26) ? 'A' : 'a'; //if < 26 append A else append a (To calibrate if uppercase or lowercase)
		    returned_string += (char) (base + rnd % 26);//value of a or A + % of random number / cuantity of characters in normal alphabet (26)
		}
		return returned_string;
	}
	
	public String getFormattedCopiesOfMovie(Movie movie) throws MovieNotExistsException {
		if(!movieExists(movie)) {
			throw new MovieNotExistsException("Movie do not exists in Videoclub Title: " + movie.getTitle());
		}
		String str = "Copies of: " + movie.getTitle() + "\n";
		str += "Copy Code\tAvariable\tType\n\n";
		for (Copy c : this.copies.values()) {
			if(c.getMovie_code().equals(movie.getCode())) {
				str += c.getCode() + "\t";
				str += c.isAvariable() ? "Avariable" : "Rented   ";
				str += "\t";
				str += c instanceof Dvd ? "Dvd" : "Tape";
				str += "\n";
			}
		}
		return str;
	}
	
	@SuppressWarnings("deprecation")
	public String getFormattedLendsByPartnerAndMovie(Partner partner, Movie movie) throws MovieNotExistsException, PartnerException {
		if(!partnerExists(partner)) {
			throw new PartnerException("Partner " + partner.getName() + " does not exists in Videoclub");
		}
		if(!movieExists(movie)) {
			throw new MovieNotExistsException("Movie do not exists in Videoclub Title: " + movie.getTitle());
		}
		String str = partner.getName() + "'s lends of: " + movie.getTitle() + "\n";
		str += "Lend code\tCopy Code\tAvariable?\tType\tInitial Date\t\tReturn Date\n\n";
		for (Copy c : this.copies.values()) {
			if(c.getMovie_code().equals(movie.getCode())) {
				Lend lend = getLendByPartnerAndCopy(partner, c);
				if(lend != null) {
					str += lend.getCode() + "\t";
					str += c.getCode() + "\t";
					str += c.isAvariable() ? "Avariable" : "Rented   ";
					str += "\t";
					str += c instanceof Dvd ? "Dvd" : "Tape";
					str += "\t";
					str += lend.getInitial_date().toLocaleString() + "\t";
					str += lend.getReturn_date().toLocaleString()  + "\t";
					str += "\n";
				}
			}
		}
		return str;
	}

	public Lend getLendByPartnerAndCopy(Partner partner, Copy c) {
		for (Lend lend : lends) {
			if(lend.getCopy_code().equals(c.getCode()) && lend.getPartner_code().equals(partner.getCode())) {
				return lend;
			}
		}
		return null;
	}

	public Lend getLendByPartnerAndMovie(Partner partner, Movie movie) {
		for (Copy c : this.copies.values()) {
			if(c.getMovie_code().equals(movie.getCode())) {
				for (Lend l : lends) {
					if(l.getCopy_code().equals(c.getCode()) && l.getPartner_code().equals(partner.getCode())) {
						return l;
					}
				}
			}
		}
		return null;
	}
	
}
