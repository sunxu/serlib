package com.serlib.entity;

/**
 * Jar entity. @author MyEclipse Persistence Tools
 */

public class Jar implements java.io.Serializable {

	private static final long serialVersionUID = -8134089498618086464L;
	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String SIZE = "size";
	public static final String PROJECT = "project";
	public static final String PROJECTID = "projectId";
	public static final String BUILDTIME = "buildtime";
	public static final String HASH = "hash";
	public static final String ADDTIME = "addtime";
	public static final String DELTIME = "deltime";
	public static final String STATUS = "status";
	
	// Fields

	private Integer id;
	private String name;
	private Integer size;
	private String project;
	private Integer projectId;
	private Integer buildtime;
	private String hash;
	private Integer addtime;
	private Integer deltime;
	private Boolean status;

	// Constructors

	/** default constructor */
	public Jar() {
	}

	/** minimal constructor */
	public Jar(String name, Integer size, String project, Integer projectId,
			Integer buildtime, String hash, Integer addtime, Boolean status) {
		this.name = name;
		this.size = size;
		this.project = project;
		this.projectId = projectId;
		this.buildtime = buildtime;
		this.hash = hash;
		this.addtime = addtime;
		this.status = status;
	}

	/** full constructor */
	public Jar(String name, Integer size, String project, Integer projectId,
			Integer buildtime, String hash, Integer addtime, Integer deltime,
			Boolean status) {
		this.name = name;
		this.size = size;
		this.project = project;
		this.projectId = projectId;
		this.buildtime = buildtime;
		this.hash = hash;
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

	public Integer getSize() {
		return this.size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getProject() {
		return this.project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public Integer getProjectId() {
		return this.projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public Integer getBuildtime() {
		return this.buildtime;
	}

	public void setBuildtime(Integer buildtime) {
		this.buildtime = buildtime;
	}

	public String getHash() {
		return this.hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
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