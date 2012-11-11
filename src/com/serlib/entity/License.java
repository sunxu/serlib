package com.serlib.entity;

/**
 * License entity. @author MyEclipse Persistence Tools
 */

public class License implements java.io.Serializable {

	private static final long serialVersionUID = -8747351332754613629L;
	public static final String ID = "id";
	public static final String TITLE = "title";
	public static final String MESSAGE = "message";
	public static final String URL = "url";
	public static final String ADDTIME = "addtime";
	public static final String DELTIME = "deltime";
	public static final String STATUS = "status";

	// Fields

	private Integer id;
	private String title;
	private String message;
	private String url;
	private Integer addtime;
	private Integer deltime;
	private Boolean status;

	// Constructors

	/** default constructor */
	public License() {
	}

	/** minimal constructor */
	public License(String title, String message, Integer addtime, Boolean status) {
		this.title = title;
		this.message = message;
		this.addtime = addtime;
		this.status = status;
	}

	/** full constructor */
	public License(String title, String message, String url, Integer addtime, Integer deltime, Boolean status) {
		this.title = title;
		this.message = message;
		this.url = url;
		this.addtime = addtime;
		this.deltime = deltime;
		this.status = status;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getAddtime() {
		return addtime;
	}

	public void setAddtime(Integer addtime) {
		this.addtime = addtime;
	}

	public Integer getDeltime() {
		return deltime;
	}

	public void setDeltime(Integer deltime) {
		this.deltime = deltime;
	}

	public Boolean getStatus() {
		return this.status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

}