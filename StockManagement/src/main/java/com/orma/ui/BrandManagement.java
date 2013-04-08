package com.orma.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.orma.api.IDefinitionAPI;
import com.orma.domain.Brand;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Table;

@SuppressWarnings("serial")
@Configurable(preConstruction=true)
public class BrandManagement extends GridLayout {
	
	@Autowired
	IDefinitionAPI definitionAPI;
	
	private Table brandTable;
	private Button ekle;
	private Button guncelle;
	private Button kaydet;
	private GridLayout controlLayout = new GridLayout(4,1);
	private final List<Brand> baseBrandList;
	private final List<Brand> uiBrandList;
	
	@SuppressWarnings("unchecked")
	public BrandManagement(int columns, int rows) {
		
		setColumns(columns);
		setRows(rows);
		
		brandTable = new Table("Marka Yönetim Tablosu");
		brandTable.setImmediate(true);
		brandTable.setHeight("400px");
		brandTable.setWidth("350px");
		
		baseBrandList = definitionAPI.getAllBrands();
		
		uiBrandList = (List<Brand>)((ArrayList<Brand>)baseBrandList).clone();
		final Container brandContainer = new BeanItemContainer<Brand>(Brand.class, uiBrandList);
		brandTable.setContainerDataSource(brandContainer);
		brandTable.addGeneratedColumn("Seç", new Table.ColumnGenerator() {
			CheckBox selected = null;
            public Component generateCell(Table source, Object itemId,
                    Object columnId) {
            	selected = new CheckBox();
                return selected;
            }
        });
		
		brandTable.setVisibleColumns(new String[]{"name", "transactionTime", "Seç"});
		brandTable.setColumnHeaders(new String[]{"İSİM", "TANIMLANMA ZAMANI", "SEÇ"});
		
		addComponent(brandTable, 0 ,0);
		
		guncelle = new Button("Güncelle");
		ekle = new Button("Ekle");
		kaydet = new Button("Kaydet");
		controlLayout.addComponent(ekle, 0 ,0);
		controlLayout.addComponent(kaydet, 1 ,0);
		controlLayout.addComponent(guncelle, 2 ,0);
		addComponent(controlLayout, 0, 1);
		
		guncelle.addListener(new Button.ClickListener() {
			@Override
		    public void buttonClick(ClickEvent event) {
		    	brandTable.setEditable(true);
		    }
		});
		
		ekle.addListener(new Button.ClickListener() {
			@Override
		    public void buttonClick(ClickEvent event) {
				Brand emptyBrand = new Brand();
				brandContainer.addItem(emptyBrand);
		    	brandTable.refreshRowCache();
		    }
		});
		
		kaydet.addListener(new Button.ClickListener() {
			@SuppressWarnings("rawtypes")
			@Override
		    public void buttonClick(ClickEvent event) {
				Brand brand = null;
				Item item = null;
				CheckBox sec = null;
				for (Iterator i = brandContainer.getItemIds().iterator(); i.hasNext();) {
					brand =  (Brand) i.next();
					item = brandTable.getItem(brand);
					sec = (CheckBox) (item.getItemProperty("Seç").getValue());
					if ((Boolean)sec.getValue()) {
						if (brand.getName() != null
								&& brand.getName().length() > 0
								&& !brand.getName().equals("null") 
								&& !baseBrandList.contains(brand)) {
							definitionAPI.createBrand(brand);
						}
					}
				}
		    }
		});
	}

}
