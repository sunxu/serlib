package com.serlib.common.views;

import javax.servlet.ServletContext;

import org.apache.struts2.views.freemarker.FreemarkerManager;
import org.apache.struts2.views.freemarker.StrutsClassTemplateLoader;

import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.cache.WebappTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;

public class SerlibFreemarkerManager extends FreemarkerManager {
	protected TemplateLoader createTemplateLoader(
			ServletContext servletContext, String templatePath) {
		return new MultiTemplateLoader(new TemplateLoader[] {
				new WebappTemplateLoader(servletContext, "WEB-INF"
						+ System.getProperty("file.separator") + "template"),
				new StrutsClassTemplateLoader() });
	}
	
    protected Configuration createConfiguration(ServletContext servletContext) throws TemplateException {
    	 Configuration configuration = super.createConfiguration(servletContext);  
    	 configuration.setSharedVariable("datetime", new DatetimeMethod()); // 加入时间日期处理  
    	 return configuration; 
    }
}
