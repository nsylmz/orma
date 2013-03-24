package com.orma.muhasebe.domain.vega;

import java.math.BigDecimal;

public class Urun {

	private String barKodu;
	private String urunAdi;
	private BigDecimal miktar;
	private BigDecimal birimAlisFiyati;
	private BigDecimal toplamAlisTutari;
	private BigDecimal birimSatisFiyati;
	private BigDecimal toplamSatisTutari;
	private BigDecimal kar;

	public String getBarKodu() {
		return barKodu;
	}

	public void setBarKodu(String barKodu) {
		this.barKodu = barKodu;
	}

	public String getUrunAdi() {
		return urunAdi;
	}

	public void setUrunAdi(String urunAdi) {
		this.urunAdi = urunAdi;
	}

	public BigDecimal getMiktar() {
		return miktar;
	}

	public void setMiktar(BigDecimal miktar) {
		this.miktar = miktar;
	}

	public BigDecimal getBirimAlisFiyati() {
		return birimAlisFiyati;
	}

	public void setBirimAlisFiyati(BigDecimal birimAlisFiyati) {
		this.birimAlisFiyati = birimAlisFiyati;
	}

	public BigDecimal getToplamAlisTutari() {
		return toplamAlisTutari;
	}

	public void setToplamAlisTutari(BigDecimal toplamAlisTutari) {
		this.toplamAlisTutari = toplamAlisTutari;
	}

	public BigDecimal getBirimSatisFiyati() {
		return birimSatisFiyati;
	}

	public void setBirimSatisFiyati(BigDecimal birimSatisFiyati) {
		this.birimSatisFiyati = birimSatisFiyati;
	}

	public BigDecimal getToplamSatisTutari() {
		return toplamSatisTutari;
	}

	public void setToplamSatisTutari(BigDecimal toplamSatisTutari) {
		this.toplamSatisTutari = toplamSatisTutari;
	}

	public BigDecimal getKar() {
		return kar;
	}

	public void setKar(BigDecimal kar) {
		this.kar = kar;
	}

}
