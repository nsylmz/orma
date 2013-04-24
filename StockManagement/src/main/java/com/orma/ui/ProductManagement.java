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
import com.orma.domain.Brand;
import com.orma.domain.Product;
import com.vaadin.data.Container;
import com.vaadin.data.Property;
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
public class ProductManagement extends GridLayout {
	
	@Autowired
	IDefinitionAPI definitionAPI;
	
	private GridLayout controlLayout = new GridLayout(5,2);
	private GridLayout listLayout = new GridLayout(1,2);
	private HorizontalLayout ekleLayout = new HorizontalLayout();
	private Table productTable;
	private Select brandSelect;
	private Button listButton;
	private Button ekle;
	private Button sil;
	private Button guncelle;
	private Button kaydet;
	private List<Product> baseProductList;
	private List<Product> uiProductList;
	private List<Brand> brands;
	private BeanItemContainer<Product> productContainer;
	private TextField ekleSayisi;
	private Label ekleLabel = new Label("Ekleme Sayısı");
	private DecimalFormat df = new DecimalFormat("#,###,##0.00", new DecimalFormatSymbols(new Locale("en")));
	
	public ProductManagement(int columns, int rows) {
		setSizeFull();
		setSpacing(true);
		setColumns(columns);
		setRows(rows);
		
		brands = definitionAPI.getAllBrands();
		brandSelect = new Select("Marka");
		BeanItemContainer<Brand> brandContainer = new BeanItemContainer<Brand>(Brand.class, brands);
		brandSelect.setContainerDataSource(brandContainer);
		brandSelect.setItemCaptionMode(Select.ITEM_CAPTION_MODE_PROPERTY);
		brandSelect.setItemCaptionPropertyId("name");
		
		listButton = new Button("Sorgula");
		
		productTable = new Table("Ürün Yönetim Tablosu") {
		    @Override
		    protected String formatPropertyValue(Object rowId,
		            Object colId, Property property) {
		        // Format by property type
		        if (property.getType() == Date.class) {
		            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		            return df.format((Date)property.getValue());
		        } else if (property.getType() == BigDecimal.class) {
		        	 return df.format((BigDecimal) property.getValue());
		        }
		        return super.formatPropertyValue(rowId, colId, property);
		    }
		};
		productTable.setEnabled(false);
		productTable.setImmediate(true);
		productTable.setHeight("400px");
		productTable.setWidth("848px");
		productContainer = new BeanItemContainer<Product>(Product.class);
		productTable.setContainerDataSource(productContainer);
		productTable.setVisibleColumns(new String[]{"barcode", "name", "buyPrice", "sellPrice", "transactionTime"});
		productTable.setColumnHeaders(new String[]{"BARKOD", "İSİM", "ALIŞ FİYATI", "SATIŞ FİYATI", "TANIMLANMA ZAMANI"});
		
		productTable.setColumnWidth("barcode", 100);
		productTable.setColumnWidth("name", 325);
		productTable.setColumnWidth("productType", 100);
		productTable.setColumnWidth("buyPrice", 100);
		productTable.setColumnWidth("sellPrice", 100);
		productTable.setColumnWidth("transactionTime", 135);
		
		listLayout.addComponent(brandSelect, 0 ,0);
		listLayout.addComponent(listButton, 0 ,1);
		listLayout.setComponentAlignment(brandSelect, Alignment.BOTTOM_CENTER);
		listLayout.setComponentAlignment(listButton, Alignment.BOTTOM_CENTER);
		
		addComponent(listLayout, 0 ,0);
		setComponentAlignment(listLayout, Alignment.BOTTOM_CENTER);
		
		addComponent(productTable, 0 ,1);
		setComponentAlignment(productTable, Alignment.BOTTOM_CENTER);
		
		guncelle = new Button("Güncelle");
		ekle = new Button("Ekle");
		kaydet = new Button("Kaydet");
		sil = new Button("Sil");
		ekleSayisi = new TextField();
		
		ekleLayout.setSpacing(true);
		ekleLayout.addComponent(ekleLabel);
		ekleLayout.addComponent(ekleSayisi);
		
		ekleSayisi.setWidth("50px");
		ekle.setWidth("85px");
		sil.setWidth("85px");
		kaydet.setWidth("85px");
		guncelle.setWidth("85px");
		
		controlLayout.setSpacing(true);
		controlLayout.setEnabled(false);
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
				productTable.setEditable(true);
				ekle.setEnabled(true);
				kaydet.setEnabled(true);
				sil.setEnabled(true);
				ekleSayisi.setEnabled(true);
				guncelle.setEnabled(false);
				productTable.setVisibleColumns(new String[]{"barcode", "name", "buyPrice", "sellPrice", "sec"});
				productTable.setColumnHeaders(new String[]{"BARKOD", "İSİM", "ALIŞ FİYATI", "SATIŞ FİYATI", "SEÇ"});
				productTable.setColumnWidth("sec", 135);
		    }
		});
		
		ekle.addListener(new Button.ClickListener() {
			@Override
		    public void buttonClick(ClickEvent event) {
				Product emptyProduct = null;
				if (ekleSayisi.getValue() == null 
						|| ekleSayisi.getValue().toString().length() == 0) {
					emptyProduct = new Product();
					emptyProduct.setName("");
					emptyProduct.setBuyPrice(BigDecimal.ZERO);
					emptyProduct.setSellPrice(BigDecimal.ZERO);
					emptyProduct.setBarcode(0L);
					if (brandSelect.getValue() != null) {
						emptyProduct.setBrand((Brand) brandSelect.getValue());
					} else {
						// TODO : throw exception
					}
					productContainer.addItem(emptyProduct);
				} else {
					for (int i = 0; i < Integer.parseInt(ekleSayisi.getValue().toString()); i++) {
						emptyProduct = new Product();
						emptyProduct.setName("");
						emptyProduct.setBarcode(0L);
						emptyProduct.setBuyPrice(BigDecimal.ZERO);
						emptyProduct.setSellPrice(BigDecimal.ZERO);
						if (brandSelect.getValue() != null) {
							emptyProduct.setBrand((Brand) brandSelect.getValue());
						} else {
							// TODO : throw exception
						}
						productContainer.addItem(emptyProduct);
					}
				}
				productTable.refreshRowCache();
				productTable.requestRepaint();
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
				
				Product product = null;
				if (brandSelect.getValue() != null) {
					baseProductList = definitionAPI.getProductsByBrand((Brand) brandSelect.getValue());
				} else {
					// TODO : throw exception
				}
				for (Iterator i = productContainer.getItemIds().iterator(); i.hasNext();) {
					product =  (Product) i.next();
					if (product.isSec() 
							&& product.getName() != null
							&& product.getName().length() > 0
							&& !product.getName().equals("null")) {
//						if (!OrmaUtils.listContains(baseProductList, (IBaseEntity)product)) {
							product.setTransactionTime(new Date());
							definitionAPI.saveProduct(product);
//						} else {
							// TODO throw exception
//						}
					}
				}
				productTable.setWidth("850px");
				if (brandSelect.getValue() != null) {
					uiProductList = definitionAPI.getProductsByBrand((Brand) brandSelect.getValue());
				} else {
					// TODO : throw exception
				}
				productContainer = new BeanItemContainer<Product>(Product.class, uiProductList);
				productTable.setContainerDataSource(productContainer);
				productTable.setEditable(false);
				productTable.setVisibleColumns(new String[]{"barcode", "name", "buyPrice", "sellPrice", "transactionTime"});
				productTable.setColumnHeaders(new String[]{"BARKOD", "İSİM", "ALIŞ FİYATI", "SATIŞ FİYATI", "TANIMLANMA ZAMANI"});
				productTable.refreshRowCache();
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
				
				Product product = null;
				if (brandSelect.getValue() != null) {
					baseProductList = definitionAPI.getProductsByBrand((Brand) brandSelect.getValue());
				} else {
					// TODO : throw exception
				}
				for (Iterator i = productContainer.getItemIds().iterator(); i.hasNext();) {
					product =  (Product) i.next();
					if (product.isSec()) {
						if (product.getName() != null
								&& product.getName().length() > 0
								&& !product.getName().equals("null") 
								&& baseProductList.contains(product)) {
							try {
								definitionAPI.deleteProduct(baseProductList.get(baseProductList.indexOf(product)));
							} catch (Exception e) {
								if (e.getMessage().contains("MySQLIntegrityConstraintViolationException")
										|| e.getMessage().contains("ConstraintViolationException")) {
									getWindow().showNotification(product.getName() + " ürününe ait depo kayıtkarını lütfen siliniz!!!");
								}
							}
						}
					}
				}
				if (brandSelect.getValue() != null) {
					uiProductList = definitionAPI.getProductsByBrand((Brand) brandSelect.getValue());
				} else {
					// TODO : throw exception
				}
				productContainer = new BeanItemContainer<Product>(Product.class, uiProductList);
				productTable.setContainerDataSource(productContainer);
				productTable.setEditable(false);
				productTable.setVisibleColumns(new String[]{"barcode", "name", "buyPrice", "sellPrice", "transactionTime"});
				productTable.setColumnHeaders(new String[]{"BARKOD", "İSİM", "ALIŞ FİYATI", "SATIŞ FİYATI", "TANIMLANMA ZAMANI"});
				productTable.refreshRowCache();
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
				productTable.setEnabled(true);
				
				if (brandSelect.getValue() != null) {
					uiProductList = definitionAPI.getProductsByBrand((Brand) brandSelect.getValue());
				} else {
					// TODO : throw exception
				}
				productTable.setTableFieldFactory(new TableFieldFactory() {
		            @Override
		            public Field createField(Container container, Object itemId,
		                    Object propertyId, Component uiContext) {
		                Field field = null;
		                productTable.setColumnWidth("barcode", 120);
		        		productTable.setColumnWidth("name", 325);
		        		productTable.setColumnWidth("buyPrice", 100);
		        		productTable.setColumnWidth("sellPrice", 100);
		        		productTable.setColumnWidth("transactionTime", 135);
		                if ("name".equals(propertyId)) {
		                	field = new TextField();
		                    field.setWidth("315");
		                    field.setHeight("24");
		                } else if ("barcode".equals(propertyId)) {
		                	field = new TextField();
		                	field.setWidth("110");
		                	field.setHeight("24");
		                } else if ("buyPrice".equals(propertyId)) {
		                	field = new TextField();
		                	field.setWidth("90");
		                	field.setHeight("24");
		                } else if ("sellPrice".equals(propertyId)) {
		                	field = new TextField();
		                	field.setWidth("90");
		                	field.setHeight("24");
		                } else if ("productType".equals(propertyId)) {
		                	field = new TextField();
		                	field.setWidth("90");
		                	field.setHeight("24");
		                } else if ("transactionTime".equals(propertyId)) {
		                	field = new DateField();
		                	field.setWidth("125");
		                	field.setHeight("24");
		                } else if ("sec".equals(propertyId)) {
		                	field = new CheckBox();
		                }
		                return field;
		            }
		        });
				productContainer.removeAllItems();
				productContainer.addAll(uiProductList);
				productTable.setVisibleColumns(new String[]{"barcode", "name", "buyPrice", "sellPrice", "transactionTime"});
				productTable.setColumnHeaders(new String[]{"BARKOD", "İSİM", "ALIŞ FİYATI", "SATIŞ FİYATI", "TANIMLANMA ZAMANI"});
				productTable.refreshRowCache();
		    }
		});
	}

}
