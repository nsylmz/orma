package com.orma.ui;

import java.util.ArrayList;
import java.util.Arrays;

import com.orma.muhasebe.domain.isletme.IsletmeSayfa;
import com.orma.muhasebe.domain.vega.VegaDokuman;
import com.orma.muhasebe.isletme.IsletmeProcessor;
import com.orma.muhasebe.isletme.IsletmeWriter;
import com.orma.muhasebe.vega.VegaDokumanProcessor;
import com.orma.muhasebe.vega.VegaDokumanReader;
import com.orma.muhasebe.vega.VegaDokumanWriter;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Select;
import com.vaadin.ui.TextField;

@SuppressWarnings("serial")
public class MuhasebeManagement extends GridLayout implements Button.ClickListener {
	
	private final String textFieldSize = "200px";
	private final String labelSize = "250px";
	
	Label vegaKlasor = new Label("D:\\Enes\\Springsource\\test data\\input\\");
	TextField vegaFileName = new TextField("Vega Dosya İsmi");
	TextField vegaSheetName = new TextField("Vege Excelindeki Sayfa İsmi");
	Label sablonKlasor = new Label("D:\\Enes\\Springsource\\test data\\sablon\\");
	Label isletmeKlasor = new Label("D:\\Enes\\Springsource\\test data\\output\\");
	Select months = new Select("İşletme Ayı");
	TextField year = new TextField("İşletme Yılı");
	TextField templatePageNumber = new TextField("Şablon Toplam Sayfa Sayısı");
	TextField isletmeFileName = new TextField("İşletme Dosya İsmi");
	TextField branchName = new TextField("İşletmede Oluşturulacak Şube Adı");
	
	public MuhasebeManagement() {
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
		TextField sablonFileName = new TextField("Şablon Dosya İsmi");
		sablonFileName.setWidth(textFieldSize);
		sablonFileName.setInputPrompt("Örn: sablon.xlsx");
		sablonFileName.setRequired(true);
		addComponent(sablonKlasor, 0, 1);
		setComponentAlignment(sablonKlasor, Alignment.BOTTOM_RIGHT);
		addComponent(sablonFileName, 1, 1);
		
		
		isletmeKlasor.setWidth(labelSize);
		
		isletmeFileName.setWidth(textFieldSize);
		isletmeFileName.setInputPrompt("Örn: MARKET02.xlsx");
		isletmeFileName.setRequired(true);
		addComponent(isletmeKlasor, 0, 2);
		setComponentAlignment(isletmeKlasor, Alignment.BOTTOM_RIGHT);
		addComponent(isletmeFileName, 1, 2);
		
		
		String[] trMonths = {"OCAK", "ŞUBAT", "MART", "NİSAN", "MAYIS", "HAZİRAN", "TEMMUZ", "AĞUSTOS", "EYLÜL", "EKİM", "KASIM", "ARALIK"};
		BeanItemContainer<String> monthList = new BeanItemContainer<String>(String.class, Arrays.asList(trMonths));
		months.setContainerDataSource(monthList);
		months.setRequired(true);
		
		year.setRequired(true);
		
		branchName.setWidth(textFieldSize);
		branchName.setRequired(true);
		
		templatePageNumber.setWidth(textFieldSize);
		templatePageNumber.setRequired(true);
		
		addComponent(months, 0, 3);
		addComponent(year, 1, 3);
		addComponent(branchName, 2, 3);
		addComponent(templatePageNumber, 3, 3);
		
		Button submit = new Button("Oluştur", this);
		
		addComponent(submit, 0, 5);
		
		
//		submit.addValidator(validator);
		
		
	}

	@Override
	public void buttonClick(ClickEvent event) {
		try {
			VegaDokuman dokuman = VegaDokumanReader.readDokuman(vegaKlasor.getValue().toString().trim() + vegaFileName.getValue().toString().trim(), 
																vegaSheetName.getValue().toString().trim(), false);
		
			for (int i = 0; i < dokuman.getFaturaListesi().size(); i++) {
				VegaDokumanProcessor.faturaCheckAndFix(dokuman.getFaturaListesi().get(i));
			}
			VegaDokumanWriter.writeDataToExcelFile("D:\\Enes\\Springsource\\test data\\output\\bufeOutput.xls", dokuman);
			
			// Isletme Defteri
			VegaDokuman processedDokuman = VegaDokumanReader.readDokuman(
					"D:\\Enes\\Springsource\\test data\\output\\bufeOutput.xls", "",
					true);
			
			ArrayList<IsletmeSayfa> isletmeSayfaList = IsletmeProcessor.convertVegaToIsletme(processedDokuman);
			
			IsletmeWriter.writeIsletmeFile(
					sablonKlasor.getValue().toString().trim(),
					isletmeKlasor.getValue().toString().trim() + isletmeFileName.getValue().toString().trim(),
					"SABLON", Integer.parseInt(templatePageNumber.getValue().toString().trim()), 
					isletmeSayfaList, months.getValue().toString(), Integer.parseInt(year.getValue().toString().trim()),
					branchName.getValue().toString().trim());
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
