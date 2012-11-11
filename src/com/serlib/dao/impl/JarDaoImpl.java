package com.serlib.dao.impl;

import java.util.List;

import com.serlib.common.bean.Pagination;
import com.serlib.dao.JarDao;
import com.serlib.dao.common.BaseDao;
import com.serlib.entity.Jar;

public class JarDaoImpl extends BaseDao implements JarDao {

	@Override
	public Jar getJarById(int id) {
		return (Jar) this.getHibernateTemplate().get(Jar.class, id);
	}

	@Override
	public Jar saveOrUpdate(Jar jar) {
		this.getHibernateTemplate().saveOrUpdate(jar);
		return jar;
	}

	@Override
	public void delete(Jar jar) {
		this.getHibernateTemplate().delete(jar);
	}

	@Override
	public void recoverById(int id) {
		Jar jar = getJarById(id);
		if(jar != null){
			jar.setStatus(true);
			saveOrUpdate(jar);
		}
	}

	@Override
	public void removeById(int id) {
		Jar jar = getJarById(id);
		if(jar != null){
			jar.setStatus(false);
			jar.setDeltime(com.serlib.util.Tool.getTimestamp());
			saveOrUpdate(jar);	
		}
	}

	@Override
	public int getJarCount(boolean isTrash) {
		String hql;
		if(isTrash)
			hql = "select count(*) from Jar jar where jar.status = 0 ";
		else
			hql = "select count(*) from Jar jar where jar.status = 1 ";
		return ((Long) this.getHibernateTemplate().find(hql).listIterator().next()).intValue();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Jar> getJarsByHash(String hash){
		String hql = "from Jar jar where jar.hash = '" + hash + "'";
		return this.getHibernateTemplate().find(hql);
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Jar> getJarsByPagination(Pagination pagination, boolean isTrash) {
		String hql;
		if(isTrash)
			hql = "from Jar jar where jar.status = 0 order by jar.id DESC";
		else
			hql = "from Jar jar where jar.status = 1 order by jar.id DESC";
		return getListByPagination(hql, pagination);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Jar> getJarsByProjectId(int id) {
		String hql = "from Jar jar where jar.projectId = " + id;
		return this.getHibernateTemplate().find(hql);
	}
}
