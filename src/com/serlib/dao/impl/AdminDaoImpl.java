package com.serlib.dao.impl;

import java.util.List;

import com.serlib.dao.AdminDao;
import com.serlib.dao.common.BaseDao;
import com.serlib.entity.Admin;

public class AdminDaoImpl extends BaseDao implements AdminDao {

	public Admin getAdminById(int id) {
		return (Admin) this.getHibernateTemplate().get(Admin.class, id);
	}

	@SuppressWarnings("unchecked")
	public Admin getAdminByUsername(String username) {
		String hql = "from Admin admin where admin.username = '"+username+"'";
		List<Admin> list = this.getHibernateTemplate().find(hql);
		if(list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	public void updateLoginInfo(Admin admin) {
		this.getHibernateTemplate().update(admin);
	}


}
