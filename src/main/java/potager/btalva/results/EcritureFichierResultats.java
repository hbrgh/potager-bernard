package potager.btalva.results;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import potager.btalva.mng.RootMng;

public class EcritureFichierResultats {
	public static String ecrireFichierResultats(RecuperationResultats pRecuperationResultats) throws IOException  {
			EcritureResultats lEcritureResultats =
					new EcritureResultats(pRecuperationResultats);
			//OSX
			//		OUTPUT_DIR = /Users/herve/Public/LARDY_BANCS_ESSAIS/results/
			//		RESULT_FILE = results.xlsx
			SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd_HHmmss");
		    String instantPresent = formater.format(new Date());
			String workbookName = RootMng.getInstance().getRootDirApp()+"/resultats/res__" + instantPresent + ".xlsx";
			
		
			lEcritureResultats.writeResultsFile(workbookName);
			return(workbookName);
			
	}
}

