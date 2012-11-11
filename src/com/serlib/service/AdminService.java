package com.serlib.service;

public interface AdminService{
	
	public boolean checkVerifycode(String verifycode);
	
	public boolean login(String username, String password);
	
	public void logout();
	
}
