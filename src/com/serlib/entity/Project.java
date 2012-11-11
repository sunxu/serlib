package com.serlib.entity;

/**
 * Project entity. @author MyEclipse Persistence Tools
 */

public class Project implements java.io.Serializable {

	private static final long serialVersionUID = -8235418176617142247L;
	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String HOME = "home";
	public static final String COMPANY = "company";
	public static final String DETAIL = "detail";
	public static final String LICENSE = "license";
	public static final String LICENSEID = "licenseId";
	public static final String ADDTIME = "addtime";
	public static final String DELTIME = "deltime";
	public static final String STATUS = "status";
	
	// Fields

	private Integer id;
	private String name;
	private String home;
	private String company;
	private String detail;
	private String license;
	private Integer licenseId;
	private Integer addtime;
	private Integer deltime;
	private Boolean status;

	// Constructors

	/** default constructor */
	public Project() {
	}

	/** minimal constructor */
	public Project(String name, String home, Integer addtime, Boolean status) {
		this.name = name;
		this.home = home;
		this.addtime = addtime;
		this.status = status;
	}

	/** full constructor */
	public Project(String name, String home, String company, String detail,
			String license, Integer licenseId, Integer addtime,
			Integer deltime, Boolean status) {
		this.name = name;
		this.home = home;
		this.company = company;
		this.detail = detail;
		this.license = license;
		this.licenseId = licenseId;
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHome() {
		return this.home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDetail() {
		return this.detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getLicense() {
		return this.license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public Integer getLicenseId() {
		return this.licenseId;
	}

	public void setLicenseId(Integer licenseId) {
		this.licenseId = licenseId;
	}

	public Integer getAddtime() {
		return this.addtime;
	}

	public void setAddtime(Integer addtime) {
		this.addtime = addtime;
	}

	public Integer getDeltime() {
		return this.deltime;
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