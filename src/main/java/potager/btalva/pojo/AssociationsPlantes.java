package potager.btalva.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AssociationsPlantes extends MotherParams {
	

	private List<Plante> lstPlantesConnues;
	private List<Plante> lstPlantesFamDiffDeFleur;
	
	private List<AssocPlantesInterdite> lstAssocsInterditesPlantes;
	private List<AssocPlantesRecommandee> lstAssocsRecommandeesPlantes;
	
	private Map<String, Plante> mapPlantesFamDiffDeFleur;
	
	
	private Set<AssocPlantesInterdite> setAssocsPlantesInterdites;
	private Set<AssocPlantesRecommandee> setAssocsPlantesRecommandees;
	
	
	
	
	public AssociationsPlantes() {
		super();
		this.lstPlantesConnues = new ArrayList<>();
		this.lstPlantesFamDiffDeFleur = new ArrayList<>();
		this.lstAssocsInterditesPlantes = new ArrayList<>();
		this.lstAssocsRecommandeesPlantes = new ArrayList<>();
		this.mapPlantesFamDiffDeFleur = new HashMap<>();
		this.setAssocsPlantesInterdites = new HashSet<>();
		this.setAssocsPlantesRecommandees = new HashSet<>();
		
		
	}
	
	public boolean verifCoherence() {
		return(true);
	}
	
	public void addPlante(Plante pPlante) {
		this.lstPlantesConnues.add(pPlante);
	}

	public void addPlanteFamDiffFleur(Plante pPlante) {
		this.lstPlantesFamDiffDeFleur.add(pPlante);
		this.mapPlantesFamDiffDeFleur.put(pPlante.getNom(), pPlante);
	}
	
	public boolean isFamDiffFleur(String nomPlante) {
		return(this.mapPlantesFamDiffDeFleur.get(nomPlante) != null);
	}
	
	public void addAssocPlanteInterdite(String nomPlante1, String nomPlante2 ) {
		AssocPlantesInterdite lAssocPlantesInterdite = new AssocPlantesInterdite(nomPlante1, nomPlante2);
		if (this.setAssocsPlantesInterdites.contains(lAssocPlantesInterdite)) {
			return;
		}
	
		if (nomPlante1.equalsIgnoreCase(nomPlante2) == false) { // Bon, c'est toujours vrai, mais paranoïa ne saurait nuire...
			this.lstAssocsInterditesPlantes.add(lAssocPlantesInterdite);
		}
		this.setAssocsPlantesInterdites.add(lAssocPlantesInterdite);
	}

	public void addAssocPlanteRecommandee(String nomPlante1, String nomPlante2 ) {
		AssocPlantesRecommandee lAssocPlantesRecommandee = new AssocPlantesRecommandee(nomPlante1, nomPlante2);
		if (this.setAssocsPlantesRecommandees.contains(lAssocPlantesRecommandee)) {
			return;
		}
		if (nomPlante1.equalsIgnoreCase(nomPlante2) == false) { // Pas toujours vrai, car chaque plante est amoureuse d'elle-même !
			this.lstAssocsRecommandeesPlantes.add(lAssocPlantesRecommandee);
		}
		
		this.setAssocsPlantesRecommandees.add(lAssocPlantesRecommandee);
	}
	
	public String toStringSpecial() {
		
		StringBuilder sb = new StringBuilder();
		
		Map<String, List<Plante>> plantesParFamille = this.getLstPlantesConnues().stream()
				  .collect(Collectors.groupingBy(Plante::getFamille));
		int nbFamilles = plantesParFamille.size();
		
	
		appendParam(sb,"nombre de familles détectées", nbFamilles);
		appendParam(sb,"nombre de plantes connues", this.getNbPlantesConnues());
		appendParam(sb,"nombre de plantes connues dont la famille est différente de Fleur", this.getNbPlantesFamDiffFleur());
		appendParam(sb,"nombre d'associations de plantes interdites", this.lstAssocsInterditesPlantes.size() / 2);
		appendParam(sb,"nombre d'associations de plantes recommandées", this.lstAssocsRecommandeesPlantes.size() / 2);
		return sb.toString();	
	}

	public int getNbPlantesConnues() {
		return (this.lstPlantesConnues.size());
	}
	
	public int getNbPlantesFamDiffFleur() {
		return (this.lstPlantesFamDiffDeFleur.size());
	}

	public List<Plante> getLstPlantesConnues() {
		return lstPlantesConnues;
	}
	
	

	public List<Plante> getLstPlantesFamDiffDeFleur() {
		return lstPlantesFamDiffDeFleur;
	}

	public void setLstPlantesFamDiffDeFleur(List<Plante> lstPlantesFamDiffDeFleur) {
		this.lstPlantesFamDiffDeFleur = lstPlantesFamDiffDeFleur;
	}

	public void setLstPlantesConnues(List<Plante> lstPlantesConnues) {
		this.lstPlantesConnues = lstPlantesConnues;
	}

	public List<AssocPlantesInterdite> getLstAssocsInterditesPlantes() {
		return lstAssocsInterditesPlantes;
	}

	public void setLstAssocsInterditesPlantes(List<AssocPlantesInterdite> lstAssocsInterditesPlantes) {
		this.lstAssocsInterditesPlantes = lstAssocsInterditesPlantes;
	}

	public List<AssocPlantesRecommandee> getLstAssocsRecommandeesPlantes() {
		return lstAssocsRecommandeesPlantes;
	}

	public void setLstAssocsRecommandeesPlantes(List<AssocPlantesRecommandee> lstAssocsRecommandeesPlantes) {
		this.lstAssocsRecommandeesPlantes = lstAssocsRecommandeesPlantes;
	}

	

	public Set<AssocPlantesInterdite> getSetAssocsPlantesInterdites() {
		return setAssocsPlantesInterdites;
	}

	public void setSetAssocsPlantesInterdites(Set<AssocPlantesInterdite> setAssocsPlantesInterdites) {
		this.setAssocsPlantesInterdites = setAssocsPlantesInterdites;
	}

	public Set<AssocPlantesRecommandee> getSetAssocsPlantesRecommandees() {
		return setAssocsPlantesRecommandees;
	}

	public void setSetAssocsPlantesRecommandees(Set<AssocPlantesRecommandee> setAssocsPlantesRecommandees) {
		this.setAssocsPlantesRecommandees = setAssocsPlantesRecommandees;
	}

	public Map<String, Plante> getMapPlantesFamDiffDeFleur() {
		return mapPlantesFamDiffDeFleur;
	}

	public void setMapPlantesFamDiffDeFleur(Map<String, Plante> mapPlantesFamDiffDeFleur) {
		this.mapPlantesFamDiffDeFleur = mapPlantesFamDiffDeFleur;
	}

	
	
}
