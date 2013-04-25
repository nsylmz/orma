package com.orma.muhasebe.sayim;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.orma.api.ISearchAPI;
import com.orma.exception.StockManagementException;
import com.orma.vo.ReportType;
import com.orma.vo.WarehouseStockReport;


@Component
public class DepoSayimWriter {
	
	private static Logger log = Logger.getLogger(SayimReader.class);

	@Autowired
	ISearchAPI searchAPI;
	
	public void write(String filePath) throws StockManagementException {
		
		HSSFRow row = null;
		HSSFCell cell = null;
		int rowNum = 0;
		int siraNo = 1;
		try {
			HSSFWorkbook workBook = new HSSFWorkbook();
			HSSFSheet sheet = workBook.createSheet();
			
			prepareHeader(sheet, rowNum);
			rowNum++;
			
			prepareDokumanSeparetionRow(sheet, rowNum);
			rowNum++;
			
			BigDecimal totalBuy = BigDecimal.ZERO;
			BigDecimal totalSell = BigDecimal.ZERO;
			List<WarehouseStockReport> reports = searchAPI.getReportsByWarehouseOrProductOrBrand(null, null, null, ReportType.sayim);
			for (int i = 0; i < reports.size(); i++) {
				row = sheet.createRow(rowNum);
				
				cell = row.createCell(0);
				cell.setCellValue(siraNo);
				siraNo++;
				
				cell = row.createCell(1);
				cell.setCellValue(reports.get(i).getProductBarcode());
				
				cell = row.createCell(2);
				cell.setCellValue(reports.get(i).getBrandName() + " " + reports.get(i).getProductName());
				
				cell = row.createCell(3);
				cell.setCellValue(reports.get(i).getTotalAmount());
				
				cell = row.createCell(4);
				if (reports.get(i).getProductBuyPrice() != null) {
					cell.setCellValue(reports.get(i).getProductBuyPrice().doubleValue());
				} else {
					cell.setCellValue(BigDecimal.ZERO.doubleValue());
				}
				
				cell = row.createCell(5);
				cell.setCellValue(reports.get(i).getTotalBuyPrice().doubleValue());
				if (reports.get(i).getProductBuyPrice() != null) {
					cell.setCellValue(reports.get(i).getProductBuyPrice().doubleValue());
				} else {
					cell.setCellValue(BigDecimal.ZERO.doubleValue());
				}
				
				cell = row.createCell(6);
				if (reports.get(i).getProductBuyPrice() != null) {
					cell.setCellValue(reports.get(i).getProductBuyPrice().doubleValue());
				} else {
					cell.setCellValue(BigDecimal.ZERO.doubleValue());
				}
				
				cell = row.createCell(7);
				if (reports.get(i).getTotalSellPrice() != null) {
					cell.setCellValue(reports.get(i).getTotalSellPrice().doubleValue());
				} else {
					cell.setCellValue(BigDecimal.ZERO.doubleValue());
				}
				
				cell = row.createCell(8);
				if (reports.get(i).getTotalSellPrice() != null) {
					cell.setCellValue(reports.get(i).getTotalSellPrice().subtract(reports.get(i).getTotalBuyPrice()).doubleValue());
				} else {
					cell.setCellValue(BigDecimal.ZERO.doubleValue());
				}
				
				rowNum++;
				
				totalBuy = totalBuy.add(reports.get(i).getTotalBuyPrice());
				totalSell = totalSell.add(reports.get(i).getTotalSellPrice());
			}
			prepareFaturaSeparetionRow(sheet, rowNum);
			rowNum++;
			
			row = sheet.createRow(rowNum);
			
			cell = row.createCell(5);
			cell.setCellValue(totalBuy.doubleValue());
			
			cell = row.createCell(7);
			cell.setCellValue(totalSell.doubleValue());
			
			cell = row.createCell(8);
			cell.setCellValue(totalSell.subtract(totalBuy).doubleValue());
			rowNum++;
			
			prepareDokumanSeparetionRow(sheet, rowNum);
			rowNum++;
			
			row = sheet.createRow(rowNum);
			
			cell = row.createCell(5);
			cell.setCellValue(totalBuy.doubleValue());
			
			cell = row.createCell(7);
			cell.setCellValue(totalSell.doubleValue());
			
			cell = row.createCell(8);
			cell.setCellValue(totalSell.subtract(totalBuy).doubleValue());
			rowNum++;
			
			FileOutputStream out = new FileOutputStream(filePath);
			workBook.write(out);
			out.close();
		} catch (Exception e) {
			log.info(e.getMessage());
			log.info(e.getStackTrace().toString());
			throw new StockManagementException(e);
		} finally {

		}
	}
	
	private static void prepareHeader(HSSFSheet sheet, int rowNum) {
		HSSFRow row = null;
		HSSFCell cell = null;	
		
		row = sheet.createRow(rowNum);
		
		cell = row.createCell(0);
		cell.setCellValue("Stk No");
		
		cell = row.createCell(1);
		cell.setCellValue("Bar No");
		
		cell = row.createCell(2);
		cell.setCellValue("Aciklama");
		
		cell = row.createCell(3);
		cell.setCellValue("Miktar");
		
		cell = row.createCell(4);
		cell.setCellValue("Maliyet");
		
		cell = row.createCell(5);
		cell.setCellValue("Alis Tutari");
		
		cell = row.createCell(6);
		cell.setCellValue("Satis Fiyati");
		
		cell = row.createCell(6);
		cell.setCellValue("Satis Tutari");
		
		cell = row.createCell(7);
		cell.setCellValue("Kar");
	}
	
	
	private static void prepareDokumanSeparetionRow(HSSFSheet sheet, int rowNum) {
		HSSFRow row = null;
		
		HSSFCell cell = null;	
		
		row = sheet.createRow(rowNum);
		
		cell = row.createCell(0);
		cell.setCellValue("===");
		
		cell = row.createCell(1);
		cell.setCellValue("==============================");
		
		cell = row.createCell(2);
		cell.setCellValue("=========");
		
		cell = row.createCell(3);
		cell.setCellValue("=========");
		
		cell = row.createCell(4);
		cell.setCellValue("=========");
		
		cell = row.createCell(5);
		cell.setCellValue("=========");
		
		cell = row.createCell(6);
		cell.setCellValue("=========");
		
		cell = row.createCell(7);
		cell.setCellValue("=========");
	}
	
	private static void prepareFaturaSeparetionRow(HSSFSheet sheet, int rowNum) {
		HSSFRow row = null;
		HSSFCell cell = null;	
		
		row = sheet.createRow(rowNum);
		
		cell = row.createCell(0);
		cell.setCellValue("---");
		
		cell = row.createCell(1);
		cell.setCellValue("------------------------------");
		
		cell = row.createCell(2);
		cell.setCellValue("---------");
		
		cell = row.createCell(3);
		cell.setCellValue("---------");
		
		cell = row.createCell(4);
		cell.setCellValue("---------");
		
		cell = row.createCell(5);
		cell.setCellValue("---------");
		
		cell = row.createCell(6);
		cell.setCellValue("---------");
		
		cell = row.createCell(7);
		cell.setCellValue("---------");
	}
}
