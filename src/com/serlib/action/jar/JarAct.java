package com.serlib.action.jar;

import com.serlib.common.bean.Pagination;

@SuppressWarnings("serial")
public class JarAct extends JarAbstrct {

	@Override
	public String edit() throws Exception {
		if(getJar() != null && !getJar().getName().equals("")){
			setMessage(getJarService().update(getJar()));
			return SUCCESS;
		}
		if(getId() <= 0)
			return ADD;
		setJar(getJarService().getJarById(getId()));
		if(getJar() == null)
			return ADD;
		setProjects(getProjectService().getProjectsAll());
		return EDIT;
	}
	
	@Override
	public String list() throws Exception {
		Pagination pagination = new Pagination();
		pagination.setNow(getPage());
		pagination.setPerPage(4);
		setJars(getJarService().getJarsByPagination(pagination, getIsTrash()));
		setPagination(pagination);
		return SUCCESS;
	}

	@Override
	public String remove() throws Exception {
		setMessage(getJarService().remove(getId()));
		return SUCCESS;
	}
	
	@Override
	public String recover() throws Exception {
		setMessage(getJarService().recover(getId()));
		return SUCCESS;
	}
	
	
	@Override
	public String show() throws Exception {
		setJar(getJarService().getJarById(getId()));
		return SUCCESS;
	}
}
