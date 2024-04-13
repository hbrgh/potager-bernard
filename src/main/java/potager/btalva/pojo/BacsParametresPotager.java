package potager.btalva.pojo;

import potager.btalva.mng.ParamsInputUtils;

public class BacsParametresPotager extends MotherParams{
	
	
	
	private int nbBacs;
	private int nbMaxCarresParBac;
	
	public BacsParametresPotager() {
		super();
		
	}
	
	
	

	
	
	public String toStringSpecial() {
		
		StringBuilder sb = new StringBuilder();

		appendParam(sb, ParamsInputUtils.NOM_PARAM_NB_BACS_DANS_ONGLET_BACS,
				this.nbBacs);
		
		appendParam(sb, ParamsInputUtils.NOM_PARAM_NB_MAX_CARRES_PAR_BAC_DANS_ONGLET_BACS,
				this.nbMaxCarresParBac);
		
		return sb.toString();	
	}



	


	public int getNbBacs() {
		return nbBacs;
	}


	public void setNbBacs(int nbBacs) {
		this.nbBacs = nbBacs;
	}


	public int getNbMaxCarresParBac() {
		return nbMaxCarresParBac;
	}


	public void setNbMaxCarresParBac(int nbMaxCarresParBac) {
		this.nbMaxCarresParBac = nbMaxCarresParBac;
	}



	
	
	

}
