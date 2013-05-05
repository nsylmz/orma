package com.orma.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.orma.exception.StockManagementException;
import com.orma.muhasebe.domain.isletme.IsletmeSayfa;
import com.orma.muhasebe.domain.vega.VegaDokuman;
import com.orma.muhasebe.isletme.IsletmeProcessor;
import com.orma.muhasebe.isletme.IsletmeWriter;
import com.orma.muhasebe.vega.VegaDokumanProcessor;
import com.orma.muhasebe.vega.VegaDokumanReader;
import com.orma.muhasebe.vega.VegaDokumanWriter;
import com.vaadin.data.Validator.EmptyValueException;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Select;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window.Notification;

@SuppressWarnings("serial")
public class IsletmeManagement extends GridLayout implements Button.ClickListener {
	
//	private static Logger log = Logger.getLogger(IsletmeProcessor.class);OO
	
	private final String textFieldSize = "200px";
	private final String labelSize = "250px";
	private final String[] trMonths = {"OCAK", "ŞUBAT", "MART", "NİSAN", "MAYIS", "HAZİRAN", "TEMMUZ", "AĞUSTOS", "EYLÜL", "EKİM", "KASIM", "ARALIK"};
	private final List<String> monthList = Arrays.asList(trMonths);
	private final String[] numMonths = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
	private final List<String> monthNumList = Arrays.asList(numMonths);
	private final String[] templateFilePaths = {"C:\\Muhasebe\\Sablon\\20 Sayfa Sablon.xlsx", 
								  				"C:\\Muhasebe\\Sablon\\168 Sayfa Sablon.xlsx"};
	private String isletmeKlasor = "C:\\Muhasebe\\Isletme\\";
	private final String[] isletmeTypes = {"AKOĞUZ", "ASKERLİK ŞUBESi", "BÜFE", "ER VE ERBAŞ", "İŞOCAK", "KARARGAH", "KURUYEMİŞ", "MARKET"};
	private final List<String> isletmeList = Arrays.asList(isletmeTypes);
	
	Label vegaKlasor = new Label("C:\\Muhasebe\\Vega\\");
	TextField vegaFileName = new TextField("Vega Dosya İsmi");
	TextField vegaSheetName = new TextField("Vege Excelindeki Sayfa İsmi");
	Select months = new Select("İşletme Ayı");
	Select isletme = new Select("İşletme Kantini");
	TextField year = new TextField("İşletme Yılı"); 
	
	public IsletmeManagement() {
		setSpacing(true);
		setHeight("185");
		
		setColumns(3);
		setRows(4);
		
		setColumnExpandRatio(0, 0.25f);
		setColumnExpandRatio(1, 1.0f);
		setColumnExpandRatio(2, 1.0f);
		
		
		vegaKlasor.setWidth(labelSize);
		vegaFileName.setRequired(true);
		vegaFileName.setWidth(textFieldSize);
		vegaFileName.setInputPrompt("Örn: MARKET02");
		vegaSheetName.setWidth(textFieldSize);
		vegaSheetName.setInputPrompt("Örn: BUFE02");
		vegaSheetName.setRequired(false);
		addComponent(vegaKlasor, 0, 2);
		setComponentAlignment(vegaKlasor, Alignment.BOTTOM_RIGHT);
		addComponent(vegaFileName, 1, 2);
		addComponent(vegaSheetName, 2, 2);
		
		BeanItemContainer<String> monthContainer = new BeanItemContainer<String>(String.class, monthList);
		months.setContainerDataSource(monthContainer);
		months.setRequired(true);
		
		BeanItemContainer<String> isletmeContainer = new BeanItemContainer<String>(String.class, isletmeList);
		isletme.setContainerDataSource(isletmeContainer);
		isletme.setRequired(true);
		
		year.setRequired(true);
		
		addComponent(isletme, 0, 0);
		addComponent(months, 0, 1);
		addComponent(year, 1, 1);
		
		Button submit = new Button("Oluştur", this);
		
		addComponent(submit, 0, 3);
		
		
	}

	@Override
	public void buttonClick(ClickEvent event) {
		Notification errorN = null;
		try {
			vegaFileName.validate();
			months.validate();
			year.validate();
			VegaDokuman dokuman = VegaDokumanReader.readDokuman(vegaKlasor.getValue().toString().trim() 
															  + vegaFileName.getValue().toString().trim() + ".xls", 
																vegaSheetName.getValue().toString().trim(), false);
		
			for (int i = 0; i < dokuman.getFaturaListesi().size(); i++) {
				VegaDokumanProcessor.faturaCheckAndFix(dokuman.getFaturaListesi().get(i));
			}
			String vegaOutputFileName = "C:\\Muhasebe\\Vega\\" + vegaSheetName.getValue().toString().trim() + "Output.xls";
			VegaDokumanWriter.writeDataToExcelFile(vegaOutputFileName, dokuman);
			
			// Isletme Defteri
			VegaDokuman processedDokuman = VegaDokumanReader.readDokuman(vegaOutputFileName, "", true);
			
			ArrayList<IsletmeSayfa> isletmeSayfaList = IsletmeProcessor.convertVegaToIsletme(processedDokuman);
			
			String sablon = "";
			int templatePageNumber = 0;
			if (isletme.getValue() != null) {
				if (isletme.getValue().toString().trim().equals("MARKET")) {
					sablon = templateFilePaths[1];
					templatePageNumber = 168;
				} else {
					sablon = templateFilePaths[0];
					templatePageNumber = 20;
				}
			} else {
				// TODO throw exception
			}
			
			String isletmeFileName = "";
			if (isletme.getValue() != null) {
				if (monthList.indexOf(isletme.getValue().toString().trim()) != -1) {
					// TODO throw exception
				}
				isletmeFileName = isletme.getValue().toString().trim() 
								+ monthNumList.get(monthList.indexOf(months.getValue().toString().trim())) + ".xlsx";
			}
			IsletmeWriter.writeIsletmeFile(
					sablon,
					isletmeKlasor + isletmeFileName,
					"SABLON", templatePageNumber, 
					isletmeSayfaList, months.getValue().toString(), Integer.parseInt(year.getValue().toString().trim()),
					isletme.getValue().toString().trim());
			
			Notification transactionComplate = new Notification("iŞLETİM DEFTERİ OLUŞTURULMUŞTUR. DOSYAYI KONTROL EDİNİZ. " 
										+ isletmeKlasor + isletmeFileName, 
										Notification.TYPE_HUMANIZED_MESSAGE);
			transactionComplate.setDelayMsec(-1);
			getWindow().showNotification(transactionComplate);
			
		} catch (EmptyValueException e) {
			errorN = new Notification("Zorunlu Alanları Doldurup Tekrar Deniyeniz!!!", Notification.TYPE_WARNING_MESSAGE);
			errorN.setDelayMsec(-1);
			getWindow().showNotification(errorN);
		} catch (StockManagementException e) {
			if (e.getMessage() != null 
					&& e.getMessage().contains("OutOfMemoryError")) {
				getWindow().showNotification("Bilgisayar hafızası yetersiz!!! Bilgisayarı yeniden başlatıp tekrar deneyiniz.", Notification.TYPE_ERROR_MESSAGE);
			} else if (e.getCode() != null 
					&& e.getCode().equals("1")) {
				if (vegaSheetName.getValue() != null) {
					getWindow().showNotification(vegaKlasor.getValue().toString().trim() 
							  				   + vegaFileName.getValue().toString().trim() + ".xls" 
							  				   + " dosyasındaki " + vegaSheetName.getValue().toString().trim() + " sayfası bulunamıyor!!!"
							  				   , Notification.TYPE_ERROR_MESSAGE);
				} else {
					getWindow().showNotification(vegaKlasor.getValue().toString().trim() 
			  				   + vegaFileName.getValue().toString().trim() + ".xls" 
			  				   + " dosyasında okunacak sayfa bulunamıyor. Lütfen Dosyayı kontrol ediniz!!!"
			  				   , Notification.TYPE_ERROR_MESSAGE);
				}
			} else if (e.getCode() != null 
					&& e.getCode().equals("2")) {
				getWindow().showNotification(e.getMessage()
						  				   , Notification.TYPE_ERROR_MESSAGE);
			} else if (e.getCode() != null 
					&& e.getCode().equals("3")) {
				getWindow().showNotification(e.getMessage()
						  				   , Notification.TYPE_ERROR_MESSAGE);
			} else if (e.getCode() != null 
					&& e.getCode().equals("4")) {
				getWindow().showNotification(e.getMessage()
						  				   , Notification.TYPE_ERROR_MESSAGE);
			} else if (e.getMessage() != null 
					&& e.getMessage().contains("Row Number")) {
				getWindow().showNotification(vegaKlasor.getValue().toString().trim() 
		  				   + vegaFileName.getValue().toString().trim() + ".xls" 
		  				   + " dosyasındaki " + e.getMessage().substring(e.getMessage().lastIndexOf("Row Number") + 11)
		  				   + " nolu satırda hata var. Lütfen kontrol ediniz!!!"
		  				   , Notification.TYPE_ERROR_MESSAGE);
			}
		}
	}

}
