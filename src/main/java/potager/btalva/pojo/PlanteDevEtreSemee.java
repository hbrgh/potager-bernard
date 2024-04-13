package potager.btalva.pojo;

public class PlanteDevEtreSemee extends Plante {
	
	

	private int numero;
	

	public PlanteDevEtreSemee(int numeroPlante, Plante pPlante) {
		super(pPlante);
		this.numero = numeroPlante;
		
		
		
	}

	public PlanteDevEtreSemee(PlanteDevEtreSemee modelPlanteDevEtreSemee) {
		super(new Plante(modelPlanteDevEtreSemee.getNom(), modelPlanteDevEtreSemee.getFamille()));
		this.numero = modelPlanteDevEtreSemee.getNumero();
		
		
		
	}
	

	public int getNumero() {
		return numero;
	}


	public void setNumero(int numero) {
		this.numero = numero;
	}

	
	
	
}
