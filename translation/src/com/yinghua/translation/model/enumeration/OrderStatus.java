package com.yinghua.translation.model.enumeration;

public enum OrderStatus
{
	CREATED("待支付", 1),
	CANCELLED("已取消", 2),
	PAY_SUCCESS("支付成功", 3),
	FINISHED("交易成功", 4),
	REFOUNDED("已退款", 5),
	INVALID("已失效", 6);

	private String name;
	private int index;

	// 构造方法
	private OrderStatus(String name, int index)
	{
		this.name = name;
		this.index = index;
	}

	public static OrderStatus getOrderStatus(int index)
	{
		OrderStatus us = null;
		switch (index)
		{
			case 1:
				us = OrderStatus.CREATED;
				break;
			case 2:
				us = OrderStatus.CANCELLED;
				break;
			case 3:
				us = OrderStatus.PAY_SUCCESS;
				break;
			case 4:
				us = OrderStatus.FINISHED;
				break;
			case 5:
				us = OrderStatus.REFOUNDED;
				break;
			case 6:
				us = OrderStatus.INVALID;
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
