package com.serlib.service;

import com.serlib.common.bean.Message;
import com.serlib.common.bean.Pagination;
import com.serlib.entity.Project;

public interface ProjectService {
	
	public Project getProjectById(int id);
	
	public Message save(Project project);

	public Message update(Project project);

	public Message remove(int id);
	
	public Message recover(int id);
	
	public Message delete(int id);
	
	public java.util.List<Project> getProjectsByPagination(Pagination pagination, boolean isTrash);
	
	public java.util.List<Project> getProjectsAll();
	
}
