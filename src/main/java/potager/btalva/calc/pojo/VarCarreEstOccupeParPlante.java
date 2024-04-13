package potager.btalva.calc.pojo;

import ilog.concert.IloException;
import ilog.concert.IloIntVar;
import ilog.cplex.IloCplex;

public class VarCarreEstOccupeParPlante {
	
	private static final String PREFIX_VAR = "cep__";
	
	
	private int indiceBac;
	private int indiceCarreDansBac;
	private int indicePlante;
	
	private IloIntVar cplxAffectation;

	public VarCarreEstOccupeParPlante(int indiceBac, int indiceCarreDansBac, int indicePlante, IloCplex pIloCplex) throws IloException {
		super();
		
		this.indiceBac = indiceBac;
		this.indiceCarreDansBac = indiceCarreDansBac;
		this.indicePlante = indicePlante;
		
		this.cplxAffectation = pIloCplex.boolVar(PREFIX_VAR+"b"+indiceBac+"_c"+indiceCarreDansBac+"_p"+indicePlante);
	}

	
	

	public int getIndiceBac() {
		return indiceBac;
	}




	public void setIndiceBac(int indiceBac) {
		this.indiceBac = indiceBac;
	}




	public int getIndiceCarreDansBac() {
		return indiceCarreDansBac;
	}




	public void setIndiceCarreDansBac(int indiceCarreDansBac) {
		this.indiceCarreDansBac = indiceCarreDansBac;
	}




	public int getIndicePlante() {
		return indicePlante;
	}




	public void setIndicePlante(int indicePlante) {
		this.indicePlante = indicePlante;
	}




	public IloIntVar getCplxAffectation() {
		return cplxAffectation;
	}

	public void setCplxAffectation(IloIntVar cplxAffectation) {
		this.cplxAffectation = cplxAffectation;
	}



	

}
