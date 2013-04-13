package com.orma.ui;

import java.util.HashMap;
import java.util.Map;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Tree;

@SuppressWarnings("serial")
public class StockManagementMenuLayout extends GridLayout implements
		Property.ValueChangeListener {

	private Tree menuTree;
	
	private HorizontalSplitPanel mainPanel;
	
	private Map<String, GridLayout> layouts = new HashMap<String, GridLayout>();

	public StockManagementMenuLayout(HorizontalSplitPanel mainPanel) {
		this.mainPanel = mainPanel;
		
		menuTree = new Tree("Kantin Yönetim Programı");
		menuTree.addListener(this);
		menuTree.setImmediate(true);
		menuTree.setCaption("Kantin Yönetim Programı");
		
		menuTree.addItem("Muhasebe Destek Programı");
		menuTree.addItem("Depo Stok Yönetim Programı");

		menuTree.addItem("Ürün Yönetimi");
		menuTree.addItem("Firma Yönetimi");
		menuTree.addItem("Marka Yönetimi");
		menuTree.addItem("Depo Yönetimi");
		menuTree.addItem("Depo Kayıt Yönetimi");

		menuTree.setParent("Ürün Yönetimi", "Depo Stok Yönetim Programı");
		menuTree.setParent("Firma Yönetimi", "Depo Stok Yönetim Programı");
		menuTree.setParent("Marka Yönetimi", "Depo Stok Yönetim Programı");
		menuTree.setParent("Depo Yönetimi", "Depo Stok Yönetim Programı");
		menuTree.setParent("Depo Kayıt Yönetimi", "Depo Stok Yönetim Programı");

		menuTree.setChildrenAllowed("Muhasebe Destek Programı", false);
		menuTree.setChildrenAllowed("Ürün Yönetimi", false);
		menuTree.setChildrenAllowed("Firma Yönetimi", false);
		menuTree.setChildrenAllowed("Marka Yönetimi", false);
		menuTree.setChildrenAllowed("Depo Yönetimi", false);
		menuTree.setChildrenAllowed("Depo Kayıt Yönetimi", false);
		
		setSpacing(true);
		addComponent(menuTree);
	}

	public void valueChange(ValueChangeEvent event) {
		if (event.getProperty().getValue() != null) {
			GridLayout appLayout = null;
			if (event.getProperty().getValue().equals("Muhasebe Destek Programı")) {
				appLayout = layouts.get("muhasebe");
				if (appLayout == null) {
					appLayout = new MuhasebeManagement();
					layouts.put("muhasebe", appLayout);
				}
				mainPanel.setSecondComponent(appLayout) ;
			} else if (event.getProperty().getValue().equals("Ürün Yönetimi")) {
//				getWindow().showNotification("Selected item: " + event.getProperty().getValue());
				appLayout = layouts.get("ürün");
				if (appLayout == null) {
					appLayout = new ProductManagement(2, 2);
					layouts.put("ürün", appLayout);
				}
				mainPanel.setSecondComponent(appLayout);
			} else if (event.getProperty().getValue().equals("Depo Yönetimi")) {
				appLayout = layouts.get("warehouse");
				if (appLayout == null) {
					appLayout = new WarehouseManagement(2, 2);
					layouts.put("warehouse", appLayout);
				}
				mainPanel.setSecondComponent(appLayout);
			} else if (event.getProperty().getValue().equals("Depo Kayıt Yönetimi")) {
				appLayout = layouts.get("warehouseRecord");
				if (appLayout == null) {
					appLayout = new WarehouseRecordManagement(2, 2);
					layouts.put("warehouseRecord", appLayout);
				}
				mainPanel.setSecondComponent(appLayout);
			} else if (event.getProperty().getValue().equals("Firma Yönetimi")) {
				appLayout = layouts.get("company");
				if (appLayout == null) {
					appLayout = new CompanyManagement(2, 2);
					layouts.put("company", appLayout);
				}
				mainPanel.setSecondComponent(appLayout);
			} else if (event.getProperty().getValue().equals("Marka Yönetimi")) {
				appLayout = layouts.get("brand");
				if (appLayout == null) {
					appLayout = new BrandManagement(2, 2);
					layouts.put("brand", appLayout);
				}
				mainPanel.setSecondComponent(appLayout);
			}    
		}
	}
}
