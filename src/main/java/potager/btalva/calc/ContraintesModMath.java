package potager.btalva.calc;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ilog.concert.IloException;
import ilog.concert.IloNumExpr;
import ilog.cplex.IloCplex;
import potager.btalva.calc.pojo.VarCarreEstOccupeParPlante;
import potager.btalva.calc.pojo.VarIndicNbAssocRecommandeesBac;
import potager.btalva.mng.DataPotager;
import potager.btalva.mng.ParamsPotager;
import potager.btalva.pojo.AssocPlantesDevEtreSemeesInterdite;
import potager.btalva.pojo.AssocPlantesDevEtreSemeesRecommandee;
import potager.btalva.pojo.AssocPlantesInterdite;
import potager.btalva.pojo.AssocPlantesRecommandee;
import potager.btalva.pojo.BacsParametresPotager;
import potager.btalva.pojo.PlanteDevEtreSemee;



public class ContraintesModMath {
	
	private static Logger LOGGER = LoggerFactory.getLogger(ContraintesModMath.class);


	private ModMath modeleMathematique;
	
	private int nbContraintesPasPlusDeNbMaxCarresParBacPlantesParBac = 0;
	
	private int nbContraintesToutesLesPlantesDoiventEtreSemeesEtUnePlanteVaDansUnSeulBac = 0;
	
	private int nbContraintesDefTableauIndicNbAssocRecommandeesBac = 0;
	
	
	private int nbContraintesPlantesDansUnBacDoiventEtreCompatibles = 0;

	public ContraintesModMath(ModMath modeleMathematique) {
		super();
		this.modeleMathematique = modeleMathematique;
	}

	public ModMath getModeleMathematique() {
		return modeleMathematique;
	}

	public void setModeleMathematique(ModMath modeleMathematique) {
		this.modeleMathematique = modeleMathematique;
	}
	
	
	
	public int getNbContraintesPasPlusDeNbMaxCarresParBacPlantesParBac() {
		return nbContraintesPasPlusDeNbMaxCarresParBacPlantesParBac;
	}

	public void setNbContraintesPasPlusDeNbMaxCarresParBacPlantesParBac(
			int nbContraintesPasPlusDeNbMaxCarresParBacPlantesParBac) {
		this.nbContraintesPasPlusDeNbMaxCarresParBacPlantesParBac = nbContraintesPasPlusDeNbMaxCarresParBacPlantesParBac;
	}
	
	

	public int getNbContraintesPlantesDansUnBacDoiventEtreCompatibles() {
		return nbContraintesPlantesDansUnBacDoiventEtreCompatibles;
	}

	public void setNbContraintesPlantesDansUnBacDoiventEtreCompatibles(
			int nbContraintesPlantesDansUnBacDoiventEtreCompatibles) {
		this.nbContraintesPlantesDansUnBacDoiventEtreCompatibles = nbContraintesPlantesDansUnBacDoiventEtreCompatibles;
	}

	
	
	
	public int getNbContraintesToutesLesPlantesDoiventEtreSemeesEtUnePlanteVaDansUnSeulBac() {
		return nbContraintesToutesLesPlantesDoiventEtreSemeesEtUnePlanteVaDansUnSeulBac;
	}

	public void setNbContraintesToutesLesPlantesDoiventEtreSemeesEtUnePlanteVaDansUnSeulBac(
			int nbContraintesToutesLesPlantesDoiventEtreSemeesEtUnePlanteVaDansUnSeulBac) {
		this.nbContraintesToutesLesPlantesDoiventEtreSemeesEtUnePlanteVaDansUnSeulBac = nbContraintesToutesLesPlantesDoiventEtreSemeesEtUnePlanteVaDansUnSeulBac;
	}
	
	
	
	public int getNbContraintesDefTableauIndicNbAssocRecommandeesBac() {
		return nbContraintesDefTableauIndicNbAssocRecommandeesBac;
	}

	public void setNbContraintesDefTableauIndicNbAssocRecommandeesBac(
			int nbContraintesDefTableauIndicNbAssocRecommandeesBac) {
		this.nbContraintesDefTableauIndicNbAssocRecommandeesBac = nbContraintesDefTableauIndicNbAssocRecommandeesBac;
	}

	// contrainte (1)
	// Définition de la variable tableau IndicNbAssocRecommandeesBac,
	// qui donne une idée du nombre effectif d’associations recommandées dans un bac !
	private void creerContraintesDefTableauIndicNbAssocRecommandeesBac(IloCplex pIloCplex) throws IloException {

		int nbConts = 0;
		
		
			
		Set <AssocPlantesRecommandee> setAssocPlantesRecommandee = ParamsPotager.getInstance().getAssocsPlantes().getSetAssocsPlantesRecommandees();
		Set <AssocPlantesDevEtreSemeesRecommandee> setAssocPlantesDevEtreSemeeRecommandee = new HashSet<>();
			
		for (PlanteDevEtreSemee lPlanteDevEtreSemee : DataPotager.getInstance().getInputDataPotagerBernard().getLstPlantesDevEtreSemees()) {
			for (PlanteDevEtreSemee lPlanteDevEtreSemee2 : DataPotager.getInstance().getInputDataPotagerBernard().getLstPlantesDevEtreSemees()) {

				if (lPlanteDevEtreSemee.getNumero() == lPlanteDevEtreSemee2.getNumero()) {
					continue;
				}
				AssocPlantesRecommandee lAssocPlantesRecommandee =
						new AssocPlantesRecommandee(lPlanteDevEtreSemee.getNom(), lPlanteDevEtreSemee2.getNom());
				if (setAssocPlantesRecommandee.contains(lAssocPlantesRecommandee)) {
					AssocPlantesDevEtreSemeesRecommandee lAssocPlantesDevEtreSemeesRecommandee =
							new AssocPlantesDevEtreSemeesRecommandee(
									lPlanteDevEtreSemee.getNumero(),
									lPlanteDevEtreSemee2.getNumero()
									);
					setAssocPlantesDevEtreSemeeRecommandee.add(lAssocPlantesDevEtreSemeesRecommandee);
				}
			}
		}
			
		
		Map<Integer, List<VarCarreEstOccupeParPlante>> map = 
				this.modeleMathematique.getListeCepVars().stream()
				.collect(Collectors.groupingBy(VarCarreEstOccupeParPlante::getIndiceBac));
		for (Entry<Integer, List<VarCarreEstOccupeParPlante>> entry : map.entrySet()) {
			List<VarCarreEstOccupeParPlante> lstCepPourBacCourant = entry.getValue();
			IloNumExpr lSumIndicAssocsRecommPlantesPourLeBacCourant = pIloCplex.numExpr();
			
			Map<Integer, List<VarCarreEstOccupeParPlante>> map2 = 
				lstCepPourBacCourant.stream()
				.collect(Collectors.groupingBy(VarCarreEstOccupeParPlante::getIndicePlante));
			for (Entry<Integer, List<VarCarreEstOccupeParPlante>> entry21 : map2.entrySet()) {
				
				for (Entry<Integer, List<VarCarreEstOccupeParPlante>> entry22 : map2.entrySet()) {
					if (entry21.getKey() >= entry22.getKey()) { // p1 doit être < p2
						continue;
					}
					AssocPlantesDevEtreSemeesRecommandee lAssocPlantesDevEtreSemeesRecommandee =
					new AssocPlantesDevEtreSemeesRecommandee(
							entry21.getKey(),
							entry22.getKey()
							);
					if (setAssocPlantesDevEtreSemeeRecommandee.contains(lAssocPlantesDevEtreSemeesRecommandee)) {
						List<VarCarreEstOccupeParPlante> lstCepPourBacCourantEtPlanteCourante1 = entry21.getValue();
						List<VarCarreEstOccupeParPlante> lstCepPourBacCourantEtPlanteCourante2 = entry22.getValue();
						
						for (VarCarreEstOccupeParPlante lVarCarreEstOccupeParPlante1 : lstCepPourBacCourantEtPlanteCourante1) {
							lSumIndicAssocsRecommPlantesPourLeBacCourant = 
									pIloCplex.sum(lSumIndicAssocsRecommPlantesPourLeBacCourant,
											lVarCarreEstOccupeParPlante1.getCplxAffectation() );
						}
						for (VarCarreEstOccupeParPlante lVarCarreEstOccupeParPlante2 : lstCepPourBacCourantEtPlanteCourante2) {
							lSumIndicAssocsRecommPlantesPourLeBacCourant = 
									pIloCplex.sum(lSumIndicAssocsRecommPlantesPourLeBacCourant,
											lVarCarreEstOccupeParPlante2.getCplxAffectation() );
						}
						
						
					}
					
				

					
					
				
				}
				
	
			
			}
			VarIndicNbAssocRecommandeesBac lVarNbAssocRecommandeesBacPourBacCourant =  
					this.modeleMathematique.getListeIndicNarbVars().get(entry.getKey());
			pIloCplex.addEq(lSumIndicAssocsRecommPlantesPourLeBacCourant, lVarNbAssocRecommandeesBacPourBacCourant.getCplxAffectation());

			nbConts++;
		
		}
		
		LOGGER.info("Nb de contraintes définissant le tableau IndicNbAssocRecommandeesBac créées : {}"
				, nbConts);
		this.nbContraintesDefTableauIndicNbAssocRecommandeesBac = nbConts;




	}

	// contrainte (2)
	// Pas plus de nbMaxCarresParBac plantes par bac !
	private void creerContraintesNbMaxPlantesDansBacs(IloCplex pIloCplex) throws IloException {

		int nbConts = 0;
		
		BacsParametresPotager lBacParametresPotager = ParamsPotager.getInstance().getBacsParams();
	
		int nbMaxCarresParBac = lBacParametresPotager.getNbMaxCarresParBac();
		
		
		
		Map<Integer, List<VarCarreEstOccupeParPlante>> map = 
				this.modeleMathematique.getListeCepVars().stream()
				.collect(Collectors.groupingBy(VarCarreEstOccupeParPlante::getIndiceBac));
		for (Entry<Integer, List<VarCarreEstOccupeParPlante>> entry : map.entrySet()) {
			IloNumExpr lSumVarsAffectationPlantesPourBacCourant = pIloCplex.numExpr();
			List<VarCarreEstOccupeParPlante> listeDesVarEstOccupeParPlantePourBacCourant = 
					entry.getValue();
			for (VarCarreEstOccupeParPlante lVarCarreEstOccupeParPlante : listeDesVarEstOccupeParPlantePourBacCourant) {
				lSumVarsAffectationPlantesPourBacCourant = pIloCplex.sum(lSumVarsAffectationPlantesPourBacCourant,
						lVarCarreEstOccupeParPlante.getCplxAffectation());
			}
			pIloCplex.addLe(lSumVarsAffectationPlantesPourBacCourant, nbMaxCarresParBac);
			nbConts++;
		}
		
	

		LOGGER.info("Nb de contraintes d'inégalité de type pas plus de {} plantes par bac créées: {}",
				nbMaxCarresParBac, nbConts);
		this.nbContraintesPasPlusDeNbMaxCarresParBacPlantesParBac = nbConts;

	}
	
	// contrainte (3)
	// Toutes les plantes doivent être semées et une plante donnée va dans un bac et un seul !
	private void creerContraintesToutesLesPlantesSontSemeesEtUnePlanteVaDansUnSeulBac(IloCplex pIloCplex) throws IloException {

		int nbConts = 0;
		
		int nbPlantesDevEtreSemees = DataPotager.getInstance().getInputDataPotagerBernard().getLstPlantesDevEtreSemees().size();

		
	
		
		
		
		Map<Integer, List<VarCarreEstOccupeParPlante>> map = 
				this.modeleMathematique.getListeCepVars().stream()
				.collect(Collectors.groupingBy(VarCarreEstOccupeParPlante::getIndicePlante));
		for (Entry<Integer, List<VarCarreEstOccupeParPlante>> entry : map.entrySet()) {
			IloNumExpr lSumVarsAffectationPlanteCourante = pIloCplex.numExpr();
			List<VarCarreEstOccupeParPlante> listeDesVarEstOccupeParPlantePourPlanteCourante = 
					entry.getValue();
			for (VarCarreEstOccupeParPlante lVarCarreEstOccupeParPlante : listeDesVarEstOccupeParPlantePourPlanteCourante) {
				lSumVarsAffectationPlanteCourante = pIloCplex.sum(lSumVarsAffectationPlanteCourante,
						lVarCarreEstOccupeParPlante.getCplxAffectation());
			}
			pIloCplex.addEq(lSumVarsAffectationPlanteCourante, 1);
			nbConts++;
		}
		
	

		LOGGER.info("Nb de contraintes créées disant que toutes les {} plantes doivent être semées: {}",
				nbPlantesDevEtreSemees, nbConts);
		this.nbContraintesToutesLesPlantesDoiventEtreSemeesEtUnePlanteVaDansUnSeulBac = nbConts;

	}
	

			
	// contrainte (4)
	// Dans un bac, on ne peut planter que des plantes compatibles entre elles !
	private void creerContraintesPlantesCompatiblesDansUnBac(IloCplex pIloCplex) throws IloException {

		int nbConts = 0;
		
		
			
		Set <AssocPlantesInterdite> setAssocPlantesInterdite = ParamsPotager.getInstance().getAssocsPlantes().getSetAssocsPlantesInterdites();
		Set <AssocPlantesDevEtreSemeesInterdite> setAssocPlantesDevEtreSemeeInterdite = new HashSet<>();
			
		for (PlanteDevEtreSemee lPlanteDevEtreSemee : DataPotager.getInstance().getInputDataPotagerBernard().getLstPlantesDevEtreSemees()) {
			for (PlanteDevEtreSemee lPlanteDevEtreSemee2 : DataPotager.getInstance().getInputDataPotagerBernard().getLstPlantesDevEtreSemees()) {
				if (lPlanteDevEtreSemee.getNom().equalsIgnoreCase(lPlanteDevEtreSemee2.getNom())) {
					continue;
				}
				AssocPlantesInterdite lAssocPlantesInterdite =
						new AssocPlantesInterdite(lPlanteDevEtreSemee.getNom(), lPlanteDevEtreSemee2.getNom());
				if (setAssocPlantesInterdite.contains(lAssocPlantesInterdite)) {
					AssocPlantesDevEtreSemeesInterdite lAssocPlantesDevEtreSemeesInterdite =
							new AssocPlantesDevEtreSemeesInterdite(
									lPlanteDevEtreSemee.getNumero(),
									lPlanteDevEtreSemee2.getNumero()
									);
					setAssocPlantesDevEtreSemeeInterdite.add(lAssocPlantesDevEtreSemeesInterdite);
				}
			}
		}
			
		
		Map<Integer, List<VarCarreEstOccupeParPlante>> map = 
				this.modeleMathematique.getListeCepVars().stream()
				.collect(Collectors.groupingBy(VarCarreEstOccupeParPlante::getIndiceBac));
		for (Entry<Integer, List<VarCarreEstOccupeParPlante>> entry : map.entrySet()) {
			List<VarCarreEstOccupeParPlante> lstCepPourBacCourant = entry.getValue();
			
			Map<Integer, List<VarCarreEstOccupeParPlante>> map2 = 
				lstCepPourBacCourant.stream()
				.collect(Collectors.groupingBy(VarCarreEstOccupeParPlante::getIndicePlante));
			for (Entry<Integer, List<VarCarreEstOccupeParPlante>> entry21 : map2.entrySet()) {
				
				for (Entry<Integer, List<VarCarreEstOccupeParPlante>> entry22 : map2.entrySet()) {
					if (entry21.getKey() >= entry22.getKey()) { // p1 doit être < p2
						continue;
					}
					AssocPlantesDevEtreSemeesInterdite lAssocPlantesDevEtreSemeesInterdite =
					new AssocPlantesDevEtreSemeesInterdite(
							entry21.getKey(),
							entry22.getKey()
							);
					if (setAssocPlantesDevEtreSemeeInterdite.contains(lAssocPlantesDevEtreSemeesInterdite)) {
						List<VarCarreEstOccupeParPlante> lstCepPourBacCourantEtPlanteCourante1 = entry21.getValue();
						List<VarCarreEstOccupeParPlante> lstCepPourBacCourantEtPlanteCourante2 = entry22.getValue();
						IloNumExpr lSumPlante1Plante2DansLeBacCourant = pIloCplex.numExpr();
						for (VarCarreEstOccupeParPlante lVarCarreEstOccupeParPlante1 : lstCepPourBacCourantEtPlanteCourante1) {
							lSumPlante1Plante2DansLeBacCourant = 
									pIloCplex.sum(lSumPlante1Plante2DansLeBacCourant,
											lVarCarreEstOccupeParPlante1.getCplxAffectation() );
						}
						for (VarCarreEstOccupeParPlante lVarCarreEstOccupeParPlante2 : lstCepPourBacCourantEtPlanteCourante2) {
							lSumPlante1Plante2DansLeBacCourant = 
									pIloCplex.sum(lSumPlante1Plante2DansLeBacCourant,
											lVarCarreEstOccupeParPlante2.getCplxAffectation() );
						}
						pIloCplex.addLe(lSumPlante1Plante2DansLeBacCourant, 1);
						nbConts++;
						
					}
					
				

					
					
				
				}
				
	
			
			}
		
		}
		
		LOGGER.info("Nb de contraintes créées disant que toutes les plantes d'un bac doivent être compatibles: {}"
				, nbConts);
		this.nbContraintesPlantesDansUnBacDoiventEtreCompatibles = nbConts;




	}
		
	
	public void creerContraintesModMathComplet() throws IloException {
		creerContraintesNbMaxPlantesDansBacs(this.getModeleMathematique().getCplexPb());
		creerContraintesToutesLesPlantesSontSemeesEtUnePlanteVaDansUnSeulBac(this.getModeleMathematique().getCplexPb());
		creerContraintesPlantesCompatiblesDansUnBac(this.getModeleMathematique().getCplexPb());
		creerContraintesDefTableauIndicNbAssocRecommandeesBac(this.getModeleMathematique().getCplexPb());

	}

}
