package com.serlib.action.project;

import java.util.List;

import com.serlib.action.common.BaseAction;
import com.serlib.entity.License;
import com.serlib.entity.Project;

@SuppressWarnings("serial")
public abstract class ProjectAbstract extends BaseAction {
	public final String execute() throws Exception {
		return SUCCESS;
	}
	
	public String add() throws Exception{
		return SUCCESS;
	}
	
	public String edit() throws Exception{
		return SUCCESS;
	}
	
	public String show() throws Exception{
		return SUCCESS;
	}
	
	public String list() throws Exception{
		return SUCCESS;
	}
	
	public String remove() throws Exception{
		return SUCCESS;
	}
	
	public String recover() throws Exception{
		return SUCCESS;
	}

	public String delete() throws Exception{
		return SUCCESS;
	}
	
	private Project project;
	private List<Project> projects;
	private List<License> licenses;
	
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public List<License> getLicenses() {
		return licenses;
	}

	public void setLicenses(List<License> licenses) {
		this.licenses = licenses;
	}
	
}
