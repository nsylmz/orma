package com.orma.muhasebe.vega;

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
import com.orma.muhasebe.domain.vega.Fatura;
import com.orma.muhasebe.domain.vega.Urun;
import com.orma.muhasebe.domain.vega.VegaDokuman;
import com.thoughtworks.xstream.XStream;

public class VegaDokumanReader {
	
	private static Logger log = Logger.getLogger(VegaDokumanReader.class);
	
	private static XStream stream = new XStream();
	
	public static VegaDokuman readDokuman(String filePath, String sheetName, boolean processed) throws StockManagementException {
		HSSFSheet sheet = null;
		VegaDokuman dokuman = null;
		Fatura fatura = null;
		Urun urun = null;
		Row row = null;
		ArrayList<Fatura> faturaListesi = null;
		ArrayList<Urun> urunListesi = null; 
		boolean faturaBasladi = false;
		boolean faturaBitti = false;
		boolean dokumanBasladi = false;
		boolean dokumanBitti = false;
		String firmaAdi = null;
		
		try {
			FileInputStream file = new FileInputStream(new File(filePath));
	        
			//Get the workbook instance for XLS file 
			HSSFWorkbook workbook = new HSSFWorkbook(file);
			
			//Get first sheet from the workbook
			
			if (sheetName != null && sheetName.length() > 0) {
				sheet = workbook.getSheet(sheetName);
			} else {
				sheet = workbook.getSheetAt(0);
			}
			
			int siraNo = 1;
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				row = (Row) rowIterator.next();
				
				if(!dokumanBasladi) {
					dokumanBasladi = checkDokumanSeperator(row);
					if (dokumanBasladi) {
						fatura = new Fatura();
						urunListesi = new ArrayList<Urun>();
						fatura.setUrunListesi(urunListesi);
						faturaBasladi = true;
						
						dokuman = new VegaDokuman();
						faturaListesi = new ArrayList<Fatura>();
						dokuman.setFaturaListesi(faturaListesi);
					}
				} else if (!dokumanBitti && faturaBitti && dokumanBasladi && !faturaBasladi) {
					dokumanBitti = checkDokumanSeperator(row);
					if (dokumanBitti) {
						row = (Row) rowIterator.next();
						if (!processed) {
							dokuman.setToplamAlisTutari(row.getCell(7).getNumericCellValue());
							dokuman.setToplamSatisTutari(row.getCell(9).getNumericCellValue());
							dokuman.setToplamKar(row.getCell(10).getNumericCellValue());
							break;
						} else {
							dokuman.setToplamAlisTutari(row.getCell(6).getNumericCellValue());
							dokuman.setToplamSatisTutari(row.getCell(8).getNumericCellValue());
							dokuman.setToplamKar(row.getCell(9).getNumericCellValue());
							break;
						}
					} else {
						faturaBasladi = checkFaturaSeperator(row);
						if (faturaBasladi) {
							faturaBitti = false;
							fatura = new Fatura();
							urunListesi = new ArrayList<Urun>();
							fatura.setUrunListesi(urunListesi);
						}
					}
				} else if (dokumanBasladi && faturaBasladi) {
					faturaBitti = checkFaturaSeperator(row);
					if (faturaBitti) { // Fatura Bitti
						row = (Row) rowIterator.next();
						fatura.setTarih(row.getCell(1).getDateCellValue());
						if (row.getCell(2).getCellType() == Cell.CELL_TYPE_NUMERIC) {
							fatura.setEvrakNo(BigDecimal.valueOf(row.getCell(2).getNumericCellValue()).unscaledValue().toString());
						} else if (row.getCell(2).getCellType() == Cell.CELL_TYPE_STRING) {
							fatura.setEvrakNo(row.getCell(2).getStringCellValue().trim());
						}
						
						if (!processed) {
							fatura.setSiraNo(siraNo);
							fatura.setToplamAlisTutari(BigDecimal.valueOf(row.getCell(7).getNumericCellValue()));
							fatura.setToplamSatisTutari(BigDecimal.valueOf(row.getCell(9).getNumericCellValue()));
							fatura.setToplamKar(BigDecimal.valueOf(row.getCell(10).getNumericCellValue()));
							fatura.setFirmaUnvani(firmaAdi);
							faturaListesi.add(fatura);
							siraNo++;
						} else {
							fatura.setSiraNo((int) row.getCell(0).getNumericCellValue());
							fatura.setToplamAlisTutari(BigDecimal.valueOf(row.getCell(6).getNumericCellValue()));
							fatura.setToplamSatisTutari(BigDecimal.valueOf(row.getCell(8).getNumericCellValue()));
							fatura.setToplamKar(BigDecimal.valueOf(row.getCell(9).getNumericCellValue()));
							fatura.setFirmaUnvani(row.getCell(10).getStringCellValue());
							faturaListesi.add(fatura);
						}
						faturaBasladi = false;
					} else {
						urun = new Urun();
						if (!processed) {
							urun.setUrunAdi(row.getCell(4).getStringCellValue());
							urun.setMiktar(BigDecimal.valueOf(row.getCell(5).getNumericCellValue()));
							urun.setBirimAlisFiyati(BigDecimal.valueOf(row.getCell(6).getNumericCellValue()));
							urun.setToplamAlisTutari(BigDecimal.valueOf(row.getCell(7).getNumericCellValue()));
							urun.setBirimSatisFiyati(BigDecimal.valueOf(row.getCell(8).getNumericCellValue()));
							urun.setToplamSatisTutari(BigDecimal.valueOf(row.getCell(9).getNumericCellValue()));
							urun.setKar(BigDecimal.valueOf(row.getCell(10).getNumericCellValue()));
							firmaAdi = row.getCell(11).getStringCellValue();
						} else {
							urun.setUrunAdi(row.getCell(3).getStringCellValue());
							urun.setMiktar(BigDecimal.valueOf(row.getCell(4).getNumericCellValue()));
							urun.setBirimAlisFiyati(BigDecimal.valueOf(row.getCell(5).getNumericCellValue()));
							urun.setToplamAlisTutari(BigDecimal.valueOf(row.getCell(6).getNumericCellValue()));
							urun.setBirimSatisFiyati(BigDecimal.valueOf(row.getCell(7).getNumericCellValue()));
							urun.setToplamSatisTutari(BigDecimal.valueOf(row.getCell(8).getNumericCellValue()));
							urun.setKar(BigDecimal.valueOf(row.getCell(9).getNumericCellValue()));
						}
						urunListesi.add(urun);
					}
				}
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			throw new StockManagementException(e);
		} finally {
			log.debug(stream.toXML(dokuman));
		}
		return dokuman;
	}
	
	private static boolean checkDokumanSeperator(Row row) {
		Cell cell = row.getCell(0);
		if (cell != null && cell.getCellType() == 1) {
			String value = cell.getStringCellValue();
			if (value.startsWith("===")) {
				return true;
			}
		}
		return false;
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

}
