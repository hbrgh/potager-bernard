package potager.btalva.mng;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import potager.btalva.pojo.AssociationsPlantes;
import potager.btalva.pojo.BacsParametresPotager;



public class ParamsPotager {
	
	private static Logger LOGGER = LoggerFactory.getLogger(ParamsPotager.class);
	
	private static ParamsPotager uniqueInstance = null;
	
	private AssociationsPlantes assocsPlantes;
	private BacsParametresPotager bacsParams;
	
	
	private ParamsPotager() {
		
	}

	public static synchronized ParamsPotager getInstance()
	{
          if(uniqueInstance==null)
          {
                  uniqueInstance = new ParamsPotager();
          }
          return uniqueInstance;
	}
	
	public static synchronized void newInstance()
	{
     
          uniqueInstance = new ParamsPotager();
        
         
	}
	
	
	
	public boolean lireFichierParametrage(String fullPath) {
		
		AssociationsPlantes  lAssocsPlantes = null;
		try {
			lAssocsPlantes = ParamsInputUtils.readAssocsPlantesFromFileParam(fullPath);
			if (lAssocsPlantes == null) {
				return(false);
			}
		} catch (InvalidFormatException e) {
			LOGGER.error(e.toString());
			return(false);
		} catch (NullPointerException e1) {
			e1.printStackTrace();
		}
		
	
		this.setAssocsPlantes(lAssocsPlantes);
		
		BacsParametresPotager lBacsParametresPotager = null;
		try {
			lBacsParametresPotager = ParamsInputUtils.readBacsParamsFromFileParam(fullPath);
			if (lBacsParametresPotager == null) {
				return(false);
			}
		} catch (InvalidFormatException e) {
			LOGGER.error(e.toString());
			return(false);
		}
		
		this.setBacsParams(lBacsParametresPotager);
		return(true);
	}



	public AssociationsPlantes getAssocsPlantes() {
		return assocsPlantes;
	}

	public void setAssocsPlantes(AssociationsPlantes assocsPlantes) {
		this.assocsPlantes = assocsPlantes;
	}

	public BacsParametresPotager getBacsParams() {
		return bacsParams;
	}

	public void setBacsParams(BacsParametresPotager autresParams) {
		this.bacsParams = autresParams;
	}

	


	
}
