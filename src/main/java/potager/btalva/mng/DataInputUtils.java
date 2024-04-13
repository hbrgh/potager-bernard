package potager.btalva.mng;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import potager.btalva.pojo.AssociationsPlantes;
import potager.btalva.pojo.Plante;
import potager.btalva.pojo.PlanteDevEtreSemee;


public class DataInputUtils {
	private static Logger LOGGER = LoggerFactory.getLogger(DataInputUtils.class);
	
	
	private static final String NOM_ONGLET_A_SEMER = "a_semer";
	
	
	private static final int IND_COL_NOM_PLANTE_DANS_ONGLET_A_SEMER = 0;
	


	
	
	
	public static boolean lireOngletPlantesDevEtreSemees(XSSFSheet sheet, InputDataPotagerBernard pInputDataPotagerBernard,
			AssociationsPlantes pAssociationsPlantes) throws InputDataPotagerBernardException {

		XSSFRow row = sheet.getRow(0);
		if (row == null) {
			String fatalError = "Fichier de plantes à semer vide ou commençant après la première ligne!";
			LOGGER.error(fatalError);
			throw new InputDataPotagerBernardException(fatalError);
		}
		int ligDernData = sheet.getLastRowNum();
		for (int lig = 0; lig <= ligDernData; lig++ ) {
			row = sheet.getRow(lig);
			XSSFCell cellCode = row.getCell(IND_COL_NOM_PLANTE_DANS_ONGLET_A_SEMER);
			String nomPlante = cellCode.getStringCellValue().trim();
			if (pAssociationsPlantes.getMapPlantesFamDiffDeFleur().containsKey(nomPlante)) {
				Plante lPlante = pAssociationsPlantes.getMapPlantesFamDiffDeFleur().get(nomPlante);
				PlanteDevEtreSemee lPlanteDevEtreSemee = new PlanteDevEtreSemee(lig, lPlante);
				pInputDataPotagerBernard.getLstPlantesDevEtreSemees().add(lPlanteDevEtreSemee);
			}
		}
		int nbMaxCarresDispos = ParamsPotager.getInstance().getBacsParams().getNbBacs() *
				ParamsPotager.getInstance().getBacsParams().getNbMaxCarresParBac();
		int nbPlantesDevEtreSemees = pInputDataPotagerBernard.getLstPlantesDevEtreSemees().size();
		if (nbPlantesDevEtreSemees > nbMaxCarresDispos) {
			String fatalErrorPatternLog = "{} plantes à semer! C'est trop élevé par rapport aux {} carrés disponibles! Corrigez votre fichier de plantes à semer!";
			LOGGER.error(fatalErrorPatternLog, nbPlantesDevEtreSemees, nbMaxCarresDispos);
			String fatalError = nbPlantesDevEtreSemees+ " plantes à semer! C'est trop élevé par rapport aux "+
					nbMaxCarresDispos + " carrés disponibles! Corrigez votre fichier de plantes à semer!";
			throw new InputDataPotagerBernardException(fatalError);
		}
		LOGGER.info("nbMaxCarresDispos: {}", nbMaxCarresDispos);
		LOGGER.info("nbPlantesDevEtreSemees: {} (c'est inférieur ou égal à {}: OK)", 
				nbPlantesDevEtreSemees, nbMaxCarresDispos);
		
		pInputDataPotagerBernard.buildAssocsInterditesPlantes();
		pInputDataPotagerBernard.buildAssocsRecommandeesPlantes();
		
		logResumeLirePlantesDevantEtreSemees(pInputDataPotagerBernard);
		return (true);
	}
	
	
	private static void logResumeLirePlantesDevantEtreSemees(InputDataPotagerBernard pInputDataPotagerBernard) {
		LOGGER.info("nbPlantesDevantEtreSemees = {}", pInputDataPotagerBernard.getLstPlantesDevEtreSemees().size());
		LOGGER.info("Liste des plantes devant être semées: DEBUT");
		for (PlanteDevEtreSemee lPlanteDevEtreSemee : pInputDataPotagerBernard.getLstPlantesDevEtreSemees()) {
			LOGGER.info("Plante à semer {}: {} (famille={})", lPlanteDevEtreSemee.getNumero(), 
					lPlanteDevEtreSemee.getNom(),lPlanteDevEtreSemee.getFamille() );
		}
		
		LOGGER.info("Liste des plantes devant être semées: FIN");
		LOGGER.info("Nombre d'associations interdites dans les plantes à semer: {}",
				pInputDataPotagerBernard.getNbAssociationsInterditesDansPlantesASemer());
				pInputDataPotagerBernard.logAssocsInterditesPlantesDevEtreSemees();
		LOGGER.info("Nombre d'associations recommandées dans les plantes à semer: {}",
				pInputDataPotagerBernard.getNbAssociationsRecommandeesDansPlantesASemer());
			pInputDataPotagerBernard.logAssocsRecommandeesPlantesDevEtreSemees();
	}
	
	
	

	

	

	
	
	

	public static InputDataPotagerBernard readPlantesDevEtreSemessFromFileData(String filePathName)
			throws InvalidFormatException {

		InputDataPotagerBernard lInputDataPotagerBernard = new InputDataPotagerBernard();
		boolean fileInputDataPotagerBernardOK = false;
		LOGGER.info("Début lecture du fichier : {}", filePathName);

		try (FileInputStream stream = new FileInputStream(new File(filePathName))) {

			XSSFWorkbook workbook = (XSSFWorkbook) WorkbookFactory.create(stream);
			XSSFSheet sheet = workbook.getSheet(NOM_ONGLET_A_SEMER);
			fileInputDataPotagerBernardOK = lireOngletPlantesDevEtreSemees(sheet, lInputDataPotagerBernard,
					ParamsPotager.getInstance().getAssocsPlantes());

			workbook.close();
		} catch (IOException e) {
			LOGGER.error(e.toString());

		} catch (InputDataPotagerBernardException e1) {
			LOGGER.error(e1.toString());
		}
		LOGGER.info("Fin lecture du fichier : {}", filePathName);
		if (fileInputDataPotagerBernardOK == true) {
			return (lInputDataPotagerBernard);
		}
		return (null);

	}

}
