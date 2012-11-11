package com.serlib.common.views;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

public class DatetimeMethod implements TemplateMethodModel{

	private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";  
	
	@SuppressWarnings("unchecked")
	@Override
	public Object exec(List args) throws TemplateModelException {
		try {  
			int timestap = Integer.parseInt(args.get(0).toString());  
			Date date = new Date(timestap*1000L);  
			return new SimpleDateFormat(DEFAULT_PATTERN).format(date);  
		} catch (RuntimeException e) {  
			return new SimpleDateFormat(DEFAULT_PATTERN).format(new java.util.Date());  
		}  
	}

}
