package com.serlib.dao;

import java.util.List;

import com.serlib.common.bean.Pagination;
import com.serlib.entity.Jar;

public interface JarDao {

	public Jar saveOrUpdate(Jar jar);

	public void delete(Jar jar);
	
	public void removeById(int id);
	
	public void recoverById(int id);
	
	public Jar getJarById(int id);

	public int getJarCount(boolean isTrash);
	
	public List<Jar> getJarsByHash(String hash);
	
	public java.util.List<Jar> getJarsByPagination(Pagination pagination, boolean isTrash);

	public java.util.List<Jar> getJarsByProjectId(int id);
	
}
