package com.orma.ui;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.orma.api.IDefinitionAPI;
import com.orma.domain.IBaseEntity;
import com.orma.domain.Product;
import com.orma.domain.Warehouse;
import com.orma.domain.WarehouseRecord;
import com.orma.utils.OrmaUtils;
import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Field;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Select;
import com.vaadin.ui.Table;
import com.vaadin.ui.TableFieldFactory;
import com.vaadin.ui.TextField;

@SuppressWarnings("serial")
@Configurable(preConstruction=true)
public class WarehouseRecordManagement extends GridLayout {
	
	@Autowired
	IDefinitionAPI definitionAPI;
	
	private Table recordTable;
	private Button ekle;
	private Button sil;
	private Button guncelle;
	private Button kaydet;
	private GridLayout controlLayout = new GridLayout(5,2);
	private List<WarehouseRecord> baseRecordList;
	private List<WarehouseRecord> uiRecordList;
	private BeanItemContainer<WarehouseRecord> recordContainer;
	private TextField ekleSayisi;
	private List<Product> products;
	private List<Warehouse> warehouses;
	
	public WarehouseRecordManagement(int columns, int rows) {
		setSizeFull();
		setSpacing(true);
		setColumns(columns);
		setRows(rows);
		
		recordTable = new Table("Marka Yönetim Tablosu");
		recordTable.setImmediate(true);
		recordTable.setHeight("400px");
		recordTable.setWidth("885px");

		uiRecordList = definitionAPI.getAllWarehouseRecords();
		recordContainer = new BeanItemContainer<WarehouseRecord>(WarehouseRecord.class, uiRecordList);
		recordContainer.addNestedContainerProperty("product.name");
		recordContainer.addNestedContainerProperty("warehouse.name");
		recordTable.setContainerDataSource(recordContainer);
		recordTable.setTableFieldFactory(new TableFieldFactory() {
            @Override
            public Field createField(Container container, Object itemId,
                    Object propertyId, Component uiContext) {
                Field field = null;
                if ("warehouse.name".equals(propertyId)) {
                	Warehouse warehosue = ((WarehouseRecord) itemId).getWarehouse();
                	if (recordTable.isEditable()) {
                		warehouses = definitionAPI.getAllWarehouses();
                		BeanItemContainer<Warehouse> warehouseContainer = new BeanItemContainer<Warehouse>(Warehouse.class, warehouses);
                		Select warehouseSelect = new Select();
                		warehouseSelect.setContainerDataSource(warehouseContainer);
                		warehouseSelect.setItemCaptionMode(Select.ITEM_CAPTION_MODE_PROPERTY);
                		warehouseSelect.setItemCaptionPropertyId("name");
                		if (warehosue != null 
                				&& warehosue.getName() != null
                				&& warehosue.getName().trim().length() > 0) {
                			warehouseSelect.setValue(warehosue.getName().trim());
                		} else {
                			warehouseSelect.setValue(""); 	
                		}
                		warehouseSelect.setImmediate(true);
                		field = warehouseSelect;
					} else {
						if (warehosue != null 
                				&& warehosue.getName() != null
                				&& warehosue.getName().trim().length() > 0) {
                			field = new TextField(warehosue.getName().trim());
                		} else {
                			field = new TextField("");
                		}
					}
                } else if ("amount".equals(propertyId)) {
                	field = new TextField();
                } else if ("buyPrice".equals(propertyId)) {
                	field = new TextField();
                } else if ("sellPrice".equals(propertyId)) {
                	field = new TextField();
                } else if ("billNumber".equals(propertyId)) {
                	field = new TextField();
                } else if ("place".equals(propertyId)) {
                	field = new TextField();
                } else if ("transactionTime".equals(propertyId)) {
                	field = new DateField();
                } else if ("sec".equals(propertyId)) {
                	field = new CheckBox();
                } else if ("product.name".equals(propertyId)) {
                	Product product = ((WarehouseRecord) itemId).getProduct();
                	if (recordTable.isEditable()) {
                		products = definitionAPI.getAllProducts();
                		BeanItemContainer<Product> brandContainer = new BeanItemContainer<Product>(Product.class, products);
                		Select productSelect = new Select();
                		productSelect.setContainerDataSource(brandContainer);
                		productSelect.setItemCaptionMode(Select.ITEM_CAPTION_MODE_PROPERTY);
                		productSelect.setItemCaptionPropertyId("name");
                		if (product != null 
                				&& product.getName() != null
                				&& product.getName().trim().length() > 0) {
                			productSelect.setValue(product.getName().trim());
                		} else {
                			productSelect.setValue(""); 	
                		}
                		productSelect.setImmediate(true);
                		field = productSelect;
					} else {
						if (product != null 
                				&& product.getName() != null
                				&& product.getName().trim().length() > 0) {
                			field = new TextField(product.getName().trim());
                		} else {
                			field = new TextField("");
                		}
					}
                }
                return field;
            }
        });
		
		recordTable.setVisibleColumns(new String[]{"warehouse.name", "product.name", "amount", "buyPrice", "sellPrice", "billNumber", "place", "transactionTime"});
		recordTable.setColumnHeaders(new String[]{"DEPO", "ÜRÜN", "MİKTAR", "ALIŞ FİYATI", "SATIŞ FİYATI", "FATURA NO", "YER", "TANIMLANMA ZAMANI"});
		recordTable.setColumnWidth("warehouse", 150);
		recordTable.setColumnWidth("product", 150);
		recordTable.setColumnWidth("amount", 50);
		recordTable.setColumnWidth("buyPrice", 75);
		recordTable.setColumnWidth("sellPrice", 85);
		recordTable.setColumnWidth("billNumber", 75);
		recordTable.setColumnWidth("place", 150);
		recordTable.setColumnWidth("transactionTime", 135);
		addComponent(recordTable, 0 ,0);
		setComponentAlignment(recordTable, Alignment.BOTTOM_CENTER);
		
		
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
				recordTable.setEditable(true);
				recordTable.setVisibleColumns(new String[]{"warehouse.name", "product.name", "amount", "buyPrice", "sellPrice", "billNumber", "place", "sec"});
				recordTable.setColumnHeaders(new String[]{"DEPO", "ÜRÜN", "MİKTAR", "ALIŞ FİYATI", "SATIŞ FİYATI", "FATURA NO", "YER", "SEÇ"});
				recordTable.setColumnWidth("sec", 135);
		    }
		});
		
		ekle.addListener(new Button.ClickListener() {
			@Override
		    public void buttonClick(ClickEvent event) {
				WarehouseRecord emptyWarehouseRecord = null;
				if (ekleSayisi.getValue() == null 
						|| ekleSayisi.getValue().toString().length() == 0) {
					emptyWarehouseRecord = new WarehouseRecord();
					emptyWarehouseRecord.setAmount(0);
					emptyWarehouseRecord.setBillNumber(0L);
					emptyWarehouseRecord.setBuyPrice(0);
					emptyWarehouseRecord.setSellPrice(0);
					emptyWarehouseRecord.setPlace("");
					emptyWarehouseRecord.setWarehouse(new Warehouse());
					emptyWarehouseRecord.setProduct(new Product());
					recordContainer.addItem(emptyWarehouseRecord);
				} else {
					for (int i = 0; i < Integer.parseInt(ekleSayisi.getValue().toString()); i++) {
						emptyWarehouseRecord = new WarehouseRecord();
						emptyWarehouseRecord.setAmount(0);
						emptyWarehouseRecord.setBillNumber(0L);
						emptyWarehouseRecord.setBuyPrice(0);
						emptyWarehouseRecord.setSellPrice(0);
						emptyWarehouseRecord.setPlace("");
						emptyWarehouseRecord.setWarehouse(new Warehouse());
						emptyWarehouseRecord.setProduct(new Product());
						recordContainer.addItem(emptyWarehouseRecord);
					}
				}
				recordTable.refreshRowCache();
				recordTable.requestRepaint();
		    }
		});
		
		kaydet.addListener(new Button.ClickListener() {
			@SuppressWarnings("rawtypes")
			@Override
		    public void buttonClick(ClickEvent event) {
				WarehouseRecord warehouseRecord = null;
				baseRecordList = definitionAPI.getAllWarehouseRecords();
				for (Iterator i = recordContainer.getItemIds().iterator(); i.hasNext();) {
					warehouseRecord =  (WarehouseRecord) i.next();
					if (warehouseRecord.isSec() 
							&& warehouseRecord.getProduct() != null
							&& warehouseRecord.getWarehouse() != null) {
						if (!OrmaUtils.listContains(baseRecordList, (IBaseEntity)warehouseRecord)) {
							warehouseRecord.setTransactionTime(new Date());
							definitionAPI.saveWarehouseRecord(warehouseRecord);
						} else {
							// TODO throw exception
						}
					}
				}
				recordTable.setWidth("850px");
				uiRecordList = definitionAPI.getAllWarehouseRecords();
				recordContainer = new BeanItemContainer<WarehouseRecord>(WarehouseRecord.class, uiRecordList);
				recordContainer.addNestedContainerProperty("product.name");
				recordContainer.addNestedContainerProperty("warehouse.name");
				recordTable.setContainerDataSource(recordContainer);
				recordTable.setEditable(false);
				recordTable.setVisibleColumns(new String[]{"warehouse.name", "product.name", "amount", "buyPrice", "sellPrice", "billNumber", "place", "sec"});
				recordTable.setColumnHeaders(new String[]{"DEPO", "ÜRÜN", "MİKTAR", "ALIŞ FİYATI", "SATIŞ FİYATI", "FATURA NO", "YER", "SEÇ"});
				recordTable.refreshRowCache();
		    }
		});
		
		sil.addListener(new Button.ClickListener() {
			@SuppressWarnings("rawtypes")
			@Override
		    public void buttonClick(ClickEvent event) {
				WarehouseRecord warehouseRecord = null;
				baseRecordList = definitionAPI.getAllWarehouseRecords();
				for (Iterator i = recordContainer.getItemIds().iterator(); i.hasNext();) {
					warehouseRecord =  (WarehouseRecord) i.next();
					if (warehouseRecord.isSec()) {
						if (warehouseRecord.getProduct() != null
								&& warehouseRecord.getWarehouse() != null
								&& baseRecordList.contains(warehouseRecord)) {
							definitionAPI.deleteWarehouseRecord(baseRecordList.get(baseRecordList.indexOf(warehouseRecord)));
						}
					}
				}
				uiRecordList = definitionAPI.getAllWarehouseRecords();
				recordContainer = new BeanItemContainer<WarehouseRecord>(WarehouseRecord.class, uiRecordList);
				recordContainer.addNestedContainerProperty("product.name");
				recordContainer.addNestedContainerProperty("warehouse.name");
				recordTable.setContainerDataSource(recordContainer);
				recordTable.setEditable(false);
				recordTable.setVisibleColumns(new String[]{"warehouse.name", "product.name", "amount", "buyPrice", "sellPrice", "billNumber", "place", "sec"});
				recordTable.setColumnHeaders(new String[]{"DEPO", "ÜRÜN", "MİKTAR", "ALIŞ FİYATI", "SATIŞ FİYATI", "FATURA NO", "YER", "SEÇ"});
				recordTable.refreshRowCache();
		    }
		});
	}

}
