package com.serlib.dao.impl;

import java.util.List;

import com.serlib.common.bean.Pagination;
import com.serlib.dao.ProjectDao;
import com.serlib.dao.common.BaseDao;
import com.serlib.entity.Project;

public class ProjectDaoImpl extends BaseDao implements ProjectDao {

	@Override
	public Project getProjectById(int id) {
		return (Project) this.getHibernateTemplate().get(Project.class, id);
	}

	@Override
	public Project saveOrUpdate(Project project) {
		this.getHibernateTemplate().saveOrUpdate(project);
		return project;
	}

	@Override
	public void delete(Project project) {
		this.getHibernateTemplate().delete(project);
	}

	@Override
	public void recoverById(int id) {
		Project project = getProjectById(id);
		if(project != null){
			project.setStatus(true);
			saveOrUpdate(project);
		}
	}

	@Override
	public void removeById(int id) {
		Project project = getProjectById(id);
		if(project != null){
			project.setStatus(false);
			project.setDeltime(com.serlib.util.Tool.getTimestamp());
			saveOrUpdate(project);	
		}
	}

	@Override
	public int getProjectCount(boolean isTrash) {
		String hql;
		if(isTrash)
			hql = "select count(*) from Project project where project.status = 0 ";
		else
			hql = "select count(*) from Project project where project.status = 1 ";
		return ((Long) this.getHibernateTemplate().find(hql).listIterator().next()).intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Project> getProjectsByPagination(Pagination pagination, boolean isTrash) {
		String hql;
		if(isTrash)
			hql = "from Project project where project.status = 0 order by project.id DESC";
		else
			hql = "from Project project where project.status = 1 order by project.id DESC";
		return getListByPagination(hql, pagination);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Project> getProjectsByLicenseId(int id) {
		String hql = "from Project project where project.licenseId = " + id;
		return this.getHibernateTemplate().find(hql);
	}

	@SuppressWarnings("unchecked")
	@Override
	public java.util.List<Project> getProjectsAll(){
		return this.getHibernateTemplate().find("from Project ");
	}
}
