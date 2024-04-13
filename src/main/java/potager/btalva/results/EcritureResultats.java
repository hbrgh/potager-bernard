package potager.btalva.results;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import potager.btalva.pojo.PlanteDevEtreSemee;




public class EcritureResultats  extends CosmetiqueResultats {

	private static Logger LOGGER = LoggerFactory.getLogger(EcritureResultats.class);




	

	private RecuperationResultats recupResultats;

	
	

	public EcritureResultats(RecuperationResultats recupResultats) {
		super();

		this.recupResultats = recupResultats;
		
	}
	

	private void writeOngletBac(ResultatsBac pResultatsBac, Workbook workbook, CellStyle styleTitle, CellStyle styleValue) {
		Sheet sheetBac = workbook.createSheet("Bac "+ pResultatsBac.getNumeroBac());
		Row row = sheetBac.createRow(0);
		
		createTitleCell(row,0,  "PLANTE", styleTitle);
		createTitleCell(row,1,  "ID", styleTitle);
		createTitleCell(row,2,  "Nb Assocs Recommandées (sens commun)", styleTitle);
		createTitleCell(row,3,  "Nb Assocs Recommandées (sens commun + plantes de même nom)", styleTitle);
		
		int iRowPlante = 1;
		for (PlanteDevEtreSemee lPlanteDevEtreSemee : pResultatsBac.getListePlantesASemer()) {
			Row rowPlante = sheetBac.createRow(iRowPlante);
			createValueCell(rowPlante, 0,lPlanteDevEtreSemee.getNom() , styleValue );
			createValueCell(rowPlante, 1,lPlanteDevEtreSemee.getNumero() , styleValue );
			iRowPlante++;
			
		}
		int iRowNBAR = 1;
		Row rowNBAR = sheetBac.getRow(iRowNBAR);
		createValueCell(rowNBAR, 2, pResultatsBac.getNbAssocsEffectivesRecommSensCommun() , styleValue );
		createValueCell(rowNBAR, 3, pResultatsBac.getNbAssocsEffectivesRecommSensCommunAvecPlantesDeMemeNomPec() , styleValue );
		
		for (int iCol=0; iCol <= 3; iCol++) {
			sheetBac.autoSizeColumn(iCol);
		}

	}
	public void writeResultsFile(String pathFilename) throws IOException {
		
		LOGGER.info("Ecriture du fichier résultat {}", pathFilename);
		
		Workbook workbook = new XSSFWorkbook();
		CellStyle styleTitle = CosmetiqueResultats.createCellStyleTitle(workbook);
		CellStyle styleValue = CosmetiqueResultats.createCellStyleValue(workbook);
		

		for (ResultatsBac lResultatsBac : this.recupResultats.getListeResultatsBac()) {
			writeOngletBac(lResultatsBac, workbook,styleTitle, styleValue);
		}


		FileOutputStream fileOutput = new FileOutputStream(pathFilename);
		workbook.write(fileOutput);
		workbook.close();
		fileOutput.close();
	}

	
	




	

}
