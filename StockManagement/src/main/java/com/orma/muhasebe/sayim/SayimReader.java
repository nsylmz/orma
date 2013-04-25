package com.orma.muhasebe.sayim;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.orma.exception.StockManagementException;
import com.orma.muhasebe.domain.sayim.SayimDokuman;
import com.orma.muhasebe.domain.sayim.SayimSatir;
import com.orma.muhasebe.domain.sayim.SayimSayfa;
import com.thoughtworks.xstream.XStream;

public class SayimReader {
	
	private static Logger log = Logger.getLogger(SayimReader.class);
	
	private static XStream stream = new XStream();
	
	public static SayimDokuman readSayim(String filePath, String sheetName)  throws StockManagementException {
		
		SayimDokuman sayimDokuman = null;
		boolean dokumanBasladi = false;
		boolean dokumanBitti = false;
		Row row = null;
		SayimSayfa sayfa = null;
		SayimSatir satir = null;
		ArrayList<SayimSayfa> sayimSayfaList = null;
		ArrayList<SayimSatir> sayimSatirList = null;
		
		BigDecimal kumulatifAlisToplam = BigDecimal.ZERO;
		BigDecimal kumulatifSatisToplam = BigDecimal.ZERO;
		BigDecimal sayfaAlisToplam = BigDecimal.ZERO;
		BigDecimal sayfaSatisToplam = BigDecimal.ZERO;
		
		int sayfaNo = 1; 
		int satirCounter = 1;
		
		try {
			FileInputStream file = new FileInputStream(new File(filePath));
	        
			//Get the workbook instance for XLS file 
			HSSFWorkbook workbook = new HSSFWorkbook(file);
			
			//Get first sheet from the workbook
			HSSFSheet sheet = null;
			if (sheetName != null && sheetName.length() > 0) {
				sheet = workbook.getSheet(sheetName);
			} else {
				sheet = workbook.getSheetAt(0);
			}
			
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) { 
				row = (Row) rowIterator.next();
				
				if(!dokumanBasladi) {
					dokumanBasladi = checkDokumanSeperator(row);
					if (dokumanBasladi) {
						sayimDokuman = new SayimDokuman();
						sayimSayfaList = new ArrayList<SayimSayfa>();
						sayimDokuman.setSayfaList(sayimSayfaList);
						sayfa = new SayimSayfa();
						sayimSatirList = new ArrayList<SayimSatir>();
						sayfa.setSayimSatirList(sayimSatirList);
						sayfa.setSayfaNo(sayfaNo);
						sayfaNo++;
					}
				} else if (dokumanBasladi) {
					dokumanBitti = checkFaturaSeperator(row);
					if (dokumanBitti) {
						if (satirCounter < 50) {
							kumulatifAlisToplam = kumulatifAlisToplam.add(sayfaAlisToplam);
							kumulatifSatisToplam = kumulatifSatisToplam.add(sayfaSatisToplam);
							sayfa.setKumulatifAlisToplam(kumulatifAlisToplam);
							sayfa.setKumulatifSatisToplam(kumulatifSatisToplam);
							sayfa.setKumulatifKarToplam(kumulatifSatisToplam.subtract(kumulatifAlisToplam));
							sayfa.setSayfaAlisToplam(sayfaAlisToplam);
							sayfa.setSayfaSatisToplam(sayfaSatisToplam);
							sayfa.setSayfaKarToplam(sayfaSatisToplam.subtract(sayfaAlisToplam));
							sayimSayfaList.add(sayfa);
						}
						row = (Row) rowIterator.next();
						row = (Row) rowIterator.next();
						row = (Row) rowIterator.next();
						if (BigDecimal.valueOf(row.getCell(6).getNumericCellValue()).compareTo(kumulatifAlisToplam) != 0) {
							// TODO: throw exception
						} else if (BigDecimal.valueOf(row.getCell(8).getNumericCellValue()).compareTo(kumulatifSatisToplam) != 0) {
							// TODO: throw exception
						}
						sayimDokuman.setKumulatifAlisToplam(kumulatifAlisToplam);
						sayimDokuman.setKumulatifSatisToplam(kumulatifSatisToplam);
						sayimDokuman.setKumulatifKarToplam(kumulatifSatisToplam.subtract(kumulatifAlisToplam));
					} else if (dokumanBasladi && !dokumanBitti) {
						if (satirCounter == 51) {
							// Eski sayfa
							kumulatifAlisToplam = kumulatifAlisToplam.add(sayfaAlisToplam);
							kumulatifSatisToplam = kumulatifSatisToplam.add(sayfaSatisToplam);
							sayfa.setKumulatifAlisToplam(kumulatifAlisToplam);
							sayfa.setKumulatifSatisToplam(kumulatifSatisToplam);
							sayfa.setKumulatifKarToplam(kumulatifSatisToplam.subtract(kumulatifAlisToplam));
							sayfa.setSayfaAlisToplam(sayfaAlisToplam);
							sayfa.setSayfaSatisToplam(sayfaSatisToplam);
							sayfa.setSayfaKarToplam(sayfaSatisToplam.subtract(sayfaAlisToplam));
							sayimSayfaList.add(sayfa);
							
							// Yeni sayfa
							sayfa = new SayimSayfa();
							sayimSatirList = new ArrayList<SayimSatir>();
							sayfa.setSayimSatirList(sayimSatirList);
							sayfa.setSayfaNo(sayfaNo);
							sayfaAlisToplam = BigDecimal.ZERO;
							sayfaSatisToplam = BigDecimal.ZERO;
							sayfaNo++;
							satirCounter = 1;
						}
						satir = new SayimSatir();
						satir.setUrunAdi(row.getCell(2).getStringCellValue());
						satir.setMiktar(BigDecimal.valueOf(row.getCell(3).getNumericCellValue()));
						satir.setBirimAlisFiyati(BigDecimal.valueOf(row.getCell(4).getNumericCellValue()));
						satir.setToplamAlisTutari(BigDecimal.valueOf(row.getCell(5).getNumericCellValue()));
						satir.setBirimSatisFiyati(BigDecimal.valueOf(row.getCell(6).getNumericCellValue()));
						satir.setToplamSatisTutari(BigDecimal.valueOf(row.getCell(7).getNumericCellValue()));
						satir.setKar(BigDecimal.valueOf(row.getCell(8).getNumericCellValue()));
						sayimSatirList.add(satir);
						satirCounter++;
						
						sayfaAlisToplam = sayfaAlisToplam.add(satir.getToplamAlisTutari());
						sayfaSatisToplam = sayfaSatisToplam.add(satir.getToplamSatisTutari());
					}
				}
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			throw new StockManagementException(e);
		} finally {
			log.debug(stream.toXML(sayimDokuman));
		}
		return sayimDokuman;
	}
	
	private static boolean checkFaturaSeperator(Row row) {
		Cell cell = row.getCell(0);
		if (cell != null && cell.getCellType() == 1) {
			String value = cell.getStringCellValue();
			if (value.startsWith("---")) {
				return true;
			}
		}
		return false;
	}
	
	private static boolean checkDokumanSeperator(Row row) {
		Cell cell = row.getCell(0);
		if (cell != null && cell.getCellType() == 1) {
			String value = cell.getStringCellValue();
			if (value.startsWith("=")) {
				return true;
			}
		}
		return false;
	}

}
