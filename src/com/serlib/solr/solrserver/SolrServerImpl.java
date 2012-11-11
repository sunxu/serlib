package com.serlib.solr.solrserver;

import java.io.File;

import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.core.CoreContainer;

public class SolrServerImpl implements SolrServer {

	private String solrHome;
	private CoreContainer container;
	private EmbeddedSolrServer methodSolrServer;
	private EmbeddedSolrServer classSolrServer;
	private EmbeddedSolrServer jarSolrServer;
	
	public void init() {
		try {
			File classDir = new File(this.getClass().getResource("/").toURI());
			File webRootDir = classDir.getParentFile().getParentFile();
			File home = new File(webRootDir,getSolrHome());
		    File solrconfig = new File(home, "solr.xml");
		    container = new CoreContainer();
			container.load(home.getPath(), solrconfig);
			
			methodSolrServer = new EmbeddedSolrServer(container, "method");
			classSolrServer = new EmbeddedSolrServer(container, "class");
			jarSolrServer = new EmbeddedSolrServer(container, "jar");
			
			System.out.println("solr初始化成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("solr初始化失败");
		}
	}
	
	public void destroy() {
		try {
			container.shutdown();
			System.out.println("solr关闭成功");
		}catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			System.out.println("solr关闭失败");
		}
	}
	
	@Override
	public EmbeddedSolrServer getClassSolrServer() {
		return classSolrServer;
	}

	@Override
	public EmbeddedSolrServer getJarSolrServer() {
		return jarSolrServer;
	}

	@Override
	public EmbeddedSolrServer getMethodSolrServer() {
		return methodSolrServer;
	}

	public String getSolrHome() {
		return solrHome;
	}

	public void setSolrHome(String solrHome) {
		this.solrHome = solrHome;
	}

}
