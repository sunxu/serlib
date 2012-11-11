package com.serlib.action.project;

import com.serlib.common.bean.Pagination;
import com.serlib.entity.Project;

@SuppressWarnings("serial")
public class ProjectAct extends ProjectAbstract {

	@Override
	public String add() throws Exception{
		if(getProject() == null){
			setProject(new Project());
			setLicenses(getLicenseService().getLicensesAll());
			return INPUT;
		}else{
			getProject().setAddtime(com.serlib.util.Tool.getTimestamp());
			setMessage(getProjectService().save(getProject()));
			return SUCCESS;
		}
	}
	
	@Override
	public String edit() throws Exception {
		if(getProject() != null && !getProject().getName().equals("")){
			setMessage(getProjectService().update(getProject()));
			return SUCCESS;
		}
		if(getId() <= 0)
			return ADD;
		setProject(getProjectService().getProjectById(getId()));
		if(getProject() == null)
			return ADD;
		setLicenses(getLicenseService().getLicensesAll());
		return EDIT;
	}
	
	@Override
	public String list() throws Exception {
		Pagination pagination = new Pagination();
		pagination.setNow(getPage());
		pagination.setPerPage(4);
		setProjects(getProjectService().getProjectsByPagination(pagination, getIsTrash()));
		setPagination(pagination);
		return SUCCESS;
	}

	@Override
	public String delete() throws Exception {
		setMessage(getProjectService().delete(getId()));
		return SUCCESS;
	}
	
	@Override
	public String remove() throws Exception {
		setMessage(getProjectService().remove(getId()));
		return SUCCESS;
	}
	
	@Override
	public String recover() throws Exception {
		setMessage(getProjectService().recover(getId()));
		return SUCCESS;
	}
	
	@Override
	public String show() throws Exception {
		setProject(getProjectService().getProjectById(getId()));
		return SUCCESS;
	}
}
