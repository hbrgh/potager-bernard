package potager.btalva.results;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;




public class CosmetiqueResultats {
	
	




	
	public CosmetiqueResultats() {
		super();
			
	}


	

	/** creation du style pour les titres
	 * 
	 * @param workbook
	 * @return
	 */
	public static CellStyle createCellStyleTitle(Workbook workbook) {

		CellStyle styleTitle = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setBold(true);
		font.setFontName("Calibri");
		font.setFontHeightInPoints((short) 20);
		
		styleTitle.setFont(font);

		styleTitle.setAlignment(HorizontalAlignment.CENTER);
		styleTitle.setVerticalAlignment(VerticalAlignment.CENTER);
		styleTitle.setBorderBottom(BorderStyle.THIN);
		styleTitle.setBorderLeft(BorderStyle.THIN);
		styleTitle.setBorderRight(BorderStyle.THIN);
		styleTitle.setBorderTop(BorderStyle.THIN);

		return styleTitle;
	}

	/** creation du style pour les cellules de valeurs
	 * 
	 * @param workbook
	 * @return
	 */
	public static CellStyle createCellStyleValue(Workbook workbook) {

		CellStyle styleValue = workbook.createCellStyle();
		
		Font font = workbook.createFont();
		
		font.setFontName("Calibri");
		font.setFontHeightInPoints((short) 18);
		styleValue.setFont(font);
		styleValue.setAlignment(HorizontalAlignment.CENTER);
		styleValue.setVerticalAlignment(VerticalAlignment.CENTER);
		styleValue.setBorderBottom(BorderStyle.THIN);
		styleValue.setBorderLeft(BorderStyle.THIN);
		styleValue.setBorderRight(BorderStyle.THIN);
		styleValue.setBorderTop(BorderStyle.THIN);


		return styleValue;
	}

	


	
	
	
	
	protected void createTitleCell(Row row, int indexCell, String title, CellStyle styleTitle) {
		Cell cell = row.createCell(indexCell);
		cell.setCellValue(title);
		cell.setCellStyle(styleTitle);	

	}

	protected void createValueCell(Row row,  int indexCell, String val, CellStyle styleValue) {
		Cell cell = row.createCell(indexCell);
		cell.setCellValue(val);
		cell.setCellStyle(styleValue);	

	} 

	protected void createValueCell(Row row,  int indexCell, double val, CellStyle styleValue) {
		Cell cell = row.createCell(indexCell);
		cell.setCellValue(val);
		cell.setCellStyle(styleValue);	

	} 
	


	


}
