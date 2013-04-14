package com.orma.muhasebe.isletme;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.orma.exception.StockManagementException;
import com.orma.muhasebe.domain.isletme.IsletmeSayfa;

public class IsletmeWriter {
	
	private static Logger log = Logger.getLogger(IsletmeWriter.class);
	
	public static String[] months = {"OCAK", "ŞUBAT", "MART", "NİSAN", "MAYIS", "HAZİRAN", "TEMMUZ", "AĞUSTOS", "EYLÜL", "EKİM", "KASIM", "ARALIK"};

	public static void writeIsletmeFile(String baseFilePath,
			String outputFilePath, String sheetName, int totalPageNumber,
			ArrayList<IsletmeSayfa> isletmeSayfaList, String month, int year, String kantinType) throws StockManagementException {

		Row row = null;
		Cell cell = null;
		int rowNum = 0;
		
		BigDecimal isletmeToplamAlis = BigDecimal.ZERO;
		BigDecimal isletmeToplamSatis = BigDecimal.ZERO;
		try {
			totalPageNumber = totalPageNumber - 1;
			
			InputStream inp = new FileInputStream(baseFilePath);
			Workbook wb = WorkbookFactory.create(inp);
			Sheet sheet = wb.getSheet(sheetName);
			
			row = sheet.getRow(rowNum);
			cell = row.getCell(0);
			cell.setCellValue(month + " " + year + " " + kantinType + " AİT İŞLETME DEFTERİ");
			
			rowNum = 4;
			ArrayList<Object> sayfaSatirIcerik = null;
			for (int i = 0; i < isletmeSayfaList.size(); i++) {
				for (int j = 0; j < isletmeSayfaList.get(i).getSayfaSatir().size(); j++) {
					row = sheet.getRow(rowNum);
					sayfaSatirIcerik = isletmeSayfaList.get(i).getSayfaSatir().get(j);
					for (int m = 0; m < 11; m++) {
						cell = row.getCell(m);
						if (cell == null) {
							cell = row.createCell(m);
						}
						if (sayfaSatirIcerik.get(m).getClass().isAssignableFrom(BigDecimal.class)) {
							cell.setCellValue(((BigDecimal) sayfaSatirIcerik.get(m)).doubleValue());
						} else if (sayfaSatirIcerik.get(m).getClass().isAssignableFrom(String.class)) {
							cell.setCellValue((String) sayfaSatirIcerik.get(m));
						} else if (sayfaSatirIcerik.get(m).getClass().isAssignableFrom(Date.class)) {
							cell.setCellValue((Date) sayfaSatirIcerik.get(m));
						}  else if (sayfaSatirIcerik.get(m).getClass().isAssignableFrom(Integer.class)) {
							cell.setCellValue((Integer) sayfaSatirIcerik.get(m));
						}
					}
					rowNum++;
				}
				if (isletmeSayfaList.get(i).getSayfaSatir().size() < 26) {
					rowNum = rowNum + (26 - isletmeSayfaList.get(i).getSayfaSatir().size());
				}
				row = sheet.getRow(rowNum);
				cell = row.getCell(6);
				cell.setCellValue(isletmeSayfaList.get(i).getSayfaToplamAlis().doubleValue());
				
				cell = row.getCell(8);
				cell.setCellValue(isletmeSayfaList.get(i).getSayfaToplamSatis().doubleValue());
				
				cell = row.getCell(9);
				cell.setCellValue(isletmeSayfaList.get(i).getSayfaToplamKar().doubleValue());
				rowNum++;
				
				row = sheet.getRow(rowNum);
				cell = row.getCell(6);
				cell.setCellValue(isletmeSayfaList.get(i).getSayfaKumulatifToplamAlis().doubleValue());
				
				cell = row.getCell(8);
				cell.setCellValue(isletmeSayfaList.get(i).getSayfaKumulatifToplamSatis().doubleValue());
				
				cell = row.getCell(9);
				cell.setCellValue(isletmeSayfaList.get(i).getSayfaKumulatifToplamKar().doubleValue());
				rowNum = rowNum + 3;
				
				row = sheet.getRow(rowNum);
				cell = row.getCell(6);
				cell.setCellValue(isletmeSayfaList.get(i).getSayfaKumulatifToplamAlis().doubleValue());
				
				cell = row.getCell(8);
				cell.setCellValue(isletmeSayfaList.get(i).getSayfaKumulatifToplamSatis().doubleValue());
				
				cell = row.getCell(9);
				cell.setCellValue(isletmeSayfaList.get(i).getSayfaKumulatifToplamKar().doubleValue());
				
				if (i == isletmeSayfaList.size() - 1) {
					isletmeToplamAlis = isletmeSayfaList.get(i).getSayfaKumulatifToplamAlis();
					isletmeToplamSatis = isletmeSayfaList.get(i).getSayfaKumulatifToplamSatis();
					rowNum = rowNum + 28;
				} else {
					rowNum++;
				}
			}
			for (int j = 0; j < totalPageNumber-isletmeSayfaList.size(); j++) {
				row = sheet.getRow(rowNum);
				cell = row.getCell(6);
				cell.setCellValue(isletmeToplamAlis.doubleValue());
				
				cell = row.getCell(8);
				cell.setCellValue(isletmeToplamSatis.doubleValue());
				
				cell = row.getCell(9);
				cell.setCellValue(isletmeToplamSatis.subtract(isletmeToplamAlis).doubleValue());
				if(j != totalPageNumber-isletmeSayfaList.size()-1) {
					rowNum = rowNum + 3;
					
					row = sheet.getRow(rowNum);
					cell = row.getCell(6);
					cell.setCellValue(isletmeToplamAlis.doubleValue());
					
					cell = row.getCell(8);
					cell.setCellValue(isletmeToplamSatis.doubleValue());
					
					cell = row.getCell(9);
					cell.setCellValue(isletmeToplamSatis.subtract(isletmeToplamAlis).doubleValue());
					rowNum = rowNum + 28;
				}
			}
			
			rowNum = rowNum + 5;
			row = sheet.getRow(rowNum);
			cell = row.getCell(0);
			cell.setCellValue("2'nci ORDU KH.DES.GRP. KOMUTANLIĞI " + kantinType + " KANTİNİNİN " + month + " " + year + " AYI HESAP ÖZETİ");
			List<String> monthList =  Arrays.asList(months);
			//TODO ocak aralik kontrol
			String previousMonth = monthList.get(monthList.indexOf(month)-1);
			String nextMonth = monthList.get(monthList.indexOf(month)+1);
			rowNum = rowNum + 3;
			
			row = sheet.getRow(rowNum);
			cell = row.getCell(3);
			cell.setCellValue(previousMonth + " " + year + "'DEN DEVREDEN MAL");
			rowNum++;
			
			row = sheet.getRow(rowNum);
			cell = row.getCell(3);
			cell.setCellValue(month + " " + year + "'DE ALINAN MAL");
			rowNum = rowNum + 2;
			
			row = sheet.getRow(rowNum);
			cell = row.getCell(3);
			cell.setCellValue(nextMonth + " " + year + "'E DEVREDEN MAL");
			rowNum = rowNum + 2;
			
			row = sheet.getRow(rowNum);
			cell = row.getCell(3);
			cell.setCellValue(month + " " + year + "'DE YAPILAN SATIŞ TUTARI");
			
			
			FileOutputStream fileOut = new FileOutputStream(outputFilePath);
			wb.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			log.info(e.getMessage());
			throw new StockManagementException(e);
		} finally {
	
		}
	}
	
}
