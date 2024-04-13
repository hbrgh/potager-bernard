package potager.btalva.mng;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import potager.btalva.pojo.AssocPlantesInterdite;
import potager.btalva.pojo.AssocPlantesRecommandee;
import potager.btalva.pojo.AssociationsPlantes;
import potager.btalva.pojo.BacsParametresPotager;
import potager.btalva.pojo.FamillePlante;
import potager.btalva.pojo.Plante;


public class ParamsInputUtils {
	private static Logger LOGGER = LoggerFactory.getLogger(ParamsInputUtils.class);
	
	private static final String NOM_ONGLET_ASSOCIATIONS = "associations";
	private static final String NOM_ONGLET_BACS = "bacs";
	
	private static final int IND_COL_NOM_PARAM_DANS_ONGLET_BACS = 0;
	public static final String NOM_PARAM_NB_BACS_DANS_ONGLET_BACS = "NB_BACS";
	private static final int IND_LIG_NB_BACS_DANS_ONGLET_BACS = 0;
	public static final String NOM_PARAM_NB_MAX_CARRES_PAR_BAC_DANS_ONGLET_BACS = "NB_MAX_CARRES_PAR_BAC";
	private static final int IND_LIG_NB_MAX_CARRES_PAR_BAC = 1;
	private static final int IND_COL_VALEUR_PARAM_DANS_ONGLET_BACS = 1;
	
	private static final int IND_LIG_DEBUT_PLANTES_DANS_ONGLET_ASSOCS = 1;
	private static final int IND_COL_FAM_DANS_ONGLET_ASSOCS = 0;
	private static final int IND_COL_PLANTE_DANS_ONGLET_ASSOC = 1;
	
	private static final int IND_LIG_NOMS_PLANTE_DANS_ONGLET_ASSOCS = 0;
	private static final int IND_COL_DEBUT_PLANTE_DANS_ONGLET_ASSOCS = 2;

	
	private static XSSFRow verifPresenceParamDansOngletBacs(XSSFSheet sheet, String nomParam, int ligParam) throws MissingOrIncorrectParamPotagerBtalvaException {
		XSSFRow row = sheet.getRow(ligParam);
		if (row == null) {
			String fatalError = "Paramètre absent: " + nomParam;
			LOGGER.error(fatalError);
			throw new MissingOrIncorrectParamPotagerBtalvaException(fatalError);
		}
		XSSFCell cellCode = row.getCell(IND_COL_NOM_PARAM_DANS_ONGLET_BACS);
		String nomParamCell = cellCode.getStringCellValue();
		if (nomParamCell.equalsIgnoreCase(nomParam) == false) {

			String fatalError = "Paramètre absent: " + nomParam;
			LOGGER.error(fatalError);
			throw new MissingOrIncorrectParamPotagerBtalvaException(fatalError);
		}

		return(row);
	}
	
	
	public static boolean lireOngletBacs(XSSFSheet sheet, BacsParametresPotager bacsParams) {

		XSSFRow row;
		
		// Nombre de bacs
		try {
			row = verifPresenceParamDansOngletBacs
							(
								sheet,
								NOM_PARAM_NB_BACS_DANS_ONGLET_BACS,
								IND_LIG_NB_BACS_DANS_ONGLET_BACS
							);
		} catch (MissingOrIncorrectParamPotagerBtalvaException e) {
			return(false);
		}
		XSSFCell cellCode = row.getCell(IND_COL_VALEUR_PARAM_DANS_ONGLET_BACS);
		int nbBacs = (int)cellCode.getNumericCellValue();
	
		bacsParams.setNbBacs(nbBacs);

		
		// Nombre max de carrés par bac
		try {
			row = verifPresenceParamDansOngletBacs
									(
										sheet,
										NOM_PARAM_NB_MAX_CARRES_PAR_BAC_DANS_ONGLET_BACS,
										IND_LIG_NB_MAX_CARRES_PAR_BAC
									);
		} catch (MissingOrIncorrectParamPotagerBtalvaException e) {
			return(false);
		}
		cellCode = row.getCell(IND_COL_VALEUR_PARAM_DANS_ONGLET_BACS);
		int nbMaxCarresParBac = (int)cellCode.getNumericCellValue();
	
		bacsParams.setNbMaxCarresParBac(nbMaxCarresParBac);
	
		LOGGER.info("nbBacs = {}", nbBacs);
		LOGGER.info("nbMaxCarresParBac = {}", nbMaxCarresParBac);

		return (true);
	}
	
	
	private static void logResumeLirePlantesFamille(AssociationsPlantes pAssociationsPlantes) {
		LOGGER.info("nombre de plantes connues = {}", pAssociationsPlantes.getNbPlantesConnues());
		LOGGER.info("nombre de plantes dont la famille est différente de Fleur = {}",  pAssociationsPlantes.getNbPlantesFamDiffFleur());

		Map<String, List<Plante>> plantesParFamille = pAssociationsPlantes.getLstPlantesConnues().stream()
				  .collect(Collectors.groupingBy(Plante::getFamille));
		int nbFamilles = 0;
		for (Entry<String, List<Plante>> entryyyy : plantesParFamille.entrySet()) {
			nbFamilles++;
			LOGGER.info("famille détectée = {}", entryyyy.getKey());
		}
		LOGGER.info("{} familles détectées", nbFamilles);
		LOGGER.info(System.lineSeparator());
		LOGGER.info(System.lineSeparator());
		
		LOGGER.info("Liste des plantes connues dont la famille est différente de Fleur: DEBUT");
		for (Plante lPlante : pAssociationsPlantes.getLstPlantesFamDiffDeFleur()) {
			LOGGER.info("Plante {} appartenant à la famille {}", lPlante.getNom(), lPlante.getFamille());
		}
		LOGGER.info("Liste des plantes connues dont la famille est différente de Fleur: FIN");
	}
	
	
	

	private static void lirePlantesFamilles(XSSFSheet sheet, AssociationsPlantes pAssociationsPlantes) {
		

		int lastRowNum = sheet.getLastRowNum();
		
		
		
		for(int rowNum=IND_LIG_DEBUT_PLANTES_DANS_ONGLET_ASSOCS; rowNum<=lastRowNum; rowNum++) {
			

			XSSFRow row = sheet.getRow(rowNum);
			XSSFCell cellCode = row.getCell(IND_COL_FAM_DANS_ONGLET_ASSOCS);
			String nomFam = cellCode.getStringCellValue().trim();
			if (FamillePlante.estFamillePlante(nomFam)) {
				cellCode = row.getCell(IND_COL_PLANTE_DANS_ONGLET_ASSOC);
				String nomPlante = cellCode.getStringCellValue().trim();
				
				Plante lPlante = new Plante(nomPlante, nomFam);
				if (Plante.familleEstFleur(nomFam) == false) {
					
					pAssociationsPlantes.addPlanteFamDiffFleur(lPlante);
				}
				pAssociationsPlantes.addPlante(lPlante);
				
				
			} else {
				
				
				break;
			}
			
		}
		
		logResumeLirePlantesFamille
			(
					pAssociationsPlantes
			);
		
		
	}
	
	private static void logResumeMatriceAssociationsPlantes(AssociationsPlantes pAssociationsPlantes) {
		LOGGER.info("ASSOCIATIONS DE PLANTES INTERDITES:");
		for (AssocPlantesInterdite lAssocPlantesInterdite : pAssociationsPlantes.getSetAssocsPlantesInterdites()) {
			LOGGER.info(lAssocPlantesInterdite.toString());
		}
		
		LOGGER.info("ASSOCIATIONS DE PLANTES RECOMMANDEES:");
		for (AssocPlantesRecommandee lAssocPlantesRecommandee : pAssociationsPlantes.getLstAssocsRecommandeesPlantes()) {
			LOGGER.info(lAssocPlantesRecommandee.toString());
		}
	
	}
	
	
	
	private static void lireMatriceAssociationsPlantes(XSSFSheet sheet, AssociationsPlantes pAssociationsPlantes) {
		
		
		
		
		int rowNumEnd = IND_LIG_DEBUT_PLANTES_DANS_ONGLET_ASSOCS + pAssociationsPlantes.getNbPlantesConnues() - 1;
		int colNumEnd = IND_COL_DEBUT_PLANTE_DANS_ONGLET_ASSOCS + pAssociationsPlantes.getNbPlantesConnues() - 1;
		
		
		
		XSSFRow rowNomsPlantes = sheet.getRow(IND_LIG_NOMS_PLANTE_DANS_ONGLET_ASSOCS);
		for(int rowNum=IND_LIG_DEBUT_PLANTES_DANS_ONGLET_ASSOCS; rowNum<=rowNumEnd; rowNum++) {
			

			XSSFRow row = sheet.getRow(rowNum);
			XSSFCell cellCodeNomPlanteRow = row.getCell(IND_COL_PLANTE_DANS_ONGLET_ASSOC);
			String nomPlanteRow = cellCodeNomPlanteRow.getStringCellValue().trim();
			if (pAssociationsPlantes.isFamDiffFleur(nomPlanteRow) == false) {
				continue;
			}

			for (int colNum = IND_COL_DEBUT_PLANTE_DANS_ONGLET_ASSOCS; colNum <=  colNumEnd; colNum++) {
				XSSFCell cellCodeNomPlanteCol= rowNomsPlantes.getCell(colNum);
				String nomPlanteCol = cellCodeNomPlanteCol.getStringCellValue().trim();
				if (pAssociationsPlantes.isFamDiffFleur(nomPlanteCol) == false) {
					continue;
				}
				
				XSSFCell cellCodeAssocPlantes= row.getCell(colNum, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
				
				String valAssocPlantes = cellCodeAssocPlantes.getStringCellValue().trim();
				if (valAssocPlantes.isEmpty()) {
					continue;
				}
				if (valAssocPlantes.equalsIgnoreCase("N")) {
					pAssociationsPlantes.addAssocPlanteInterdite(nomPlanteRow, nomPlanteCol);
					pAssociationsPlantes.addAssocPlanteInterdite(nomPlanteCol, nomPlanteRow);
					continue;
				}
				if (valAssocPlantes.equalsIgnoreCase("O")) {
					pAssociationsPlantes.addAssocPlanteRecommandee(nomPlanteRow, nomPlanteCol);
					pAssociationsPlantes.addAssocPlanteRecommandee(nomPlanteCol, nomPlanteRow);
					continue;
				}				
				
			}
			
			
		}
		
		logResumeMatriceAssociationsPlantes
			(
					pAssociationsPlantes
			);
		
		
	}
	
	private static void lireOngletAssociationsPlantes(XSSFSheet sheet, AssociationsPlantes pAssociationsPlantes) {
		
		lirePlantesFamilles(sheet, pAssociationsPlantes);
		lireMatriceAssociationsPlantes(sheet, pAssociationsPlantes);
	}
	

	

	
	
	public static AssociationsPlantes readAssocsPlantesFromFileParam( String filePathName ) throws InvalidFormatException  {
	
		

		AssociationsPlantes lAssociationsPlantes = new AssociationsPlantes();
		LOGGER.info("Début lecture du fichier : {}", filePathName);
		
		try (FileInputStream stream = new FileInputStream(new File(filePathName))){
			
			XSSFWorkbook workbook = (XSSFWorkbook) WorkbookFactory.create(stream);
			XSSFSheet sheet = workbook.getSheet(NOM_ONGLET_ASSOCIATIONS);
			lireOngletAssociationsPlantes(sheet, lAssociationsPlantes);
			
			workbook.close();
		} catch (IOException e) {
			LOGGER.error(e.toString());
			
		} 
		LOGGER.info("Fin lecture du fichier : {}", filePathName);
		if (lAssociationsPlantes.verifCoherence()) {
			return(lAssociationsPlantes);
		}
		return(null);
		
	}


	public static BacsParametresPotager readBacsParamsFromFileParam(String filePathName)
			throws InvalidFormatException {

		BacsParametresPotager lBacsParametresPotager = new BacsParametresPotager();
		boolean bacsParamsOK = false;
		LOGGER.info("Début lecture du fichier : {}", filePathName);

		try (FileInputStream stream = new FileInputStream(new File(filePathName))) {

			XSSFWorkbook workbook = (XSSFWorkbook) WorkbookFactory.create(stream);
			XSSFSheet sheet = workbook.getSheet(NOM_ONGLET_BACS);
			bacsParamsOK = lireOngletBacs(sheet, lBacsParametresPotager);

			workbook.close();
		} catch (IOException e) {
			LOGGER.error(e.toString());

		}
		LOGGER.info("Fin lecture du fichier : {}", filePathName);
		if (bacsParamsOK == true) {
			return (lBacsParametresPotager);
		}
		return (null);

	}

}
