package com.orma.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface CommonDaoI<T, ID extends Serializable> {
	
	public Class<T> getEntityClass();

    public T findById(final ID id);

    public List<T> findAll();

    public List<T> findByExample(final T exampleInstance);

    public List<T> findByNamedQuery(
        final String queryName,
        Object... params
    );

    public List<T> findByNamedQueryAndNamedParams(
        final String queryName,
        final Map<String, ?extends Object> params
    );

    public int countAll();

    public int countByExample(final T exampleInstance);

    public void merge(final T entity);
    
    public void persist(T entity);

    public void delete(final T entity);
}
