package potager.btalva.pojo;

public class AssocPlantesDevEtreSemeesInterdite   {
	
	

	private int numeroP1;
	private int numeroP2;

	public AssocPlantesDevEtreSemeesInterdite(int numeroPlante1, int numeroPlante2) {
		
		this.numeroP1 = numeroPlante1;
		this.numeroP2 = numeroPlante2;
		
		
	}

	public int getNumeroP1() {
		return numeroP1;
	}

	public void setNumeroP1(int numeroP1) {
		this.numeroP1 = numeroP1;
	}

	public int getNumeroP2() {
		return numeroP2;
	}

	public void setNumeroP2(int numeroP2) {
		this.numeroP2 = numeroP2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + numeroP1;
		result = prime * result + numeroP2;
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
		AssocPlantesDevEtreSemeesInterdite other = (AssocPlantesDevEtreSemeesInterdite) obj;
		if (numeroP1 != other.numeroP1)
			return false;
		if (numeroP2 != other.numeroP2)
			return false;
		return true;
	}


	
	
	
}
