package com.online.college.common.page;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.online.college.common.util.BeanUtil;

public abstract class AbstractPage<E> implements Page<E> {

	public static final int DEFAULT_FIRST_PAGE_NUM = 1;
	public static final int DEFAULT_PAGE_SIZE = 10;

	protected int pageSize = DEFAULT_PAGE_SIZE;
	protected int pageNum = DEFAULT_FIRST_PAGE_NUM;

	protected int itemsTotalCount = 0;// 总记录数
	protected int pageTotalCount = 0;// 总页数
	protected List<E> items;
	protected boolean firstPage;// 是否是第一页
	protected boolean lastPage;// 是否是最后一页
	protected int startIndex;

	private String sortField = "update_time";// 排序
	private String sortDirection = "DESC";// 排序方向

	@Override
	public int getFirstPageNum() {
		return DEFAULT_FIRST_PAGE_NUM;
	}

	@Override
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		if (pageNum < DEFAULT_FIRST_PAGE_NUM)
			pageNum = DEFAULT_FIRST_PAGE_NUM;
		this.pageNum = pageNum;
	}

	@Override
	public List<E> getItems() {
		return items;
	}

	public void setItems(Collection<E> items) {
		if (items == null)
			items = Collections.emptyList();
		this.items = new ArrayList<E>(items);	// 设置记录属性，此处的items变量不是方法形参items，而是成员变量items.
		this.lastPage = this.pageNum == this.pageTotalCount;	// 设置首页标记
		this.firstPage = this.pageNum == DEFAULT_FIRST_PAGE_NUM;	// 设置尾页标记
	}

	@Override
	public boolean isFirstPage() {
		firstPage = (getPageNum() <= getFirstPageNum());
		return firstPage;
	}

	@Override
	public boolean isLastPage() {
		return lastPage;
	}

	public int getPrePageNum() {
		return isFirstPage() ? getFirstPageNum() : getPageNum() - 1;
	}

	public int getNextPageNum() {
		return isLastPage() ? getPageNum() : getPageNum() + 1;
	}

	@Override
	public Iterator<E> iterator() {
		return this.items.iterator();
	}

	@Override
	public boolean isEmpty() {
		return items.isEmpty();
	}

	/**
	 * 给AbstractPage对象的一些属性赋值
	 * @param itemsTotalCount
	 */
	public void setItemsTotalCount(int itemsTotalCount) {
		this.itemsTotalCount = itemsTotalCount;	// 设置总记录数，默认为0
		if (itemsTotalCount % this.pageSize == 0) {	// 设置总页数，默认为0
			this.pageTotalCount = itemsTotalCount / this.pageSize;
		} else {
			this.pageTotalCount = itemsTotalCount / this.pageSize + 1;
		}
		if (this.pageNum > this.pageTotalCount) {	// 设置当前页号，默认为DEFAULT_FIRST_PAGE_NUM=1
			this.pageNum = DEFAULT_FIRST_PAGE_NUM;
		}
		if (this.itemsTotalCount <= this.pageSize) {	// 如果总记录数小于等于页面记录大小，则设置首页尾页标记
			this.firstPage = true;	// 成员变量firstPage、lastPage没有被赋初值，应该默认为false.
			this.lastPage = true;
		}
	}

	@Override
	public int getItemsTotalCount() {
		return itemsTotalCount;
	}

	@Override
	public int getLastPageNum() {
		// 总感觉这个地方有问题，应该是pageTotalCount吧？
		return itemsTotalCount;
	}

	/**
	 * startIndex原本是没有初值的，不带任何参数地创建TailPage对象时，默认是调用TailPage类的空构造方法。
	 * 当在CourseMapper.xml文件中调用param2.startIndex值时，实际上调用的是TailPage对象所继承的getStartIndex方法，在这个方法中，
	 * startIndex被赋了一个值。
	 * @return
	 */
	public int getStartIndex() {
		this.startIndex = (this.pageNum - 1) * this.pageSize;
		if (this.startIndex <= 0) {
			this.startIndex = 0;
		}
		return this.startIndex;
	}

	/**
	 * 按照sortField升序
	 * 
	 * @param sortField：指java bean中的属性
	 */
	public void ascSortField(String sortField) {
		if (StringUtils.isNotEmpty(sortField)) {
			this.sortField = BeanUtil.fieldToColumn(sortField);
			this.sortDirection = " ASC ";
		}
	}

	/**
	 * 按照sortField降序
	 * 
	 * @param sortField ：指java bean中的属性
	 */
	public void descSortField(String sortField) {
		if (StringUtils.isNotEmpty(sortField)) {
			this.sortField = BeanUtil.fieldToColumn(sortField);
			this.sortDirection = " DESC ";
		}
	}

	public String getSortDirection() {
		return sortDirection;
	}

	public void setSortDirection(String sortDirection) {
		this.sortDirection = sortDirection;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	@Override
	public String toString() {
		return "Page[" + this.getPageNum() + "]:" + items.toString();
	}
}
