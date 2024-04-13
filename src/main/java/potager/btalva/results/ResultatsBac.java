package potager.btalva.results;

import java.util.ArrayList;
import java.util.List;

import potager.btalva.pojo.PlanteDevEtreSemee;

public class ResultatsBac {
	
	protected static final String ESPACE_ENTRE_DATA = "    ";
	
	private int numeroBac;
	private List<PlanteDevEtreSemee> listePlantesASemer;
	private int indicNbAssocsEffectivesRecomm;
	private int nbAssocsEffectivesRecommSensCommunAvecPlantesDeMemeNomPec;
	private int nbAssocsEffectivesRecommSensCommun;
	
	public ResultatsBac(int numeroBac) {
		super();
		this.numeroBac = numeroBac;
		this.listePlantesASemer = new ArrayList<>();
	}

	public int getNumeroBac() {
		return numeroBac;
	}

	public void setNumeroBac(int numeroBac) {
		this.numeroBac = numeroBac;
	}

	public List<PlanteDevEtreSemee> getListePlantesASemer() {
		return listePlantesASemer;
	}

	public void setListePlantesASemer(List<PlanteDevEtreSemee> listePlantesASemer) {
		this.listePlantesASemer = listePlantesASemer;
	}
	
	
	
	public int getIndicNbAssocsEffectivesRecomm() {
		return indicNbAssocsEffectivesRecomm;
	}

	public void setIndicNbAssocsEffectivesRecomm(int indicNbAssocsEffectivesRecomm) {
		this.indicNbAssocsEffectivesRecomm = indicNbAssocsEffectivesRecomm;
	}
	
	

	public int getNbAssocsEffectivesRecommSensCommunAvecPlantesDeMemeNomPec() {
		return nbAssocsEffectivesRecommSensCommunAvecPlantesDeMemeNomPec;
	}

	public void setNbAssocsEffectivesRecommSensCommunAvecPlantesDeMemeNomPec(
			int nbAssocsEffectivesRecommSensCommunAvecPlantesDeMemeNomPec) {
		this.nbAssocsEffectivesRecommSensCommunAvecPlantesDeMemeNomPec = nbAssocsEffectivesRecommSensCommunAvecPlantesDeMemeNomPec;
	}

	public int getNbAssocsEffectivesRecommSensCommun() {
		return nbAssocsEffectivesRecommSensCommun;
	}

	public void setNbAssocsEffectivesRecommSensCommun(int nbAssocsEffectivesRecommSensCommun) {
		this.nbAssocsEffectivesRecommSensCommun = nbAssocsEffectivesRecommSensCommun;
	}

	protected void appendResultDoubleValue(StringBuilder pSb, String name, 
			double val, String suffix) {
		pSb.append(name);
		pSb.append(" = ");
		pSb.append(String.format( "%.2f", val ));
		if (suffix.length() > 0) {
			pSb.append(suffix);
		}
		pSb.append(System.lineSeparator());
	}
	
	protected void appendResultDoubleValueSansFinDeLigne(StringBuilder pSb, String name, 
			double val, String suffix) {
		pSb.append(name);
		pSb.append(" = ");
		pSb.append(String.format( "%.2f", val ));
		if (suffix.length() > 0) {
			pSb.append(suffix);
		}
		
	}
	
	protected void appendResultIntegerValue(StringBuilder pSb, String name, 
			int val, String suffix) {
		pSb.append(name);
		pSb.append(" = ");
		pSb.append(String.valueOf(val));
		if (suffix.length() > 0) {
			pSb.append(suffix);
		}
		pSb.append(System.lineSeparator());
	}
	
	

	protected void appendResultStringValue(StringBuilder pSb, String val) {
		pSb.append(val);
		
		pSb.append(System.lineSeparator());
	}
	
	protected void appendResultStringValueSansFinDeLigne(StringBuilder pSb, String val) {
		pSb.append(val);
		
		
	}
	
	protected void appendEspace(StringBuilder pSb) {
		pSb.append(ESPACE_ENTRE_DATA);
		
		
	}
	
	
	public String toStringSpecial() {
		StringBuilder sb = new StringBuilder("Bac numéro:");
		sb.append(this.numeroBac);
		sb.append(System.lineSeparator());
		
		for (PlanteDevEtreSemee lPlante : this.listePlantesASemer) {
			sb.append("Plante ");
			sb.append(String.valueOf(lPlante.getNumero()));
			sb.append(":  ");
			appendResultStringValue(sb, lPlante.getNom());
			
		}
		sb.append(System.lineSeparator());
		appendResultIntegerValue(sb, "NBAR (sens commun): ", this.nbAssocsEffectivesRecommSensCommun, "");
		appendResultIntegerValue(sb, "NBAR (sens commun en comptant les plantes de même nom): ", this.nbAssocsEffectivesRecommSensCommunAvecPlantesDeMemeNomPec, "");
		return (sb.toString());
	}
	
	

}
