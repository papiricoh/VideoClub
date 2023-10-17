package logica.partners;


public class Partner implements Comparable<Partner>{
	private int[] code;
	private String name;
	
	
	public Partner(String code, String name) {
		this.code = toCode(code);
		this.name = name;
	}
	
	public int[] toCode(String code) {
		int[] toReturn = new int[7];
		for (int i = 0; i < code.length(); i++) {
			toReturn[i] = code.charAt(i) % 10; //Deja numeros de un solo digito si hay algun problema de input por el usuario
		}
		return toReturn;
	}

	public String getCode() {
		String formatted_code = "";
		for (int i = 0; i < this.code.length; i++) {
			formatted_code += this.code[i];
		}
		return formatted_code;
	}
	
	public int[] getIntCode() {
		return this.code;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Partner)) {
			return false;
		}
		Partner partner = (Partner) o;
		for (int i = 0 ; i < code.length ; i++) {
			if(i != partner.getIntCode()[i]) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int compareTo(Partner o) {
		return o.getCode().compareTo(this.getCode());
	}
}
