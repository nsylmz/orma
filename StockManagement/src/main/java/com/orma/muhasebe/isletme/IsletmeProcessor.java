package com.orma.muhasebe.isletme;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.orma.exception.StockManagementException;
import com.orma.muhasebe.domain.isletme.IsletmeSayfa;
import com.orma.muhasebe.domain.vega.Fatura;
import com.orma.muhasebe.domain.vega.Urun;
import com.orma.muhasebe.domain.vega.VegaDokuman;

public class IsletmeProcessor {
	
	private static Logger log = Logger.getLogger(IsletmeProcessor.class);
	
	public static ArrayList<IsletmeSayfa> convertVegaToIsletme(VegaDokuman dokuman) throws StockManagementException {
		ArrayList<IsletmeSayfa> isletmeSayfaList = new ArrayList<IsletmeSayfa>();
		
		IsletmeSayfa isletmeSayfa = null;
		int rowCounter = 0;
		int pageCounter = 1;
		Map<Integer, ArrayList<Object>> sayfaSatir = null;
		Urun urun = null;
		Fatura fatura = null;
		ArrayList<Object> row = null;
		
		BigDecimal sayfaToplamAlis = BigDecimal.ZERO;
		BigDecimal sayfaToplamSatis = BigDecimal.ZERO;
		BigDecimal sayfaKumulatifToplamAlis = BigDecimal.ZERO;
		BigDecimal sayfaKumulatifToplamSatis = BigDecimal.ZERO;
		
		try {
			
			for (int i = 0; i < dokuman.getFaturaListesi().size(); i++) {
				if (i == 0) {
					isletmeSayfa = new IsletmeSayfa();
					isletmeSayfa.setSayfaNo(pageCounter);
					sayfaSatir = new HashMap<Integer, ArrayList<Object>>();
					isletmeSayfa.setSayfaSatir(sayfaSatir);
					pageCounter++;
				} else {
					sayfaSatir.put(rowCounter, prepareBillSeperator());
					rowCounter++;
					
					if (rowCounter == 26) {
						//Eski Sayfa
						sayfaKumulatifToplamAlis = sayfaKumulatifToplamAlis.add(sayfaToplamAlis);
						sayfaKumulatifToplamSatis = sayfaKumulatifToplamSatis.add(sayfaToplamSatis);
						
						isletmeSayfa.setSayfaToplamAlis(sayfaToplamAlis);
						isletmeSayfa.setSayfaToplamSatis(sayfaToplamSatis);
						isletmeSayfa.setSayfaToplamKar(sayfaToplamSatis.subtract(sayfaToplamAlis));
						
						isletmeSayfa.setSayfaKumulatifToplamAlis(sayfaKumulatifToplamAlis);
						isletmeSayfa.setSayfaKumulatifToplamSatis(sayfaKumulatifToplamSatis);
						isletmeSayfa.setSayfaKumulatifToplamKar(sayfaKumulatifToplamSatis.subtract(sayfaKumulatifToplamAlis));
						isletmeSayfaList.add(isletmeSayfa);
						
						//Yeni sayfa
						isletmeSayfa = new IsletmeSayfa();
						isletmeSayfa.setSayfaNo(pageCounter);
						sayfaSatir = new HashMap<Integer, ArrayList<Object>>();
						isletmeSayfa.setSayfaSatir(sayfaSatir);
						pageCounter++;
						rowCounter = 0;
						sayfaToplamAlis = BigDecimal.ZERO;
						sayfaToplamSatis = BigDecimal.ZERO;
					}
				}
				fatura = dokuman.getFaturaListesi().get(i);
				for (int j = 0; j < fatura.getUrunListesi().size(); j++) {
					row = new ArrayList<Object>();
					urun = fatura.getUrunListesi().get(j);
					row.add("");
					row.add("");
					row.add("");
					row.add(urun.getUrunAdi());
					row.add(urun.getMiktar());
					row.add(urun.getBirimAlisFiyati());
					row.add(urun.getToplamAlisTutari());
					row.add(urun.getBirimSatisFiyati());
					row.add(urun.getToplamSatisTutari());
					row.add(urun.getKar());
					row.add("");
					sayfaSatir.put(rowCounter, row);
					rowCounter++;
					
					sayfaToplamAlis = sayfaToplamAlis.add(urun.getToplamAlisTutari());
					sayfaToplamSatis = sayfaToplamSatis.add(urun.getToplamSatisTutari());
					
					if (rowCounter == 26) {
						//Eski Sayfa
						sayfaKumulatifToplamAlis = sayfaKumulatifToplamAlis.add(sayfaToplamAlis);
						sayfaKumulatifToplamSatis = sayfaKumulatifToplamSatis.add(sayfaToplamSatis);
						
						isletmeSayfa.setSayfaToplamAlis(sayfaToplamAlis);
						isletmeSayfa.setSayfaToplamSatis(sayfaToplamSatis);
						isletmeSayfa.setSayfaToplamKar(sayfaToplamSatis.subtract(sayfaToplamAlis));
						
						isletmeSayfa.setSayfaKumulatifToplamAlis(sayfaKumulatifToplamAlis);
						isletmeSayfa.setSayfaKumulatifToplamSatis(sayfaKumulatifToplamSatis);
						isletmeSayfa.setSayfaKumulatifToplamKar(sayfaKumulatifToplamSatis.subtract(sayfaKumulatifToplamAlis));
						isletmeSayfaList.add(isletmeSayfa);
						
						//Yeni sayfa
						isletmeSayfa = new IsletmeSayfa();
						isletmeSayfa.setSayfaNo(pageCounter);
						sayfaSatir = new HashMap<Integer, ArrayList<Object>>();
						isletmeSayfa.setSayfaSatir(sayfaSatir);
						pageCounter++;
						rowCounter = 0;
						sayfaToplamAlis = BigDecimal.ZERO;
						sayfaToplamSatis = BigDecimal.ZERO;
					}
				}
				
				sayfaSatir.put(rowCounter, prepareBillSeperator());
				rowCounter++;
				
				if (rowCounter == 26) {
					//Eski Sayfa
					sayfaKumulatifToplamAlis = sayfaKumulatifToplamAlis.add(sayfaToplamAlis);
					sayfaKumulatifToplamSatis = sayfaKumulatifToplamSatis.add(sayfaToplamSatis);
					
					isletmeSayfa.setSayfaToplamAlis(sayfaToplamAlis);
					isletmeSayfa.setSayfaToplamSatis(sayfaToplamSatis);
					isletmeSayfa.setSayfaToplamKar(sayfaToplamSatis.subtract(sayfaToplamAlis));
					
					isletmeSayfa.setSayfaKumulatifToplamAlis(sayfaKumulatifToplamAlis);
					isletmeSayfa.setSayfaKumulatifToplamSatis(sayfaKumulatifToplamSatis);
					isletmeSayfa.setSayfaKumulatifToplamKar(sayfaKumulatifToplamSatis.subtract(sayfaKumulatifToplamAlis));
					isletmeSayfaList.add(isletmeSayfa);
					
					//Yeni sayfa
					isletmeSayfa = new IsletmeSayfa();
					isletmeSayfa.setSayfaNo(pageCounter);
					sayfaSatir = new HashMap<Integer, ArrayList<Object>>();
					isletmeSayfa.setSayfaSatir(sayfaSatir);
					pageCounter++;
					rowCounter = 0;
					sayfaToplamAlis = BigDecimal.ZERO;
					sayfaToplamSatis = BigDecimal.ZERO;
				}
				
				row = new ArrayList<Object>();
				row.add(fatura.getSiraNo());
				row.add(fatura.getTarih());
				row.add(fatura.getEvrakNo());
				row.add("");
				row.add("");
				row.add("·········");
				row.add(fatura.getToplamAlisTutari());
				row.add("");
				row.add(fatura.getToplamSatisTutari());
				row.add(fatura.getToplamSatisTutari().subtract(fatura.getToplamAlisTutari()));
				row.add(fatura.getFirmaUnvani());
				sayfaSatir.put(rowCounter, row);
				rowCounter++;
				
				if (rowCounter == 26) {
					//Eski Sayfa
					sayfaKumulatifToplamAlis = sayfaKumulatifToplamAlis.add(sayfaToplamAlis);
					sayfaKumulatifToplamSatis = sayfaKumulatifToplamSatis.add(sayfaToplamSatis);
					
					isletmeSayfa.setSayfaToplamAlis(sayfaToplamAlis);
					isletmeSayfa.setSayfaToplamSatis(sayfaToplamSatis);
					isletmeSayfa.setSayfaToplamKar(sayfaToplamSatis.subtract(sayfaToplamAlis));
					
					isletmeSayfa.setSayfaKumulatifToplamAlis(sayfaKumulatifToplamAlis);
					isletmeSayfa.setSayfaKumulatifToplamSatis(sayfaKumulatifToplamSatis);
					isletmeSayfa.setSayfaKumulatifToplamKar(sayfaKumulatifToplamSatis.subtract(sayfaKumulatifToplamAlis));
					isletmeSayfaList.add(isletmeSayfa);
					
					//Yeni sayfa
					isletmeSayfa = new IsletmeSayfa();
					isletmeSayfa.setSayfaNo(pageCounter);
					sayfaSatir = new HashMap<Integer, ArrayList<Object>>();
					isletmeSayfa.setSayfaSatir(sayfaSatir);
					pageCounter++;
					rowCounter = 0;
					sayfaToplamAlis = BigDecimal.ZERO;
					sayfaToplamSatis = BigDecimal.ZERO;
				}
			}
			if (rowCounter != 26) {
				sayfaKumulatifToplamAlis = sayfaKumulatifToplamAlis.add(sayfaToplamAlis);
				sayfaKumulatifToplamSatis = sayfaKumulatifToplamSatis.add(sayfaToplamSatis);
				
				isletmeSayfa.setSayfaToplamAlis(sayfaToplamAlis);
				isletmeSayfa.setSayfaToplamSatis(sayfaToplamSatis);
				isletmeSayfa.setSayfaToplamKar(sayfaToplamSatis.subtract(sayfaToplamAlis));
				
				isletmeSayfa.setSayfaKumulatifToplamAlis(sayfaKumulatifToplamAlis);
				isletmeSayfa.setSayfaKumulatifToplamSatis(sayfaKumulatifToplamSatis);
				isletmeSayfa.setSayfaKumulatifToplamKar(sayfaKumulatifToplamSatis.subtract(sayfaKumulatifToplamAlis));
				isletmeSayfaList.add(isletmeSayfa);
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			throw new StockManagementException(e);
		} finally {
	
		}
		return isletmeSayfaList;
	}
	
	private static ArrayList<Object> prepareBillSeperator() {
		ArrayList<Object> row = new ArrayList<Object>();
		row.add("---");
		row.add("--------");
		row.add("-------");
		row.add("------------------------------");
		row.add("---------");
		row.add("---------");
		row.add("---------");
		row.add("---------");
		row.add("---------");
		row.add("---------");
		row.add("");
		return row;
	}
}