package com.serlib.action.license;

import java.util.List;

import com.serlib.action.common.BaseAction;
import com.serlib.entity.License;

@SuppressWarnings("serial")
public abstract class LicenseAbstrct extends BaseAction {

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
	
	private License license;
	private List<License> licenses;
	
	public License getLicense() {
		return license;
	}
	public void setLicense(License license) {
		this.license = license;
	}
	public List<License> getLicenses() {
		return licenses;
	}
	public void setLicenses(List<License> licenses) {
		this.licenses = licenses;
	}
}
