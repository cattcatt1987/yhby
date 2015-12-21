package com.yinghua.translation.model.enumeration;

public enum ProductStatus
{

	/**
	 * 正常
	 */
	NORMAL("正常", 1),
	/**
	 * 停用
	 */
	DEL("停用", 2);

	private String name;
	private int index;

	// 构造方法
	private ProductStatus(String name, int index)
	{
		this.name = name;
		this.index = index;
	}

	public static ProductStatus getOrderStatus(int index)
	{
		ProductStatus us = null;
		switch (index)
		{
			case 0:
				us = ProductStatus.NORMAL;
				break;
			case 1:
				us = ProductStatus.DEL;
				break;
		}
		return us;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getIndex()
	{
		return index;
	}

	public void setIndex(int index)
	{
		this.index = index;
	}

}
