package com.dating.base;

import java.io.Serializable;
import java.util.List;

import com.dating.request.BaseRequest;

public class Page<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final int INIT_SIZE = 10;
	private int pageSize = 10;
	private long totalCount;
	private int currentPage;
	private List<T> data;
	private String unit = "条";
	private String extInfo;

	public Page() {
	}

	public Page(int currentPage) {
		this.currentPage = currentPage;
	}

	public Page(int currentPage, int pageSize) {
		this.currentPage = currentPage;
		this.pageSize = pageSize;
	}

	public Page<T> EmptyPage(BaseRequest baseRequest) {
		if (baseRequest == null) {
			return null;
		}
		pageSize = baseRequest.getPageSize();
		currentPage = baseRequest.getPageNum();
		totalCount = 0L;
		return this;
	}

	public Page<T> data(List<T> data) {
		this.data = data;
		return this;
	}

	public Page<T> totalCount(Long count) {
		this.totalCount = count;
		return this;
	}

	public Page<T> pageSize(Integer pageSize) {
		this.pageSize = pageSize;
		return this;
	}

	public Page<T> currentPage(Integer pageNo) {
		this.currentPage = pageNo;
		return this;
	}

	public int getStartIndex() {
		return (this.getCurrentPage() - 1) * this.pageSize;
	}

	public int getEndIndex() {
		return this.getCurrentPage() * this.pageSize;
	}

	public boolean isFirstPage() {
		return this.getCurrentPage() <= 1;
	}

	public boolean isLastPage() {
		return (long) this.getCurrentPage() >= this.getPageCount();
	}

	public int getNextPage() {
		return this.isLastPage() ? this.getCurrentPage() : this.getCurrentPage() + 1;
	}

	public int getPreviousPage() {
		return this.isFirstPage() ? 1 : this.getCurrentPage() - 1;
	}

	public int getCurrentPage() {
		if (this.currentPage == 0) {
			this.currentPage = 1;
		}
		return this.currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public long getPageCount() {
		return this.totalCount % (long) this.pageSize == 0L
				? this.totalCount / (long) this.pageSize
				: this.totalCount / (long) this.pageSize + 1L;
	}

	public long getTotalCount() {
		return this.totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public boolean hasNextPage() {
		return (long) this.getCurrentPage() < this.getPageCount();
	}

	public boolean hasPreviousPage() {
		return this.getCurrentPage() > 1;
	}

	public List<T> getResult() {
		return this.data;
	}

	public void setResult(List<T> data) {
		this.data = data;
	}

	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getExtInfo() {
		return this.extInfo;
	}

	public void setExtInfo(String extInfo) {
		this.extInfo = extInfo;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("Page{");
		sb.append("pageSize=").append(pageSize);
		sb.append(", totalCount=").append(totalCount);
		sb.append(", currentPage=").append(currentPage);
		sb.append(", data=").append(data);
		sb.append(", unit='").append(unit).append('\'');
		sb.append(", extInfo='").append(extInfo).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
