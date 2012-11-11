package com.serlib.action.admin;

import com.opensymphony.xwork2.ActionContext;
import com.serlib.entity.Admin;

@SuppressWarnings("serial")
public class AdminAct extends AdminAbstract {

	public String login() throws Exception{
		if(ActionContext.getContext().getSession().get(Admin.ID) != null){
			return SUCCESS;
		}
		if(getUsername() == null || getUsername().equals(""))
			return INPUT;
		if(!getAdminService().checkVerifycode(getVerifycode())){
			addFieldError("verifycode", "验证码错误！");
			return INPUT;
		}
		if(getAdminService().login(getUsername(), getPassword())){
			return SUCCESS;
		}else{
			addActionError("管理员账号或密码错误！");
			return INPUT;
		}
			
	}
	
	public String logout() throws Exception{
		getAdminService().logout();
		return SUCCESS;
	}
}
