package vn.webapp.modules.researchmanagement.controller.cp;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.web.servlet.view.document.AbstractExcelView;

public class mExcelThreadsBuilder extends AbstractExcelView{
	
	/**
	 * 
	 */
	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// get data model which is passed by the Spring container
		List<List<String>> summaryThreadList = (List<List<String>>) model.get("summaryThreadList");
		
		// create a new Excel sheet
		HSSFSheet sheet = workbook.createSheet("Thong ke de tai");
		//sheet.setDefaultColumnWidth(20);
		
		// preparing data
		String yearOfPaper = (String) model.get("yearOfPaper");
		
		int iCurrentRow = 1;
		/**
		 * 1. Create a cell for the title
		 */
		// create style for title cells
		CellStyle style = workbook.createCellStyle();
		Font fontTitle = workbook.createFont();
		fontTitle.setFontHeightInPoints((short)12);
		fontTitle.setFontName("Times New Roman");
		fontTitle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		fontTitle.setColor(HSSFColor.BLACK.index);
		style.setFont(fontTitle);
		
		// create title of the paper
		HSSFRow title = sheet.createRow(iCurrentRow);
		title.createCell(1).setCellValue("BẢNG THỐNG KÊ ĐỀ TÀI NĂM HỌC " + yearOfPaper);
		title.getCell(1).setCellStyle(style);
		sheet.setColumnWidth(0, 400);
		sheet.addMergedRegion(new CellRangeAddress(
		            1, //first row (0-based)
		            1, //last row  (0-based)
		            1, //first column (0-based)
		            8  //last column  (0-based)
		    ));
		
		/**
		 * Create a cell for sub title
		 */
		// create style for sub title cells
		CellStyle styleSubtitle = workbook.createCellStyle();
		Font fontSubTitle = workbook.createFont();
		fontSubTitle.setFontHeightInPoints((short)10);
		fontSubTitle.setFontName("Times New Roman");
		fontSubTitle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		fontSubTitle.setColor(HSSFColor.BLACK.index);
		styleSubtitle.setFont(fontSubTitle);
		
		/**
		 * Create a cell for author's info
		 */
		CellStyle styleAuthorInfo = workbook.createCellStyle();
		Font fontAuthorInfo = workbook.createFont();
		fontAuthorInfo.setFontHeightInPoints((short)10);
		fontAuthorInfo.setFontName("Times New Roman");
		fontAuthorInfo.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		fontAuthorInfo.setColor(HSSFColor.BLACK.index);
		styleAuthorInfo.setFont(fontAuthorInfo);
		
		/**
		 * Create the box
		 */
		// Style the cell with borders all around.
	    CellStyle styleBox = workbook.createCellStyle();
	    styleBox.setBorderBottom(CellStyle.BORDER_THIN);
	    styleBox.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	    styleBox.setBorderLeft(CellStyle.BORDER_THIN);
	    styleBox.setLeftBorderColor(IndexedColors.BLACK.getIndex());
	    styleBox.setBorderRight(CellStyle.BORDER_THIN);
	    styleBox.setRightBorderColor(IndexedColors.BLACK.getIndex());
	    styleBox.setBorderTop(CellStyle.BORDER_THIN);
	    styleBox.setTopBorderColor(IndexedColors.BLACK.getIndex());
	    styleBox.setFont(fontSubTitle);
	    
	    iCurrentRow = 3;
		HSSFRow boxHeader = sheet.createRow(iCurrentRow);
		boxHeader.createCell(1).setCellValue("STT");
		boxHeader.getCell(1).setCellStyle(styleBox);
		
		boxHeader.createCell(2).setCellValue("Chủ nhiệm đề tài");
		boxHeader.getCell(2).setCellStyle(styleBox);
		
		boxHeader.createCell(3).setCellValue("Bộ môn");
		boxHeader.getCell(3).setCellStyle(styleBox);
		
		boxHeader.createCell(4).setCellValue("Khoa/Viện");
		boxHeader.getCell(4).setCellStyle(styleBox);
		
		boxHeader.createCell(5).setCellValue("Tên đề tài");
		boxHeader.getCell(5).setCellStyle(styleBox);
		
		boxHeader.createCell(6).setCellValue("Kinh phí");
		boxHeader.getCell(6).setCellStyle(styleBox);
		
		/**
		 * Show numbers of column
		 */
		iCurrentRow++;
		HSSFRow boxNo = sheet.createRow(iCurrentRow);
		for(int iNo = 1; iNo <=6; iNo++){
			boxNo.createCell(iNo).setCellValue(iNo);
			boxNo.getCell(iNo).setCellStyle(styleBox);
		}

		/**
		 * Show all contents for the first category
		 */
		CellStyle styleContent = workbook.createCellStyle();
		Font fontContent = workbook.createFont();
		fontContent.setFontHeightInPoints((short)10);
		fontContent.setFontName("Times New Roman");
		fontContent.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		fontContent.setColor(HSSFColor.BLACK.index);
		styleContent.setFont(fontContent);
		
		// Style the cell with borders all around.
	    CellStyle styleContentBox = workbook.createCellStyle();
	    styleContentBox.setBorderBottom(CellStyle.BORDER_THIN);
	    styleContentBox.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	    styleContentBox.setBorderLeft(CellStyle.BORDER_THIN);
	    styleContentBox.setLeftBorderColor(IndexedColors.BLACK.getIndex());
	    styleContentBox.setBorderRight(CellStyle.BORDER_THIN);
	    styleContentBox.setRightBorderColor(IndexedColors.BLACK.getIndex());
	    styleContentBox.setBorderTop(CellStyle.BORDER_THIN);
	    styleContentBox.setTopBorderColor(IndexedColors.BLACK.getIndex());
	    styleContentBox.setFont(fontContent);
	    
		int iCount = 1;
		iCurrentRow++;
		int iTotalBudget = 0;
		for (List<String> oThread : summaryThreadList) { 
			
				HSSFRow boxFisrtCatContent = sheet.createRow(iCurrentRow);
				boxFisrtCatContent.createCell(1).setCellValue(iCount++);
				boxFisrtCatContent.getCell(1).setCellStyle(styleContentBox);
				
				// Name
				boxFisrtCatContent.createCell(2).setCellValue(oThread.get(0));
				boxFisrtCatContent.getCell(2).setCellStyle(styleContentBox);
				
				// Budget
				boxFisrtCatContent.createCell(3).setCellValue(oThread.get(1));
				boxFisrtCatContent.getCell(3).setCellStyle(styleContentBox);
				
				boxFisrtCatContent.createCell(4).setCellValue(oThread.get(2));
				boxFisrtCatContent.getCell(4).setCellStyle(styleContentBox);
				
				// Author Converted hours
				boxFisrtCatContent.createCell(5).setCellValue(oThread.get(3));
				boxFisrtCatContent.getCell(5).setCellStyle(styleContentBox);
				
				// Author Converted hours
				boxFisrtCatContent.createCell(6).setCellValue(oThread.get(4));
				boxFisrtCatContent.getCell(6).setCellStyle(styleContentBox);
				
				iTotalBudget += Integer.parseInt(oThread.get(4));
				iCurrentRow++;
			
		}
		
		/**
		 * Papers - total line
		 */
		HSSFRow boxNoTotal = sheet.createRow(iCurrentRow);
		for(int iNo = 1; iNo <=6; iNo++){
			
			if(iNo == 1){
				boxNoTotal.createCell(iNo).setCellValue("Tổng cộng");
				boxNoTotal.getCell(iNo).setCellStyle(styleBox);
			}else if (iNo == 6){
				boxNoTotal.createCell(iNo).setCellValue(iTotalBudget);
				boxNoTotal.getCell(iNo).setCellStyle(styleBox);
			}else{
				boxNoTotal.createCell(iNo).setCellValue("");
				boxNoTotal.getCell(iNo).setCellStyle(styleBox);
			}
		}
		
		/**
		 * Confirmation and Signature
		 */
		iCurrentRow +=3 ;
		HSSFRow dateEnd = sheet.createRow(iCurrentRow);
		dateEnd.createCell(6).setCellValue("Hà Nội, Ngày.........tháng.........năm.........");
		dateEnd.getCell(6).setCellStyle(styleSubtitle);
		
	}
}
