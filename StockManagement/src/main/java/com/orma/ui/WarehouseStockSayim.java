package com.orma.ui;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.orma.exception.StockManagementException;
import com.orma.muhasebe.sayim.DepoSayimWriter;
import com.vaadin.data.Validator.EmptyValueException;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window.Notification;

@SuppressWarnings("serial")
@Configurable(preConstruction=true)
public class WarehouseStockSayim extends GridLayout implements Button.ClickListener {
	
	private static Logger log = Logger.getLogger(WarehouseStockSayim.class);
	
	private final String textFieldSize = "150px";
	private final String labelSize = "150px";
	private final String sdf = "dd-MM-yyyy";
	
	@Autowired
	DepoSayimWriter writer;
	
	Label vegaKlasor = new Label("C:\\Muhasebe\\Vega\\");
	TextField vegaFileName = new TextField("Vega Dosya İsmi");
	DateField endDate = new DateField("Bitiş Tarihi");
	
	public WarehouseStockSayim() {
		setSpacing(true);
		setHeight("150");
		
		setColumns(2);
		setRows(3);
		
		endDate.setDateFormat(sdf);
		
		addComponent(endDate, 0, 0);
		
		vegaKlasor.setWidth(labelSize);
		vegaFileName.setRequired(true);
		vegaFileName.setWidth(textFieldSize);
		vegaFileName.setInputPrompt("Örn: MARKET02");
		vegaFileName.setInputPrompt("Örn: BUFE02");
		addComponent(vegaKlasor, 0, 1);
		setComponentAlignment(vegaKlasor, Alignment.BOTTOM_RIGHT);
		addComponent(vegaFileName, 1, 1);
		
		Button submit = new Button("Oluştur", this);
		addComponent(submit, 0, 2);
	}

	@Override
	public void buttonClick(ClickEvent event) {
		try {
			vegaFileName.validate();

			writer.write(vegaKlasor.getValue().toString().trim()  + vegaFileName.getValue().toString().trim() + ".xls", (Date) endDate.getValue());
			
			Notification transactionComplate = new Notification("SAYIM TARTI OLUŞTURULMUŞTUR. DOSYAYI KONTROL EDİNİZ. " 
										+ vegaKlasor.getValue().toString().trim()  + vegaFileName.getValue().toString().trim(), 
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
