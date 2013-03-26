package com.orma.muhasebe.sayim;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.orma.muhasebe.domain.sayim.SayimDokuman;
import com.orma.muhasebe.domain.sayim.SayimSatir;

public class SayimWriter {
	
	
	public static void writeSayimFile(String baseFilePath,
			String outputFilePath, String sheetName, int templatePageNumber,
			SayimDokuman sayimDokuman, String month, int year) throws Exception {
		templatePageNumber = templatePageNumber - 1;
		
		InputStream inp = new FileInputStream(baseFilePath);
		Workbook wb = WorkbookFactory.create(inp);
		Sheet sheet = wb.getSheet(sheetName);
		
		Row row = null;
		Cell cell = null;
		int rowNum = 0;
		SayimSatir sayimSatir = null;
		
		row = sheet.getRow(rowNum);
		cell = row.getCell(0);
		cell.setCellValue(month + " " + year + " MARKET ŞUBESİNE AİT SAYIM LİSTESİ");
		
		rowNum = 2;
		
		for (int i = 0; i < sayimDokuman.getSayfaList().size(); i++) {
			for (int j = 0; j < sayimDokuman.getSayfaList().get(i).getSayimSatirList().size(); j++) {
				sayimSatir = sayimDokuman.getSayfaList().get(i).getSayimSatirList().get(j);
				row = sheet.getRow(rowNum);
				row.getCell(1).setCellValue(sayimSatir.getUrunAdi());
				row.getCell(2).setCellValue(sayimSatir.getMiktar().doubleValue());
				row.getCell(3).setCellValue(sayimSatir.getBirimAlisFiyati().doubleValue());
				row.getCell(4).setCellValue(sayimSatir.getToplamAlisTutari().doubleValue());
				row.getCell(5).setCellValue(sayimSatir.getBirimSatisFiyati().doubleValue());
				row.getCell(6).setCellValue(sayimSatir.getToplamSatisTutari().doubleValue());
				row.getCell(7).setCellValue(sayimSatir.getKar().doubleValue());
			}
		}
	}

}
