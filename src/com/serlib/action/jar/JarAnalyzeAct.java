package com.serlib.action.jar;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.jar.JarInputStream;

import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionContext;
import com.serlib.common.bean.Message;
import com.serlib.common.bean.Url;
import com.serlib.entity.Jar;
import com.serlib.service.JarService;
import com.serlib.solr.service.SolrService;
import com.serlib.util.Tool;

@SuppressWarnings("serial")
public class JarAnalyzeAct extends JarAbstrct {

	@SuppressWarnings("deprecation")
	@Override
	public String add() throws Exception{
		if(getJar() == null){
			String jarTempName = ActionContext.getContext().getSession().get(Tool.JAR_FILE_TEMPNAME).toString();
			String jarName = ActionContext.getContext().getSession().get(Tool.JAR_FILE_NAME).toString();
			File file = new File(ServletActionContext.getRequest().getRealPath("/data/temp") ,jarTempName);
			JarInputStream jis = new JarInputStream(new FileInputStream(file), false);
			Jar jar;
			String hash = Tool.getFileMD5String(file);
			List<Jar> jars = getJarService().getJarsByHash(hash);
			if(jars.size() > 0){
				jar = jars.get(0);
				Message message = new Message();
				message.setTitle("该类库已被索引");
				List<Url> urls = new ArrayList<Url>();
				urls.add(new Url("查看类库","jar-show.do?id="+jar.getId(),Url.BLANK));
				urls.add(new Url("修改类库","jar-edit.do?id="+jar.getId(),Url.SEFT));
				urls.add(new Url("继续添加","jar-upload.do",Url.SEFT));
				message.setUrls(urls);
				setMessage(message);
				file.delete();
				ActionContext.getContext().getSession().put(Tool.JAR_FILE_STATUS, "none");
				return SUCCESS;
			}
			
			jar = new Jar();
			jar.setHash(hash);
			jar.setSize((int)(file.length()/1024));
			jar.setName(jarName);
			jar.setBuildtime((int)(jis.getNextEntry().getTime()/1000));
			jar.setStatus(true);
			setJar(jar);
			setProjects(getProjectService().getProjectsAll());
			return INPUT;
		}else{
			getJar().setAddtime(com.serlib.util.Tool.getTimestamp());
			setMessage(getJarService().save(getJar()));
			return SUCCESS;
		}
	}
	
	public String extract() throws Exception{
		setMessage(getJarService().extract());
		return SUCCESS;
	}

	public String generate() throws Exception{
		Object object = ActionContext.getContext().getSession().get(Tool.JAR_ANALYZE_STATUS);
		String status = (object == null) ? "null" : object.toString();
		
		if(status.equals("null")){
			new InnerThread(ActionContext.getContext().getSession(), 
					getJarService(), getSolrService(), "generate").start();
			setMessage(new Message());
			getMessage().setTitle("类库文档创建中<span id=dot></span>");
			getMessage().setInfo("<script language=\"JavaScript\">"+
					"function d(){$('#dot').append('..');setTimeout('d()',500);}"+
					"function f(){window.location.reload();}"+
					"setTimeout('f()',2000);"+
					"setTimeout('d()',500);"+
					"</script> ");
			getMessage().setUrls(new ArrayList<Url>());
			ActionContext.getContext().getSession().put(Tool.JAR_ANALYZE_STATUS, "doing");
			ActionContext.getContext().getSession().put(Tool.JAR_ANALYZE_MESSAGE, getMessage());
			return SUCCESS;
		}

		if(status.equals("doing")){
			setMessage((Message) ActionContext.getContext().getSession().get(Tool.JAR_ANALYZE_MESSAGE));
			return SUCCESS;
		}
		
		if(status.equals("error")){
			setMessage((Message) ActionContext.getContext().getSession().get(Tool.JAR_ANALYZE_MESSAGE));
			ActionContext.getContext().getSession().put(Tool.JAR_ANALYZE_STATUS, "null");
			return SUCCESS;
		}		
		
		if(status.equals("done")){
			setMessage((Message) ActionContext.getContext().getSession().get(Tool.JAR_ANALYZE_MESSAGE));
			ActionContext.getContext().getSession().put(Tool.JAR_ANALYZE_STATUS, "null");
			ActionContext.getContext().getSession().put(Tool.JAR_FILE_STATUS, "generateed");
			return SUCCESS;
		}
		
		return SUCCESS;
	}
	
	public String index() throws Exception{
		Object object = ActionContext.getContext().getSession().get(Tool.JAR_ANALYZE_STATUS);
		String status = (object == null) ? "null" : object.toString();
		
		if(status.equals("null")){
			new InnerThread(ActionContext.getContext().getSession(), 
					getJarService(), getSolrService(), "index").start();
			setMessage(new Message());
			getMessage().setTitle("类库索引创建中<span id=dot></span>");
			getMessage().setInfo("<script language=\"JavaScript\">"+
					"function d(){$('#dot').append('..');setTimeout('d()',500);}"+
					"function f(){window.location.reload();}"+
					"setTimeout('f()',2000);"+
					"setTimeout('d()',500);"+
					"</script> ");
			getMessage().setUrls(new ArrayList<Url>());
			ActionContext.getContext().getSession().put(Tool.JAR_ANALYZE_STATUS, "doing");
			ActionContext.getContext().getSession().put(Tool.JAR_ANALYZE_MESSAGE, getMessage());
			return SUCCESS;
		}

		if(status.equals("doing")){
			setMessage((Message) ActionContext.getContext().getSession().get(Tool.JAR_ANALYZE_MESSAGE));
			return SUCCESS;
		}
		
		if(status.equals("error")){
			setMessage((Message) ActionContext.getContext().getSession().get(Tool.JAR_ANALYZE_MESSAGE));
			ActionContext.getContext().getSession().put(Tool.JAR_ANALYZE_STATUS, "null");
			return SUCCESS;
		}		
		
		if(status.equals("done")){
			setMessage((Message) ActionContext.getContext().getSession().get(Tool.JAR_ANALYZE_MESSAGE));
			getMessage().setInfo("");
			ActionContext.getContext().getSession().put(Tool.JAR_ANALYZE_STATUS, "null");
			ActionContext.getContext().getSession().put(Tool.JAR_FILE_STATUS, "none");
			return SUCCESS;
		}
		
		
		return SUCCESS;
	}
	
	@Override
	public String delete() throws Exception {
		setMessage(getJarService().delete(getId()));
		if(getMessage().getUrls().size() == 2){
			try{
				getSolrService().deleteDoc(getMessage().getInfo());
			}catch (Exception e) {
				getMessage().setInfo(e.getMessage());
			}
			getMessage().setInfo(null);
		}
		return SUCCESS;
	}
	
	public class InnerThread extends Thread{
		Map<String, Object> session;
		JarService jarService;
		SolrService solrService;
		String type;
		InnerThread(Map<String, Object> session, 
				JarService jarService, SolrService solrService, String type){
			this.session = session;
			this.jarService = jarService;
			this.solrService = solrService;
			this.type = type;
		}
		
		@Override
		public void run() {
			try {
				if (type.equals("generate")) {
					System.out.println("generate running");
					jarService.generate(session);
				} else if(type.equals("index")){
					System.out.println("index running");
					solrService.index(session);
				}
			} catch (Exception e) {
				e.printStackTrace();
				session.put(Tool.JAR_ANALYZE_STATUS, "error");
			}
		}
	}

}
