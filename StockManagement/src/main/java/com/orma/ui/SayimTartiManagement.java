package com.orma.ui;

import java.util.Arrays;

import com.orma.exception.StockManagementException;
import com.orma.muhasebe.domain.sayim.SayimDokuman;
import com.orma.muhasebe.sayim.SayimReader;
import com.orma.muhasebe.sayim.SayimWriter;
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
public class SayimTartiManagement extends GridLayout implements Button.ClickListener {
	
//	private static Logger log = Logger.getLogger(SayimTartiManagement.class);
	
	private final String textFieldSize = "200px";
	private final String labelSize = "250px";
	
	Label vegaKlasor = new Label("C:\\Muhasebe\\Vega\\");
	TextField vegaFileName = new TextField("Vega Dosya İsmi");
	TextField vegaSheetName = new TextField("Vege Excelindeki Sayfa İsmi");
	String sablonFile = "C:\\Muhasebe\\sablon\\62 Sayfa Sayım Tartı Sablon.xlsx";
	Label sayimKlasor = new Label("C:\\Muhasebe\\Sayım Tartı\\");
	TextField sayimFileName = new TextField("Sayim Dosya İsmi");
	Select months = new Select("Sayım Tartı Ayı");
	TextField year = new TextField("Sayım Tartı Yılı");
	int templatePageNumber = 62;
	
	public SayimTartiManagement() {
		setSpacing(true);
		setHeight("210");
		
		setColumns(4);
		setRows(6);
		vegaKlasor.setWidth(labelSize);
		vegaFileName.setRequired(true);
		vegaFileName.setWidth(textFieldSize);
		vegaFileName.setInputPrompt("Örn: MARKET02.xls");
		vegaSheetName.setWidth(textFieldSize);
		vegaFileName.setInputPrompt("Örn: BUFE02");
		vegaSheetName.setRequired(false);
		addComponent(vegaKlasor, 0, 0);
		setComponentAlignment(vegaKlasor, Alignment.BOTTOM_RIGHT);
		addComponent(vegaFileName, 1, 0);
		addComponent(vegaSheetName, 2, 0);
		
		
		sayimKlasor.setWidth(labelSize);
		sayimFileName.setWidth(textFieldSize);
		sayimFileName.setInputPrompt("Örn: MARKET02.xlsx");
		sayimFileName.setRequired(true);
		addComponent(sayimKlasor, 0, 2);
		setComponentAlignment(sayimKlasor, Alignment.BOTTOM_RIGHT);
		addComponent(sayimFileName, 1, 2);
//		addComponent(sayimSheetName, 2, 2);
		
		String[] trMonths = {"OCAK", "ŞUBAT", "MART", "NİSAN", "MAYIS", "HAZİRAN", "TEMMUZ", "AĞUSTOS", "EYLÜL", "EKİM", "KASIM", "ARALIK"};
		BeanItemContainer<String> monthList = new BeanItemContainer<String>(String.class, Arrays.asList(trMonths));
		months.setContainerDataSource(monthList);
		months.setRequired(true);
		
		year.setRequired(true);
		
		addComponent(months, 0, 3);
		addComponent(year, 1, 3);
		
		Button submit = new Button("Oluştur", this);
		
		addComponent(submit, 0, 5);
	}

	@Override
	public void buttonClick(ClickEvent event) {
		Notification warnN = null;
		try {
			vegaFileName.validate();
			sayimFileName.validate();
			months.validate();
			year.validate();
			
			SayimDokuman sayimDokuman = SayimReader.readSayim(vegaKlasor.getValue().toString().trim() 
															+ vegaFileName.getValue().toString().trim() + ".xls", 
															  vegaSheetName.getValue().toString().trim());
			
			SayimWriter.writeSayimFile(sablonFile, 
									   sayimKlasor.getValue().toString().trim() + sayimFileName.getValue().toString().trim() + ".xlsx", 
									   "MARKET", templatePageNumber, 
									   sayimDokuman, months.getValue().toString(), 
									   Integer.parseInt(year.getValue().toString().trim()));
			
			Notification transactionComplate = new Notification("SAYIM TARTI OLUŞTURULMUŞTUR. DOSYAYI KONTROL EDİNİZ. " 
										+ sayimKlasor.getValue().toString().trim() + sayimFileName.getValue().toString().trim(), 
										Notification.TYPE_HUMANIZED_MESSAGE);
			transactionComplate.setDelayMsec(-1);
			getWindow().showNotification(transactionComplate);
			
		} catch (EmptyValueException e) {
			warnN = new Notification("Zorunlu Alanları Doldurup Tekrar Deniyeniz!!!", Notification.TYPE_WARNING_MESSAGE);
			warnN.setDelayMsec(-1);
			getWindow().showNotification(warnN);
		} catch (StockManagementException e) {
			if (e.getMessage().contains("OutOfMemoryError")) {
				getWindow().showNotification("Bilgisayar hafızası yetersiz!!! Bilgisayarı yeniden başlatıp tekrar deneyiniz.", 
										  Notification.TYPE_ERROR_MESSAGE);
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
