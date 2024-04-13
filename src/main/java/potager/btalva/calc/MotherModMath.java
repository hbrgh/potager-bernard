package potager.btalva.calc;

import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ilog.concert.IloException;
import ilog.cplex.IloCplex;
import potager.btalva.utils.UtilsLogs;
import potager.btalva.utils.VariousUtils;

public class MotherModMath {
	
	private static Logger LOGGER = LoggerFactory.getLogger(MotherModMath.class);

	public static final double INTEGRALITY_TOLERANCE_DEFAULT = 0.000000001;
	public static final double INTEGRALITY_TOLERANCE_STRICT = 0.0;
	public static final int TIME_5_MINS = 300;
	public static final int TIME_60_MINS = 3600;

	protected IloCplex cplexPb = null;
	
	public MotherModMath() {
		super();
		

	}

	public IloCplex getCplexPb() {
		return cplexPb;
	}

	public void setCplexPb(IloCplex cplexPb) {
		this.cplexPb = cplexPb;
	}
	
	public void creerInstancePbCplex() throws IloException {
		IloCplex lIloCplex = null;
		try {
			lIloCplex = new IloCplex();
		} catch (IloException e) {
			VariousUtils.harakiri(e);
		}
		this.cplexPb = lIloCplex;
		LOGGER.info("Algo utilisé par Cplex: {}", lIloCplex.getAlgorithm());
		LOGGER.info("Version de Cplex: {}", lIloCplex.getVersion());
		LOGGER.info("Numéro de version de Cplex: {}", lIloCplex.getVersionNumber());
		
		
      	OutputStream out = UtilsLogs.getLogbackLogFileOutputStream(); // fait que le moteur CPLEX va écrire dans la log de l'application
        this.cplexPb.setOut(out);
        this.cplexPb.setWarning(out);
        	
		

	}

	public void detruireInstancePbCplex() {

		this.cplexPb.end();
	}
	
	public boolean resoudrePlnePb(double timeLimit, double integralityTolerance) throws IloException {

		long nbSolsMaxDefault = this.cplexPb.getParam(IloCplex.Param.MIP.Limits.Solutions);

		this.cplexPb.setParam(IloCplex.Param.TimeLimit, timeLimit);

		this.cplexPb.setParam(IloCplex.Param.MIP.Tolerances.Integrality, integralityTolerance);

		this.cplexPb.setParam(IloCplex.Param.MIP.Limits.Solutions, 1);
		this.cplexPb.setParam(IloCplex.Param.Emphasis.MIP, IloCplex.MIPEmphasis.Feasibility);

		boolean faisable = this.cplexPb.solve();
		if (faisable) {
			LOGGER.info("Le problème a au moins une solution.");
		} else {
			LOGGER.warn("Le problème n'a pas de solution.");
		}

		restoreMIPparamsCPLEXdefaults(nbSolsMaxDefault);

		return (faisable);

	}
	
	private void restoreMIPparamsCPLEXdefaults(long nbSolsMaxDefault) throws IloException {

		this.cplexPb.setParam(IloCplex.Param.MIP.Limits.Solutions, nbSolsMaxDefault);
		this.cplexPb.setParam(IloCplex.Param.Emphasis.MIP, IloCplex.MIPEmphasis.Balanced);

	}


}
