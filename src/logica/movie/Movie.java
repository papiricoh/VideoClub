package logica.movie;


public class Movie {
	private String code;
	private String title;
	private Genre genre;
	private String company;
	private int year;
	
	
	public Movie(String code, String title, Genre genre, String company, int year) {
		this.code = code;
		this.title = title;
		this.genre = genre != null ? genre : Genre.UNDEFINED;
		this.company = company;
		this.year = year;
	}
	
	public String getCode() {
		return code;
	}


	public String getTitle() {
		return title;
	}


	public Genre getGenre() {
		return genre;
	}


	public String getCompany() {
		return company;
	}


	public int getYear() {
		return year;
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Movie)) {
			return false;
		}
		Movie movie = (Movie) o;
		if(movie.getCode().equals(this.code)) {
			return true;
		}
		return false;
	}
	
	
}
