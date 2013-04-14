package com.orma.ui;

import java.util.Arrays;

import org.apache.log4j.Logger;

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
	
	private static Logger log = Logger.getLogger(SayimTartiManagement.class);
	
	private final String textFieldSize = "200px";
	private final String labelSize = "250px";
	
	Label vegaKlasor = new Label("C:\\Muhasebe\\Vega\\");
	TextField vegaFileName = new TextField("Vega Dosya İsmi");
	TextField vegaSheetName = new TextField("Vege Excelindeki Sayfa İsmi");
	Label sablonKlasor = new Label("C:\\Muhasebe\\sablon\\");
	TextField sablonFileName = new TextField("Şablon Dosya İsmi");
	Label sayimKlasor = new Label("C:\\Muhasebe\\Sayım Tartı\\");
	TextField sayimFileName = new TextField("Sayim Dosya İsmi");
//	TextField sayimSheetName = new TextField("Sayım Excelindeki Sayfa İsmi");
	Select months = new Select("Sayım Tartı Ayı");
	TextField year = new TextField("Sayım Tartı Yılı");
	TextField templatePageNumber = new TextField("Şablon Toplam Sayfa Sayısı");
	
	public SayimTartiManagement() {
		setSpacing(true);
		setHeight("225px");
		
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
		
		sablonKlasor.setWidth(labelSize);
		sablonFileName.setWidth(textFieldSize);
		sablonFileName.setInputPrompt("Örn: sablon.xlsx");
		sablonFileName.setRequired(true);
		addComponent(sablonKlasor, 0, 1);
		setComponentAlignment(sablonKlasor, Alignment.BOTTOM_RIGHT);
		addComponent(sablonFileName, 1, 1);
		
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
		
		templatePageNumber.setWidth(textFieldSize);
		templatePageNumber.setRequired(true);
		
		addComponent(months, 0, 3);
		addComponent(year, 1, 3);
		addComponent(templatePageNumber, 2, 3);
		
		Button submit = new Button("Oluştur", this);
		
		addComponent(submit, 0, 5);
	}

	@Override
	public void buttonClick(ClickEvent event) {
		try {
			vegaFileName.validate();
 			sablonFileName.validate();
			sayimFileName.validate();
			months.validate();
			year.validate();
			templatePageNumber.validate();
			
			SayimDokuman sayimDokuman = SayimReader.readSayim(vegaKlasor.getValue().toString().trim()  + vegaFileName.getValue().toString().trim(), 
															  vegaSheetName.getValue().toString().trim());
			
			SayimWriter.writeSayimFile(sablonKlasor.getValue().toString().trim() + sablonFileName.getValue().toString().trim(), 
									   sayimKlasor.getValue().toString().trim() + sayimFileName.getValue().toString().trim(), 
									   "MARKET", Integer.parseInt(templatePageNumber.getValue().toString().trim()), 
									   sayimDokuman, months.getValue().toString(), 
									   Integer.parseInt(year.getValue().toString().trim()));
			
			Notification transactionComplate = new Notification("SAYIM TARTI OLUŞTURULMUŞTUR. DOSYAYI KONTROL EDİNİZ. " 
										+ sayimKlasor.getValue().toString().trim() + sayimFileName.getValue().toString().trim(), 
										Notification.TYPE_HUMANIZED_MESSAGE);
			transactionComplate.setDelayMsec(100);
			getWindow().showNotification(transactionComplate);
			
		} catch (EmptyValueException e) {
			getWindow().showNotification("Zorunlu Alanları Doldurup Tekrar Deniyeniz!!!", Notification.TYPE_WARNING_MESSAGE);
		} catch (StockManagementException e) {
			if (e.getMessage().contains("OutOfMemoryError")) {
				getWindow().showNotification("Bilgisayar hafızası yetersiz!!! Bilgisayarı yeniden başlatıp tekrar deneyiniz.");
			}
			log.debug(e);
		} finally {
			
		}
	}

}
