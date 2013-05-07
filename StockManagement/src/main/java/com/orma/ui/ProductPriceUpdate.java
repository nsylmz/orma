package com.orma.ui;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.orma.api.IDefinitionAPI;
import com.orma.api.ISearchAPI;
import com.orma.domain.Product;
import com.orma.exception.StockManagementException;
import com.orma.muhasebe.domain.vega.Fatura;
import com.orma.muhasebe.domain.vega.VegaDokuman;
import com.orma.muhasebe.isletme.IsletmeProcessor;
import com.orma.muhasebe.vega.VegaDokumanReader;
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
public class ProductPriceUpdate extends GridLayout implements Button.ClickListener {
	
	private static Logger log = Logger.getLogger(IsletmeProcessor.class);
	
	@Autowired
	ISearchAPI searchAPI;
	
	@Autowired
	IDefinitionAPI definitionAPI;	
	
	private final String textFieldSize = "200px";
	private final String labelSize = "250px";
	
	Label vegaKlasor = new Label("C:\\Muhasebe\\Vega\\");
	TextField vegaFileName = new TextField("Vega Dosya İsmi");
	
	public ProductPriceUpdate() {
		setSpacing(true);
		setHeight("80");
		
		setColumns(2);
		setRows(2);
		
		vegaKlasor.setWidth(labelSize);
		vegaFileName.setRequired(true);
		vegaFileName.setWidth(textFieldSize);
		vegaFileName.setInputPrompt("Örn: MARKET02");
		addComponent(vegaKlasor, 0, 0);
		setComponentAlignment(vegaKlasor, Alignment.BOTTOM_RIGHT);
		addComponent(vegaFileName, 1, 0);
		
		Button submit = new Button("Oluştur", this);
		
		addComponent(submit, 0, 1);
	}

	@Override
	public void buttonClick(ClickEvent event) {
		Notification errorN = null;
		try {
			vegaFileName.validate();
			VegaDokuman dokuman = VegaDokumanReader.readDokuman(vegaKlasor.getValue().toString().trim() 
															  + vegaFileName.getValue().toString().trim() + ".xls", 
																null, false);
		
			Fatura fatura = null;
			Product product = null;
			
			Long barcode = 0L;
			int change = 0;
			for (int i = 0; i < dokuman.getFaturaListesi().size(); i++) {
				fatura = dokuman.getFaturaListesi().get(i);
				for (int j = 0; j < fatura.getUrunListesi().size(); j++) {
					try {
						barcode = Long.parseLong(fatura.getUrunListesi().get(j).getBarKodu());
					} catch (NumberFormatException e) {
						
					}
					if (barcode != 0) {
						product = definitionAPI.getProductByBarcode(barcode);
						if (product != null) {
							log.info(fatura.getUrunListesi().get(j).getBirimAlisFiyati() + " işletme - depo " + product.getBuyPrice());
						}
						if (product != null 
								&& fatura.getUrunListesi().get(j).getBirimAlisFiyati().compareTo(product.getBuyPrice()) != 0) {
							log.info(product.getBarcode() + " barkodlu ürünün aliş fiyatı değişmiştir " 
									+ product.getBuyPrice() 
									+ " --> " + fatura.getUrunListesi().get(j).getBirimAlisFiyati());
							product.setBuyPrice(fatura.getUrunListesi().get(j).getBirimAlisFiyati());
							definitionAPI.saveProduct(product);
							change++;
						}
					}
				}
			}
			
			Notification transactionComplate = new Notification(change + " tane ürünün fiyatları başarı ile güncellenmiştir.", Notification.TYPE_HUMANIZED_MESSAGE);
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
				getWindow().showNotification(vegaKlasor.getValue().toString().trim() 
			  				   + vegaFileName.getValue().toString().trim() + ".xls" 
			  				   + " dosyasında okunacak sayfa bulunamıyor. Lütfen Dosyayı kontrol ediniz!!!"
			  				   , Notification.TYPE_ERROR_MESSAGE);
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
