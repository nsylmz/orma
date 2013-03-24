package com.orma.dao.Impl;

import org.springframework.stereotype.Repository;

import com.orma.dao.CompanyDaoI;
import com.orma.domain.Company;

@Repository
public class CompanyDaoImpl extends CommonDaoImpl<Company, Long> implements CompanyDaoI {


}
