package com.serlib.service.common;

import com.serlib.dao.AdminDao;
import com.serlib.dao.JarDao;
import com.serlib.dao.LicenseDao;
import com.serlib.dao.ProjectDao;

public abstract class BaseService {

	private AdminDao adminDao;
	private JarDao jarDao;
	private LicenseDao licenseDao;
	private ProjectDao projectDao;
	
	public AdminDao getAdminDao() {
		return adminDao;
	}
	public void setAdminDao(AdminDao adminDao) {
		this.adminDao = adminDao;
	}
	public JarDao getJarDao() {
		return jarDao;
	}
	public void setJarDao(JarDao jarDao) {
		this.jarDao = jarDao;
	}
	public LicenseDao getLicenseDao() {
		return licenseDao;
	}
	public void setLicenseDao(LicenseDao licenseDao) {
		this.licenseDao = licenseDao;
	}
	public ProjectDao getProjectDao() {
		return projectDao;
	}
	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}
	
}
