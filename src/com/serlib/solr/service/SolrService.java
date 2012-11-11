package com.serlib.solr.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import com.serlib.common.bean.Message;
import com.serlib.common.bean.Url;
import com.serlib.solr.solrserver.SolrServer;
import com.serlib.util.SolrTool;
import com.serlib.util.Tool;

public class SolrService {

	private SolrServer solrServer;
	
	public SolrServer getSolrServer() {
		return solrServer;
	}

	public void setSolrServer(SolrServer solrServer) {
		this.solrServer = solrServer;
	}

	public void index(Map<String, Object> session)
			throws Exception {
		Message message = new Message();
		String jarMd5 = session.get(Tool.JAR_FILE_MD5).toString();
		String jarName = session.get(Tool.JAR_FILE_NAME).toString();
		String jarId = session.get(Tool.JAR_FILE_ID).toString();
		
		String directory;
		try {
			directory = new File(this.getClass().getResource("/").toURI())
					.getParentFile().getParentFile().toString()
					+ "/data/jar/" + jarMd5 + "/solr/";
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
			return;
		}

		File dir = new File(directory);
		String[] files = dir.list();
		InputStream input;
		File file;
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		XMLStreamReader parser;
		Collection<SolrInputDocument> methodDocs = new ArrayList<SolrInputDocument>();
		UpdateResponse response;
		SolrInputDocument jarDoc = new SolrInputDocument();
		SolrInputDocument classDoc = new SolrInputDocument();
		
		StringBuffer sb = new StringBuffer();
		sb.append("<div class=\"C_list\">");
		sb.append("\t<ul>");
		String className;
		
		for (int i = 0; i < files.length; i++) {
			file = new File(dir, files[i]);
			input = new FileInputStream(file);
			parser = inputFactory.createXMLStreamReader(input);
			methodDocs.addAll(SolrTool.readDocs(parser));
			className = files[i].substring(0, files[i].length()-4);
			classDoc.addField("class", className);
			sb.append("\t\t<li>"+className+"<br>");
			if (i % 10 == 9) {
				response = getSolrServer().getMethodSolrServer().add(methodDocs);
				System.out.println(response.toString());
				Thread.sleep(100);
				methodDocs.clear();
			}
		}

		sb.append("\t</ul>");
		sb.append("</div>");

		Tool.string2File(sb.toString(), new File(dir.getParent(), "class_list.html"));
		
		if (!methodDocs.isEmpty()) {
			response = getSolrServer().getMethodSolrServer().add(methodDocs);
			System.out.println(response.toString());
		}
		response = getSolrServer().getMethodSolrServer().commit();

		jarDoc.addField("id", jarMd5);
		jarDoc.addField("jar", jarName);
		jarDoc.addField("jarid", jarId);
		getSolrServer().getJarSolrServer().add(jarDoc);
		response = getSolrServer().getJarSolrServer().commit();
		
		classDoc.addField("id", jarMd5);
		classDoc.addField("jar", jarName);
		classDoc.addField("jarid", jarId);
		getSolrServer().getClassSolrServer().add(classDoc);
		response = getSolrServer().getClassSolrServer().commit();
		
		message.setTitle("类库索引成功");
		message.setInfo("恭喜，类库"+jarName+"添加成功！");
		List<Url> urls = new ArrayList<Url>();
		urls.add(new Url("查看类库","jar-show.do?id="+jarId,Url.BLANK));
		urls.add(new Url("进入列表","jar-list.do",Url.SEFT));
		urls.add(new Url("继续添加","jar-upload.do",Url.SEFT));
		message.setUrls(urls);
		
		session.put(Tool.JAR_ANALYZE_STATUS, "done");
		session.put(Tool.JAR_ANALYZE_MESSAGE, message);
	}
	
	public SolrDocumentList searchJar(SolrQuery query) throws SolrServerException{
		QueryResponse rsp =  getSolrServer().getJarSolrServer().query(query);
		SolrDocumentList docs = rsp.getResults();
		return docs;
	}
	
	public SolrDocumentList searchClass(SolrQuery query) throws SolrServerException{
		QueryResponse rsp =  getSolrServer().getClassSolrServer().query(query);
		SolrDocumentList docs = rsp.getResults();
		return docs;
	}
	
	public SolrDocumentList searchMethod(SolrQuery query) throws SolrServerException{
		QueryResponse rsp =  getSolrServer().getMethodSolrServer().query(query);
		SolrDocumentList docs = rsp.getResults();
		return docs;
	}
	
	public void deleteDoc(String md5) throws SolrServerException, IOException{
		String directory;
		try {
			directory = new File(this.getClass().getResource("/").toURI())
					.getParentFile().getParentFile().toString()
					+ "/data/jar/" + md5;
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
			return;
		}

		List<String> classids= Tool.readStringListFromFile(new File(directory, "classids.data"));
		
		System.out.println(classids.size());
		for(String string:classids)
			System.out.println(string);
		
		getSolrServer().getJarSolrServer().deleteById(md5);
		getSolrServer().getJarSolrServer().commit();
		getSolrServer().getClassSolrServer().deleteById(md5);
		getSolrServer().getClassSolrServer().commit();
		getSolrServer().getMethodSolrServer().deleteById(classids);
		getSolrServer().getMethodSolrServer().commit();
		
	}
}
