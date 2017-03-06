package com.tarang.ewallet.util;



/**
* @author  : prasadj
* @date    : Oct 10, 2012
* @time    : 8:59:55 AM
* @version : 1.0.0
* @comments: holds the jquery filter information
*
*/
public class QueryFilter {

	/**
	 * total number of records against to the filter
	 */
	private Integer total;
	
	/**
	 * total number of pages based on total/rows
	 */
	private Integer totalPages;
	
	/**
	 * the current page or fetching page
	 */
	private Integer page;
	
	/**
	 * maximum number of rows per page
	 */
	private Integer rows;
	
	/**
	 * sorting column
	 */
	private String sidx;
	
	/**
	 * sorting order
	 */
	private String sord;
	
	/**
	 *  filter string from page in json format
	 */
	private String filterString;
	
	/**
	 * parent id 
	 */
	private Long parentId;
	
	public QueryFilter(){
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

	public String getFilterString() {
		return filterString;
	}

	public void setFilterString(String filterString) {
		this.filterString = filterString;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
}
