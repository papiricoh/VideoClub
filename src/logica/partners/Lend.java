package logica.partners;

import java.util.Calendar;
import java.util.Date;

import logica.movie.Copy;
import logica.movie.Dvd;

public class Lend {
	private String code;
	private String partner_code;
	private String copy_code;
	private Copy copy;
	private Date initial_date;
	private Date return_date;
	
	public Lend(String code, String partner_code, 
			Copy copy) {
		this.code = code;
		this.partner_code = partner_code;
		this.copy_code = copy.getCode();
		this.copy = copy;
		this.initial_date = new Date();
		this.return_date = calculateDate(copy);
		this.copy.lend();
	}
	
	private Date calculateDate(Copy copy) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(this.initial_date.getTime()));

		if(copy instanceof Dvd) {
	        calendar.add(Calendar.DAY_OF_MONTH, 5);
		}else {
	        calendar.add(Calendar.DAY_OF_MONTH, 3);
		}
		
		return calendar.getTime();
	}

	public String getCode() {
		return code;
	}
	public String getPartner_code() {
		return partner_code;
	}
	public String getCopy_code() {
		return copy_code;
	}
	public Date getInitial_date() {
		return initial_date;
	}
	public Date getReturn_date() {
		return return_date;
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Lend)) {
			return false;
		}
		Lend lend = (Lend) o;
		if(lend.getCode().equals(this.code)) {
			return true;
		}
		return false;
	}

	public Copy getCopy() {
		return copy;
	}
	
}
