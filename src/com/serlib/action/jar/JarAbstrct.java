package com.serlib.action.jar;

import java.util.List;

import com.serlib.action.common.BaseAction;
import com.serlib.entity.Jar;
import com.serlib.entity.Project;

@SuppressWarnings("serial")
public abstract class JarAbstrct extends BaseAction {

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
	
	private Jar jar;
	private List<Jar> jars;
	private List<Project> projects;
	
	public Jar getJar() {
		return jar;
	}
	public void setJar(Jar jar) {
		this.jar = jar;
	}
	public List<Jar> getJars() {
		return jars;
	}
	public void setJars(List<Jar> jars) {
		this.jars = jars;
	}
	public List<Project> getProjects() {
		return projects;
	}
	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
}
