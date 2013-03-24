package com.orma.muhasebe.domain.isletme;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;

public class IsletmeSayfa {

	private Integer sayfaNo;

	private BigDecimal sayfaToplamAlis;

	private BigDecimal sayfaToplamSatis;

	private BigDecimal sayfaToplamKar;

	private BigDecimal sayfaKumulatifToplamAlis;

	private BigDecimal sayfaKumulatifToplamSatis;

	private BigDecimal sayfaKumulatifToplamKar;

	private Map<Integer, ArrayList<Object>> sayfaSatir;

	public Integer getSayfaNo() {
		return sayfaNo;
	}

	public void setSayfaNo(Integer sayfaNo) {
		this.sayfaNo = sayfaNo;
	}

	public BigDecimal getSayfaToplamAlis() {
		return sayfaToplamAlis;
	}

	public void setSayfaToplamAlis(BigDecimal sayfaToplamAlis) {
		this.sayfaToplamAlis = sayfaToplamAlis;
	}

	public BigDecimal getSayfaToplamSatis() {
		return sayfaToplamSatis;
	}

	public void setSayfaToplamSatis(BigDecimal sayfaToplamSatis) {
		this.sayfaToplamSatis = sayfaToplamSatis;
	}

	public BigDecimal getSayfaToplamKar() {
		return sayfaToplamKar;
	}

	public void setSayfaToplamKar(BigDecimal sayfaToplamKar) {
		this.sayfaToplamKar = sayfaToplamKar;
	}

	public BigDecimal getSayfaKumulatifToplamSatis() {
		return sayfaKumulatifToplamSatis;
	}

	public void setSayfaKumulatifToplamSatis(
			BigDecimal sayfaKumulatifToplamSatis) {
		this.sayfaKumulatifToplamSatis = sayfaKumulatifToplamSatis;
	}

	public BigDecimal getSayfaKumulatifToplamKar() {
		return sayfaKumulatifToplamKar;
	}

	public void setSayfaKumulatifToplamKar(BigDecimal sayfaKumulatifToplamKar) {
		this.sayfaKumulatifToplamKar = sayfaKumulatifToplamKar;
	}

	public BigDecimal getSayfaKumulatifToplamAlis() {
		return sayfaKumulatifToplamAlis;
	}

	public void setSayfaKumulatifToplamAlis(BigDecimal sayfaKumulatifToplamAlis) {
		this.sayfaKumulatifToplamAlis = sayfaKumulatifToplamAlis;
	}

	public Map<Integer, ArrayList<Object>> getSayfaSatir() {
		return sayfaSatir;
	}

	public void setSayfaSatir(Map<Integer, ArrayList<Object>> sayfaSatir) {
		this.sayfaSatir = sayfaSatir;
	}

}
