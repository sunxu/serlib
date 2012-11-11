package com.serlib.entity;

/**
 * Admin entity. @author MyEclipse Persistence Tools
 */

public class Admin implements java.io.Serializable {

	private static final long serialVersionUID = -5212606263368841081L;
	public static final String Admin = "admin";
	public static final String ID = "adminid";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String LASTLOGONDATE = "lastlogondate";
	public static final String LASTLOGONIP = "lastlogonip";
	
	// Fields

	private Integer id;
	private String username;
	private String password;
	private Integer lastlogondate;
	private String lastlogonip;

	// Constructors

	/** default constructor */
	public Admin() {
	}

	/** minimal constructor */
	public Admin(String username, String password) {
		this.username = username;
		this.password = password;
	}

	/** full constructor */
	public Admin(String username, String password, Integer lastlogondate,
			String lastlogonip) {
		this.username = username;
		this.password = password;
		this.lastlogondate = lastlogondate;
		this.lastlogonip = lastlogonip;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getLastlogondate() {
		return this.lastlogondate;
	}

	public void setLastlogondate(Integer lastlogondate) {
		this.lastlogondate = lastlogondate;
	}

	public String getLastlogonip() {
		return this.lastlogonip;
	}

	public void setLastlogonip(String lastlogonip) {
		this.lastlogonip = lastlogonip;
	}

}