package potager.btalva.pojo;

public class Plante {
	
	public static boolean familleEstFleur(String fam) {
		return (fam.equalsIgnoreCase(FamillePlante.FAMILLE_FLEUR));
	}

	private String nom;
	private String famille;
	private boolean fleurFam = false;

	public Plante(String nom, String nomFamille) {
		super();
		this.nom = nom;
		this.famille = nomFamille;
		
		if (familleEstFleur(nomFamille)) {
			this.fleurFam = true;
		} else {
			this.fleurFam = false;
		}
	}
	
	public Plante(Plante pPlante) {
		this.nom = pPlante.getNom();
		this.famille = pPlante.getFamille();
		
		if (familleEstFleur(pPlante.getFamille())) {
			this.fleurFam = true;
		} else {
			this.fleurFam = false;
		}
		
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getFamille() {
		return famille;
	}

	public void setFamille(String famille) {
		this.famille = famille;
	}

	public boolean isFleurFam() {
		return fleurFam;
	}

	public void setFleurFam(boolean fleurFam) {
		this.fleurFam = fleurFam;
	}
	
	
}
