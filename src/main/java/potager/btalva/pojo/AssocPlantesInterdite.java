package potager.btalva.pojo;

public class AssocPlantesInterdite {
	
	private String nomPlante1;
	private String nomPlante2;
	
	public AssocPlantesInterdite(String nomPlante1, String nomPlante2) {
		super();
		this.nomPlante1 = nomPlante1;
		this.nomPlante2 = nomPlante2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nomPlante1 == null) ? 0 : nomPlante1.hashCode());
		result = prime * result + ((nomPlante2 == null) ? 0 : nomPlante2.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AssocPlantesInterdite other = (AssocPlantesInterdite) obj;
		if (nomPlante1 == null) {
			if (other.nomPlante1 != null)
				return false;
		} else if (!nomPlante1.equals(other.nomPlante1))
			return false;
		if (nomPlante2 == null) {
			if (other.nomPlante2 != null)
				return false;
		} else if (!nomPlante2.equals(other.nomPlante2))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AssocPlantesInterdite [nomPlante1=" + nomPlante1 + ", nomPlante2=" + nomPlante2 + "]";
	}
	
	public String toStringSpecial() {
		return "AssocPlantesInterdite: ("+nomPlante1+", "+ nomPlante2+")";
	}

	public String getNomPlante1() {
		return nomPlante1;
	}

	public void setNomPlante1(String nomPlante1) {
		this.nomPlante1 = nomPlante1;
	}

	public String getNomPlante2() {
		return nomPlante2;
	}

	public void setNomPlante2(String nomPlante2) {
		this.nomPlante2 = nomPlante2;
	}
	
	

}
