package com.orma.muhasebe.domain.vega;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class Fatura {
	
	private Integer siraNo;
	private String evrakNo;
	private Date tarih;
	private BigDecimal toplamAlisTutari;
	private BigDecimal toplamSatisTutari;
	private BigDecimal toplamKar;
	private String firmaUnvani;
	private ArrayList<Urun> urunListesi;

	public Integer getSiraNo() {
		return this.siraNo;
	}

	public void setSiraNo(Integer siraNo) {
		this.siraNo = siraNo;
	}

	public String getEvrakNo() {
		return this.evrakNo;
	}

	public void setEvrakNo(String evrakNo) {
		this.evrakNo = evrakNo;
	}

	public Date getTarih() {
		return this.tarih;
	}

	public void setTarih(Date tarih) {
		this.tarih = tarih;
	}

	public ArrayList<Urun> getUrunListesi() {
		return this.urunListesi;
	}

	public void setUrunListesi(ArrayList<Urun> urunListesi) {
		this.urunListesi = urunListesi;
	}

	public BigDecimal getToplamAlisTutari() {
		return this.toplamAlisTutari;
	}

	public void setToplamAlisTutari(BigDecimal toplamAlisTutari) {
		this.toplamAlisTutari = toplamAlisTutari;
	}

	public BigDecimal getToplamSatisTutari() {
		return this.toplamSatisTutari;
	}

	public void setToplamSatisTutari(BigDecimal toplamSatisTutari) {
		this.toplamSatisTutari = toplamSatisTutari;
	}

	public BigDecimal getToplamKar() {
		return this.toplamKar;
	}

	public void setToplamKar(BigDecimal toplamKar) {
		this.toplamKar = toplamKar;
	}

	public String getFirmaUnvani() {
		return this.firmaUnvani;
	}

	public void setFirmaUnvani(String firmaUnvani) {
		this.firmaUnvani = firmaUnvani;
	}
}