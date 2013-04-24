package com.orma.utils;

public class SqlConstants {
	
	// Spesifik sorgulama
	public static String reportGeneral = "select w.name as wName, "					+
										 "	    b.name as bName, "										+
										 "	    p.name as pName, "										+
										 "	    sum(case "												+
										 "	        when wr.transaction_type = 'girdi' then wr.amount " +
										 "	        else - wr.amount "									+
										 "	    end) as total_amount, "									+
										 "	    sum(case "												+
										 "	        when wr.transaction_type = 'girdi' then wr.amount "	+
										 "	        else - wr.amount "									+
										 "	    end) * p.buy_price as total_buy, "						+
										 "	    sum(case "												+
										 "	        when wr.transaction_type = 'girdi' then wr.amount "	+
										 "	        else - wr.amount "									+
										 "	    end) * sell_price as total_sell "						+
										 "	from "														+
										 "	    warehouse_record wr, "							+
										 "	    warehouse w, "									+
										 "	    product p, "										+
										 "	    brand b "											+
										 "	where wr.product_id = p.id "								+
										 "	  and wr.warehouse_id = w.id "								+
										 "	  and p.brand_id = b.id "									+
										 "	group by wName, bName, pName";
	
	
	// Depo Sorgulama
	public static String reportWarehouses = "   select report.wName,												" +
											"		   sum(report.total_amount) as total_amount,					" +
											"		   sum(report.total_buy) as total_buy,                          " +
											"		   sum(report.total_sell) as total_sell                         " +
											"	from (  select                                                      " +
											"				w.name as wName,                                        " +
											"				b.name as bName,                                        " +
											"				p.name as pName,                                        " +
											"				sum(case                                                " +
											"					when wr.transaction_type = 'girdi' then wr.amount   " +
											"					else - wr.amount                                    " +
											"				end) as total_amount,                                   " +
											"				sum(case                                                " +
											"					when wr.transaction_type = 'girdi' then wr.amount   " +
											"					else - wr.amount                                    " +
											"				end) * p.buy_price as total_buy,                        " +
											"				sum(case												" +
											"					when wr.transaction_type = 'girdi' then wr.amount	" +
											"					else - wr.amount                                    " +
											"				end) * sell_price as total_sell                         " +
											"			from                                                        " +
											"				warehouse_record wr,                              		" +
											"				warehouse w,                                      		" +
											"				product p,                                        		" +
											"				brand b                                           		" +
											"			where                                                       " +
											"				wr.product_id = p.id                                    " +
											"					and wr.warehouse_id = w.id                          " +
											"					and p.brand_id = b.id                               " +
											"			group by wName , bName , pName) report                      " +
											"	group by report.wName";            
	
	// Depoları Marka Bazında sorgulama
	public static String reportBrands =				"   select report.bName,												" +
													"		   sum(report.total_amount) as total_amount,					" +
													"		   sum(report.total_buy) as total_buy,                          " +
													"		   sum(report.total_sell) as total_sell                         " +
													"	from (  select                                                      " +
													"				w.name as wName,                                        " +
													"				b.name as bName,                                        " +
													"				p.name as pName,                                        " +
													"				sum(case                                                " +
													"					when wr.transaction_type = 'girdi' then wr.amount   " +
													"					else - wr.amount                                    " +
													"				end) as total_amount,                                   " +
													"				sum(case                                                " +
													"					when wr.transaction_type = 'girdi' then wr.amount   " +
													"					else - wr.amount                                    " +
													"				end) * p.buy_price as total_buy,                        " +
													"				sum(case												" +
													"					when wr.transaction_type = 'girdi' then wr.amount	" +
													"					else - wr.amount                                    " +
													"				end) * sell_price as total_sell                         " +
													"			from                                                        " +
													"				warehouse_record wr,                              " +
													"				warehouse w,                                      " +
													"				product p,                                        " +
													"				brand b                                           " +
													"			where                                                       " +
													"				wr.product_id = p.id                                    " +
													"					and wr.warehouse_id = w.id                          " +
													"					and p.brand_id = b.id                               " +
													"			group by wName , bName , pName) report                      " +
													"	group by report.bName";            
	// Markaya Gore sorgulama
	public static String reportBrandsByWarehouse =  "   select report.bName,												" +
													"		   sum(report.total_amount) as total_amount,					" +
													"		   sum(report.total_buy) as total_buy,                          " +
													"		   sum(report.total_sell) as total_sell                         " +
													"	from (  select                                                      " +
													"				w.name as wName,                                        " +
													"				w.id as wId,                                        	" +
													"				b.name as bName,                                        " +
													"				p.name as pName,                                        " +
													"				sum(case                                                " +
													"					when wr.transaction_type = 'girdi' then wr.amount   " +
													"					else - wr.amount                                    " +
													"				end) as total_amount,                                   " +
													"				sum(case                                                " +
													"					when wr.transaction_type = 'girdi' then wr.amount   " +
													"					else - wr.amount                                    " +
													"				end) * p.buy_price as total_buy,                        " +
													"				sum(case												" +
													"					when wr.transaction_type = 'girdi' then wr.amount	" +
													"					else - wr.amount                                    " +
													"				end) * sell_price as total_sell                         " +
													"			from                                                        " +
													"				warehouse_record wr,                              " +
													"				warehouse w,                                      " +
													"				product p,                                        " +
													"				brand b                                           " +
													"			where                                                       " +
													"				wr.product_id = p.id                                    " +
													"					and wr.warehouse_id = w.id                          " +
													"					and p.brand_id = b.id                               " +
													"			group by wName , wId, bName , pName) report                 " +
													"   where report.wId = ? 												" +
													"	group by report.bName";
	// Ürün genel
	public static String reportByBrandAndProduct=   "   select report.wName,												" +
													"		   report.bName,												" +
													"		   report.pName,												" +
													"		   sum(report.total_amount) as total_amount,					" +
													"		   sum(report.total_buy) as total_buy,                          " +
													"		   sum(report.total_sell) as total_sell                         " +
													"	from (  select                                                      " +
													"				w.name as wName,                                        " +
													"				w.id as wId,                                        	" +
													"				b.name as bName,                                        " +
													"				b.id as bId,                                        	" +
													"				p.name as pName,                                        " +
													"				p.id as pId,                                        	" +
													"				sum(case                                                " +
													"					when wr.transaction_type = 'girdi' then wr.amount   " +
													"					else - wr.amount                                    " +
													"				end) as total_amount,                                   " +
													"				sum(case                                                " +
													"					when wr.transaction_type = 'girdi' then wr.amount   " +
													"					else - wr.amount                                    " +
													"				end) * p.buy_price as total_buy,                        " +
													"				sum(case												" +
													"					when wr.transaction_type = 'girdi' then wr.amount	" +
													"					else - wr.amount                                    " +
													"				end) * sell_price as total_sell                         " +
													"			from                                                        " +
													"				warehouse_record wr,                              " +
													"				warehouse w,                                      " +
													"				product p,                                        " +
													"				brand b                                           " +
													"			where                                                       " +
													"				wr.product_id = p.id                                    " +
													"					and wr.warehouse_id = w.id                          " +
													"					and p.brand_id = b.id                               " +
													"			group by wName, wId, bName, bId, pName, pId) report         " +
													" 	where report.bId=?													" +
													" 	  and report.pId=?													" +
													"	group by report.wName, report.bName, report.pName";

	public static String reportByWarehouseAndBrandAndProduct=   "   select report.wName,												" +
																"		   report.bName,												" +
																"		   report.pName,												" +
																"		   sum(report.total_amount) as total_amount,					" +
																"		   sum(report.total_buy) as total_buy,                          " +
																"		   sum(report.total_sell) as total_sell                         " +
																"	from (  select                                                      " +
																"				w.name as wName,                                        " +
																"				w.id as wId,                                        	" +
																"				b.name as bName,                                        " +
																"				b.id as bId,                                        	" +
																"				p.name as pName,                                        " +
																"				p.id as pId,                                        	" +
																"				sum(case                                                " +
																"					when wr.transaction_type = 'girdi' then wr.amount   " +
																"					else - wr.amount                                    " +
																"				end) as total_amount,                                   " +
																"				sum(case                                                " +
																"					when wr.transaction_type = 'girdi' then wr.amount   " +
																"					else - wr.amount                                    " +
																"				end) * p.buy_price as total_buy,                        " +
																"				sum(case												" +
																"					when wr.transaction_type = 'girdi' then wr.amount	" +
																"					else - wr.amount                                    " +
																"				end) * sell_price as total_sell                         " +
																"			from                                                        " +
																"				warehouse_record wr,                              " +
																"				warehouse w,                                      " +
																"				product p,                                        " +
																"				brand b                                           " +
																"			where                                                       " +
																"				wr.product_id = p.id                                    " +
																"					and wr.warehouse_id = w.id                          " +
																"					and p.brand_id = b.id                               " +
																"			group by wName, wId, bName, bId, pName, pId) report         " +
																" 	where report.wId=?													" +
																" 	  and report.bId=?													" +
																" 	  and report.pId=?													" +
																"	group by report.wName, report.bName, report.pName";
}
