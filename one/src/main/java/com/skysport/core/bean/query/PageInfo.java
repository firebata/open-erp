package com.skysport.core.bean.query;

/**
 * 
 * 此类描述的是：
 * 
 * @author: zhangjh
 * @version: 2015年4月29日 下午5:44:37
 */
public class PageInfo {
	/**
	 * 开始记录
	 */
	private int start = 1;
	/**
	 * 每页记录数
	 */
	private int limit = 20;
	/**
	 * 页面请求次数
	 */
	private int draw;

	/**
	 * 记录数：用于数据更新时保存过滤条件查询出的记录数
	 */
	private int filterCounts;

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

	public int getFilterCounts() {
		return filterCounts;
	}

	public void setFilterCounts(int filterCounts) {
		this.filterCounts = filterCounts;
	}

}
