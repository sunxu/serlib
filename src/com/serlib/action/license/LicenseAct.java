package com.serlib.action.license;

import com.serlib.common.bean.Pagination;
import com.serlib.entity.License;

@SuppressWarnings("serial")
public class LicenseAct extends LicenseAbstrct{

	@Override
	public String add() throws Exception{
		if(getLicense() == null){
			setLicense(new License());
			return INPUT;
		}else{
			getLicense().setAddtime(com.serlib.util.Tool.getTimestamp());
			setMessage(getLicenseService().save(getLicense()));
			return SUCCESS;
		}
	}
	
	@Override
	public String edit() throws Exception {
		if(getLicense() != null && !getLicense().getTitle().equals("")){
			setMessage(getLicenseService().update(getLicense()));
			return SUCCESS;
		}
		if(getId() <= 0)
			return ADD;
		setLicense(getLicenseService().getLicenseById(getId()));
		if(getLicense() == null)
			return ADD;
		return EDIT;
	}
	
	@Override
	public String list() throws Exception {
		Pagination pagination = new Pagination();
		pagination.setNow(getPage());
		pagination.setPerPage(4);
		setLicenses(getLicenseService().getLicensesByPagination(pagination, getIsTrash()));
		setPagination(pagination);
		return SUCCESS;
	}
	
	@Override
	public String delete() throws Exception {
		setMessage(getLicenseService().delete(getId()));
		return SUCCESS;
	}
	
	@Override
	public String remove() throws Exception {
		setMessage(getLicenseService().remove(getId()));
		return SUCCESS;
	}
	
	@Override
	public String recover() throws Exception {
		setMessage(getLicenseService().recover(getId()));
		return SUCCESS;
	}
	
	@Override
	public String show() throws Exception {
		setLicense(getLicenseService().getLicenseById(getId()));
		return SUCCESS;
	}
}
