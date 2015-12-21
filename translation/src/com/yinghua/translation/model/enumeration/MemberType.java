package com.yinghua.translation.model.enumeration;

/**
 * 用户状态
 * 
 * @author allan
 *
 */
public enum MemberType
{

	/**
	 * 普通用户
	 */
	ORDINARY("普通用户", 0),
	/**
	 * VIP用户
	 */
	VIP("VIP用户", 1),
	/**
	 *企业用户
	 */
	ENTERPRISE("企业用户", 2);
	

	private String name;
	private int index;

	// 构造方法
	private MemberType(String name, int index)
	{
		this.name = name;
		this.index = index;
	}

	public static MemberType getOrderStatus(int index)
	{
		MemberType us = null;
		switch (index)
		{
			case 0:
				us = MemberType.ORDINARY;
				break;
			case 1:
				us = MemberType.VIP;
				break;
			case 2:
				us = MemberType.ENTERPRISE;
				break;
		}
		return us;
	}
	
	public static int getMemberType(String name)
	{
		int us = 0;
		switch (name)
		{
			case "ORDINARY":
				us = 0;
				break;
			case "VIP":
				us = 1;
				break;
			case "ENTERPRISE":
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
