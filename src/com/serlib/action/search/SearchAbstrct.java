package com.serlib.action.search;

import org.apache.solr.common.SolrDocumentList;

import com.serlib.action.common.BaseAction;
import com.serlib.solr.service.SolrService;

@SuppressWarnings("serial")
public abstract class SearchAbstrct extends BaseAction {
	
	public final String execute() throws Exception {
		return SUCCESS;
	}
	
	public String search() throws Exception{
		return SUCCESS;
	}
	
	private SolrService solrService;
	private SolrDocumentList solrDocumentList;
	private String text;

	public SolrService getSolrService() {
		return solrService;
	}

	public void setSolrService(SolrService solrService) {
		this.solrService = solrService;
	}

	public SolrDocumentList getSolrDocumentList() {
		return solrDocumentList;
	}

	public void setSolrDocumentList(SolrDocumentList solrDocumentList) {
		this.solrDocumentList = solrDocumentList;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
