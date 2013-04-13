package com.orma.ui;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.orma.api.IDefinitionAPI;
import com.orma.domain.IBaseEntity;
import com.orma.domain.Warehouse;
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
public class WarehouseManagement extends GridLayout {
	
	@Autowired
	IDefinitionAPI definitionAPI;
	
	private Table warehouseTable;
	private Button ekle;
	private Button sil;
	private Button guncelle;
	private Button kaydet;
	private GridLayout controlLayout = new GridLayout(5,2);
	private List<Warehouse> baseWarehouseList;
	private List<Warehouse> uiWarehouseList;
	private Container warehouseContainer;
	private TextField ekleSayisi;
	
	public WarehouseManagement(int columns, int rows) {
		setSizeFull();
		setSpacing(true);
		setColumns(columns);
		setRows(rows);
		
		warehouseTable = new Table("Marka Yönetim Tablosu");
		warehouseTable.setImmediate(true);
		warehouseTable.setHeight("400px");
		warehouseTable.setWidth("463px");

		uiWarehouseList = definitionAPI.getAllWarehouses();

		warehouseContainer = new BeanItemContainer<Warehouse>(Warehouse.class, uiWarehouseList);
		warehouseTable.setContainerDataSource(warehouseContainer);
		
		warehouseTable.setVisibleColumns(new String[]{"name", "place", "transactionTime"});
		warehouseTable.setColumnHeaders(new String[]{"İSİM", "YER", "TANIMLANMA ZAMANI"});
		warehouseTable.setColumnWidth("name", 187);
		warehouseTable.setColumnWidth("place", 100);
		warehouseTable.setColumnWidth("transactionTime", 135);
		addComponent(warehouseTable, 0 ,0);
		setComponentAlignment(warehouseTable, Alignment.BOTTOM_CENTER);
		
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
				warehouseTable.setEditable(true);
				warehouseTable.setVisibleColumns(new String[]{"name", "place", "sec"});
				warehouseTable.setColumnHeaders(new String[]{"İSİM", "YER", "SEÇ"});
				warehouseTable.setColumnWidth("sec", 50);
		    }
		});
		
		ekle.addListener(new Button.ClickListener() {
			@Override
		    public void buttonClick(ClickEvent event) {
				Warehouse emptyWarehouse = null;
				if (ekleSayisi.getValue() == null 
						|| ekleSayisi.getValue().toString().length() == 0) {
					emptyWarehouse = new Warehouse();
					emptyWarehouse.setName("");
					emptyWarehouse.setPlace("");
					warehouseContainer.addItem(emptyWarehouse);
				} else {
					for (int i = 0; i < Integer.parseInt(ekleSayisi.getValue().toString()); i++) {
						emptyWarehouse = new Warehouse();
						emptyWarehouse.setName("");
						emptyWarehouse.setPlace("");
						warehouseContainer.addItem(emptyWarehouse);
					}
				}
				warehouseTable.refreshRowCache();
		    }
		});
		
		kaydet.addListener(new Button.ClickListener() {
			@SuppressWarnings("rawtypes")
			@Override
		    public void buttonClick(ClickEvent event) {
				Warehouse warehouse = null;
				baseWarehouseList = definitionAPI.getAllWarehouses();
				for (Iterator i = warehouseContainer.getItemIds().iterator(); i.hasNext();) {
					warehouse =  (Warehouse) i.next();
					if (warehouse.isSec() 
							&& warehouse.getName() != null
							&& warehouse.getName().length() > 0
							&& !warehouse.getName().equals("null")) {
						if (!OrmaUtils.listContains(baseWarehouseList, (IBaseEntity)warehouse)) {
							warehouse.setTransactionTime(new Date());
							definitionAPI.saveWarehouse(warehouse);
						} else {
							// TODO throw exception
						}
					}
				}
				warehouseTable.setWidth("475px");
				uiWarehouseList = definitionAPI.getAllWarehouses();
				warehouseContainer = new BeanItemContainer<Warehouse>(Warehouse.class, uiWarehouseList);
				warehouseTable.setContainerDataSource(warehouseContainer);
				warehouseTable.setEditable(false);
				warehouseTable.setVisibleColumns(new String[]{"name", "place", "transactionTime"});
				warehouseTable.setColumnHeaders(new String[]{"İSİM", "YER", "TANIMLANMA ZAMANI"});
				warehouseTable.refreshRowCache();
		    }
		});
		
		sil.addListener(new Button.ClickListener() {
			@SuppressWarnings("rawtypes")
			@Override
		    public void buttonClick(ClickEvent event) {
				Warehouse warehouse = null;
				baseWarehouseList = definitionAPI.getAllWarehouses();
				for (Iterator i = warehouseContainer.getItemIds().iterator(); i.hasNext();) {
					warehouse =  (Warehouse) i.next();
					if (warehouse.isSec()) {
						if (warehouse.getName() != null
								&& warehouse.getName().length() > 0
								&& !warehouse.getName().equals("null") 
								&& baseWarehouseList.contains(warehouse)) {
							definitionAPI.deleteWarehouse(baseWarehouseList.get(baseWarehouseList.indexOf(warehouse)));
						}
					}
				}
				uiWarehouseList = definitionAPI.getAllWarehouses();
				warehouseContainer = new BeanItemContainer<Warehouse>(Warehouse.class, uiWarehouseList);
				warehouseTable.setContainerDataSource(warehouseContainer);
				warehouseTable.setEditable(false);
				warehouseTable.setVisibleColumns(new String[]{"name", "place", "transactionTime"});
				warehouseTable.setColumnHeaders(new String[]{"İSİM", "YER", "TANIMLANMA ZAMANI"});
				warehouseTable.refreshRowCache();
		    }
		});
	}

}
