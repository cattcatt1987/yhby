package com.yinghua.translation.util;

import java.util.ArrayList;
import java.util.List;

public class PageResult<E>
{
	public PageResult()
	{}

	private List<E> list = new ArrayList<E>();
	private int pageNo = 1;
	private int pageSize = 10;
	private int records = 0;
	private int pages = 1;
	private String orderBy = "";
	private String sort = "asc";

	public List<E> getList()
	{
		return list;
	}

	public void setList(List<E> list)
	{
		this.list = list;
	}

	public int getPageNo()
	{
		return pageNo;
	}

	public void setPageNo(int pageNo)
	{
		this.pageNo = pageNo;
	}

	public int getPageSize()
	{
		return (pageSize == 0) ? 10 : pageSize;
	}

	public void setPageSize(int pageSize)
	{
		this.pageSize = pageSize;
	}

	public int getRecords()
	{
		return records;
	}

	public void setRecords(int records)
	{
		this.records = records;
	}

	public int getFirstRec()
	{
		int ret = (this.getPageNo() - 1) * this.getPageSize();
		ret = (ret < 1) ? 0 : ret;
		return ret;
	}

	public String getOrderBy()
	{
		return orderBy;
	}

	public int getPages()
	{
		return pages;
	}

	public void setOrderBy(String orderBy)
	{
		this.orderBy = orderBy;
	}

	public String getSort()
	{
		return sort;
	}

	public void setSort(String sort)
	{
		this.sort = sort;
	}

	public void setPages(int pages)
	{
		this.pages = pages;
	}

}
