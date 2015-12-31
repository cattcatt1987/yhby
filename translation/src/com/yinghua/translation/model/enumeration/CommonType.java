package com.yinghua.translation.model.enumeration;

/**
 * 
* @ClassName: CommonType 
* @Description: 通用数据类型
*
 */
public enum CommonType
{

	FAQ("常见问题",1),
	MSG("消息", 2);

	private String name;
	private int index;

	// 构造方法
	private CommonType(String name, int index)
	{
		this.name = name;
		this.index = index;
	}

	public static CommonType getCommonType(int index)
	{
		CommonType us = null;
		switch (index)
		{
			case 1:
				us = CommonType.FAQ;
				break;
			case 2:
				us = CommonType.MSG;
				break;
		}
		return us;
	}
	
	public static int getCommonIndex(String name)
	{
		int us = 0;
		switch(name){
			case "FAQ":
				us = 1;
				break;
			case "MSG":
				us = 2;
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
