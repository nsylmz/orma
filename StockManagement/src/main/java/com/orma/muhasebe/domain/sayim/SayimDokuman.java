package com.orma.muhasebe.domain.sayim;

import java.math.BigDecimal;
import java.util.ArrayList;

public class SayimDokuman {

	private BigDecimal kumulatifAlisToplam;

	private BigDecimal kumulatifSatisToplam;

	private BigDecimal kumulatifKarToplam;

	private ArrayList<SayimSayfa> sayfaList = new ArrayList<SayimSayfa>();

	public BigDecimal getKumulatifAlisToplam() {
		return kumulatifAlisToplam;
	}

	public void setKumulatifAlisToplam(BigDecimal kumulatifAlisToplam) {
		this.kumulatifAlisToplam = kumulatifAlisToplam;
	}

	public BigDecimal getKumulatifSatisToplam() {
		return kumulatifSatisToplam;
	}

	public void setKumulatifSatisToplam(BigDecimal kumulatifSatisToplam) {
		this.kumulatifSatisToplam = kumulatifSatisToplam;
	}

	public BigDecimal getKumulatifKarToplam() {
		return kumulatifKarToplam;
	}

	public void setKumulatifKarToplam(BigDecimal kumulatifKarToplam) {
		this.kumulatifKarToplam = kumulatifKarToplam;
	}

	public ArrayList<SayimSayfa> getSayfaList() {
		return sayfaList;
	}

	public void setSayfaList(ArrayList<SayimSayfa> sayfaList) {
		this.sayfaList = sayfaList;
	}

}
