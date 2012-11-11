package com.serlib.common;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class NullAct extends ActionSupport {

	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}
}
