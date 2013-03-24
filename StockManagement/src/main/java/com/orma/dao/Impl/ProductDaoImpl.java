package com.orma.dao.Impl;

import org.springframework.stereotype.Repository;

import com.orma.dao.ProductDaoI;
import com.orma.domain.Product;

@Repository
public class ProductDaoImpl extends CommonDaoImpl<Product, Long> implements ProductDaoI {


}
