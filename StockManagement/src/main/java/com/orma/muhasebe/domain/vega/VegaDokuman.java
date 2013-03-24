package com.orma.muhasebe.domain.vega;

import java.util.ArrayList;

public class VegaDokuman {

	private Double toplamAlisTutari;
	private Double toplamSatisTutari;
	private Double toplamKar;
	private ArrayList<Fatura> faturaListesi;

	public Double getToplamAlisTutari() {
		return toplamAlisTutari;
	}

	public void setToplamAlisTutari(Double toplamAlisTutari) {
		this.toplamAlisTutari = toplamAlisTutari;
	}

	public Double getToplamSatisTutari() {
		return toplamSatisTutari;
	}

	public void setToplamSatisTutari(Double toplamSatisTutari) {
		this.toplamSatisTutari = toplamSatisTutari;
	}

	public Double getToplamKar() {
		return toplamKar;
	}

	public void setToplamKar(Double toplamKar) {
		this.toplamKar = toplamKar;
	}

	public ArrayList<Fatura> getFaturaListesi() {
		return faturaListesi;
	}

	public void setFaturaListesi(ArrayList<Fatura> faturaListesi) {
		this.faturaListesi = faturaListesi;
	}

}
