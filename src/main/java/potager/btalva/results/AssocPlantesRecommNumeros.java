package potager.btalva.results;

public class AssocPlantesRecommNumeros {
	
	
		private int numero1;
		private int numero2;
		public AssocPlantesRecommNumeros(int numero1, int numero2) {
			super();
			this.numero1 = numero1;
			this.numero2 = numero2;
		}
		public int getNumero1() {
			return numero1;
		}
		public void setNumero1(int numero1) {
			this.numero1 = numero1;
		}
		public int getNumero2() {
			return numero2;
		}
		public void setNumero2(int numero2) {
			this.numero2 = numero2;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + numero1;
			result = prime * result + numero2;
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
			AssocPlantesRecommNumeros other = (AssocPlantesRecommNumeros) obj;
			if (numero1 != other.numero1)
				return false;
			if (numero2 != other.numero2)
				return false;
			return true;
		}
		
		

}
