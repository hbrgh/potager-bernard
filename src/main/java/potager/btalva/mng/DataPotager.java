package potager.btalva.mng;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class DataPotager {
	
	private static Logger LOGGER = LoggerFactory.getLogger(DataPotager.class);
	
	private static DataPotager uniqueInstance = null;
	
	private InputDataPotagerBernard inputDataPotagerBernard;
	
	
	private DataPotager() {
		
	}

	public static synchronized DataPotager getInstance()
	{
          if(uniqueInstance==null)
          {
                  uniqueInstance = new DataPotager();
          }
          return uniqueInstance;
	}
	
	public static synchronized void newInstance()
	{
     
          uniqueInstance = new DataPotager();
        
         
	}
	
	public boolean lireFichierData(String fullPath) {
		
		InputDataPotagerBernard  lInputDataPotagerBernard = null;
		try {
			lInputDataPotagerBernard = DataInputUtils.readPlantesDevEtreSemessFromFileData(fullPath);
			if (lInputDataPotagerBernard == null) {
				return(false);
			}
		} catch (InvalidFormatException e) {
			LOGGER.error(e.toString());
			return(false);
		} catch (NullPointerException e1) {
			e1.printStackTrace();
		}
		
	
		this.setInputDataPotagerBernard(lInputDataPotagerBernard);
		
		
		return(true);
	}

	public InputDataPotagerBernard getInputDataPotagerBernard() {
		return inputDataPotagerBernard;
	}

	public void setInputDataPotagerBernard(InputDataPotagerBernard inputDataPotagerBernard) {
		this.inputDataPotagerBernard = inputDataPotagerBernard;
	}

	

	


	
}
