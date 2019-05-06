package com.hhtholy.utils;

import java.io.Serializable;
import java.util.List;

/**
 * @author hht
 * @create 2019-04-04 16:59
 */
public class Page<T> implements Serializable {
    private org.springframework.data.domain.Page<T> page; //SpringdataJpa 的分页对象
    // 分页导航  [8,9,10,11,12]  形如这种  也就是 随着页面的添加 页码跟着变 保持几个页码
    //  举例的 navigatePages =5
    private Integer navigatePages;   //决定导航有多少个页码 [1,2,3,4,5,6,7] 7个

    private int[] navigateNums; //数组 展示页码 [1,2,3,4,5,6,7]

    private Integer totalPage;  //总页数

    private Integer currentPage;  //当前页码

    private Long totals; //总条数

    private Integer size;  // 每页显示的条数

    List<T> content;  // 分页的内容

    boolean isFirst;  //是否为首页

    boolean isLast;  //是否是尾页

    boolean isHasNext; //是否有下页

    boolean isHasPrecious; //是否有上页

    public Page() {//无参数

    }

    public Page(org.springframework.data.domain.Page<T> page, Integer navigatePages) {  //有参构造
        this.page = page;
        this.navigatePages = navigatePages;
        this.totalPage = page.getTotalPages(); //总页数
        this.currentPage = page.getNumber();  //当前页码
        this.totals = page.getTotalElements(); //总条数
        this.size = page.getSize();       // 每页显示的条数
        this.content = page.getContent(); //内容

        this.isFirst = page.isFirst();
        this.isLast = page.isLast();

        this.isHasNext = page.hasNext();
        this.isHasPrecious = page.hasPrevious();

        calcNavigateNums(); //处理导航页码问题
    }

    /**
     *  计算 数组的内容
     */
    private void calcNavigateNums(){
        int[] navigateNums;
        Integer totalPages = getTotalPage(); //总页数
        Integer currentPage = getCurrentPage() + 1; //当前页码
        Integer navigatePages = getNavigatePages(); //导航决定显示多少个页码

        //当总页数 <= 导航决定的个数  类似 [1,2,3,4,5] 总共才5条 导航说6条 那就全部显示了
        if(totalPages <= navigatePages){
            navigateNums = new int[totalPages];
            for (int i = 0; i < totalPages; i++) {
                navigateNums[i] = i + 1; }
        }else{  //大于 导航的话  说明 需要进行变化  类似[1,2,3,4,5] 总共10天 导航说5条 剩下的再处理
            navigateNums = new int[navigatePages];
            int startNum = currentPage - navigatePages / 2; //标记  2 - 5
            int endNum = currentPage + navigatePages / 2;  //标记
            //仿照 百度分页栏
            if(startNum < 1){
                startNum = 1;
                for (int i = 0; i < navigatePages; i++) {
                    navigateNums[i] = startNum;
                    startNum ++;
                }
              }else if(endNum > totalPages){ //已经到末尾了 页
                endNum = totalPages;
                for (int i = navigatePages - 1; i >=0; i--) {
                    navigateNums[i] = endNum;
                    endNum --;
                }
            }else {  //中间的情况  7 - 5 = 2    比如现在是第6页
                for (int i = 0; i < navigatePages; i++) {
                    navigateNums[i] = startNum;
                    startNum ++;
                }

            }
        }// else

        this.navigateNums = navigateNums; //数组指向应用
    }


    public org.springframework.data.domain.Page<T> getPage() {
        return page;
    }

    public void setPage(org.springframework.data.domain.Page<T> page) {
        this.page = page;
    }

    public Integer getNavigatePages() {
        return navigatePages;
    }

    public void setNavigatePages(Integer navigatePages) {
        this.navigatePages = navigatePages;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Long getTotals() {
        return totals;
    }

    public void setTotals(Long totals) {
        this.totals = totals;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }

    public boolean isHasNext() {
        return isHasNext;
    }

    public void setHasNext(boolean hasNext) {
        isHasNext = hasNext;
    }

    public boolean isHasPrecious() {
        return isHasPrecious;
    }

    public void setHasPrecious(boolean hasPrecious) {
        isHasPrecious = hasPrecious;
    }

    public int[] getNavigateNums() {
        return navigateNums;
    }

    public void setNavigateNums(int[] navigateNums) {
        this.navigateNums = navigateNums;
    }


}
