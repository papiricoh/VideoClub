package logica.movie;

import java.util.Objects;


public class Copy {
	private String code;
	private String movie_code;
	private boolean avariable;
	
	public Copy(String code, String movie_code) {
		this.code = code;
		this.movie_code = movie_code;
		this.avariable = true;
	}

	public String getCode() {
		return code;
	}

	public String getMovie_code() {
		return movie_code;
	}

	public boolean isAvariable() {
		return avariable;
	}
	
	public void lend() {
		this.avariable = false;
	}
	
	public void unlend() {
		this.avariable = true;
	}
	
	@Override
	public boolean equals(Object anObject) {
		if(anObject instanceof Copy) {
			Copy copy = (Copy) anObject;
			if(copy.getCode().compareTo(this.code) == 0) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.code);
	}
}
