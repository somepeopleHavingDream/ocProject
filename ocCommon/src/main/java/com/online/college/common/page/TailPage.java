package com.online.college.common.page;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 分页
 *
 * @author yx
 * @createtime 2019/04/20 15:57
 */
@Getter
@Setter
@ToString
public class TailPage<E> extends AbstractPage<E> {

    private int showPage = 10;    // 显示10个页码
    private List<Integer> showNums = new ArrayList<>();
    private boolean showDot = true;    // 不知道这个属性是用来干嘛的？好像是用来高亮显示当前页号按钮的。

    public TailPage() {
    }

    /**
     * 构造函数，将一个已有的分页对象中的分页参数，设置给自己，items需独立设置
     */
    public TailPage(Page<E> page, Collection<E> items, int itemsTotalCount) {
        this(page.getPageNum(), page.getPageSize(), itemsTotalCount, items);
    }

    public TailPage(int pageNum, int pageSize, int itemsTotalCount, Collection<E> items) {
        this.setItemsTotalCount(itemsTotalCount); // 这个地方，一定要调用setter方法才能给字段赋初值么？
        this.setPageNum(pageNum);
        this.setPageSize(pageSize);
        this.setItems(items);    // 此语句在setItems方法中设置了首页尾页标记
        this.initShowNum();
    }

    @Override
    public void setItemsTotalCount(int itemsTotalCount) {
        super.setItemsTotalCount(itemsTotalCount);

        System.out.println("itemsTotalCount: " + itemsTotalCount);
        System.out.println("pageTotalCount: " + pageTotalCount);
        System.out.println("pageNum: " + pageNum);
        System.out.println("pageSize: " + pageSize);
        System.out.println("firstPage: " + firstPage);
        System.out.println("lastPage: " + lastPage);

        initShowNum();

        System.out.println("showPage: " + showPage);
        System.out.println("showDot: " + showDot);
    }

    /**
     * 对页码做出一些设置，这也是为什么这个类叫做“TailPage”的原因。
     */
    private void initShowNum() {
        int startIndex;
        int endIndex;
        if (pageNum - showPage / 2 > 1) {    // 这一部分的作用是使当前页面的光标始终显示在中间位置的设置
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
            this.showNums.add(i);    // 将页码装箱存入页码集合中
        }
        if (this.firstPage || this.lastPage) {
            showDot = false;    // showDot是用来标识当前的光标在哪个页码上的吗？后面再看吧。似乎当从首页点击课程按钮进入到list页面时，接下来的一系列程序中就会执行这段代码，也就是showDot被设置成false
        } else {
            if (showNums.size() > 0) {
                if (showNums.get(showNums.size() - 1) == this.pageTotalCount) {
                    showDot = false;
                }
            }
        }
    }

    public int getPageTotalCount() {
        return this.pageTotalCount;
    }
}
