package com.serlib.action.common;

import com.opensymphony.xwork2.ActionSupport;
import com.serlib.common.bean.Message;
import com.serlib.common.bean.Pagination;
import com.serlib.service.AdminService;
import com.serlib.service.JarService;
import com.serlib.service.LicenseService;
import com.serlib.service.ProjectService;
import com.serlib.solr.service.SolrService;

@SuppressWarnings("serial")
public class BaseAction extends ActionSupport {

	public static final String ADD = "add";
	public static final String EDIT = "edit";
	public static final String FAILED = "failed";
	public static final String LIST = "list";
	
	public static final int NONE = 0;
	public static final int METHOD = 1;
	public static final int CLASS = 2;
	public static final int JAR = 3;
	
	private AdminService adminService;	
	private LicenseService licenseService;
	private ProjectService projectService;
	private JarService jarService;
	private SolrService solrService;
	
	private Message message;
	private Pagination pagination;
	private boolean isAjax;
	private boolean isTrash;
	private int id;
	
	private int page;

	public AdminService getAdminService() {
		return adminService;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	public LicenseService getLicenseService() {
		return licenseService;
	}

	public void setLicenseService(LicenseService licenseService) {
		this.licenseService = licenseService;
	}

	public ProjectService getProjectService() {
		return projectService;
	}

	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}

	public JarService getJarService() {
		return jarService;
	}

	public void setJarService(JarService jarService) {
		this.jarService = jarService;
	}

	public SolrService getSolrService() {
		return solrService;
	}

	public void setSolrService(SolrService solrService) {
		this.solrService = solrService;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public boolean getIsAjax() {
		return isAjax;
	}

	public void setIsAjax(boolean isAjax) {
		this.isAjax = isAjax;
	}

	public boolean getIsTrash() {
		return isTrash;
	}

	public void setIsTrash(boolean isTrash) {
		this.isTrash = isTrash;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}
	
}
