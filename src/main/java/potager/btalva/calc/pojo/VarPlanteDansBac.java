package potager.btalva.calc.pojo;

import ilog.concert.IloException;
import ilog.concert.IloIntVar;
import ilog.cplex.IloCplex;

public class VarPlanteDansBac {
	
	private static final String PREFIX_VAR = "pb__";
	
	
	
	private int indicePlante;
	private int indiceBac;
	
	private IloIntVar cplxAffectation;

	public VarPlanteDansBac(int indicePlante, int indiceBac, IloCplex pIloCplex) throws IloException {
		super();
		
	
		
		this.indicePlante = indicePlante;
		this.indiceBac = indiceBac;
		this.cplxAffectation = pIloCplex.boolVar(PREFIX_VAR+"p"+indicePlante+"_b"+indiceBac);
	}

	
	

	public int getIndiceBac() {
		return indiceBac;
	}




	public void setIndiceBac(int indiceBac) {
		this.indiceBac = indiceBac;
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
