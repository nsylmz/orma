package com.orma.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.orma.api.IDefinitionAPI;
import com.orma.domain.Company;
import com.orma.domain.IBaseEntity;
import com.orma.utils.OrmaUtils;
import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;

@SuppressWarnings("serial")
@Configurable(preConstruction=true)
public class CompanyManagement extends GridLayout {
	
	@Autowired
	IDefinitionAPI definitionAPI;
	
	private Table companyTable;
	private Button ekle;
	private Button sil;
	private Button guncelle;
	private Button kaydet;
	private GridLayout controlLayout = new GridLayout(5,2);
	private List<Company> baseCompanyList;
	private List<Company> uiCompanyList;
	private Container companyContainer;
	private TextField ekleSayisi;
	
	@SuppressWarnings("unchecked")
	public CompanyManagement(int columns, int rows) {
		setSizeFull();
		setSpacing(true);
		setColumns(columns);
		setRows(rows);
		
		companyTable = new Table("Marka Yönetim Tablosu");
		companyTable.setImmediate(true);
		companyTable.setHeight("400px");
		companyTable.setWidth("905px");

		uiCompanyList = definitionAPI.getAllCompanies();
		companyContainer = new BeanItemContainer<Company>(Company.class, uiCompanyList);
		companyTable.setContainerDataSource(companyContainer);
		
		companyTable.setVisibleColumns(new String[]{"name", "taxName", "taxNumber", "tel", "adress", "transactionTime"});
		companyTable.setColumnHeaders(new String[]{"İSİM", "VERGİ ŞUBESİ", "VERGİ NUMARASI", "TELEFON", "ADRES", "TANIMLANMA ZAMANI"});
		companyTable.setColumnWidth("name", 187);
		companyTable.setColumnWidth("taxName", 103);
		companyTable.setColumnWidth("taxNumber", 100);
		companyTable.setColumnWidth("tel", 100);
		companyTable.setColumnWidth("adress", 200);
		companyTable.setColumnWidth("transactionTime", 135);
		addComponent(companyTable, 0 ,0);
		setComponentAlignment(companyTable, Alignment.BOTTOM_CENTER);
		
		guncelle = new Button("Güncelle");
		ekle = new Button("Ekle");
		kaydet = new Button("Kaydet");
		sil = new Button("Sil");
		ekleSayisi = new TextField();
		Label ekleLabel = new Label("Ekleme Sayısı");
		ekleSayisi.setWidth("50px");
		HorizontalLayout ekleLayout = new HorizontalLayout();
		ekleLayout.setSpacing(true);
		ekleLayout.addComponent(ekleLabel);
		ekleLayout.addComponent(ekleSayisi);
		ekle.setWidth("85px");
		sil.setWidth("85px");
		kaydet.setWidth("85px");
		guncelle.setWidth("85px");
		controlLayout.setSpacing(true);
		controlLayout.setWidth("350px");
		controlLayout.setRowExpandRatio(0, 2);
		controlLayout.setRowExpandRatio(1, 1);
		controlLayout.addComponent(guncelle, 0, 0);
		controlLayout.addComponent(ekleLayout, 1, 0);
		controlLayout.addComponent(ekle, 2,0);
		controlLayout.addComponent(kaydet, 1, 1);
		controlLayout.addComponent(sil, 2, 1);
		controlLayout.setComponentAlignment(guncelle, Alignment.MIDDLE_LEFT);
		controlLayout.setComponentAlignment(ekle, Alignment.MIDDLE_LEFT);
		controlLayout.setComponentAlignment(ekleLayout, Alignment.MIDDLE_RIGHT);
		controlLayout.setComponentAlignment(kaydet, Alignment.BOTTOM_RIGHT);
		controlLayout.setComponentAlignment(sil, Alignment.BOTTOM_LEFT);
		addComponent(controlLayout, 0, 1);
		setComponentAlignment(controlLayout, Alignment.TOP_CENTER);
		guncelle.addListener(new Button.ClickListener() {
			@Override
		    public void buttonClick(ClickEvent event) {
				companyTable.setEditable(true);
				companyTable.setVisibleColumns(new String[]{"name", "taxName", "taxNumber", "tel", "adress", "sec"});
				companyTable.setColumnHeaders(new String[]{"İSİM", "VERGİ ŞUBESİ", "VERGİ NUMARASI", "TELEFON", "ADRES", "SEÇ"});
				companyTable.setColumnWidth("sec", 50);
		    }
		});
		
		ekle.addListener(new Button.ClickListener() {
			@Override
		    public void buttonClick(ClickEvent event) {
				Company emptyCompany = null;
				if (ekleSayisi.getValue() == null 
						|| ekleSayisi.getValue().toString().length() == 0) {
					emptyCompany = new Company();
					emptyCompany.setName("");
					emptyCompany.setTaxName("");
					emptyCompany.setTaxNumber(0L);
					emptyCompany.setTel("");
					emptyCompany.setAdress("");
					companyContainer.addItem(emptyCompany);
				} else {
					for (int i = 0; i < Integer.parseInt(ekleSayisi.getValue().toString()); i++) {
						emptyCompany = new Company();
						emptyCompany.setName("");
						emptyCompany.setTaxName("");
						emptyCompany.setTaxNumber(0L);
						emptyCompany.setTel("");
						emptyCompany.setAdress("");
						companyContainer.addItem(emptyCompany);
					}
				}
				companyTable.refreshRowCache();
		    }
		});
		
		kaydet.addListener(new Button.ClickListener() {
			@SuppressWarnings("rawtypes")
			@Override
		    public void buttonClick(ClickEvent event) {
				Company company = null;
				baseCompanyList = definitionAPI.getAllCompanies();
				for (Iterator i = companyContainer.getItemIds().iterator(); i.hasNext();) {
					company =  (Company) i.next();
					if (company.isSec() 
							&& company.getName() != null
							&& company.getName().length() > 0
							&& !company.getName().equals("null")) {
						if (!OrmaUtils.listContains(baseCompanyList, (IBaseEntity)company)) {
							company.setTransactionTime(new Date());
							definitionAPI.saveCompany(company);
						} else {
							// TODO throw exception
						}
					}
				}
				uiCompanyList = definitionAPI.getAllCompanies();
				companyContainer = new BeanItemContainer<Company>(Company.class, uiCompanyList);
				companyTable.setContainerDataSource(companyContainer);
				companyTable.setEditable(false);
				companyTable.setVisibleColumns(new String[]{"name", "taxName", "taxNumber", "tel", "adress", "transactionTime"});
				companyTable.setColumnHeaders(new String[]{"İSİM", "VERGİ ŞUBESİ", "VERGİ NUMARASI", "TELEFON", "ADRES", "TANIMLANMA ZAMANI"});
				companyTable.refreshRowCache();
		    }
		});
		
		sil.addListener(new Button.ClickListener() {
			@SuppressWarnings("rawtypes")
			@Override
		    public void buttonClick(ClickEvent event) {
				Company company = null;
				baseCompanyList = definitionAPI.getAllCompanies();
				for (Iterator i = companyContainer.getItemIds().iterator(); i.hasNext();) {
					company =  (Company) i.next();
					if (company.isSec()) {
						if (company.getName() != null
								&& company.getName().length() > 0
								&& !company.getName().equals("null") 
								&& baseCompanyList.contains(company)) {
							definitionAPI.deleteCompany(baseCompanyList.get(baseCompanyList.indexOf(company)));
						}
					}
				}
				uiCompanyList = definitionAPI.getAllCompanies();
				companyContainer = new BeanItemContainer<Company>(Company.class, uiCompanyList);
				companyTable.setContainerDataSource(companyContainer);
				companyTable.setEditable(false);
				companyTable.setVisibleColumns(new String[]{"name", "taxName", "taxNumber", "tel", "adress", "transactionTime"});
				companyTable.setColumnHeaders(new String[]{"İSİM", "VERGİ ŞUBESİ", "VERGİ NUMARASI", "TELEFON", "ADRES", "TANIMLANMA ZAMANI"});
				companyTable.refreshRowCache();
		    }
		});
	}

}