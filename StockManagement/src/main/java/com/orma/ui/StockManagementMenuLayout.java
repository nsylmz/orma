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
		menuTree.addItem("Depo Yönetimi");
		menuTree.addItem("Firma Yönetimi");
		menuTree.addItem("Marka Yönetimi");

		menuTree.setParent("Ürün Yönetimi", "Depo Stok Yönetim Programı");
		menuTree.setParent("Depo Yönetimi", "Depo Stok Yönetim Programı");
		menuTree.setParent("Firma Yönetimi", "Depo Stok Yönetim Programı");
		menuTree.setParent("Marka Yönetimi", "Depo Stok Yönetim Programı");

		menuTree.setChildrenAllowed("Muhasebe Destek Programı", false);
		menuTree.setChildrenAllowed("Ürün Yönetimi", false);
		menuTree.setChildrenAllowed("Depo Yönetimi", false);
		menuTree.setChildrenAllowed("Firma Yönetimi", false);
		menuTree.setChildrenAllowed("Marka Yönetimi", false);
		
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
				getWindow().showNotification("Selected item: " + event.getProperty().getValue());
			} else if (event.getProperty().getValue().equals("Depo Yönetimi")) {
				getWindow().showNotification("Selected item: " + event.getProperty().getValue());
			} else if (event.getProperty().getValue().equals("Firma Yönetimi")) {
				getWindow().showNotification("Selected item: " + event.getProperty().getValue());
			} else if (event.getProperty().getValue().equals("Marka Yönetimi")) {
				appLayout = layouts.get("brand");
				if (appLayout == null) {
					appLayout = new BrandManagement();
					layouts.put("brand", appLayout);
				}
				mainPanel.setSecondComponent(appLayout) ;
			}    
		}
	}
}
