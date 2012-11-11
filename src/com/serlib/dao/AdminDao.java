package com.serlib.dao;

import com.serlib.entity.Admin;

public interface AdminDao {

	public Admin getAdminByUsername(String username);
	
	public Admin getAdminById(int id);
	
	public void updateLoginInfo(Admin admin);
	
}
