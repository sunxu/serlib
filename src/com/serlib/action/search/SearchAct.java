package com.serlib.action.search;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.common.SolrDocumentList;

import com.serlib.common.bean.Pagination;

@SuppressWarnings("serial")
public class SearchAct extends SearchAbstrct {

	@Override
	public String search() throws Exception {
		if(getId() == NONE)
			return SUCCESS;
		
		SolrDocumentList docs = null;
		
		Pagination pagination = new Pagination();
		if(getPage()>1)
			pagination.setNow(getPage());
		else
			pagination.setNow(1);
		
		pagination.setPerPage(10);
		
		SolrQuery query = new SolrQuery();
		query.setStart(pagination.getPerPage()*(pagination.getNow()-1));
		query.setRows(pagination.getPerPage());
		
		if(getId() == JAR){
			query.setQuery("jar:"+getText()+"*");
			docs = getSolrService().searchJar(query);
		}

		if(getId() == CLASS){
			query.setQuery("class:"+getText()+"*");
			docs = getSolrService().searchClass(query);
		}
		
		if(getId() == METHOD){
			query.setQuery("method:"+getText()+"*");
			docs = getSolrService().searchMethod(query);
		}
		
		pagination.setTatolCount((int)docs.getNumFound());
		pagination.init();
		setPagination(pagination);
		setSolrDocumentList(docs);
		
		return LIST;
	}
}
