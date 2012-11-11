package com.serlib.service;

import com.serlib.common.bean.Message;
import com.serlib.common.bean.Pagination;
import com.serlib.entity.License;

public interface LicenseService {
	
	public License getLicenseById(int id);
	
	public Message save(License license);

	public Message update(License license);
	
	public Message remove(int id);
	
	public Message recover(int id);
	
	public Message delete(int id);
	
	public java.util.List<License> getLicensesAll();
	
	public java.util.List<License> getLicensesByPagination(Pagination pagination, boolean isTrash);
	
}
