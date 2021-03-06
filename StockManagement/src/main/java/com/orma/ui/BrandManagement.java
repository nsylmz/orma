package com.orma.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.orma.api.IDefinitionAPI;
import com.orma.domain.Brand;
import com.orma.domain.IBaseEntity;
import com.orma.utils.OrmaUtils;
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
import com.vaadin.ui.Table;
import com.vaadin.ui.TableFieldFactory;
import com.vaadin.ui.TextField;

@SuppressWarnings("serial")
@Configurable(preConstruction=true)
public class BrandManagement extends GridLayout {
	
	@Autowired
	IDefinitionAPI definitionAPI;
	
	private Table brandTable;
	private Button ekle;
	private Button sil;
	private Button guncelle;
	private Button kaydet;
	private GridLayout controlLayout = new GridLayout(5,2);
	private List<Brand> baseBrandList;
	private List<Brand> uiBrandList;
	private Container brandContainer;
	private TextField ekleSayisi;
	
	@SuppressWarnings("unchecked")
	public BrandManagement(int columns, int rows) {
		setSizeFull();
		setSpacing(true);
		setColumns(columns);
		setRows(rows);
		
		brandTable = new Table("Marka Yönetim Tablosu") {
		    @Override
		    protected String formatPropertyValue(Object rowId,
		            Object colId, Property property) {
		        // Format by property type
		        if (property.getType() == Date.class) {
		            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		            return df.format((Date)property.getValue());
		        }
		        return super.formatPropertyValue(rowId, colId, property);
		    }
		};
		brandTable.setImmediate(true);

		uiBrandList = definitionAPI.getAllBrands();
		brandContainer = new BeanItemContainer<Brand>(Brand.class, uiBrandList);
		brandTable.setContainerDataSource(brandContainer);
		
		brandTable.setHeight("400px");
		if (uiBrandList.size() < 15) {
			brandTable.setWidth("350px");
		} else {
			brandTable.setWidth("375px");
		}
//		brandTable.addGeneratedColumn("Seç", new Table.ColumnGenerator() {
//			CheckBox selected = null;
//            public Component generateCell(Table source, Object itemId,
//                    Object columnId) {
//            	selected = new CheckBox();
//                return selected;
//            }
//        });
		
		brandTable.setTableFieldFactory(new TableFieldFactory() {
            @Override
            public Field createField(Container container, Object itemId,
                    Object propertyId, Component uiContext) {
                Field field = null;
                if ("name".equals(propertyId)) {
                	field = new TextField();
                	field.setWidth("177");
                	field.setHeight("24");
                } else if ("transactionTime".equals(propertyId)) {
                	field = new DateField();
                	((DateField) field).setDateFormat("yyyy-MM-dd");
                	field.setWidth("125");
                	field.setHeight("24");
                } else if ("sec".equals(propertyId)) {
                	field = new CheckBox();
                }
                return field;
            }
        });
		
		brandTable.setVisibleColumns(new String[]{"name", "transactionTime"});
		brandTable.setColumnHeaders(new String[]{"İSİM", "TANIMLANMA ZAMANI"});
		brandTable.setColumnWidth("name", 187);
		brandTable.setColumnWidth("transactionTime", 135);
		addComponent(brandTable, 0 ,0);
		setComponentAlignment(brandTable, Alignment.BOTTOM_CENTER);
		
		guncelle = new Button("Güncelle");
		ekle = new Button("Ekle");
		kaydet = new Button("Kaydet");
		sil = new Button("Sil");
		ekleSayisi = new TextField();
		
		ekle.setEnabled(false);
		kaydet.setEnabled(false);
		sil.setEnabled(false);
		ekleSayisi.setEnabled(false);
		guncelle.setEnabled(true);
		
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
				ekle.setEnabled(true);
				kaydet.setEnabled(true);
				sil.setEnabled(true);
				ekleSayisi.setEnabled(true);
				guncelle.setEnabled(false);
				brandTable.setEditable(true);
				brandTable.setVisibleColumns(new String[]{"name", "sec"});
				brandTable.setColumnHeaders(new String[]{"İSİM", "SEÇ"});
				brandTable.setColumnWidth("sec", 135);
		    }
		});
		
		ekle.addListener(new Button.ClickListener() {
			@Override
		    public void buttonClick(ClickEvent event) {
				Brand emptyBrand = null;
				if (ekleSayisi.getValue() == null 
						|| ekleSayisi.getValue().toString().length() == 0) {
					emptyBrand = new Brand();
					emptyBrand.setName("");
					brandContainer.addItem(emptyBrand);
				} else {
					for (int i = 0; i < Integer.parseInt(ekleSayisi.getValue().toString()); i++) {
						emptyBrand = new Brand();
						emptyBrand.setName("");
						brandContainer.addItem(emptyBrand);
					}
				}
				
				if (brandContainer.size() < 15) {
					brandTable.setWidth("350px");
				} else {
					brandTable.setWidth("375px");
				}
				brandTable.refreshRowCache();
		    }
		});
		
		kaydet.addListener(new Button.ClickListener() {
			@SuppressWarnings("rawtypes")
			@Override
		    public void buttonClick(ClickEvent event) {
				Brand brand = null;
				
				ekle.setEnabled(false);
				kaydet.setEnabled(false);
				sil.setEnabled(false);
				ekleSayisi.setEnabled(false);
				guncelle.setEnabled(true);
				
				baseBrandList = definitionAPI.getAllBrands();
				for (Iterator i = brandContainer.getItemIds().iterator(); i.hasNext();) {
					brand =  (Brand) i.next();
					if (brand.isSec()) {
						if (brand.getName() != null
								&& brand.getName().length() > 0
								&& !brand.getName().equals("null")) {
							if (!OrmaUtils.listContains(baseBrandList, (IBaseEntity)brand)) {
								brand.setTransactionTime(new Date());
								definitionAPI.saveBrand(brand);
							} else {
								// TODO throw exception
							}
						}
					}
				}
				baseBrandList = definitionAPI.getAllBrands();
				uiBrandList = (List<Brand>)((ArrayList<Brand>)baseBrandList).clone();
				brandContainer = new BeanItemContainer<Brand>(Brand.class, uiBrandList);
				brandTable.setContainerDataSource(brandContainer);
				
				if (uiBrandList.size() < 15) {
					brandTable.setWidth("350px");
				} else {
					brandTable.setWidth("375px");
				}
				
				brandTable.setEditable(false);
				brandTable.setVisibleColumns(new String[]{"name", "transactionTime"});
				brandTable.setColumnHeaders(new String[]{"İSİM", "TANIMLANMA ZAMANI"});
				brandTable.refreshRowCache();
		    }
		});
		
		sil.addListener(new Button.ClickListener() {
			@SuppressWarnings("rawtypes")
			@Override
		    public void buttonClick(ClickEvent event) {
				Brand brand = null;
				
				ekle.setEnabled(false);
				kaydet.setEnabled(false);
				sil.setEnabled(false);
				ekleSayisi.setEnabled(false);
				guncelle.setEnabled(true);
				
				baseBrandList = definitionAPI.getAllBrands();
				for (Iterator i = brandContainer.getItemIds().iterator(); i.hasNext();) {
					brand =  (Brand) i.next();
					if (brand.isSec()) {
						if (brand.getName() != null
								&& brand.getName().length() > 0
								&& !brand.getName().equals("null") 
								&& baseBrandList.contains(brand)) {
							try {
								definitionAPI.deleteBrand(baseBrandList.get(baseBrandList.indexOf(brand)));
							} catch (Exception e) {
								if (e.getMessage().contains("MySQLIntegrityConstraintViolationException")
										|| e.getMessage().contains("ConstraintViolationException")) {
									getWindow().showNotification(brand.getName() + " markasına ait ürünleri lütfen siliniz!!!");
								}
							}
						}
					}
				}
				
				baseBrandList = definitionAPI.getAllBrands();
				uiBrandList = (List<Brand>)((ArrayList<Brand>)baseBrandList).clone();
				brandContainer = new BeanItemContainer<Brand>(Brand.class, uiBrandList);
				brandTable.setContainerDataSource(brandContainer);
				if (uiBrandList.size() < 15) {
					brandTable.setWidth("350px");
				} else {
					brandTable.setWidth("375px");
				}
				brandTable.setEditable(false);
				brandTable.setVisibleColumns(new String[]{"name", "transactionTime"});
				brandTable.setColumnHeaders(new String[]{"İSİM", "TANIMLANMA ZAMANI"});
				brandTable.refreshRowCache();
		    }
		});
	}

}
