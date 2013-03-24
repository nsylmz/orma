package com.orma.muhasebe.isletme;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.orma.muhasebe.domain.isletme.IsletmeSayfa;
import com.orma.muhasebe.domain.vega.VegaDokuman;
import com.orma.muhasebe.vega.VegaDokumanProcessor;
import com.orma.muhasebe.vega.VegaDokumanReader;
import com.orma.muhasebe.vega.VegaDokumanWriter;

public class IsletmeWriter {
	
	public static String[] months = {"OCAK", "ŞUBAT", "MART", "NİSAN", "MAYIS", "HAZİRAN", "TEMMUZ", "AĞUSTOS", "EYLÜL", "EKİM", "KASIM", "ARALIK"};

	public static void main(String[] args) throws Exception {
		
		
		// MARKET
		// On hazirlik
//		VegaDokuman dokuman = VegaDokumanReader.readDokuman("D:\\Enes\\Springsource\\test data\\input\\MARKET02.xls", "", false);
//		for (int i = 0; i < dokuman.getFaturaListesi().size(); i++) {
//			VegaDokumanProcessor.faturaCheckAndFix(dokuman.getFaturaListesi().get(i));
//		}
//		VegaDokumanWriter.writeDataToExcelFile("D:\\Enes\\Springsource\\test data\\output\\marketOutput.xls", dokuman);
//		
//		// Isletme Defteri
//		VegaDokuman processedDokuman = VegaDokumanReader.readDokuman(
//				"D:\\Enes\\Springsource\\test data\\output\\marketOutput.xls", "",
//				true);
//		
//		ArrayList<IsletmeSayfa> isletmeSayfaList = IsletmeProcessor.convertVegaToIsletme(processedDokuman);
//		
//		writeIsletmeFile(
//				"D:\\Enes\\Springsource\\test data\\sablon\\168 Sayfa Sablon.xlsx",
//				"D:\\Enes\\Springsource\\test data\\output\\ŞUBAT MARKET.xlsx",
//				"MARKET", 167, isletmeSayfaList, "ŞUBAT", 2013, "AİLE KANTİNİ");
		
		// BUFE
		// On hazirlik
		VegaDokuman dokuman = VegaDokumanReader.readDokuman("D:\\Enes\\Springsource\\test data\\input\\BUFE02.xls", "BUFE02", false);
		for (int i = 0; i < dokuman.getFaturaListesi().size(); i++) {
			VegaDokumanProcessor.faturaCheckAndFix(dokuman.getFaturaListesi().get(i));
		}
		VegaDokumanWriter.writeDataToExcelFile("D:\\Enes\\Springsource\\test data\\output\\bufeOutput.xls", dokuman);
		
		// Isletme Defteri
		VegaDokuman processedDokuman = VegaDokumanReader.readDokuman(
				"D:\\Enes\\Springsource\\test data\\output\\bufeOutput.xls", "",
				true);
		
		ArrayList<IsletmeSayfa> isletmeSayfaList = IsletmeProcessor.convertVegaToIsletme(processedDokuman);
		
		writeIsletmeFile(
				"D:\\Enes\\Springsource\\test data\\sablon\\20 Sayfa Sablon.xlsx",
				"D:\\Enes\\Springsource\\test data\\output\\ŞUBAT BUFE.xlsx",
				"SABLON", 19, isletmeSayfaList, "ŞUBAT", 2013, "BUFE");
		
		// KURUYEMIS
		// On hazirlik
//		VegaDokuman dokuman = VegaDokumanReader.readDokuman("D:\\Enes\\Springsource\\test data\\input\\KURUYE02.xls", null, false);
//		for (int i = 0; i < dokuman.getFaturaListesi().size(); i++) {
//			VegaDokumanProcessor.faturaCheckAndFix(dokuman.getFaturaListesi().get(i));
//		}
//		VegaDokumanWriter.writeDataToExcelFile("D:\\Enes\\Springsource\\test data\\output\\kuruyemisOutput.xls", dokuman);
//		
//		// Isletme Defteri
//		VegaDokuman processedDokuman = VegaDokumanReader.readDokuman(
//				"D:\\Enes\\Springsource\\test data\\output\\kuruyemisOutput.xls", null, true);
//		
//		ArrayList<IsletmeSayfa> isletmeSayfaList = IsletmeProcessor.convertVegaToIsletme(processedDokuman);
//		
//		writeIsletmeFile(
//				"D:\\Enes\\Springsource\\test data\\sablon\\20 Sayfa Sablon.xlsx",
//				"D:\\Enes\\Springsource\\test data\\output\\ŞUBAT KURUYEMİŞ.xlsx",
//				"SABLON", 19, isletmeSayfaList, "ŞUBAT", 2013, "KURUYEMİŞ");
		
		// KARARGAH
		// On hazirlik
//		VegaDokuman dokuman = VegaDokumanReader.readDokuman("D:\\Enes\\Springsource\\test data\\input\\SUBAT KANTINLER.xls", "KAORKA02", false);
//		for (int i = 0; i < dokuman.getFaturaListesi().size(); i++) {
//			VegaDokumanProcessor.faturaCheckAndFix(dokuman.getFaturaListesi().get(i));
//		}
//		VegaDokumanWriter.writeDataToExcelFile("D:\\Enes\\Springsource\\test data\\output\\karargahOutput.xls", dokuman);
//		
//		// Isletme Defteri
//		VegaDokuman processedDokuman = VegaDokumanReader.readDokuman(
//				"D:\\Enes\\Springsource\\test data\\output\\karargahOutput.xls", null, true);
//		
//		ArrayList<IsletmeSayfa> isletmeSayfaList = IsletmeProcessor.convertVegaToIsletme(processedDokuman);
//		
//		writeIsletmeFile(
//				"D:\\Enes\\Springsource\\test data\\sablon\\20 Sayfa Sablon.xlsx",
//				"D:\\Enes\\Springsource\\test data\\output\\ŞUBAT KARARGAH.xlsx",
//				"SABLON", 19, isletmeSayfaList, "ŞUBAT", 2013, "KARARGAH KANTİNİ");
		
		// ISOCAK
		// On hazirlik
//		VegaDokuman dokuman = VegaDokumanReader.readDokuman("D:\\Enes\\Springsource\\test data\\input\\SUBAT KANTINLER.xls", "ISOCAK02", false);
//		for (int i = 0; i < dokuman.getFaturaListesi().size(); i++) {
//			VegaDokumanProcessor.faturaCheckAndFix(dokuman.getFaturaListesi().get(i));
//		}
//		VegaDokumanWriter.writeDataToExcelFile("D:\\Enes\\Springsource\\test data\\output\\isocakOutput.xls", dokuman);
//		
//		// Isletme Defteri
//		VegaDokuman processedDokuman = VegaDokumanReader.readDokuman(
//				"D:\\Enes\\Springsource\\test data\\output\\isocakOutput.xls", null, true);
//		
//		ArrayList<IsletmeSayfa> isletmeSayfaList = IsletmeProcessor.convertVegaToIsletme(processedDokuman);
//		
//		writeIsletmeFile(
//				"D:\\Enes\\Springsource\\test data\\sablon\\20 Sayfa Sablon.xlsx",
//				"D:\\Enes\\Springsource\\test data\\output\\ŞUBAT ISOCAK.xlsx",
//				"SABLON", 19, isletmeSayfaList, "ŞUBAT", 2013, "İŞOCAK KANTİNİ");

		// ERKANT
		// On hazirlik
//		VegaDokuman dokuman = VegaDokumanReader.readDokuman("D:\\Enes\\Springsource\\test data\\input\\SUBAT KANTINLER.xls", "ERKANT02", false);
//		for (int i = 0; i < dokuman.getFaturaListesi().size(); i++) {
//			VegaDokumanProcessor.faturaCheckAndFix(dokuman.getFaturaListesi().get(i));
//		}
//		VegaDokumanWriter.writeDataToExcelFile("D:\\Enes\\Springsource\\test data\\output\\erkantinOutput.xls", dokuman);
//		
//		// Isletme Defteri
//		VegaDokuman processedDokuman = VegaDokumanReader.readDokuman(
//				"D:\\Enes\\Springsource\\test data\\output\\erkantinOutput.xls", null, true);
//		
//		ArrayList<IsletmeSayfa> isletmeSayfaList = IsletmeProcessor.convertVegaToIsletme(processedDokuman);
//		
//		writeIsletmeFile(
//				"D:\\Enes\\Springsource\\test data\\sablon\\20 Sayfa Sablon.xlsx",
//				"D:\\Enes\\Springsource\\test data\\output\\ŞUBAT ERKANTIN.xlsx",
//				"SABLON", 19, isletmeSayfaList, "ŞUBAT", 2013, "ER VE ERBAŞ KANTİNİ");
		
		
		// ASSB
		// On hazirlik
//		VegaDokuman dokuman = VegaDokumanReader.readDokuman("D:\\Enes\\Springsource\\test data\\input\\SUBAT KANTINLER.xls", "ASSB02", false);
//		for (int i = 0; i < dokuman.getFaturaListesi().size(); i++) {
//			VegaDokumanProcessor.faturaCheckAndFix(dokuman.getFaturaListesi().get(i));
//		}
//		VegaDokumanWriter.writeDataToExcelFile("D:\\Enes\\Springsource\\test data\\output\\assbOutput.xls", dokuman);
//		
//		// Isletme Defteri
//		VegaDokuman processedDokuman = VegaDokumanReader.readDokuman(
//				"D:\\Enes\\Springsource\\test data\\output\\assbOutput.xls", null, true);
//		
//		ArrayList<IsletmeSayfa> isletmeSayfaList = IsletmeProcessor.convertVegaToIsletme(processedDokuman);
//		
//		writeIsletmeFile(
//				"D:\\Enes\\Springsource\\test data\\sablon\\20 Sayfa Sablon.xlsx",
//				"D:\\Enes\\Springsource\\test data\\output\\ŞUBAT ASSB.xlsx",
//				"SABLON", 19, isletmeSayfaList, "ŞUBAT", 2013, "ASKERLİK ŞUBESİ KANTİNİ");
		
		// AKOGUZ
		// On hazirlik
//		VegaDokuman dokuman = VegaDokumanReader.readDokuman("D:\\Enes\\Springsource\\test data\\input\\SUBAT KANTINLER.xls", "AKOGUZ02", false);
//		for (int i = 0; i < dokuman.getFaturaListesi().size(); i++) {
//			VegaDokumanProcessor.faturaCheckAndFix(dokuman.getFaturaListesi().get(i));
//		}
//		VegaDokumanWriter.writeDataToExcelFile("D:\\Enes\\Springsource\\test data\\output\\akoguzOutput.xls", dokuman);
//		
//		// Isletme Defteri
//		VegaDokuman processedDokuman = VegaDokumanReader.readDokuman(
//				"D:\\Enes\\Springsource\\test data\\output\\akoguzOutput.xls", null, true);
//		
//		ArrayList<IsletmeSayfa> isletmeSayfaList = IsletmeProcessor.convertVegaToIsletme(processedDokuman);
//		
//		writeIsletmeFile(
//				"D:\\Enes\\Springsource\\test data\\sablon\\20 Sayfa Sablon.xlsx",
//				"D:\\Enes\\Springsource\\test data\\output\\ŞUBAT AKOGUZ.xlsx",
//				"SABLON", 19, isletmeSayfaList, "ŞUBAT", 2013, "AKOGUZ KANTİNİ");
	}

	public static void writeIsletmeFile(String baseFilePath,
			String outputFilePath, String sheetName, int totalPageNumber,
			ArrayList<IsletmeSayfa> isletmeSayfaList, String month, int year, String kantinType) throws Exception {

		totalPageNumber = totalPageNumber - 1;
		
		InputStream inp = new FileInputStream(baseFilePath);
		Workbook wb = WorkbookFactory.create(inp);
		Sheet sheet = wb.getSheet(sheetName);

		BigDecimal isletmeToplamAlis = BigDecimal.ZERO;
		BigDecimal isletmeToplamSatis = BigDecimal.ZERO;
		Row row = null;
		Cell cell = null;
		int rowNum = 0;
		
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
	}
	
}
