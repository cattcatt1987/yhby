package com.yinghua.translation.model.enumeration;

public enum OrderUseStatus
{
	PERPARE("待使用", 1),
	USING("已取消", 2),
	INVALID("已失效", 3),
	FINISHED("已用完", 4);
	
	private String name;
	private int index;

	// 构造方法
	private OrderUseStatus(String name, int index)
	{
		this.name = name;
		this.index = index;
	}

	public static OrderUseStatus getOrderStatus(int index)
	{
		OrderUseStatus us = null;
		switch (index)
		{
			case 1:
				us = OrderUseStatus.PERPARE;
				break;
			case 2:
				us = OrderUseStatus.USING;
				break;
			case 3:
				us = OrderUseStatus.INVALID;
				break;
			case 4:
				us = OrderUseStatus.FINISHED;
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
