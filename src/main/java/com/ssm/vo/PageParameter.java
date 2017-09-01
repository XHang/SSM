package com.ssm.vo;

import java.util.List;

/**
 * 分页用到的参数VO对象
 * @author Mr-hang
 *
 */
public  class  PageParameter  <T> {
	
	private int pageSize;
	
	private int currentPage;
	
	private int offset;
	
	private List<T> list;
	
	private int from;

	/**
	 * 得到页面总数
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}
	/**
	 * 设置页面总数
	 * @param pageSize
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	/**
	 * 得到当前页面数
	 * @return
	 */
	public int getCurrentPage() {
		return currentPage;
	}
	/**
	 * 设置当前页面数
	 * @param currentPage
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	/**
	 * 得到每页数据量
	 * @return
	 */
	public int getOffset() {
		return offset;
	}
	/**
	 * 设置每页数据量
	 * @param offset
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}
	/**
	 * 得到分页数据
	 * @return
	 */
	public List<T> getList() {
		return list;
	}
	
	/**
	 * 设置分页数据
	 * @param list
	 */
	public void setList(List<T> list) {
		this.list = list;
	}
	
	/**
	 * 得到sql查询内部参数:从第几个数据开始取
	 * @return
	 */
	public int getFrom() {
		return from;
	}
	
	/**
	 * 得到sql查询内部参数:从第几个数据开始取
	 * @param from
	 */
	public void setFrom(int from) {
		this.from = from;
	}
	
	
}
