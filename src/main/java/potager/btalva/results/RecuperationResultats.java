package potager.btalva.results;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ilog.concert.IloException;
import ilog.cplex.IloCplex.UnknownObjectException;
import potager.btalva.calc.ContraintesModMath;
import potager.btalva.calc.ModMath;
import potager.btalva.calc.pojo.VarCarreEstOccupeParPlante;
import potager.btalva.calc.pojo.VarIndicNbAssocRecommandeesBac;
import potager.btalva.mng.DataPotager;
import potager.btalva.mng.ParamsPotager;
import potager.btalva.pojo.AssocPlantesRecommandee;
import potager.btalva.pojo.BacsParametresPotager;
import potager.btalva.pojo.PlanteDevEtreSemee;



public class RecuperationResultats {
	
	private static Logger LOGGER = LoggerFactory.getLogger(ContraintesModMath.class);

	private ModMath modeleMathematique;
	
	private List<ResultatsBac> listeResultatsBac;
	
	
		
		
	
	
	public RecuperationResultats(ModMath modeleMathematique) {
		super();
		this.modeleMathematique = modeleMathematique;
		this.listeResultatsBac = new ArrayList<>();
		
		BacsParametresPotager lBacParametresPotager = ParamsPotager.getInstance().getBacsParams();
	
		int nbBacs = lBacParametresPotager.getNbBacs();
		for (int b = 0; b <nbBacs; b++ ) {
			this.listeResultatsBac.add(new ResultatsBac(b));
		}
		
	}
	
	public void ecrireResultatsPourTousLesBacs() {
		for (ResultatsBac lResultatsBac : this.listeResultatsBac) {
			LOGGER.info(lResultatsBac.toStringSpecial());
		}
	}
	
	
			
	public void renseignerResultatsPourTousLesBacs() throws UnknownObjectException, IloException {
		
		
		Map<Integer, List<VarCarreEstOccupeParPlante>> map = 
				this.modeleMathematique.getListeCepVars().stream()
				.collect(Collectors.groupingBy(VarCarreEstOccupeParPlante::getIndiceBac));
		for (Entry<Integer, List<VarCarreEstOccupeParPlante>> entry : map.entrySet()) {
			
			List<VarCarreEstOccupeParPlante> listeDesVarEstOccupeParPlantePourBacCourant = 
					entry.getValue();
			int bac = entry.getKey();
			renseignerResultatsPourBac(bac, listeDesVarEstOccupeParPlantePourBacCourant);
			LOGGER.info("renseignerResultatsPourBac(): bac {} traité", bac);
			
		}
	}
	
	private int compterNbarSensCommun(List<VarCarreEstOccupeParPlante> listeCepVarsPourBac, boolean pecPlantesMemeNom) throws UnknownObjectException, IloException {
		Set <AssocPlantesRecommandee> setAssocPlantesRecommandee = ParamsPotager.getInstance().getAssocsPlantes().getSetAssocsPlantesRecommandees();

		List<PlanteDevEtreSemee> lstPdes = DataPotager.getInstance().getInputDataPotagerBernard().getLstPlantesDevEtreSemees();
		Set<AssocPlantesRecommNumeros> setNbArSensComm = new HashSet<>();
		int nbVarsCarreEstOccupeParPlante = listeCepVarsPourBac.size();
		for (int iVar = 0;  iVar < nbVarsCarreEstOccupeParPlante; iVar++) {
			VarCarreEstOccupeParPlante lVarCarreEstOccupeParPlante = listeCepVarsPourBac.get(iVar);
			for (int iVar2 = 0;  iVar2 < nbVarsCarreEstOccupeParPlante; iVar2++) {
				VarCarreEstOccupeParPlante lVarCarreEstOccupeParPlante2 = listeCepVarsPourBac.get(iVar2);
				if (lVarCarreEstOccupeParPlante2.getIndiceCarreDansBac() == lVarCarreEstOccupeParPlante.getIndiceCarreDansBac()) {
					continue;
				}
				if (lVarCarreEstOccupeParPlante2.getIndicePlante() == lVarCarreEstOccupeParPlante.getIndicePlante()) {
					continue;
				}
				PlanteDevEtreSemee lPdes = lstPdes.get(lVarCarreEstOccupeParPlante.getIndicePlante());
				PlanteDevEtreSemee lPdes2 = lstPdes.get(lVarCarreEstOccupeParPlante2.getIndicePlante());
				if (pecPlantesMemeNom == false) {
					if (lPdes.getNom().equalsIgnoreCase(lPdes2.getNom())) {
						continue;
					}				
				}

				if (lPdes.getNumero() >= lPdes2.getNumero()) {
					continue;
				}
				AssocPlantesRecommandee lAssocPlantesRecommandeeP1P2 =
						new AssocPlantesRecommandee(lPdes.getNom(), lPdes2.getNom());
				if (setAssocPlantesRecommandee.contains(lAssocPlantesRecommandeeP1P2) == false) {
					continue;
				}
				boolean carreOccupeParPlante = 
						(Math.round(this.modeleMathematique.getCplexPb().getValue(lVarCarreEstOccupeParPlante.getCplxAffectation())) == 1);
				if (carreOccupeParPlante) {
					boolean carre2OccupeParPlante2 = 
							(Math.round(this.modeleMathematique.getCplexPb().getValue(lVarCarreEstOccupeParPlante.getCplxAffectation())) == 1);
					if (carre2OccupeParPlante2) {
						setNbArSensComm.add(new AssocPlantesRecommNumeros(lPdes.getNumero(), lPdes2.getNumero()));
					}
				}
				
			}
		}
		return(setNbArSensComm.size());
	}
	
	public void renseignerResultatsPourBac(int bac, List<VarCarreEstOccupeParPlante> listeCepVarsPourBac) throws UnknownObjectException, IloException {

		
				
				
		List<PlanteDevEtreSemee> lstPdes = DataPotager.getInstance().getInputDataPotagerBernard().getLstPlantesDevEtreSemees();
		ResultatsBac lResultatsBac = this.getListeResultatsBac().get(bac);
		int	nbVarsCarreEstOccupeParPlante = listeCepVarsPourBac.size();
		for (int iVar = 0;  iVar < nbVarsCarreEstOccupeParPlante; iVar++) {
			VarCarreEstOccupeParPlante lVarCarreEstOccupeParPlante = listeCepVarsPourBac.get(iVar);

			boolean carreOccupeParPlanteCourante = 
					(Math.round(this.modeleMathematique.getCplexPb().getValue(lVarCarreEstOccupeParPlante.getCplxAffectation())) == 1);
			if (carreOccupeParPlanteCourante) {
				PlanteDevEtreSemee lModelPdes = lstPdes.get(lVarCarreEstOccupeParPlante.getIndicePlante());
				lResultatsBac.getListePlantesASemer().add(new PlanteDevEtreSemee(lModelPdes));				
			}

		}
		VarIndicNbAssocRecommandeesBac lVarNbAssocRecommandeesBac = this.modeleMathematique.getListeIndicNarbVars().get(bac);
		long val = Math.round(this.modeleMathematique.getCplexPb().getValue(lVarNbAssocRecommandeesBac.getCplxAffectation()));
		lResultatsBac.setIndicNbAssocsEffectivesRecomm((int)val);
		
		

		// compter NBAR sens commun			
		int nbArSensComm = compterNbarSensCommun(listeCepVarsPourBac, false);
		int nbArSensCommPecPmn = compterNbarSensCommun(listeCepVarsPourBac, true);
		lResultatsBac.setNbAssocsEffectivesRecommSensCommun(nbArSensComm);
		lResultatsBac.setNbAssocsEffectivesRecommSensCommunAvecPlantesDeMemeNomPec(nbArSensCommPecPmn);
		
		
		
	}

	public ModMath getModeleMathematique() {
		return modeleMathematique;
	}

	public void setModeleMathematique(ModMath modeleMathematique) {
		this.modeleMathematique = modeleMathematique;
	}

	public List<ResultatsBac> getListeResultatsBac() {
		return listeResultatsBac;
	}

	public void setListeResultatsBac(List<ResultatsBac> listeResultatsBac) {
		this.listeResultatsBac = listeResultatsBac;
	}

	
	
}
