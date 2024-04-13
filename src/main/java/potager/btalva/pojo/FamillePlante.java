package potager.btalva.pojo;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FamillePlante {
	
	public static final String FAMILLE_FLEUR = "Fleur";
	public static final String FAMILLE_FEUILLE = "Feuille";
	public static final String FAMILLE_FRUIT = "Fruit";
	public static final String FAMILLE_LEGUMINEUSE = "Légumineuse";
	public static final String FAMILLE_RACINE = "Racine";
	
	
	private static final Set<String> ensembleFamillesPlantes = 
			Stream.of(FAMILLE_FLEUR, FAMILLE_FEUILLE, FAMILLE_FRUIT, FAMILLE_LEGUMINEUSE, FAMILLE_RACINE).
			collect(Collectors.toCollection(HashSet::new));
	
	public static boolean estFamillePlante(String nomFam) {
		return (ensembleFamillesPlantes.contains(nomFam));
	}
	
	public static boolean estFamillePlanteDiffDeFleur(String nomFam) {
		return (nomFam.equalsIgnoreCase(FAMILLE_FLEUR) == false);
	}
	
	private String nom;
	private boolean fleur = false;

	public FamillePlante(String nomFamillePlante) {
		super();
		this.nom = nomFamillePlante;
		if (nomFamillePlante.equalsIgnoreCase(FAMILLE_FLEUR)) {
			this.fleur = true;
		} else {
			this.fleur = false;
		}
		
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public boolean isFleur() {
		return fleur;
	}

	public void setFleur(boolean fleur) {
		this.fleur = fleur;
	}
	
	
	

}
