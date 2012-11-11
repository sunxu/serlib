package com.serlib.action.admin;

import com.serlib.action.common.BaseAction;
import com.serlib.entity.Admin;

@SuppressWarnings("serial")
public abstract class AdminAbstract extends BaseAction{

	public final String execute() throws Exception {
		return SUCCESS;
	}
	
	private String username;
	private String password;
	private String verifycode;
	private Admin admin;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getVerifycode() {
		return verifycode;
	}
	public void setVerifycode(String verifycode) {
		this.verifycode = verifycode;
	}
	public Admin getAdmin() {
		return admin;
	}
	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
	
}
