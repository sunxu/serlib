package com.serlib.service;

import java.util.List;
import java.util.Map;

import com.serlib.common.bean.Message;
import com.serlib.common.bean.Pagination;
import com.serlib.entity.Jar;

public interface JarService {

	public Jar getJarById(int id);
	
	public Message save(Jar jar);

	public Message update(Jar jar);

	public Message remove(int id);
	
	public Message recover(int id);
	
	public Message delete(int id);
	
	public Message extract();
	
	public Message generate(Map<String, Object> session);
	
	public List<Jar> getJarsByHash(String hash);
	
	public java.util.List<Jar> getJarsByPagination(Pagination pagination, boolean isTrash);
	
}
