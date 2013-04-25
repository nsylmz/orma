package com.orma.ui;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.orma.exception.StockManagementException;
import com.orma.muhasebe.sayim.DepoSayimWriter;
import com.vaadin.data.Validator.EmptyValueException;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
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
	
	@Autowired
	DepoSayimWriter writer;
	
	Label vegaKlasor = new Label("C:\\Muhasebe\\Vega\\");
	TextField vegaFileName = new TextField("Vega Dosya İsmi");
	
	public WarehouseStockSayim() {
		setSpacing(true);
		setHeight("78");
		
		setColumns(2);
		setRows(2);
		vegaKlasor.setWidth(labelSize);
		vegaFileName.setRequired(true);
		vegaFileName.setWidth(textFieldSize);
		vegaFileName.setInputPrompt("Örn: MARKET02.xls");
		vegaFileName.setInputPrompt("Örn: BUFE02");
		addComponent(vegaKlasor, 0, 0);
		setComponentAlignment(vegaKlasor, Alignment.BOTTOM_RIGHT);
		addComponent(vegaFileName, 1, 0);
		
		Button submit = new Button("Oluştur", this);
		addComponent(submit, 0, 1);
	}

	@Override
	public void buttonClick(ClickEvent event) {
		try {
			vegaFileName.validate();

			writer.write(vegaKlasor.getValue().toString().trim()  + vegaFileName.getValue().toString().trim());
			
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
