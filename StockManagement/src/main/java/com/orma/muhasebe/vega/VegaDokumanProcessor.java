package com.orma.muhasebe.vega;

import java.math.BigDecimal;

import com.orma.exception.StockManagementException;
import com.orma.muhasebe.domain.vega.Fatura;

public class VegaDokumanProcessor {
	
//	private static Logger log = Logger.getLogger(VegaDokumanProcessor.class);
	
	public static void faturaCheckAndFix(Fatura fatura) throws StockManagementException {
	
		BigDecimal toplamAlis = BigDecimal.ZERO;
		BigDecimal toplamSatis = BigDecimal.ZERO;
		BigDecimal toplamKar = BigDecimal.ZERO;
		try {
			
			for (int i = 0; i < fatura.getUrunListesi().size(); i++) {
				toplamAlis = toplamAlis.add(fatura.getUrunListesi().get(i).getToplamAlisTutari());
				toplamSatis = toplamSatis.add(fatura.getUrunListesi().get(i).getToplamSatisTutari());
				toplamKar = toplamKar.add(fatura.getUrunListesi().get(i).getKar());
			}
			
			BigDecimal fark = BigDecimal.ZERO;
			int loopCount = 0;
			if (fatura.getToplamAlisTutari().compareTo(toplamAlis) != 0) {
				if (fatura.getToplamAlisTutari().compareTo(toplamAlis) == 1) {
					fark = fatura.getToplamAlisTutari().subtract(toplamAlis);
					if (fark.compareTo(BigDecimal.ONE) != -1) {
						throw new StockManagementException("2", "Vega dosyası elle düzeltilmiş. Hatalı olan fatura " + fatura.getEvrakNo());
					}
					loopCount = fark.multiply(BigDecimal.valueOf(100)).intValue() / fatura.getUrunListesi().size();
					for (int i = 0; i < (loopCount+1)*fark.multiply(BigDecimal.valueOf(100)).intValue(); i++) {
						fatura.getUrunListesi().get(i).setToplamAlisTutari(fatura.getUrunListesi().get(i).getToplamAlisTutari().add(BigDecimal.valueOf(0.01)));
					}
				} else {
					fark = toplamAlis.subtract(fatura.getToplamAlisTutari());
					if (fark.compareTo(BigDecimal.ONE) != -1) {
						throw new StockManagementException("2", "Vega dosyası elle düzeltilmiş. Hatalı olan fatura " + fatura.getEvrakNo());
					}
					loopCount = fark.multiply(BigDecimal.valueOf(100)).intValue() / fatura.getUrunListesi().size();
					for (int i = 0; i < (loopCount+1)*fark.multiply(BigDecimal.valueOf(100)).intValue(); i++) {
						fatura.getUrunListesi().get(i).setToplamAlisTutari(fatura.getUrunListesi().get(i).getToplamAlisTutari().subtract(BigDecimal.valueOf(0.01)));
					}
				}
			}
			
			if (fatura.getToplamSatisTutari().compareTo(toplamSatis) != 0) {
				if (fatura.getToplamSatisTutari().compareTo(toplamSatis) == 1) {
					fark = fatura.getToplamSatisTutari().subtract(toplamSatis);
					if (fark.compareTo(BigDecimal.ONE) != -1) {
						throw new StockManagementException("2", "Vega dosyası elle düzeltilmiş. Hatalı olan fatura " + fatura.getEvrakNo());
					}
					loopCount = fark.multiply(BigDecimal.valueOf(100)).intValue() / fatura.getUrunListesi().size();
					for (int i = 0; i < (loopCount+1)*fark.multiply(BigDecimal.valueOf(100)).intValue(); i++) {
						fatura.getUrunListesi().get(i).setToplamSatisTutari(fatura.getUrunListesi().get(i).getToplamSatisTutari().add(BigDecimal.valueOf(0.01)));
					}
				} else {
					fark = toplamSatis.subtract(fatura.getToplamSatisTutari());
					if (fark.compareTo(BigDecimal.ONE) != -1) {
						throw new StockManagementException("2", "Vega dosyası elle düzeltilmiş. Hatalı olan fatura " + fatura.getEvrakNo());
					}
					loopCount = fark.multiply(BigDecimal.valueOf(100)).intValue() / fatura.getUrunListesi().size();
					for (int i = 0; i < (loopCount+1)*fark.multiply(BigDecimal.valueOf(100)).intValue(); i++) {
						fatura.getUrunListesi().get(i).setToplamSatisTutari(fatura.getUrunListesi().get(i).getToplamSatisTutari().subtract(BigDecimal.valueOf(0.01)));
					}
				}
			}
			
			if (fatura.getToplamKar().compareTo(toplamKar) != 0) {
				if (fatura.getToplamKar().compareTo(toplamKar) == 1) {
					fark = fatura.getToplamKar().subtract(toplamKar);
					if (fark.compareTo(BigDecimal.ONE) != -1) {
						throw new StockManagementException("2", "Vega dosyası elle düzeltilmiş. Hatalı olan fatura " + fatura.getEvrakNo());
					}
					loopCount = fark.multiply(BigDecimal.valueOf(100)).intValue() / fatura.getUrunListesi().size();
					for (int i = 0; i < (loopCount+1)*fark.multiply(BigDecimal.valueOf(100)).intValue(); i++) {
						fatura.getUrunListesi().get(i).setKar(fatura.getUrunListesi().get(i).getKar().add(BigDecimal.valueOf(0.01)));
					}
				} else {
					fark = toplamKar.subtract(fatura.getToplamKar());
					if (fark.compareTo(BigDecimal.ONE) != -1) {
						throw new StockManagementException("2", "Vega dosyası elle düzeltilmiş. Hatalı olan fatura " + fatura.getEvrakNo());
					}
					loopCount = fark.multiply(BigDecimal.valueOf(100)).intValue() / fatura.getUrunListesi().size();
					for (int i = 0; i < (loopCount+1)*fark.multiply(BigDecimal.valueOf(100)).intValue(); i++) {
						fatura.getUrunListesi().get(i).setKar(fatura.getUrunListesi().get(i).getKar().subtract(BigDecimal.valueOf(0.01)));
					}
				}
			}
		} catch (StockManagementException e) {
			throw new StockManagementException(e.getCode(), e.getMessage(), e.getCause());
		} catch (Exception e) {
			throw new StockManagementException(e);
		}
	}
	
}
