package com.orma.ui;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.orma.api.IDefinitionAPI;
import com.orma.api.ISearchAPI;
import com.orma.domain.Brand;
import com.orma.domain.Product;
import com.orma.domain.TransactionType;
import com.orma.domain.Warehouse;
import com.orma.domain.WarehouseRecord;
import com.orma.vo.ProductTotalInfo;
import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
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
	
	@Autowired
	ISearchAPI searchAPI;
	
	private GridLayout controlLayout = new GridLayout(5,2);
	private GridLayout listLayout = new GridLayout(2,4);
	private HorizontalLayout ekleLayout = new HorizontalLayout();
	private Table recordTable;
	private Select warehouseSelect;
	private Select brandSelect;
	private Select productSelect;
	private Button listButton;
	private Button ekle;
	private Button sil;
	private Button guncelle;
	private Button kaydet;
	private TextField ekleSayisi;
	private Label ekleLabel = new Label("Ekleme");
	private Label markaLabel = new Label("Marka");
	private Label depoLabel = new Label("Depo");
	private Label urunLabel = new Label("Ürün");
	private BeanItemContainer<WarehouseRecord> recordContainer;
	private BeanItemContainer<Product> productContainer;
	private List<WarehouseRecord> baseRecordList;
	private List<WarehouseRecord> uiRecordList;
	private List<Brand> brands;
	private List<Product> products;
	private List<Warehouse> warehouses;
	private DecimalFormat df = new DecimalFormat("#0.00", new DecimalFormatSymbols(new Locale("tr")));
	private ProductTotalInfo ptInfo = null; 
	
	public WarehouseRecordManagement(int columns, int rows) {
		setSizeFull();
		setSpacing(true);
		setColumns(columns);
		setRows(rows);
		warehouses = definitionAPI.getAllWarehouses();
		warehouseSelect = new Select();
		BeanItemContainer<Warehouse> warehouseContainer = new BeanItemContainer<Warehouse>(Warehouse.class, warehouses);
		warehouseSelect.setContainerDataSource(warehouseContainer);
		warehouseSelect.setItemCaptionMode(Select.ITEM_CAPTION_MODE_PROPERTY);
		warehouseSelect.setItemCaptionPropertyId("name");
		warehouseSelect.setImmediate(true);
		
		brands = definitionAPI.getAllBrands();
		brandSelect = new Select();
		BeanItemContainer<Brand> brandContainer = new BeanItemContainer<Brand>(Brand.class, brands);
		brandSelect.setContainerDataSource(brandContainer);
		brandSelect.setItemCaptionMode(Select.ITEM_CAPTION_MODE_PROPERTY);
		brandSelect.setItemCaptionPropertyId("name");
		brandSelect.setImmediate(true);
		
		productSelect = new Select();
		productSelect.setEnabled(false);
		productContainer = new BeanItemContainer<Product>(Product.class);
		productSelect.setContainerDataSource(productContainer);
		productSelect.setItemCaptionMode(Select.ITEM_CAPTION_MODE_PROPERTY);
		productSelect.setItemCaptionPropertyId("name");
		productSelect.setImmediate(true);
		
		recordTable = new Table("Depo Kayıt Tablosu") {
		    @Override
		    protected String formatPropertyValue(Object rowId,
		            Object colId, Property property) {
		        // Format by property type
		        if (property.getType() == Date.class) {
		            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		            return df.format((Date)property.getValue());
		        } else if (property.getType() == BigDecimal.class) {
		        	 return df.format((BigDecimal) property.getValue());
		        }
		        return super.formatPropertyValue(rowId, colId, property);
		    }
		};
		
		
		recordTable.setHeight("400px");
		recordTable.setWidth("527px");

		uiRecordList = definitionAPI.getAllWarehouseRecords();
		recordContainer = new BeanItemContainer<WarehouseRecord>(WarehouseRecord.class);
		recordTable.setContainerDataSource(recordContainer);
		
		recordTable.setVisibleColumns(new String[]{"transactionType", "amount", "billNumber", "transactionTime"});
		recordTable.setColumnHeaders(new String[]{"İŞLEM TİPİ", "MİKTAR", "FATURA NO", "TANIMLANMA ZAMANI"});
		recordTable.setFooterVisible(true);
		recordTable.setColumnFooter("transactionType", "TOPLAM MİKTAR");
		recordTable.setColumnFooter("amount", "");
		recordTable.setColumnWidth("amount", 100);
		recordTable.setColumnWidth("billNumber", 100);
		recordTable.setColumnWidth("transactionType", 135);
		recordTable.setColumnWidth("transactionTime", 135);
		recordTable.setImmediate(true);
		
		addComponent(recordTable, 0 ,1);
		setComponentAlignment(recordTable, Alignment.BOTTOM_CENTER);
		
		
		guncelle = new Button("Güncelle");
		ekle = new Button("Ekle");
		kaydet = new Button("Kaydet");
		sil = new Button("Sil");
		ekleSayisi = new TextField();
		listButton = new Button("Sorgula");
		
		ekle.setEnabled(false);
		kaydet.setEnabled(false);
		sil.setEnabled(false);
		ekleSayisi.setEnabled(false);
		listButton.setEnabled(false);
		guncelle.setEnabled(true);
		
		listLayout.setSpacing(true);
		listLayout.addComponent(depoLabel, 0, 0);
		listLayout.addComponent(warehouseSelect, 1 , 0);
		
		listLayout.addComponent(markaLabel, 0, 1);
		listLayout.addComponent(brandSelect, 1 , 1);
		
		listLayout.addComponent(urunLabel, 0, 2);
		listLayout.addComponent(productSelect, 1 , 2);
		
		listLayout.setSpacing(true);
		listLayout.addComponent(listButton, 1, 3);
		listLayout.setComponentAlignment(depoLabel, Alignment.BOTTOM_CENTER);
		listLayout.setComponentAlignment(warehouseSelect, Alignment.BOTTOM_CENTER);
		listLayout.setComponentAlignment(markaLabel, Alignment.BOTTOM_CENTER);
		listLayout.setComponentAlignment(brandSelect, Alignment.BOTTOM_CENTER);
		listLayout.setComponentAlignment(urunLabel, Alignment.BOTTOM_CENTER);
		listLayout.setComponentAlignment(productSelect, Alignment.BOTTOM_CENTER);
		listLayout.setComponentAlignment(listButton, Alignment.BOTTOM_CENTER);
		
		addComponent(listLayout, 0, 0);
		setComponentAlignment(listLayout, Alignment.BOTTOM_CENTER);
		
		ekleSayisi.setWidth("50px");
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
		
		addComponent(controlLayout, 0, 2);
		setComponentAlignment(controlLayout, Alignment.TOP_CENTER);
		
		guncelle.addListener(new Button.ClickListener() {
			@Override
		    public void buttonClick(ClickEvent event) {
				ekle.setEnabled(true);
				kaydet.setEnabled(true);
				sil.setEnabled(true);
				ekleSayisi.setEnabled(true);
				guncelle.setEnabled(false);
				recordTable.setEditable(true);
				
				recordTable.setVisibleColumns(new String[]{"transactionType", "amount", "billNumber", "sec"});
				recordTable.setColumnHeaders(new String[]{ "İŞLEM TİPİ", "MİKTAR", "FATURA NO", "Seç"});
				recordTable.setColumnWidth("sec", 135);
				if (ptInfo != null) {
					recordTable.setColumnFooter("amount", ptInfo.getTotalAmount() + "");
					recordTable.setColumnFooter("billNumber", "ALIŞ " + df.format(ptInfo.getTotalBuy()));
					recordTable.setColumnFooter("sec", "SATIŞ " + df.format(ptInfo.getTotalSell()));
				}
		    }
		});
		
		ekle.addListener(new Button.ClickListener() {
			@Override
		    public void buttonClick(ClickEvent event) {
				
				WarehouseRecord emptyWarehouseRecord = null;
				if (ekleSayisi.getValue() == null 
						|| ekleSayisi.getValue().toString().length() == 0) {
					emptyWarehouseRecord = new WarehouseRecord();
					emptyWarehouseRecord.setAmount(0L);
					emptyWarehouseRecord.setBillNumber(0L);
					emptyWarehouseRecord.setTransactionType(TransactionType.girdi);
					if (productSelect.getValue() != null) {
						emptyWarehouseRecord.setProduct((Product) productSelect.getValue());
					} else {
						// TODO : throw exception
					}
					if (warehouseSelect.getValue() != null) {
						emptyWarehouseRecord.setWarehouse((Warehouse) warehouseSelect.getValue());
					} else {
						// TODO : throw exception
					}
					recordContainer.addItem(emptyWarehouseRecord);
				} else {
					
					for (int i = 0; i < Integer.parseInt(ekleSayisi.getValue().toString()); i++) {
						emptyWarehouseRecord = new WarehouseRecord();
						emptyWarehouseRecord.setAmount(0L);
						emptyWarehouseRecord.setBillNumber(0L);
						emptyWarehouseRecord.setTransactionType(TransactionType.girdi);
						if (productSelect.getValue() != null) {
							emptyWarehouseRecord.setProduct((Product) productSelect.getValue());
						} else {
							// TODO : throw exception
						}
						if (warehouseSelect.getValue() != null) {
							emptyWarehouseRecord.setWarehouse((Warehouse) warehouseSelect.getValue());
						} else {
							// TODO : throw exception
						}
						recordContainer.addItem(emptyWarehouseRecord);
					}
				}
				ptInfo = searchAPI.getProductTotalInfoByWarehouse((Product) productSelect.getValue(), (Warehouse) warehouseSelect.getValue());
				recordTable.setColumnFooter("amount", ptInfo.getTotalAmount() + "");
				recordTable.setColumnFooter("billNumber", "ALIŞ " + df.format(ptInfo.getTotalBuy()));
				recordTable.setColumnFooter("transactionTime", "SATIŞ " + df.format(ptInfo.getTotalSell()));
				recordTable.refreshRowCache();
				recordTable.requestRepaint();
		    }
		});
		
		kaydet.addListener(new Button.ClickListener() {
			@SuppressWarnings("rawtypes")
			@Override
		    public void buttonClick(ClickEvent event) {
				
				ekle.setEnabled(false);
				kaydet.setEnabled(false);
				sil.setEnabled(false);
				ekleSayisi.setEnabled(false);
				guncelle.setEnabled(true);
				
				WarehouseRecord warehouseRecord = null;
				baseRecordList = definitionAPI.getAllWarehouseRecords();
				for (Iterator i = recordContainer.getItemIds().iterator(); i.hasNext();) {
					warehouseRecord =  (WarehouseRecord) i.next();
					if (warehouseRecord.isSec() 
							&& warehouseRecord.getProduct() != null
							&& warehouseRecord.getWarehouse() != null) {
						warehouseRecord.setTransactionTime(new Date());
						definitionAPI.saveWarehouseRecord(warehouseRecord);
					}
				}
//				recordTable.setWidth("850px");
				if (productSelect.getValue() != null 
						&& warehouseSelect.getValue() != null) {
					uiRecordList = definitionAPI.getWarehouseRecordsByWarehouseAndProduct((Warehouse) warehouseSelect.getValue(), 
																						  (Product) productSelect.getValue());
				} else {
					// TODO : throw exception
				}
				recordContainer = new BeanItemContainer<WarehouseRecord>(WarehouseRecord.class, uiRecordList);
				recordTable.setContainerDataSource(recordContainer);
				recordTable.setEditable(false);
				recordTable.setVisibleColumns(new String[]{"transactionType", "amount", "billNumber", "transactionTime"});
				recordTable.setColumnHeaders(new String[]{ "İŞLEM TİPİ", "MİKTAR", "FATURA NO", "TANIMLANMA ZAMANI"});
				ptInfo = searchAPI.getProductTotalInfoByWarehouse((Product) productSelect.getValue(), (Warehouse) warehouseSelect.getValue());
				recordTable.setColumnFooter("amount", ptInfo.getTotalAmount() + "");
				recordTable.setColumnFooter("billNumber", "ALIŞ " + df.format(ptInfo.getTotalBuy()));
				recordTable.setColumnFooter("transactionTime", "SATIŞ " + df.format(ptInfo.getTotalSell()));
				recordTable.refreshRowCache();
		    }
		});
		
		sil.addListener(new Button.ClickListener() {
			@SuppressWarnings("rawtypes")
			@Override
		    public void buttonClick(ClickEvent event) {
				ekle.setEnabled(false);
				kaydet.setEnabled(false);
				sil.setEnabled(false);
				ekleSayisi.setEnabled(false);
				guncelle.setEnabled(true);
				
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
				if (productSelect.getValue() != null 
						&& warehouseSelect.getValue() != null) {
					uiRecordList = definitionAPI.getWarehouseRecordsByWarehouseAndProduct((Warehouse) warehouseSelect.getValue(), 
																						  (Product) productSelect.getValue());
				} else {
					// TODO : throw exception
				}
				recordContainer = new BeanItemContainer<WarehouseRecord>(WarehouseRecord.class, uiRecordList);
				recordTable.setContainerDataSource(recordContainer);
				recordTable.setEditable(false);
				recordTable.setVisibleColumns(new String[]{"transactionType", "amount", "billNumber", "transactionTime"});
				recordTable.setColumnHeaders(new String[]{"İŞLEM TİPİ", "MİKTAR", "FATURA NO", "TANIMLANMA ZAMANI"});
				recordTable.setColumnFooter("transactionType", "TOPLAM");
				ptInfo = searchAPI.getProductTotalInfoByWarehouse((Product) productSelect.getValue(), (Warehouse) warehouseSelect.getValue());
				recordTable.setColumnFooter("amount", ptInfo.getTotalAmount() + "");
				recordTable.setColumnFooter("billNumber", "ALIŞ " + df.format(ptInfo.getTotalBuy()));
				recordTable.setColumnFooter("transactionTime", "SATIŞ " + df.format(ptInfo.getTotalSell()));
				recordTable.refreshRowCache();
		    }
		});
		
		brandSelect.addListener(new Property.ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				Brand brand = null;
				if (event.getProperty().getValue() != null) {
					brand = (Brand) event.getProperty().getValue();
					products = definitionAPI.getProductsByBrand(brand);
					if (products != null && products.size() > 0) {
						productSelect.setEnabled(true);
						productContainer.addAll(products);
					} else {
						getWindow().showNotification(brand.getName().trim() + " markası için hiç ürün tanımlanmamış. Ürün Ekranından " + 
													 brand.getName().trim() + " markası için tanımlı olan ürünleri kontrol ediniz.");
					}
					brand = (Brand) event.getProperty().getValue();
				}
			}
		});
		
		productSelect.addListener(new Property.ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() != null) {
					listButton.setEnabled(true);
				}
			}
		});
		
		
		listButton.addListener(new Button.ClickListener() {
			@Override
		    public void buttonClick(ClickEvent event) {
				ekle.setEnabled(false);
				kaydet.setEnabled(false);
				sil.setEnabled(false);
				ekleSayisi.setEnabled(false);
				guncelle.setEnabled(true);
				controlLayout.setEnabled(true);
				
				if (productSelect.getValue() != null 
						&& warehouseSelect.getValue() != null) {
					uiRecordList = definitionAPI.getWarehouseRecordsByWarehouseAndProduct((Warehouse) warehouseSelect.getValue(), 
																						  (Product) productSelect.getValue());
				} else {
					// TODO : throw exception
				}
				recordTable.setLocale(new Locale("tr"));
				recordTable.setTableFieldFactory(new TableFieldFactory() {
		            @Override
		            public Field createField(Container container, Object itemId,
		                    Object propertyId, Component uiContext) {
		                Field field = null;

		                if ("amount".equals(propertyId)) {
		                	field = new TextField();
		                } else if ("billNumber".equals(propertyId)) {
		                	field = new TextField();
		                } else if ("transactionTime".equals(propertyId)) {
		                	field = new DateField();
		                } else if ("sec".equals(propertyId)) {
		                	field = new CheckBox();
		                } else if ("transactionType".equals(propertyId)) {
		                	WarehouseRecord record = ((WarehouseRecord) itemId);
		                	Select transactionTypeSelect = new Select();
		                	transactionTypeSelect.addItem(TransactionType.girdi);
		                	transactionTypeSelect.addItem(TransactionType.çıktı);
		                	transactionTypeSelect.setNullSelectionAllowed(false);
		                	if (record != null) {
		                		transactionTypeSelect.setValue(record.getTransactionType());
	                		} else {
	                			transactionTypeSelect.setValue(TransactionType.girdi); 	
	                		}
	                		field = transactionTypeSelect;
		                }
		                return field;
		            }
		        });
				recordContainer.removeAllItems();
				recordContainer.addAll(uiRecordList);
				ptInfo = searchAPI.getProductTotalInfoByWarehouse((Product) productSelect.getValue(), (Warehouse) warehouseSelect.getValue());
				recordTable.setFooterVisible(true);
				recordTable.setVisibleColumns(new String[]{"transactionType", "amount", "billNumber", "transactionTime"});
				recordTable.setColumnHeaders(new String[]{ "İŞLEM TİPİ", "MİKTAR", "FATURA NO", "TANIMLANMA ZAMANI"});
				recordTable.setColumnFooter("amount", ptInfo.getTotalAmount() + "");
				recordTable.setColumnFooter("billNumber", "ALIŞ " + df.format(ptInfo.getTotalBuy()));
				recordTable.setColumnFooter("transactionTime", "SATIŞ " + df.format(ptInfo.getTotalSell()));
				recordTable.setImmediate(true);
				recordTable.refreshRowCache();
		    }
		});
	}

}
