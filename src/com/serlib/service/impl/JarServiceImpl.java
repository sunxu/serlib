package com.serlib.service.impl;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.serlib.common.bean.Message;
import com.serlib.common.bean.Pagination;
import com.serlib.common.bean.Url;
import com.serlib.entity.Jar;
import com.serlib.service.JarService;
import com.serlib.service.common.BaseService;
import com.serlib.util.Tool;

public class JarServiceImpl extends BaseService implements JarService {


	@Override
	public Jar getJarById(int id) {
		return getJarDao().getJarById(id);
	}

	@Override
	public Message save(Jar jar) {
		Message message = new Message();
		try{
			jar = getJarDao().saveOrUpdate(jar);
			message.setTitle("类库添加成功");
			List<Url> urls = new ArrayList<Url>();
			urls.add(new Url("开始解压","jar-extract.do",Url.SEFT));
			message.setUrls(urls);
			ActionContext.getContext().getSession().put(Tool.JAR_FILE_MD5,jar.getHash());
			ActionContext.getContext().getSession().put(Tool.JAR_FILE_ID,jar.getId());
			ActionContext.getContext().getSession().put(Tool.JAR_FILE_STATUS,"added");
		}catch (Exception e) {
			message.setTitle("类库添加失败！");
			message.setInfo(e.getMessage());
			List<Url> urls = new ArrayList<Url>();
			urls.add(new Url("返回","javascript:history.back();",Url.SEFT));
			message.setUrls(urls);
		}
		 return message;
	}
	
	@Override
	public Message update(Jar jar) {
		Message message = new Message();
		try{
			jar = getJarDao().saveOrUpdate(jar);
			message.setTitle("修改成功");
			List<Url> urls = new ArrayList<Url>();
			urls.add(new Url("查看本文","jar-show.do?id="+jar.getId(),Url.BLANK));
			urls.add(new Url("再次修改","jar-edit.do?id="+jar.getId(),Url.SEFT));
			urls.add(new Url("进入列表","jar-list.do",Url.SEFT));
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
		Jar jar = getJarDao().getJarById(id);
		if(jar != null){
			message.setInfo(jar.getHash());
			getJarDao().delete(jar);
			message.setTitle("删除成功！");
			List<Url> urls = new ArrayList<Url>();
			urls.add(new Url("进入列表","jar-list.do",Url.SEFT));
			urls.add(new Url("进入回收站","jar-trash.do",Url.SEFT));			
			message.setUrls(urls);
		}else{
			message.setTitle("删除失败！");
			message.setInfo("可能该项不存在");
			List<Url> urls = new ArrayList<Url>();
			urls.add(new Url("返回列表","jar-list.do",Url.SEFT));
			message.setUrls(urls);
		}
		return message;
	}

	@Override
	public Message recover(int id) {
		Message message = new Message();
		getJarDao().recoverById(id);
		message.setTitle("操作成功");
		List<Url> urls = new ArrayList<Url>();
		urls.add(new Url("进入列表","jar-list.do",Url.SEFT));
		urls.add(new Url("进入回收站","jar-trash.do",Url.SEFT));
		message.setUrls(urls);
		return message;
	}

	@Override
	public Message remove(int id) {
		Message message = new Message();
		getJarDao().removeById(id);
		message.setTitle("操作成功");
		List<Url> urls = new ArrayList<Url>();
		urls.add(new Url("进入列表","jar-list.do",Url.SEFT));
		urls.add(new Url("进入回收站","jar-trash.do",Url.SEFT));
		message.setUrls(urls);
		return message;		
	}
	
	@Override
	public List<Jar> getJarsByHash(String hash){
		return getJarDao().getJarsByHash(hash);
	}
	
	@Override
	public List<Jar> getJarsByPagination(Pagination pagination, boolean isTrash) {
		pagination.setTatolCount(getJarDao().getJarCount(isTrash));
		pagination.init();
		return getJarDao().getJarsByPagination(pagination,isTrash);
	}

	@SuppressWarnings("deprecation")
	@Override
	public Message extract() {
		Message message = new Message();
		String jarName = ActionContext.getContext().getSession().get(Tool.JAR_FILE_TEMPNAME).toString();
		String directory = ServletActionContext.getRequest().getRealPath("/data/temp");
		
		try {
			Tool.decompress(new File(directory,jarName), new File(directory,jarName.substring(0,jarName.lastIndexOf("."))));
		} catch (IOException e) {
			System.out.println(e.getMessage());
			message.setTitle("解压缩失败");
			message.setInfo(e.getMessage());
			List<Url> urls = new ArrayList<Url>();
			urls.add(new Url("重新解压缩","jar-extract.do",Url.SEFT));
			message.setUrls(urls);
			return message;
		}
		
		ActionContext.getContext().getSession().put(Tool.JAR_FILE_STATUS, "extracted");
		message.setTitle("解压缩成功");
		List<Url> urls = new ArrayList<Url>();
		urls.add(new Url("开始生成类库文档","jar-generate.do",Url.SEFT));
		message.setUrls(urls);
		return message;
	}
	
	@Override
	public Message generate(Map<String, Object> session) {
		Message message = new Message();
		String jarTempName = session.get(Tool.JAR_FILE_TEMPNAME).toString();
		String jarName = session.get(Tool.JAR_FILE_NAME).toString();
		String jarMd5 = session.get(Tool.JAR_FILE_MD5).toString();
		String jarId = session.get(Tool.JAR_FILE_ID).toString();
		
		String directory;
		try {
			directory = new File(this.getClass().getResource("/").toURI()).getParentFile().getParentFile().toString()+"/data";
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
			return null;
		}
		
		File classDir = new File(directory+"/temp",jarTempName.substring(0,jarTempName.lastIndexOf(".")));
		File outputDir = new File(directory+"/jar/"+jarMd5);

		Map<String, String> solrInfoMap = new HashMap<String, String>();
		solrInfoMap.put(Tool.JAR_FILE_MD5, jarMd5);
		solrInfoMap.put(Tool.JAR_FILE_NAME, jarName);
		solrInfoMap.put(Tool.JAR_FILE_ID, jarId);

		session.put(Tool.JAR_ANALYZE_STATUS, "doing");
		message.setTitle("类库文档创建中<span id=dot></span>");
		message.setInfo("<script language=\"JavaScript\">"+
				"function d(){$('#dot').append('..');setTimeout('d()',500);}"+
				"function f(){window.location.reload();}"+
				"setTimeout('f()',2000);"+
				"setTimeout('d()',500);"+
				"</script> ");
		message.setUrls(new ArrayList<Url>());
		session.put(Tool.JAR_ANALYZE_MESSAGE, message);
		
		List<String> classIds = new ArrayList<String>();
		
		try {
			Tool.analyzeClass(classDir, outputDir, solrInfoMap,classIds);
			Tool.writeListToFile(classIds, new File(outputDir, "classids.data"));
		} catch (IOException e) {
			message.setTitle("类库文档创建失败");
			message.setInfo(e.getMessage());
			List<Url> urls = new ArrayList<Url>();
			urls.add(new Url("重新创建","jar-generate.do",Url.SEFT));
			message.setUrls(urls);
			session.put(Tool.JAR_ANALYZE_STATUS, "error");
			session.put(Tool.JAR_ANALYZE_MESSAGE, message);
			return message;
		}
		
		try {
			File tempFile = new File(directory+"/temp/",jarTempName);
			FileUtils.copyFile(tempFile, new File(outputDir, jarName));
			tempFile.delete();
			FileUtils.deleteDirectory(classDir);
			message.setInfo("");
		} catch (IOException e) {
			message.setInfo(e.getMessage());
		}
		
		message.setTitle("类库文档创建成功");
		List<Url> urls = new ArrayList<Url>();
		urls.add(new Url("开始建立索引","jar-index.do",Url.SEFT));
		message.setUrls(urls);
		
		session.put(Tool.JAR_ANALYZE_STATUS, "done");
		session.put(Tool.JAR_ANALYZE_MESSAGE, message);
		
		return message;
	}
	
}
