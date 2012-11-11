package com.serlib.dao;

import com.serlib.common.bean.Pagination;
import com.serlib.entity.License;

public interface LicenseDao {
	
	public License saveOrUpdate(License license);
	
	public void delete(License license);
	
	public void removeById(int id);
	
	public void recoverById(int id);
	
	public License getLicenseById(int id);

	public int getLicenseCount(boolean isTrash);
	
	public java.util.List<License> getLicensesAll();
	
	public java.util.List<License> getLicensesByPagination(Pagination pagination, boolean isTrash);

}
