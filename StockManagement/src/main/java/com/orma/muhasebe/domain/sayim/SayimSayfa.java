package com.orma.muhasebe.domain.sayim;

import java.math.BigDecimal;
import java.util.ArrayList;

public class SayimSayfa {

	private Integer sayfaNo;

	private BigDecimal sayfaAlisToplam;

	private BigDecimal sayfaSatisToplam;

	private BigDecimal sayfaKarToplam;

	private BigDecimal kumulatifAlisToplam;

	private BigDecimal kumulatifSatisToplam;

	private BigDecimal kumulatifKarToplam;

	private ArrayList<SayimSatir> sayimSatirList = new ArrayList<SayimSatir>();

	public Integer getSayfaNo() {
		return sayfaNo;
	}

	public void setSayfaNo(Integer sayfaNo) {
		this.sayfaNo = sayfaNo;
	}

	public BigDecimal getSayfaAlisToplam() {
		return sayfaAlisToplam;
	}

	public void setSayfaAlisToplam(BigDecimal sayfaAlisToplam) {
		this.sayfaAlisToplam = sayfaAlisToplam;
	}

	public BigDecimal getSayfaSatisToplam() {
		return sayfaSatisToplam;
	}

	public void setSayfaSatisToplam(BigDecimal sayfaSatisToplam) {
		this.sayfaSatisToplam = sayfaSatisToplam;
	}

	public BigDecimal getSayfaKarToplam() {
		return sayfaKarToplam;
	}

	public void setSayfaKarToplam(BigDecimal sayfaKarToplam) {
		this.sayfaKarToplam = sayfaKarToplam;
	}

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

	public ArrayList<SayimSatir> getSayimSatirList() {
		return sayimSatirList;
	}

	public void setSayimSatirList(ArrayList<SayimSatir> sayimSatirList) {
		this.sayimSatirList = sayimSatirList;
	}

}
