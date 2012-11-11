package com.serlib.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.serlib.common.bean.Message;
import com.serlib.common.bean.Pagination;
import com.serlib.common.bean.Url;
import com.serlib.entity.License;
import com.serlib.entity.Project;
import com.serlib.service.LicenseService;
import com.serlib.service.common.BaseService;

public class LicenseServiceImpl extends BaseService implements LicenseService {

	@Override
	public License getLicenseById(int id) {
		return getLicenseDao().getLicenseById(id);
	}

	@Override
	public Message save(License license) {
		Message message = new Message();
		try{
			license = getLicenseDao().saveOrUpdate(license);
			message.setTitle("添加成功");
			List<Url> urls = new ArrayList<Url>();
			urls.add(new Url("修改本文","license-edit.do?id="+license.getId(),Url.SEFT));
			urls.add(new Url("查看本文","license-show.do?id="+license.getId(),Url.BLANK));
			urls.add(new Url("继续添加","license-add.do",Url.SEFT));
			urls.add(new Url("进入列表","license-list.do",Url.SEFT));
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
	public Message update(License license) {
		Message message = new Message();
		try{
			license = getLicenseDao().saveOrUpdate(license);
			message.setTitle("修改成功");
			List<Url> urls = new ArrayList<Url>();
			urls.add(new Url("查看本文","license-show.do?id="+license.getId(),Url.BLANK));
			urls.add(new Url("再次修改","license-edit.do?id="+license.getId(),Url.SEFT));
			urls.add(new Url("进入列表","license-list.do",Url.SEFT));
			message.setUrls(urls);
		}catch (Exception e) {
			System.out.println(e.getMessage());
			message.setTitle("修改失败！");
			List<Url> urls = new ArrayList<Url>();
			urls.add(new Url("返回","javascript:history.back();",Url.SEFT));
		}
		 return message;
	}

	@Override
	public Message delete(int id) {
		Message message = new Message();
		List<Project> projects = getProjectDao().getProjectsByLicenseId(id);
		if(projects != null && projects.size() > 0){
			message.setTitle("删除失败！");
			String info = "<table class=\"todelete\"><caption>以下项目使用本协议，请修改或删除。</caption>";
			for (Project project : projects) {
				info += "<tr><td><a href=\"project-show.do?id=" 
					+ project.getId()+"\" target=\"_blank\">"
					+project.getName()+"</a></td></tr>";
			}
			info += "</table>";
			message.setInfo(info);
			List<Url> urls = new ArrayList<Url>();
			urls.add(new Url("返回","javascript:history.back();",Url.SEFT));
			message.setUrls(urls);
		}else{
			try{
				getLicenseDao().delete(getLicenseDao().getLicenseById(id));
				message.setTitle("删除成功！");
				List<Url> urls = new ArrayList<Url>();
				urls.add(new Url("进入协议列表","license-list.do",Url.SEFT));
				urls.add(new Url("进入回收站","license-trash.do",Url.SEFT));			
				message.setUrls(urls);
			}catch (Exception e) {
				message.setTitle("删除失败！");
				message.setInfo("可能该项不存在");
				List<Url> urls = new ArrayList<Url>();
				urls.add(new Url("返回列表","license-list.do",Url.SEFT));
				message.setUrls(urls);
			}
		}
		return message;
	}

	@Override
	public Message recover(int id) {
		Message message = new Message();
		getLicenseDao().recoverById(id);
		message.setTitle("操作成功");
		List<Url> urls = new ArrayList<Url>();
		urls.add(new Url("进入协议列表","license-list.do",Url.SEFT));
		urls.add(new Url("进入回收站","license-trash.do",Url.SEFT));
		message.setUrls(urls);
		return message;
	}

	@Override
	public Message remove(int id) {
		Message message = new Message();
		getLicenseDao().removeById(id);
		message.setTitle("操作成功");
		List<Url> urls = new ArrayList<Url>();
		urls.add(new Url("进入协议列表","license-list.do",Url.SEFT));
		urls.add(new Url("进入回收站","license-trash.do",Url.SEFT));
		message.setUrls(urls);
		return message;		
	}
	
	@Override
	public java.util.List<License> getLicensesAll(){
		return getLicenseDao().getLicensesAll();
	}
	
	@Override
	public List<License> getLicensesByPagination(Pagination pagination, boolean isTrash) {
		pagination.setTatolCount(getLicenseDao().getLicenseCount(isTrash));
		pagination.init();
		return getLicenseDao().getLicensesByPagination(pagination,isTrash);
	}

}
