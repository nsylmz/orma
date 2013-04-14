package com.orma.ui;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.orma.api.IDefinitionAPI;
import com.orma.domain.Brand;
import com.orma.domain.IBaseEntity;
import com.orma.domain.Product;
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
public class ProductManagement extends GridLayout {
	
	@Autowired
	IDefinitionAPI definitionAPI;
	
	private Table productTable;
	private Button ekle;
	private Button sil;
	private Button guncelle;
	private Button kaydet;
	private GridLayout controlLayout = new GridLayout(5,2);
	private List<Product> baseProductList;
	private List<Product> uiProductList;
	private BeanItemContainer<Product> productContainer;
	private TextField ekleSayisi;
	private List<Brand> brands;
	
	public ProductManagement(int columns, int rows) {
		setSizeFull();
		setSpacing(true);
		setColumns(columns);
		setRows(rows);
		
		productTable = new Table("Marka Yönetim Tablosu");
		productTable.setImmediate(true);
		productTable.setHeight("400px");
		productTable.setWidth("850px");

		uiProductList = definitionAPI.getAllProducts();
		productContainer = new BeanItemContainer<Product>(Product.class, uiProductList);
		productContainer.addNestedContainerProperty("brand.name");
		productTable.setContainerDataSource(productContainer);
		productTable.setTableFieldFactory(new TableFieldFactory() {
            @Override
            public Field createField(Container container, Object itemId,
                    Object propertyId, Component uiContext) {
                Field field = null;
                if ("name".equals(propertyId)) {
                	field = new TextField();
                    field.setWidth("240px");
                } else if ("barcode".equals(propertyId)) {
                	field = new TextField();
                } else if ("productType".equals(propertyId)) {
                	field = new TextField();
                } else if ("transactionTime".equals(propertyId)) {
                	field = new DateField();
                } else if ("sec".equals(propertyId)) {
                	field = new CheckBox();
                } else if ("brand".equals(propertyId)) {
                	Brand brand = ((Product) itemId).getBrand();
                	if (productTable.isEditable()) {
                		brands = definitionAPI.getAllBrands();
                		BeanItemContainer<Brand> brandContainer = new BeanItemContainer<Brand>(Brand.class, brands);
                		Select brandSelect = new Select();
                		brandSelect.setContainerDataSource(brandContainer);
                		brandSelect.setItemCaptionMode(Select.ITEM_CAPTION_MODE_PROPERTY);
                		brandSelect.setItemCaptionPropertyId("name");
                		if (brand != null 
                				&& brand.getName() != null
                				&& brand.getName().trim().length() > 0) {
                			brandSelect.setValue(brand.getName().trim());
                		} else {
                			brandSelect.setValue(""); 	
                		}
                		brandSelect.setImmediate(true);
                		field = brandSelect;
					} else {
						if (brand != null 
                				&& brand.getName() != null
                				&& brand.getName().trim().length() > 0) {
                			field = new TextField(brand.getName().trim());
                		} else {
                			field = new TextField("");
                		}
					}
                }
                return field;
            }
        });
		
		productTable.setVisibleColumns(new String[]{"barcode", "name", "brand.name", "productType", "transactionTime"});
		productTable.setColumnHeaders(new String[]{"BARKOD", "İSİM", "MARKA", "ÜRÜN TİPİ", "TANIMLANMA ZAMANI"});
		productTable.setColumnWidth("barcode", 100);
		productTable.setColumnWidth("name", 250);
		productTable.setColumnWidth("brand", 135);
		productTable.setColumnWidth("productType", 135);
		productTable.setColumnWidth("transactionTime", 135);
		addComponent(productTable, 0 ,0);
		setComponentAlignment(productTable, Alignment.BOTTOM_CENTER);
		
		
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
				productTable.setEditable(true);
				productTable.setVisibleColumns(new String[]{"barcode", "name", "brand", "productType", "sec"});
				productTable.setColumnHeaders(new String[]{"BARKOD", "İSİM", "MARKA", "ÜRÜN TİPİ", "SEÇ"});
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
					emptyProduct.setBarcode(0L);
					emptyProduct.setProductType("");
					emptyProduct.setBrand(new Brand());
					productContainer.addItem(emptyProduct);
				} else {
					for (int i = 0; i < Integer.parseInt(ekleSayisi.getValue().toString()); i++) {
						emptyProduct = new Product();
						emptyProduct.setName("");
						emptyProduct.setProductType("");
						emptyProduct.setBarcode(0L);
						emptyProduct.setBrand(new Brand());
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
				Product product = null;
				baseProductList = definitionAPI.getAllProducts();
				for (Iterator i = productContainer.getItemIds().iterator(); i.hasNext();) {
					product =  (Product) i.next();
					if (product.isSec() 
							&& product.getName() != null
							&& product.getName().length() > 0
							&& !product.getName().equals("null")) {
						if (!OrmaUtils.listContains(baseProductList, (IBaseEntity)product)) {
							product.setTransactionTime(new Date());
							definitionAPI.saveProduct(product);
						} else {
							// TODO throw exception
						}
					}
				}
				productTable.setWidth("850px");
				uiProductList = definitionAPI.getAllProducts();
				productContainer = new BeanItemContainer<Product>(Product.class, uiProductList);
				productContainer.addNestedContainerProperty("brand.name");
				productTable.setContainerDataSource(productContainer);
				productTable.setEditable(false);
				productTable.setVisibleColumns(new String[]{"barcode", "name", "brand.name", "productType", "transactionTime"});
				productTable.setColumnHeaders(new String[]{"BARKOD", "İSİM", "MARKA", "ÜRÜN TİPİ", "TANIMLANMA ZAMANI"});
				productTable.refreshRowCache();
		    }
		});
		
		sil.addListener(new Button.ClickListener() {
			@SuppressWarnings("rawtypes")
			@Override
		    public void buttonClick(ClickEvent event) {
				Product product = null;
				baseProductList = definitionAPI.getAllProducts();
				for (Iterator i = productContainer.getItemIds().iterator(); i.hasNext();) {
					product =  (Product) i.next();
					if (product.isSec()) {
						if (product.getName() != null
								&& product.getName().length() > 0
								&& !product.getName().equals("null") 
								&& baseProductList.contains(product)) {
							definitionAPI.deleteProduct(baseProductList.get(baseProductList.indexOf(product)));
						}
					}
				}
				uiProductList = definitionAPI.getAllProducts();
				productContainer = new BeanItemContainer<Product>(Product.class, uiProductList);
				productContainer.addNestedContainerProperty("brand.name");
				productTable.setContainerDataSource(productContainer);
				productTable.setEditable(false);
				productTable.setVisibleColumns(new String[]{"barcode", "name", "brand.name", "productType", "transactionTime"});
				productTable.setColumnHeaders(new String[]{"BARKOD", "İSİM", "MARKA", "ÜRÜN TİPİ", "TANIMLANMA ZAMANI"});
				productTable.refreshRowCache();
		    }
		});
	}

}
