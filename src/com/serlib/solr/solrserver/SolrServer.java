package com.serlib.solr.solrserver;

import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;

public interface SolrServer {
	
	public EmbeddedSolrServer getMethodSolrServer();
	
	public EmbeddedSolrServer getClassSolrServer();
	
	public EmbeddedSolrServer getJarSolrServer();
	
}
