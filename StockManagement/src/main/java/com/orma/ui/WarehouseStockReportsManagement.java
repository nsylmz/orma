package com.orma.ui;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.tepi.filtertable.FilterTable;

import com.orma.api.IDefinitionAPI;
import com.orma.api.ISearchAPI;
import com.orma.domain.Brand;
import com.orma.domain.Product;
import com.orma.domain.Warehouse;
import com.orma.vo.ReportType;
import com.orma.vo.WarehouseStockReport;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Select;

@SuppressWarnings("serial")
@Configurable(preConstruction=true)
public class WarehouseStockReportsManagement extends GridLayout {
	
	@Autowired
	IDefinitionAPI definitionAPI;
	
	@Autowired
	ISearchAPI searchAPI;
	
	private GridLayout listLayout = new GridLayout(2,5);
	private FilterTable reportTable;
	private Select reportSelect;
	private Select warehouseSelect;
	private Select brandSelect;
	private Select productSelect;
	private Button listButton;
	private Label reportLabel = new Label("Rapor Tipi");
	private Label markaLabel = new Label("Marka");
	private Label depoLabel = new Label("Depo");
	private Label urunLabel = new Label("Ürün");
	private BeanItemContainer<WarehouseStockReport> reportContainer;
	private BeanItemContainer<Product> productContainer;
	private BeanItemContainer<Brand> brandContainer;
	private BeanItemContainer<Warehouse> warehouseContainer;
	private List<WarehouseStockReport> uiReportList;
	private List<Brand> brands;
	private List<Product> products;
	private List<Warehouse> warehouses;
	private DecimalFormat df = new DecimalFormat("#,###,##0.00", new DecimalFormatSymbols(new Locale("tr")));
	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	
	public WarehouseStockReportsManagement(int columns, int rows) {
		setSizeFull();
		setSpacing(true);
		setColumns(columns);
		setRows(rows);
		
		warehouses = definitionAPI.getAllWarehouses();
		warehouseSelect = new Select();
		warehouseContainer = new BeanItemContainer<Warehouse>(Warehouse.class, warehouses);
		warehouseSelect.setContainerDataSource(warehouseContainer);
		warehouseSelect.setItemCaptionMode(Select.ITEM_CAPTION_MODE_PROPERTY);
		warehouseSelect.setItemCaptionPropertyId("name");
		warehouseSelect.setImmediate(true);
		
		brands = definitionAPI.getAllBrands();
		brandSelect = new Select();
		brandContainer = new BeanItemContainer<Brand>(Brand.class, brands);
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
		
		reportSelect = new Select();
		reportSelect.addItem(ReportType.general);
		reportSelect.setItemCaption(ReportType.general, "GENEL RAPOR");
		reportSelect.addItem(ReportType.warehouse);
		reportSelect.setItemCaption(ReportType.warehouse, "DEPO BAZLI GENEL RAPOR");
		reportSelect.addItem(ReportType.brands);
		reportSelect.setItemCaption(ReportType.brands, "MARKA BAZLI GENEL RAPOR");
		reportSelect.addItem(ReportType.brandsByWarehouse);
		reportSelect.setItemCaption(ReportType.brandsByWarehouse, "MARKANIN DEPO BAZINDA RAPOR");
		reportSelect.addItem(ReportType.brandAndProduct);
		reportSelect.setItemCaption(ReportType.brandAndProduct, "ÜRÜN RAPOR");
		reportSelect.setImmediate(true);
		
		listButton = new Button("Sorgula");
		listButton.setImmediate(true);
		
		listLayout.addComponent(reportLabel, 0, 0);
		listLayout.addComponent(reportSelect, 1 , 0);
		
		listLayout.addComponent(depoLabel, 0, 1);
		listLayout.addComponent(warehouseSelect, 1 , 1);
		
		listLayout.addComponent(markaLabel, 0, 2);
		listLayout.addComponent(brandSelect, 1 , 2);
		
		listLayout.addComponent(urunLabel, 0, 3);
		listLayout.addComponent(productSelect, 1 , 3);
		
		reportSelect.setWidth("350px");
		warehouseSelect.setWidth("350px");
		brandSelect.setWidth("350px");
		productSelect.setWidth("350px");
		
		listLayout.setSpacing(true);
		listLayout.addComponent(listButton, 1, 4);
		listLayout.setComponentAlignment(depoLabel, Alignment.BOTTOM_CENTER);
		listLayout.setComponentAlignment(warehouseSelect, Alignment.BOTTOM_CENTER);
		listLayout.setComponentAlignment(markaLabel, Alignment.BOTTOM_CENTER);
		listLayout.setComponentAlignment(brandSelect, Alignment.BOTTOM_CENTER);
		listLayout.setComponentAlignment(urunLabel, Alignment.BOTTOM_CENTER);
		listLayout.setComponentAlignment(productSelect, Alignment.BOTTOM_CENTER);
		listLayout.setComponentAlignment(listButton, Alignment.BOTTOM_CENTER);
		
		depoLabel.setEnabled(false);
		warehouseSelect.setEnabled(false);
		markaLabel.setEnabled(false);
		brandSelect.setEnabled(false);
		urunLabel.setEnabled(false);
		productSelect.setEnabled(false);
		listButton.setEnabled(false);
		
		reportTable = new FilterTable("Rapor Tablosu") {
		    @Override
		    protected String formatPropertyValue(Object rowId,
		            Object colId, Property property) {
		        // Format by property type
		        if (property.getType() == Date.class) {
		            return sdf.format((Date)property.getValue());
		        } else if (property.getType() == BigDecimal.class) {
		        	 return df.format((BigDecimal) property.getValue());
		        }
		        return super.formatPropertyValue(rowId, colId, property);
		    }
		};
		
		reportTable.setHeight("400px");
		reportTable.setWidth("933px");

		reportContainer = new BeanItemContainer<WarehouseStockReport>(WarehouseStockReport.class);
		reportTable.setContainerDataSource(reportContainer);
		
		reportTable.setVisibleColumns(new String[]{"warehouseName", "brandName", "productName", "productBarcode", "totalAmount", "totalBuyPrice", "totalSellPrice"});
		reportTable.setColumnHeaders(new String[]{"DEPO", "MARKA", "ÜRÜN", "BARKOD", "MİKTAR", "ALIŞ", "SATIŞ"});
		reportTable.setFooterVisible(true);
		reportTable.setFilterBarVisible(true);
		reportTable.setColumnWidth("warehouseName", 125);
		reportTable.setColumnWidth("brandName", 125);
		reportTable.setColumnWidth("productName", 250);
		reportTable.setColumnWidth("productBarcode", 100);
		reportTable.setColumnWidth("totalAmount", 80);
		reportTable.setColumnWidth("totalBuyPrice", 80);
		reportTable.setColumnWidth("totalSellPrice", 80);
		reportTable.setImmediate(true);
		
		addComponent(listLayout, 0, 0);
		setComponentAlignment(listLayout, Alignment.BOTTOM_CENTER);
		addComponent(reportTable, 0 ,1);
		setComponentAlignment(reportTable, Alignment.BOTTOM_CENTER);
		
		reportSelect.addListener(new Property.ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				ReportType type = null;
				if (event.getProperty().getValue() != null) {
					type = (ReportType) event.getProperty().getValue();
					if (type.equals(ReportType.general)
							|| type.equals(ReportType.brands)
							|| type.equals(ReportType.warehouse)) {
						listButton.setEnabled(true);
						warehouseSelect.setEnabled(false);
						depoLabel.setEnabled(false);
						markaLabel.setEnabled(false);
						brandSelect.setEnabled(false);
						urunLabel.setEnabled(false);
						productSelect.setEnabled(false);
					} else if (type.equals(ReportType.brandsByWarehouse)) {
						listButton.setEnabled(false);
						warehouseSelect.setEnabled(true);
						depoLabel.setEnabled(true);
						markaLabel.setEnabled(false);
						brandSelect.setEnabled(false);
						urunLabel.setEnabled(false);
						productSelect.setEnabled(false);
					} else if (type.equals(ReportType.brandAndProduct)) {
						listButton.setEnabled(false);
						markaLabel.setEnabled(true);
						brandSelect.setEnabled(true);
						urunLabel.setEnabled(false);
						productSelect.setEnabled(false);
					} else if (type.equals(ReportType.warehouseAndBrandAndProduct)) {
						listButton.setEnabled(false);
						warehouseSelect.setEnabled(true);
						depoLabel.setEnabled(true);
						markaLabel.setEnabled(true);
						brandSelect.setEnabled(true);
						urunLabel.setEnabled(false);
						productSelect.setEnabled(false);
					}
				}
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
						urunLabel.setEnabled(true);
						productSelect.setEnabled(true);
						productContainer.removeAllItems();
						productContainer.addAll(products);
						listButton.setEnabled(false);
					} else {
						getWindow().showNotification(brand.getName().trim() + " markası için hiç ürün tanımlanmamış. Ürün Ekranından " + 
													 brand.getName().trim() + " markası için tanımlı olan ürünleri kontrol ediniz.");
					}
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
		
		warehouseSelect.addListener(new Property.ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() != null) {
					ReportType type = null;
					if (reportSelect.getValue() != null) {
						type = (ReportType) reportSelect.getValue();
						if (type.equals(ReportType.brandsByWarehouse)) {
							listButton.setEnabled(true);
						}
					}
				}
			}
		});
		
		listButton.addListener(new Button.ClickListener() {
			@Override
		    public void buttonClick(ClickEvent event) {
				uiReportList = searchAPI.getReportsByWarehouseOrProductOrBrand((Warehouse) warehouseSelect.getValue(), 
																(Product) productSelect.getValue(), 
																(Brand) brandSelect.getValue(), 
																(ReportType) reportSelect.getValue());
				reportTable.setLocale(new Locale("tr"));
				reportTable.setFooterVisible(true);
				ReportType type = null;
				if (reportSelect.getValue() != null) {
					type = (ReportType) reportSelect.getValue();
					if (type.equals(ReportType.warehouse)) {
						reportTable.setWidth("420px");
						reportTable.setVisibleColumns(new String[]{"warehouseName", "totalAmount", "totalBuyPrice", "totalSellPrice"});
						reportTable.setColumnHeaders(new String[]{"DEPO", "MİKTAR", "ALIŞ", "SATIŞ"});
						reportTable.setColumnFooter("warehouseName", "TOPLAM");
						reportTable.setColumnFooter("brandName", "");
						reportTable.setColumnFooter("productName", "");
						reportTable.setFilterFieldVisible("warehouseName", true);
						reportTable.setFilterFieldVisible("brandName", false);
						reportTable.setFilterFieldVisible("productName", false);
						reportTable.setFilterFieldVisible("productBarcode", false);
					} else if (type.equals(ReportType.brandsByWarehouse)) {
						reportTable.setWidth("420px");
						reportTable.setVisibleColumns(new String[]{"brandName", "totalAmount", "totalBuyPrice", "totalSellPrice"});
						reportTable.setColumnHeaders(new String[]{"MARKA", "MİKTAR", "ALIŞ", "SATIŞ"});
						reportTable.setColumnFooter("brandName", "TOPLAM");
						reportTable.setColumnFooter("warehouseName", "");
						reportTable.setColumnFooter("productName", "");
						reportTable.setFilterFieldVisible("warehouseName", false);
						reportTable.setFilterFieldVisible("brandName", true);
						reportTable.setFilterFieldVisible("productName", false);
						reportTable.setFilterFieldVisible("productBarcode", false);
					} else if (type.equals(ReportType.general)
							|| type.equals(ReportType.warehouseAndBrandAndProduct)
							|| type.equals(ReportType.brandAndProduct)) {
						reportTable.setWidth("933px");
						reportTable.setVisibleColumns(new String[]{"warehouseName", "brandName", "productName", "productBarcode", "totalAmount", "totalBuyPrice", "totalSellPrice"});
						reportTable.setColumnHeaders(new String[]{"DEPO", "MARKA", "ÜRÜN", "BARKOD", "MİKTAR", "ALIŞ", "SATIŞ"});
						reportTable.setColumnFooter("productName", "TOPLAM");
						reportTable.setColumnFooter("warehouseName", "");
						reportTable.setColumnFooter("brandName", "");
						reportTable.setFilterFieldVisible("warehouseName", true);
						reportTable.setFilterFieldVisible("brandName", true);
						reportTable.setFilterFieldVisible("productName", true);
						reportTable.setFilterFieldVisible("productBarcode", true);
					} else if (type.equals(ReportType.brands)) {
						reportTable.setWidth("420px");
						reportTable.setVisibleColumns(new String[]{"brandName", "totalAmount", "totalBuyPrice", "totalSellPrice"});
						reportTable.setColumnHeaders(new String[]{"MARKA", "MİKTAR", "ALIŞ", "SATIŞ"});
						reportTable.setColumnFooter("brandName", "TOPLAM");
						reportTable.setColumnFooter("warehouseName", "");
						reportTable.setColumnFooter("productName", "");
						reportTable.setFilterFieldVisible("warehouseName", true);
						reportTable.setFilterFieldVisible("brandName", true);
						reportTable.setFilterFieldVisible("productName", false);
						reportTable.setFilterFieldVisible("productBarcode", false);
					}
				} else {
					// TODO throw exception
				}
				Long totalAmount = 0L;
				BigDecimal totalBuyPrice = BigDecimal.ZERO;
				BigDecimal totalSellPrice = BigDecimal.ZERO;
				for (int i = 0; i < uiReportList.size(); i++) {
					totalAmount = totalAmount + uiReportList.get(i).getTotalAmount();
					totalBuyPrice = totalBuyPrice.add(uiReportList.get(i).getTotalBuyPrice());
					totalSellPrice = totalSellPrice.add(uiReportList.get(i).getTotalSellPrice());
				}
				reportTable.setColumnFooter("totalAmount", totalAmount + "");
				reportTable.setColumnFooter("totalBuyPrice", df.format(totalBuyPrice));
				reportTable.setColumnFooter("totalSellPrice", df.format(totalSellPrice));
				
				reportContainer.removeAllItems();
				reportContainer.addAll(uiReportList);
				
				reportTable.setImmediate(true);
				reportTable.refreshRowCache();
		    }
		});
	}
	
}
