package com.online.college.common.page;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 分页
 */
public class TailPage<E> extends AbstractPage<E> {

	protected int showPage = 10;	// 显示10个页码
	protected List<Integer> showNums = new ArrayList<Integer>();
	protected boolean showDot = true;	// 不知道这个属性是用来干嘛的？

	public TailPage() {
	}

	/**
	 * 构造函数，将一个已有的分页对象中的分页参数，设置给自己，items需独立设置
	 * 
	 * @param page
	 * @param items
	 */
	public TailPage(Page<E> page, Collection<E> items, int itemsTotalCount) {
		this(page.getPageNum(), page.getPageSize(), itemsTotalCount, items);
	}

	public TailPage(int pageNum, int pageSize, int itemsTotalCount, Collection<E> items) {
		this.setItemsTotalCount(itemsTotalCount); // 这个地方，一定要调用setter方法才能给字段赋初值么？
		this.setPageNum(pageNum);
		this.setPageSize(pageSize);
		this.setItems(items);
		this.initShowNum();
	}

	public int getShowPage() {
		return showPage;
	}

	public void setShowPage(int showPage) {
		this.showPage = showPage;
	}

	@Override
	public void setItemsTotalCount(int itemsTotalCount) {
		super.setItemsTotalCount(itemsTotalCount);
		initShowNum();
	}

	/**
	 * 不知道这里是干嘛的，先过了吧，后面结合到业务再看这一段的源码
	 */
	private void initShowNum() {
		int startIndex;
		int endIndex;
		if (pageNum - showPage / 2 > 1) {
			startIndex = pageNum - showPage / 2;
			endIndex = pageNum + showPage / 2 - 1;
			if (endIndex > pageTotalCount) {
				endIndex = pageTotalCount;
				startIndex = endIndex - showPage + 1;
			}
		} else {
			startIndex = 1;
			endIndex = pageTotalCount <= showPage ? pageTotalCount : showPage;
		}
		for (int i = startIndex; i <= endIndex; i++) {
			this.showNums.add(Integer.valueOf(i));
		}
		if (this.firstPage || this.lastPage) {
			showDot = false;
		} else {
			if (showNums.size() > 0) {
				if (showNums.get(showNums.size() - 1) == this.pageTotalCount) {
					showDot = false;
				}
			}
		}
	}

	public List<Integer> getShowNums() {
		return showNums;
	}

	public boolean getShowDot() {
		return showDot;
	}

	public int getPageTotalCount() {
		return this.pageTotalCount;
	}
}
