package com.serlib.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.serlib.common.bean.Message;
import com.serlib.common.bean.Pagination;
import com.serlib.common.bean.Url;
import com.serlib.entity.Jar;
import com.serlib.entity.Project;
import com.serlib.service.ProjectService;
import com.serlib.service.common.BaseService;

public class ProjectServiceImpl extends BaseService implements ProjectService {

	@Override
	public Project getProjectById(int id) {
		return getProjectDao().getProjectById(id);
	}

	@Override
	public Message save(Project project) {
		Message message = new Message();
		try{
			project = getProjectDao().saveOrUpdate(project);
			message.setTitle("添加成功");
			List<Url> urls = new ArrayList<Url>();
			urls.add(new Url("修改本文","project-edit.do?id="+project.getId(),Url.SEFT));
			urls.add(new Url("查看本文","project-show.do?id="+project.getId(),Url.BLANK));
			urls.add(new Url("继续添加","project-add.do",Url.SEFT));
			urls.add(new Url("进入列表","project-list.do",Url.SEFT));
			message.setUrls(urls);
		}catch (Exception e) {
			System.out.println(e.getMessage());
			message.setTitle("添加失败！");
			List<Url> urls = new ArrayList<Url>();
			urls.add(new Url("返回","javascript:history.back();",Url.SEFT));
			message.setUrls(urls);
		}
		 return message;
	}

	@Override
	public Message update(Project project) {
		Message message = new Message();
		try{
			project = getProjectDao().saveOrUpdate(project);
			message.setTitle("修改成功");
			List<Url> urls = new ArrayList<Url>();
			urls.add(new Url("查看本文","project-show.do?id="+project.getId(),Url.BLANK));
			urls.add(new Url("再次修改","project-edit.do?id="+project.getId(),Url.SEFT));
			urls.add(new Url("进入列表","project-list.do",Url.SEFT));
			message.setUrls(urls);
		}catch (Exception e) {
			System.out.println(e.getMessage());
			message.setTitle("修改失败！");
			List<Url> urls = new ArrayList<Url>();
			urls.add(new Url("返回","javascript:history.back();",Url.SEFT));
			message.setUrls(urls);
		}
		 return message;
	}

	@Override
	public Message delete(int id) {
		Message message = new Message();
		List<Jar> jars = getJarDao().getJarsByProjectId(id);
		if(jars != null && jars.size() > 0){
			message.setTitle("删除失败！");
			String info = "<table class=\"todelete\"><caption>以下类库属于本项目，请修改或删除。</caption>";
			for (Jar jar : jars) {
				info += "<tr><td><a href=\"jar-show.do?id=" 
					+ jar.getId()+"\" target=\"_blank\">"
					+jar.getName()+"</a></td></tr>";
			}
			info += "</table>";
			message.setInfo(info);
			List<Url> urls = new ArrayList<Url>();
			urls.add(new Url("返回","javascript:history.back();",Url.SEFT));
			message.setUrls(urls);
		}else{
			try{
				getProjectDao().delete(getProjectDao().getProjectById(id));
				message.setTitle("删除成功！");
				List<Url> urls = new ArrayList<Url>();
				urls.add(new Url("进入协议列表","project-list.do",Url.SEFT));
				urls.add(new Url("进入回收站","project-trash.do",Url.SEFT));			
				message.setUrls(urls);
			}catch (Exception e) {
				message.setTitle("删除失败！");
				message.setInfo("可能该项不存在");
				List<Url> urls = new ArrayList<Url>();
				urls.add(new Url("返回列表","project-list.do",Url.SEFT));
				message.setUrls(urls);
			}
		}
		return message;
	}

	@Override
	public Message recover(int id) {
		Message message = new Message();
		getProjectDao().recoverById(id);
		message.setTitle("操作成功");
		List<Url> urls = new ArrayList<Url>();
		urls.add(new Url("进入协议列表","project-list.do",Url.SEFT));
		urls.add(new Url("进入回收站","project-trash.do",Url.SEFT));
		message.setUrls(urls);
		return message;
	}

	@Override
	public Message remove(int id) {
		Message message = new Message();
		getProjectDao().removeById(id);
		message.setTitle("操作成功");
		List<Url> urls = new ArrayList<Url>();
		urls.add(new Url("进入协议列表","project-list.do",Url.SEFT));
		urls.add(new Url("进入回收站","project-trash.do",Url.SEFT));
		message.setUrls(urls);
		return message;		
	}

	@Override
	public List<Project> getProjectsByPagination(Pagination pagination,	boolean isTrash) {
		pagination.setTatolCount(getProjectDao().getProjectCount(isTrash));
		pagination.init();
		return getProjectDao().getProjectsByPagination(pagination,isTrash);
	}

	@Override
	public java.util.List<Project> getProjectsAll(){
		return getProjectDao().getProjectsAll();
	}
}
