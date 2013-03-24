package com.orma.ui;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.orma.api.IDefinitionAPI;
import com.orma.domain.Brand;
import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
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
	
	public BrandManagement() {
		
		brandTable = new Table("Marka Yönetim Tablosu");
		
		List<Brand> brandList = definitionAPI.getAllBrands();
		if (brandList == null || brandList.size() == 0) {
			Brand emptyBrand = new Brand();
			brandList = new ArrayList<Brand>();
			brandList.add(emptyBrand);
		}
		Container brandContainer = new BeanItemContainer<Brand>(Brand.class, brandList);
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
		
		addComponent(brandTable);
	}

}
