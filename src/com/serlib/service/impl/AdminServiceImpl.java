package com.serlib.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.serlib.entity.Admin;
import com.serlib.service.AdminService;
import com.serlib.service.common.BaseService;
import com.serlib.util.Tool;

public class AdminServiceImpl extends BaseService implements AdminService {

	private static Logger logger = Logger.getLogger(AdminServiceImpl.class);
	
	public boolean checkVerifycode(String verifycode) {
		String veriCode = (String) ActionContext.getContext().getSession().get("veriCode");
		ActionContext.getContext().getSession().remove("veriCode");
		if(veriCode == null || !veriCode.equals(verifycode)){
			return false;
		}
		return true;
	}
	
	public boolean login(String username, String password) {
		Admin admin = getAdminDao().getAdminByUsername(username);
		if(admin != null && admin.getPassword().equals(Tool.getMD5String(password))){
			ActionContext.getContext().getSession().put(Admin.ID,admin.getId());
			ActionContext.getContext().getSession().put(Admin.USERNAME,admin.getUsername());
			ActionContext.getContext().getSession().put(Admin.Admin,admin);
			HttpServletRequest request = ServletActionContext.getRequest();
			ActionContext.getContext().getSession().put("jsessionid", request.getRequestedSessionId());
			logger.info("username:  "+username+"  isLogin:  "+true);
			return true;
		}else{
			logger.info("username:  "+username+"  isLogin:  "+false);
			return false;
		}
	}

	public void logout() {
		ActionContext.getContext().getSession().clear();
		ActionContext.getContext().getParameters().clear();
		HttpServletResponse response = (HttpServletResponse)ActionContext.getContext().get(ServletActionContext.HTTP_RESPONSE);   
		response.setHeader("Expires", "Thu, 19 Nov 1981 08:52:00 GMT");
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Pragma", "no-cache");
	}

}
