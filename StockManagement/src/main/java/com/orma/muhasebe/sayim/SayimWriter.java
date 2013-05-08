package com.orma.muhasebe.sayim;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.orma.exception.StockManagementException;
import com.orma.muhasebe.domain.sayim.SayimDokuman;
import com.orma.muhasebe.domain.sayim.SayimSatir;
import com.orma.muhasebe.domain.sayim.SayimSayfa;

public class SayimWriter {
	
//	private static Logger log = Logger.getLogger(SayimReader.class);
	
	public static void writeSayimFile(String baseFilePath,
			String outputFilePath, String sheetName, int templatePageNumber,
			SayimDokuman sayimDokuman, String month, int year) throws StockManagementException {
		
		Row row = null;
		Cell cell = null;
		int rowNum = 0;
		SayimSayfa sayimSayfa = null;
		SayimSatir sayimSatir = null;
		
		try {
			InputStream inp = new FileInputStream(baseFilePath);
			Workbook wb = WorkbookFactory.create(inp);
			Sheet sheet = wb.getSheet(sheetName);
			if (sheet == null) {
				throw new StockManagementException("4", baseFilePath + " dosyasındaki " + sheetName + " adlı sayfa bulunamıyor!!!");
			}
			row = sheet.getRow(rowNum);
			cell = row.getCell(0);
			cell.setCellValue(month + " " + year + " MARKET ŞUBESİNE AİT SAYIM LİSTESİ");
			
			rowNum = 2;
			
			if (sayimDokuman.getSayfaList().size() >= templatePageNumber) {
				// TODO: throw exception for getting larger the template file
			}
			for (int i = 0; i < sayimDokuman.getSayfaList().size(); i++) {
				sayimSayfa = sayimDokuman.getSayfaList().get(i);
				for (int j = 0; j < sayimSayfa.getSayimSatirList().size(); j++) {
					sayimSatir = sayimDokuman.getSayfaList().get(i).getSayimSatirList().get(j);
					row = sheet.getRow(rowNum);
					row.getCell(1).setCellValue(sayimSatir.getUrunAdi());
					row.getCell(2).setCellValue(sayimSatir.getMiktar().doubleValue());
					row.getCell(3).setCellValue(sayimSatir.getBirimAlisFiyati().doubleValue());
					row.getCell(4).setCellValue(sayimSatir.getToplamAlisTutari().doubleValue());
					row.getCell(5).setCellValue(sayimSatir.getBirimSatisFiyati().doubleValue());
					row.getCell(6).setCellValue(sayimSatir.getToplamSatisTutari().doubleValue());
					row.getCell(7).setCellValue(sayimSatir.getKar().doubleValue());
					rowNum++;
				}
				if (sayimSayfa.getSayimSatirList().size() != 50) {
					rowNum = rowNum + (50 - sayimSayfa.getSayimSatirList().size());
				}
				row = sheet.getRow(rowNum);
				row.getCell(4).setCellValue(sayimSayfa.getSayfaAlisToplam().doubleValue());
				row.getCell(6).setCellValue(sayimSayfa.getSayfaSatisToplam().doubleValue());
				row.getCell(7).setCellValue(sayimSayfa.getSayfaKarToplam().doubleValue());
				rowNum++;
				
				row = sheet.getRow(rowNum);
				row.getCell(4).setCellValue(sayimSayfa.getKumulatifAlisToplam().doubleValue());
				row.getCell(6).setCellValue(sayimSayfa.getKumulatifSatisToplam().doubleValue());
				row.getCell(7).setCellValue(sayimSayfa.getKumulatifKarToplam().doubleValue());
				rowNum++;
				
				row = sheet.getRow(rowNum);
				row.getCell(4).setCellValue(sayimSayfa.getKumulatifAlisToplam().doubleValue());
				row.getCell(6).setCellValue(sayimSayfa.getKumulatifSatisToplam().doubleValue());
				row.getCell(7).setCellValue(sayimSayfa.getKumulatifKarToplam().doubleValue());
				rowNum++;
			}
			
			for (int i = 0; i < templatePageNumber-sayimDokuman.getSayfaList().size(); i++) {
				rowNum = rowNum + 50;
				row = sheet.getRow(rowNum);
				row.getCell(4).setCellValue(sayimSayfa.getSayfaAlisToplam().doubleValue());
				row.getCell(6).setCellValue(sayimSayfa.getSayfaSatisToplam().doubleValue());
				row.getCell(7).setCellValue(sayimSayfa.getSayfaKarToplam().doubleValue());
				rowNum++;
				
				row = sheet.getRow(rowNum);
				row.getCell(4).setCellValue(sayimSayfa.getKumulatifAlisToplam().doubleValue());
				row.getCell(6).setCellValue(sayimSayfa.getKumulatifSatisToplam().doubleValue());
				row.getCell(7).setCellValue(sayimSayfa.getKumulatifKarToplam().doubleValue());
				rowNum++;
				
				if (i != templatePageNumber-sayimDokuman.getSayfaList().size()) {
					row = sheet.getRow(rowNum);
					row.getCell(4).setCellValue(sayimSayfa.getKumulatifAlisToplam().doubleValue());
					row.getCell(6).setCellValue(sayimSayfa.getKumulatifSatisToplam().doubleValue());
					row.getCell(7).setCellValue(sayimSayfa.getKumulatifKarToplam().doubleValue());
					rowNum++;
				}
			}
			FileOutputStream fileOut = new FileOutputStream(outputFilePath);
			wb.write(fileOut);
			fileOut.close();
		}  catch (FileNotFoundException e) {
			throw new StockManagementException("3", baseFilePath + " belirtilen dosya bulunamıyor!!!", e.getCause());
		} catch (StockManagementException e) {
			throw new StockManagementException(e.getCode(), e.getMessage(), e.getCause());
		} catch (Exception e) {
			throw new StockManagementException(e);
		}
		
	}

}
