package com.serlib.dao.common;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.serlib.common.bean.Pagination;

public class BaseDao extends HibernateDaoSupport {

	@SuppressWarnings("unchecked")
	public List getListByPagination(final String hql, final Pagination pagination) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {   
			public Object doInHibernate(Session session) throws HibernateException,   
			SQLException {
				return session.createQuery(hql).setFirstResult((pagination.getNow()-1)*pagination.getPerPage())   
				.setMaxResults(pagination.getPerPage())   
				.list();   
			}   
		});
	}
}
