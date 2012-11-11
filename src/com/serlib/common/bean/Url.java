package com.serlib.common.bean;

public class Url {

	public static final String BLANK = "_blank";
	public static final String PARENT = "_parent";
	public static final String SEFT = "_self";
	public static final String TOP = "_top";
	
	private String title;
	private String href;
	private String target;
	
	public Url( String title, String href, String target) {
		this.title = title;
		this.href = href;
		this.target = target;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
}
