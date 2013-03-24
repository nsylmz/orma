package com.orma.muhasebe.vega;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.orma.muhasebe.domain.vega.Urun;
import com.orma.muhasebe.domain.vega.VegaDokuman;

public class VegaDokumanWriter {
	
	public static void main(String[] args) throws IOException {
		VegaDokuman dokuman = VegaDokumanReader.readDokuman("D:\\Enes\\Springsource\\test data\\input\\BUFE02.xls", "BUFE02", false);
		for (int i = 0; i < dokuman.getFaturaListesi().size(); i++) {
			VegaDokumanProcessor.faturaCheckAndFix(dokuman.getFaturaListesi().get(i));
		}
		writeDataToExcelFile("D:\\Enes\\Springsource\\test data\\output\\bufeOutput.xls", dokuman);
	}

	public static void writeDataToExcelFile(String filePath, VegaDokuman dokuman) {

		HSSFWorkbook workBook = new HSSFWorkbook();
		HSSFSheet sheet = workBook.createSheet();
		HSSFRow row = null;
		
		HSSFCell siraNoCell = null;	
		HSSFCell tarihCell = null;	
		HSSFCell evrakNoCell = null;
		HSSFCell aciklamaCell = null;	
		HSSFCell miktarCell = null;	
		HSSFCell maliyetiCell = null;	
		HSSFCell alisTutariCell = null;	
		HSSFCell satisFiyatiCell = null;	
		HSSFCell satisTutariCell = null;	
		HSSFCell karCell = null;
		HSSFCell firmaCell = null;
		
		int rowNum = 0;
		
		prepareHeader(sheet, rowNum);
		rowNum++;
		
		prepareDokumanSeparetionRow(sheet, rowNum);
		rowNum++;
		
		Urun urun = null;
		for (int faturaNum = 0; faturaNum < dokuman.getFaturaListesi().size(); faturaNum++) {
			
			if (rowNum != 2) {
				prepareFaturaSeparetionRow(sheet, rowNum);
				rowNum++;
			}
			
			for(int urunNum = 0; urunNum < dokuman.getFaturaListesi().get(faturaNum).getUrunListesi().size(); urunNum++) {
				urun = dokuman.getFaturaListesi().get(faturaNum).getUrunListesi().get(urunNum);
				row = sheet.createRow(rowNum);
				
				aciklamaCell = row.createCell(3);
				aciklamaCell.setCellValue(urun.getUrunAdi());
				
				miktarCell = row.createCell(4);
				miktarCell.setCellValue(urun.getMiktar().doubleValue());
				
				maliyetiCell = row.createCell(5);
				maliyetiCell.setCellValue(urun.getBirimAlisFiyati().doubleValue());
				
				alisTutariCell = row.createCell(6);
				alisTutariCell.setCellValue(urun.getToplamAlisTutari().doubleValue());
				
				satisFiyatiCell = row.createCell(7);
				satisFiyatiCell.setCellValue(urun.getBirimSatisFiyati().doubleValue());
				
				satisTutariCell = row.createCell(8);
				satisTutariCell.setCellValue(urun.getToplamSatisTutari().doubleValue());
				
				karCell = row.createCell(9);
				karCell.setCellValue(urun.getKar().doubleValue());
				
				rowNum++;
			}
			prepareFaturaSeparetionRow(sheet, rowNum);
			rowNum++;
			
			row = sheet.createRow(rowNum);
			
			siraNoCell = row.createCell(0);
			siraNoCell.setCellValue(dokuman.getFaturaListesi().get(faturaNum).getSiraNo());
			
			tarihCell = row.createCell(1);
			tarihCell.setCellValue(dokuman.getFaturaListesi().get(faturaNum).getTarih());
			
			evrakNoCell = row.createCell(2);
			evrakNoCell.setCellValue(dokuman.getFaturaListesi().get(faturaNum).getEvrakNo().doubleValue());
			
			alisTutariCell = row.createCell(6);
			alisTutariCell.setCellValue(dokuman.getFaturaListesi().get(faturaNum).getToplamAlisTutari().doubleValue());
			
			satisTutariCell = row.createCell(8);
			satisTutariCell.setCellValue(dokuman.getFaturaListesi().get(faturaNum).getToplamSatisTutari().doubleValue());
			
			karCell = row.createCell(9);
			karCell.setCellValue(dokuman.getFaturaListesi().get(faturaNum).getToplamKar().doubleValue());
			
			firmaCell = row.createCell(10);
			firmaCell.setCellValue(dokuman.getFaturaListesi().get(faturaNum).getFirmaUnvani());
			rowNum++;
		}
		prepareDokumanSeparetionRow(sheet, rowNum);
		rowNum++;
		
		row = sheet.createRow(rowNum);
		alisTutariCell = row.createCell(6);
		alisTutariCell.setCellValue(dokuman.getToplamAlisTutari().doubleValue());
		
		satisTutariCell = row.createCell(8);
		satisTutariCell.setCellValue(dokuman.getToplamSatisTutari().doubleValue());
		
		karCell = row.createCell(9);
		karCell.setCellValue(dokuman.getToplamKar().doubleValue());
		
		try {
			FileOutputStream out = new FileOutputStream(filePath);
			workBook.write(out);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void prepareHeader(HSSFSheet sheet, int rowNum) {
		HSSFRow row = null;
		
		HSSFCell siraNoCell = null;	
		HSSFCell tarihCell = null;	
		HSSFCell evrakNoCell = null;
		HSSFCell aciklamaCell = null;	
		HSSFCell miktarCell = null;	
		HSSFCell maliyetiCell = null;	
		HSSFCell alisTutariCell = null;	
		HSSFCell satisFiyatiCell = null;	
		HSSFCell satisTutariCell = null;	
		HSSFCell karCell = null;
		
		row = sheet.createRow(rowNum);
		
		siraNoCell = row.createCell(0);
		siraNoCell.setCellValue("Sira No");
		
		tarihCell = row.createCell(1);
		tarihCell.setCellValue("Tarih");
		
		evrakNoCell = row.createCell(2);
		evrakNoCell.setCellValue("Evrak No");
		
		aciklamaCell = row.createCell(3);
		aciklamaCell.setCellValue("Aciklama");
		
		miktarCell = row.createCell(4);
		miktarCell.setCellValue("Miktar");
		
		maliyetiCell = row.createCell(5);
		maliyetiCell.setCellValue("Maliyet");
		
		alisTutariCell = row.createCell(6);
		alisTutariCell.setCellValue("Alis Tutari");
		
		satisFiyatiCell = row.createCell(7);
		satisFiyatiCell.setCellValue("Satis Fiyati");
		
		satisTutariCell = row.createCell(8);
		satisTutariCell.setCellValue("Satis Tutari");
		
		karCell = row.createCell(9);
		karCell.setCellValue("Kar");
	}
	
	private static void prepareDokumanSeparetionRow(HSSFSheet sheet, int rowNum) {
		HSSFRow row = null;
		
		HSSFCell siraNoCell = null;	
		HSSFCell tarihCell = null;	
		HSSFCell evrakNoCell = null;
		HSSFCell aciklamaCell = null;	
		HSSFCell miktarCell = null;	
		HSSFCell maliyetiCell = null;	
		HSSFCell alisTutariCell = null;	
		HSSFCell satisFiyatiCell = null;	
		HSSFCell satisTutariCell = null;	
		HSSFCell karCell = null;
		
		row = sheet.createRow(rowNum);
		
		siraNoCell = row.createCell(0);
		siraNoCell.setCellValue("===");
		
		tarihCell = row.createCell(1);
		tarihCell.setCellValue("========");
		
		evrakNoCell = row.createCell(2);
		evrakNoCell.setCellValue("=======");
		
		aciklamaCell = row.createCell(3);
		aciklamaCell.setCellValue("==============================");
		
		miktarCell = row.createCell(4);
		miktarCell.setCellValue("=========");
		
		maliyetiCell = row.createCell(5);
		maliyetiCell.setCellValue("=========");
		
		alisTutariCell = row.createCell(6);
		alisTutariCell.setCellValue("=========");
		
		satisFiyatiCell = row.createCell(7);
		satisFiyatiCell.setCellValue("=========");
		
		satisTutariCell = row.createCell(8);
		satisTutariCell.setCellValue("=========");
		
		karCell = row.createCell(9);
		karCell.setCellValue("=========");
	}
	
	private static void prepareFaturaSeparetionRow(HSSFSheet sheet, int rowNum) {
		HSSFRow row = null;
		
		HSSFCell siraNoCell = null;	
		HSSFCell tarihCell = null;	
		HSSFCell evrakNoCell = null;
		HSSFCell aciklamaCell = null;	
		HSSFCell miktarCell = null;	
		HSSFCell maliyetiCell = null;	
		HSSFCell alisTutariCell = null;	
		HSSFCell satisFiyatiCell = null;	
		HSSFCell satisTutariCell = null;	
		HSSFCell karCell = null;
		
		row = sheet.createRow(rowNum);
		
		siraNoCell = row.createCell(0);
		siraNoCell.setCellValue("---");
		
		tarihCell = row.createCell(1);
		tarihCell.setCellValue("--------");
		
		evrakNoCell = row.createCell(2);
		evrakNoCell.setCellValue("-------");
		
		aciklamaCell = row.createCell(3);
		aciklamaCell.setCellValue("------------------------------");
		
		miktarCell = row.createCell(4);
		miktarCell.setCellValue("---------");
		
		maliyetiCell = row.createCell(5);
		maliyetiCell.setCellValue("---------");
		
		alisTutariCell = row.createCell(6);
		alisTutariCell.setCellValue("---------");
		
		satisFiyatiCell = row.createCell(7);
		satisFiyatiCell.setCellValue("---------");
		
		satisTutariCell = row.createCell(8);
		satisTutariCell.setCellValue("---------");
		
		karCell = row.createCell(9);
		karCell.setCellValue("---------");
	}

}
