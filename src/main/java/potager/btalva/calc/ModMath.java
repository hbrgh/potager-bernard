package potager.btalva.calc;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ilog.concert.IloException;
import ilog.concert.IloNumExpr;
import potager.btalva.calc.pojo.VarCarreEstOccupeParPlante;
import potager.btalva.calc.pojo.VarIndicNbAssocRecommandeesBac;
import potager.btalva.mng.DataPotager;
import potager.btalva.mng.ParamsPotager;
import potager.btalva.pojo.BacsParametresPotager;

public class ModMath extends MotherModMath {

	private static Logger LOGGER = LoggerFactory.getLogger(ModMath.class);
	
	private List<VarCarreEstOccupeParPlante> listeCepVars;
	private List<VarIndicNbAssocRecommandeesBac> listeIndicNarbVars;
	
	public ModMath() {
		super();
	}
	
	
	
	public List<VarCarreEstOccupeParPlante> getListeCepVars() {
		return listeCepVars;
	}



	public void setListeCepVars(List<VarCarreEstOccupeParPlante> listeCepVars) {
		this.listeCepVars = listeCepVars;
	}




	public List<VarIndicNbAssocRecommandeesBac> getListeIndicNarbVars() {
		return listeIndicNarbVars;
	}


	

	public void setListeIndicNarbVars(List<VarIndicNbAssocRecommandeesBac> listeIndikkNarbVars) {
		this.listeIndicNarbVars = listeIndikkNarbVars;
	}

	public void creerFonctionObjectifMaximizeIndicNbAssocsPlantesRecommandees() throws IloException {
		
		IloNumExpr lSumObjsParBac = this.cplexPb.numExpr();
		for (VarIndicNbAssocRecommandeesBac lVarNbAssocRecommandeesBac : this.listeIndicNarbVars) {
			
			lSumObjsParBac = this.cplexPb.sum(lSumObjsParBac,
					lVarNbAssocRecommandeesBac.getCplxAffectation());
			
		}

		this.cplexPb.addMaximize(lSumObjsParBac);
	}


	public void creerVarsCarresOccupesParPlantes() throws IloException {
		
		List<VarCarreEstOccupeParPlante> lstCepVars = new ArrayList<VarCarreEstOccupeParPlante>();
		BacsParametresPotager lBacParametresPotager = ParamsPotager.getInstance().getBacsParams();
		int nbPlantesDevEtreSemees = DataPotager.getInstance().getInputDataPotagerBernard().getLstPlantesDevEtreSemees().size();
		int nbBacs = lBacParametresPotager.getNbBacs();
		int nbMaxCarresParBac = lBacParametresPotager.getNbMaxCarresParBac();
		for (int b = 0; b < nbBacs; b++) {
			for (int c = 0; c < nbMaxCarresParBac; c++) {
				for (int p = 0; p < nbPlantesDevEtreSemees; p++) {
					VarCarreEstOccupeParPlante lVarCarreEstOccupeParPlante = 
							new VarCarreEstOccupeParPlante(b, c, p, this.getCplexPb());
					lstCepVars.add(lVarCarreEstOccupeParPlante);
				}
			}
		}
		
		this.listeCepVars = lstCepVars;

		LOGGER.info("Dans creerVarsCarresOccupesParPlantes() ==> Nb Variables d'affectation booléennes créées: {}", this.listeCepVars.size());




	}

	public void creerVarsIndicsNbAssocRecommandeesBacs() throws IloException {
		
		List<VarIndicNbAssocRecommandeesBac> lstIndicNarbVars = new ArrayList<VarIndicNbAssocRecommandeesBac>();
		BacsParametresPotager lBacParametresPotager = ParamsPotager.getInstance().getBacsParams();
		
		int nbBacs = lBacParametresPotager.getNbBacs();
		
		for (int b = 0; b < nbBacs; b++) {
			VarIndicNbAssocRecommandeesBac lVarNbAssocRecommandeesBac = new VarIndicNbAssocRecommandeesBac(b, this.getCplexPb());
			lstIndicNarbVars.add(lVarNbAssocRecommandeesBac);
			
			
		}
		
		this.listeIndicNarbVars = lstIndicNarbVars;

		LOGGER.info("Dans creerVarsIndicsNbAssocRecommandeesBacs() ==> Nb Variables entières créées: {}", this.listeIndicNarbVars.size());




	}

}
