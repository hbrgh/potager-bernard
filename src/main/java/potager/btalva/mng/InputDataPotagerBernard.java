package potager.btalva.mng;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import potager.btalva.pojo.AssocPlantesInterdite;
import potager.btalva.pojo.AssocPlantesRecommandee;
import potager.btalva.pojo.AssociationsPlantes;
import potager.btalva.pojo.PlanteDevEtreSemee;
import potager.btalva.utils.VariousUtils;

public class InputDataPotagerBernard extends MotherInputDataPotagerBernard {
	
	private static Logger LOGGER = LoggerFactory.getLogger(InputDataPotagerBernard.class);
	
	private List<PlanteDevEtreSemee> lstPlantesDevEtreSemees;
	
	
	private int[][] tabAssocsInterditesPlantes;
	private int[][] tabAssocsRecommandeesPlantes;
	private int nbAssociationsInterditesDansPlantesASemer = 0;
	private int nbAssociationsRecommandeesDansPlantesASemer = 0;
	private int nbAssociationsInterditesAuSensCommunDansPlantesASemer = 0;
	private Set<AssocPlantesInterdite> setAssocsPlantesInterditesAuSensCommun;
	private int nbAssociationsRecommandeesAuSensCommunDansPlantesASemer = 0;
	private Set<AssocPlantesRecommandee> setAssocsPlantesRecommandeesAuSensCommun;
	
	public InputDataPotagerBernard() {
		super();
		this.lstPlantesDevEtreSemees = new ArrayList<>();
	}
	
	public void buildAssocsRecommandeesPlantes() {
		
		int nbAssocsRecommandeesDansPlantesASemer = 0;
		AssociationsPlantes lAssociationsPlantes = ParamsPotager.getInstance().getAssocsPlantes();
		Set<AssocPlantesRecommandee> setAssocsPlantesRecommandeesssssss = lAssociationsPlantes.getSetAssocsPlantesRecommandees();
		int nbPlantesDevEtrSemees = this.lstPlantesDevEtreSemees.size();
		this.tabAssocsRecommandeesPlantes = new int[nbPlantesDevEtrSemees][];
		for (int i=0; i<  nbPlantesDevEtrSemees; i++) {
			this.tabAssocsRecommandeesPlantes[i] = new int[nbPlantesDevEtrSemees];
		}
		for (int i=0; i<  nbPlantesDevEtrSemees; i++) {
			PlanteDevEtreSemee lPlanteDevEtreSemee1 = this.lstPlantesDevEtreSemees.get(i);
			if (i !=lPlanteDevEtreSemee1.getNumero()) {
				VariousUtils.harakiri("STALINGRAD COTE ALLEMAND. HARAKIRI. SORTIE IMMEDIATE");
			}
		
			for (int j=0; j<  nbPlantesDevEtrSemees; j++) {
				PlanteDevEtreSemee lPlanteDevEtreSemee2 = this.lstPlantesDevEtreSemees.get(j);
				if (j!=lPlanteDevEtreSemee2.getNumero()) {
					VariousUtils.harakiri("STALINGRAD COTE ALLEMAND. HARAKIRI. SORTIE IMMEDIATE");
				}
				
				if (setAssocsPlantesRecommandeesssssss.contains(
						new AssocPlantesRecommandee(lPlanteDevEtreSemee1.getNom(),lPlanteDevEtreSemee2.getNom())
								)) {
							this.tabAssocsRecommandeesPlantes[i][j] = 1;
							nbAssocsRecommandeesDansPlantesASemer++;
				} else {
					this.tabAssocsRecommandeesPlantes[i][j] = 0;
				}
			}
		}
		this.nbAssociationsRecommandeesDansPlantesASemer = nbAssocsRecommandeesDansPlantesASemer;
		Set<AssocPlantesRecommandee> setSimplifieAssocsPlantesRecommandees = new HashSet<>();
		int nbAssociationsRecommandeesAuSensCommunDansPlantesASemerrr = 0;
		for (int i=0; i<  nbPlantesDevEtrSemees; i++) {
			for (int j=i; j<  nbPlantesDevEtrSemees; j++) {
				if (this.tabAssocsRecommandeesPlantes[i][j] == 1) {
					if (this.lstPlantesDevEtreSemees.get(i).getNom().equalsIgnoreCase(this.lstPlantesDevEtreSemees.get(j).getNom())) {
						continue;
					}
					if (setSimplifieAssocsPlantesRecommandees.contains(new AssocPlantesRecommandee(
									this.lstPlantesDevEtreSemees.get(i).getNom(), 
									this.lstPlantesDevEtreSemees.get(j).getNom()))) {
						continue;
					}
					nbAssociationsRecommandeesAuSensCommunDansPlantesASemerrr++;
					setSimplifieAssocsPlantesRecommandees.add(
							new AssocPlantesRecommandee(
									this.lstPlantesDevEtreSemees.get(i).getNom(), 
									this.lstPlantesDevEtreSemees.get(j).getNom()));
				}

			}
			
		}
		this.setAssocsPlantesRecommandeesAuSensCommun = setSimplifieAssocsPlantesRecommandees;
		this.nbAssociationsRecommandeesAuSensCommunDansPlantesASemer = nbAssociationsRecommandeesAuSensCommunDansPlantesASemerrr;
		
	}
	
	public void logAssocsRecommandeesPlantesDevEtreSemees() {
		
		LOGGER.info("nbAssociationsRecommandeesAuSensCommunDansPlantesASemer: {}", this.nbAssociationsRecommandeesAuSensCommunDansPlantesASemer);
		for (AssocPlantesRecommandee lAssocPlantesRecommandee : this.setAssocsPlantesRecommandeesAuSensCommun) {

			LOGGER.info(lAssocPlantesRecommandee.toStringSpecial());
			
					
		}

	}
	
	public void buildAssocsInterditesPlantes() {
		
		int nbAssocsInterditesDansPlantesASemer = 0;
		AssociationsPlantes lAssociationsPlantes = ParamsPotager.getInstance().getAssocsPlantes();
		Set<AssocPlantesInterdite> setAssocsPlantesInterditessssssss = lAssociationsPlantes.getSetAssocsPlantesInterdites();
		int nbPlantesDevEtrSemees = this.lstPlantesDevEtreSemees.size();
		this.tabAssocsInterditesPlantes = new int[nbPlantesDevEtrSemees][];
		for (int i=0; i<  nbPlantesDevEtrSemees; i++) {
			this.tabAssocsInterditesPlantes[i] = new int[nbPlantesDevEtrSemees];
		}
		for (int i=0; i<  nbPlantesDevEtrSemees; i++) {
			PlanteDevEtreSemee lPlanteDevEtreSemee1 = this.lstPlantesDevEtreSemees.get(i);
			if (i !=lPlanteDevEtreSemee1.getNumero()) {
				VariousUtils.harakiri("STALINGRAD COTE ALLEMAND. HARAKIRI. SORTIE IMMEDIATE");
			}
		
			for (int j=0; j<  nbPlantesDevEtrSemees; j++) {
				PlanteDevEtreSemee lPlanteDevEtreSemee2 = this.lstPlantesDevEtreSemees.get(j);
				if (j!=lPlanteDevEtreSemee2.getNumero()) {
					VariousUtils.harakiri("STALINGRAD COTE ALLEMAND. HARAKIRI. SORTIE IMMEDIATE");
				}
				
				if (setAssocsPlantesInterditessssssss.contains(
						new AssocPlantesInterdite(lPlanteDevEtreSemee1.getNom(),lPlanteDevEtreSemee2.getNom())
								)) {
							this.tabAssocsInterditesPlantes[i][j] = 1;
							nbAssocsInterditesDansPlantesASemer++;
				} else {
					this.tabAssocsInterditesPlantes[i][j] = 0;
				}
			}
		}
		this.nbAssociationsInterditesDansPlantesASemer = nbAssocsInterditesDansPlantesASemer;
		Set<AssocPlantesInterdite> setSimplifieAssocsPlantesInterdites = new HashSet<>();
		int nbAssociationsInterditesAuSensCommunDansPlantesASemerrrr = 0;
		for (int i=0; i<  nbPlantesDevEtrSemees; i++) {
			for (int j=i; j<  nbPlantesDevEtrSemees; j++) {
				if (this.tabAssocsInterditesPlantes[i][j] == 1) {
					if (setSimplifieAssocsPlantesInterdites.contains(new AssocPlantesInterdite(
									this.lstPlantesDevEtreSemees.get(i).getNom(), 
									this.lstPlantesDevEtreSemees.get(j).getNom()))) {
						continue;
					}
					nbAssociationsInterditesAuSensCommunDansPlantesASemerrrr++;
					setSimplifieAssocsPlantesInterdites.add(
							new AssocPlantesInterdite(
									this.lstPlantesDevEtreSemees.get(i).getNom(), 
									this.lstPlantesDevEtreSemees.get(j).getNom()));
				}

			}
			
		}
		this.setAssocsPlantesInterditesAuSensCommun = setSimplifieAssocsPlantesInterdites;
		this.nbAssociationsInterditesAuSensCommunDansPlantesASemer = nbAssociationsInterditesAuSensCommunDansPlantesASemerrrr;
		
	}
	
	
	public void logAssocsInterditesPlantesDevEtreSemees() {
		
		LOGGER.info("nbAssociationsInterditesAuSensCommunDansPlantesASemer: {}", this.nbAssociationsInterditesAuSensCommunDansPlantesASemer);
		for (AssocPlantesInterdite lAssocPlantesInterdite : this.setAssocsPlantesInterditesAuSensCommun) {

			LOGGER.info(lAssocPlantesInterdite.toStringSpecial());
			
					
		}

	}

	public void verifDouble(String name, double val, double binf, double bsup) throws InputDataPotagerBernardException {
		if ((val < binf) || (val > bsup)) {
			StringBuilder sbZeError = new StringBuilder("Erreur sur ");
			sbZeError.append(name);
			sbZeError.append(": ");
			sbZeError.append(String.format("%.2f", val));
			sbZeError.append(". Valeur attendue: entre ");
			sbZeError.append(String.format("%.2f", binf));
			sbZeError.append(" et ");
			sbZeError.append(String.format("%.2f", bsup));
			sbZeError.append(".");
			String zeError = sbZeError.toString();
			LOGGER.error(zeError);
			throw new InputDataPotagerBernardException(zeError);

		}		
	}

	public String toStringSpecial() {
		StringBuilder sb = new StringBuilder("DONNEES D'ENTREE:");
		sb.append(System.lineSeparator());
		appendIntegerValue(sb, "Nombre de plantes à semer", this.lstPlantesDevEtreSemees.size(), "");
		for (PlanteDevEtreSemee lPlante : this.lstPlantesDevEtreSemees) {
			sb.append("Plante ");
			sb.append(String.valueOf(lPlante.getNumero()));
			sb.append(":  ");
			appendStringValueSansFinDeLigne(sb, lPlante.getNom());
			appendEspace(sb);
			appendStringValueSansFinDeLigne(sb, "(fam = ");
			appendStringValueSansFinDeLigne(sb, lPlante.getFamille());
			appendStringValue(sb, ")");
		}
		appendIntegerValue(sb, "Nombre d'associations interdites dans les plantes à semer", this.nbAssociationsInterditesDansPlantesASemer, "");
		appendIntegerValue(sb, "Nombre d'associations interdites (au sens commun) dans les plantes à semer", this.nbAssociationsInterditesAuSensCommunDansPlantesASemer, "");
		for (AssocPlantesInterdite lAssocPlantesInterdite : this.setAssocsPlantesInterditesAuSensCommun) {
			appendStringValue(sb, lAssocPlantesInterdite.toStringSpecial());
			
					
		}
		appendIntegerValue(sb, "Nombre d'associations recommandées dans les plantes à semer", this.nbAssociationsRecommandeesDansPlantesASemer, "");
		appendIntegerValue(sb, "Nombre d'associations recommandées (au sens commun) dans les plantes à semer", this.nbAssociationsRecommandeesAuSensCommunDansPlantesASemer, "");
		for (AssocPlantesRecommandee lAssocPlantesRecommandee : this.setAssocsPlantesRecommandeesAuSensCommun) {
			appendStringValue(sb, lAssocPlantesRecommandee.toStringSpecial());
			
					
		}
		return (sb.toString());
	}


	public List<PlanteDevEtreSemee> getLstPlantesDevEtreSemees() {
		return lstPlantesDevEtreSemees;
	}


	public void setLstPlantesDevEtreSemees(List<PlanteDevEtreSemee> lstPlantesDevEtreSemees) {
		this.lstPlantesDevEtreSemees = lstPlantesDevEtreSemees;
	}

	public int[][] getTabAssocsInterditesPlantes() {
		return tabAssocsInterditesPlantes;
	}

	public void setTabAssocsInterditesPlantes(int[][] tabAssocsInterditesPlantes) {
		this.tabAssocsInterditesPlantes = tabAssocsInterditesPlantes;
	}

	public int getNbAssociationsInterditesDansPlantesASemer() {
		return nbAssociationsInterditesDansPlantesASemer;
	}

	public void setNbAssociationsInterditesDansPlantesASemer(int nbAssociationsInterditesDansPlantesASemer) {
		this.nbAssociationsInterditesDansPlantesASemer = nbAssociationsInterditesDansPlantesASemer;
	}

	public int[][] getTabAssocsRecommandeesPlantes() {
		return tabAssocsRecommandeesPlantes;
	}

	public void setTabAssocsRecommandeesPlantes(int[][] tabAssocsRecommandeesPlantes) {
		this.tabAssocsRecommandeesPlantes = tabAssocsRecommandeesPlantes;
	}

	public int getNbAssociationsRecommandeesDansPlantesASemer() {
		return nbAssociationsRecommandeesDansPlantesASemer;
	}

	public void setNbAssociationsRecommandeesDansPlantesASemer(int nbAssociationsRecommandeesDansPlantesASemer) {
		this.nbAssociationsRecommandeesDansPlantesASemer = nbAssociationsRecommandeesDansPlantesASemer;
	}

	
	

	


	
	

}
