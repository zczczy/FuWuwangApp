package com.zczczy.leo.fuwuwangapp.model;

import java.util.List;

/**
 * Created by leo on 2015/6/2.
 */
public class PagerResult<T> {
    // / <summary>
    // / 页号
    // / </summary>
    public int PageIndex;
    // / <summary>
    // / 页大小
    // / </summary>
    public int PageSize;
    // / <summary>
    // / 总行数
    // / </summary>
    public int RowCount;
    // / <summary>
    // / 总页数
    // / </summary>
    public int PageCount;
    // / <summary>
    // / 当前页数据
    // / </summary>
    public List<T> ListData;
}
