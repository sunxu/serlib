package com.serlib.common.interceptor;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.serlib.util.Tool;

@SuppressWarnings("serial")
public class JarDealInterceptor extends AbstractInterceptor {

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		
		String name = invocation.getInvocationContext().getName();
		
		if(name.equals("jar-delete"))
			return invocation.invoke();
		
		Object object = ActionContext.getContext().getSession().get(com.serlib.util.Tool.JAR_FILE_STATUS);
		
		if(object == null){
			ActionContext.getContext().getSession().put(Tool.JAR_FILE_STATUS, "none");
			if(name.equals("jar-upload"))
				return invocation.invoke();
			return "upload";
		}
		
		String status = object.toString();
		System.out.println(status);
		System.out.println(name);
		
		if(status.equals("none")){
			if(name.equals("jar-upload")){
				return invocation.invoke();
			}else{
				return "upload";
			}
		}
		
		List<String> list = new ArrayList<String>();
		
		list.add("add");
		list.add("extract");
		list.add("generate");
		list.add("index");
		
		String pre = "upload";
		for(String string : list){
			if(status.equals(pre+"ed"))
				if(name.equals("jar-"+string)){
					return invocation.invoke();
				}else{
					return string;
				}
			pre = string;
		}
		
		if(status.equals("indexed")){
			ActionContext.getContext().getSession().put("jarFileStatus","none");
			return "upload";
		}

		return invocation.invoke();
	}

}
