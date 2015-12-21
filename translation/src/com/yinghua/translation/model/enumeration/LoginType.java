package com.yinghua.translation.model.enumeration;

public enum LoginType
{

	WEIXIN("微信", 1),
	QQ("QQ", 2),
	BLOG("博客", 3);
	

	private String name;
	private int index;

	// 构造方法
	private LoginType(String name, int index)
	{
		this.name = name;
		this.index = index;
	}

	public static LoginType getOrderStatus(int index)
	{
		LoginType us = null;
		switch (index)
		{
			case 1:
				us = LoginType.WEIXIN;
				break;
			case 2:
				us = LoginType.QQ;
				break;
			case 3:
				us = LoginType.BLOG;
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
