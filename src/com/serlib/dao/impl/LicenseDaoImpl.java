package com.serlib.dao.impl;

import java.util.List;

import com.serlib.common.bean.Pagination;
import com.serlib.dao.LicenseDao;
import com.serlib.dao.common.BaseDao;
import com.serlib.entity.License;

public class LicenseDaoImpl extends BaseDao implements LicenseDao {

	@Override
	public License getLicenseById(int id) {
		return (License) this.getHibernateTemplate().get(License.class, id);
	}

	@Override
	public License saveOrUpdate(License license) {
		this.getHibernateTemplate().saveOrUpdate(license);
		return license;
	}

	@Override
	public void delete(License license) {
		this.getHibernateTemplate().delete(license);
	}

	@Override
	public void recoverById(int id) {
		License license = getLicenseById(id);
		if(license != null){
			license.setStatus(true);
			saveOrUpdate(license);
		}
	}

	@Override
	public void removeById(int id) {
		License license = getLicenseById(id);
		if(license != null){
			license.setStatus(false);
			license.setDeltime(com.serlib.util.Tool.getTimestamp());
			saveOrUpdate(license);	
		}
	}
	
	@Override
	public int getLicenseCount(boolean isTrash) {
		String hql;
		if(isTrash)
			hql = "select count(*) from License license where license.status = 0 ";
		else
			hql = "select count(*) from License license where license.status = 1 ";
		return ((Long) this.getHibernateTemplate().find(hql).listIterator().next()).intValue();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public java.util.List<License> getLicensesAll(){
		return this.getHibernateTemplate().find("from License");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<License> getLicensesByPagination(Pagination pagination, boolean isTrash) {
		String hql;
		if(isTrash)
			hql = "from License license where license.status = 0 order by license.id DESC";
		else
			hql = "from License license where license.status = 1 order by license.id DESC";
		return getListByPagination(hql, pagination);
	}
}
	
