package com.serlib.common.bean;

public class Pagination {
	private int tatolCount;
	private int tatolPage;
	private int now;
	private int perPage;

	public int getTatolCount() {
		return tatolCount;
	}

	public void setTatolCount(int tatolCount) {
		this.tatolCount = tatolCount;
	}

	public int getTatolPage() {
		return tatolPage;
	}

	public void setTatolPage(int tatolPage) {
		this.tatolPage = tatolPage;
	}

	public int getNow() {
		return now;
	}

	public void setNow(int now) {
		this.now = now;
	}

	public int getPerPage() {
		return perPage;
	}

	public void setPerPage(int perPage) {
		this.perPage = perPage;
	}

	public Pagination(){
		
	}

	public void init() {
		if(tatolCount <= 0)
			tatolCount = 0;
		if(perPage <= 0)
			perPage = 10;
		
		int tatolPage;
		tatolPage = tatolCount / perPage;
		if (tatolCount % perPage > 0) {
			tatolPage++;
		}
		if (now <= 1) {
			now = 1;
		}
		if (now >= tatolPage && tatolPage > 0) {
			now = tatolPage;
		}

		setNow(now);
		setTatolCount(tatolCount);
		setTatolPage(tatolPage);
	}
}
