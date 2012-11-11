package com.serlib.dao;

import com.serlib.common.bean.Pagination;
import com.serlib.entity.Project;

public interface ProjectDao {
	
	public Project saveOrUpdate(Project project);

	public void delete(Project project);
	
	public void removeById(int id);
	
	public void recoverById(int id);
	
	public Project getProjectById(int id);

	public int getProjectCount(boolean isTrash);
	
	public java.util.List<Project> getProjectsByPagination(Pagination pagination, boolean isTrash);

	public java.util.List<Project> getProjectsByLicenseId(int id);
	
	public java.util.List<Project> getProjectsAll();

}
