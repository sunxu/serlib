package com.serlib.common.interceptor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.serlib.entity.Admin;

@SuppressWarnings("serial")
public class LoginInterceptor extends AbstractInterceptor {

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		if(ActionContext.getContext().getSession().get(Admin.ID) == null){
			return "login";
		}
		return invocation.invoke();
	}

}
