package potager.btalva.calc.pojo;

import ilog.concert.IloException;
import ilog.concert.IloIntVar;
import ilog.cplex.IloCplex;

public class VarIndicNbAssocRecommandeesBac {
	
	private static final String PREFIX_VAR = "narb__";
	
	
	

	private int indiceBac;
	
	private IloIntVar cplxAffectation;

	public VarIndicNbAssocRecommandeesBac(int indiceBac, IloCplex pIloCplex) throws IloException {
		super();
		
	
		
	
		this.indiceBac = indiceBac;
		this.cplxAffectation = pIloCplex.intVar(0,1000000,PREFIX_VAR+"b"+indiceBac);
	}

	
	

	public int getIndiceBac() {
		return indiceBac;
	}




	public void setIndiceBac(int indiceBac) {
		this.indiceBac = indiceBac;
	}







	



	public IloIntVar getCplxAffectation() {
		return cplxAffectation;
	}

	public void setCplxAffectation(IloIntVar cplxAffectation) {
		this.cplxAffectation = cplxAffectation;
	}



	

}
