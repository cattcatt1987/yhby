package com.yinghua.translation.model.enumeration;

/**
 * 用户状态
 * 
 * @author allan
 *
 */
public enum MemberStatus
{

	/**
	 * 正常
	 */
	NORMAL("正常", 1),
	/**
	 * 停用
	 */
	DEL("停用", 2),
	/**
	 *未认证
	 */
	UNAUTH("未认证", 3),
	/**
	 * 未绑定手机号
	 */
	UNBOUND("未绑定手机号", 4),
	/**
	 * 锁定
	 */
	LOCK("锁定", 5);

	private String name;
	private int index;

	// 构造方法
	private MemberStatus(String name, int index)
	{
		this.name = name;
		this.index = index;
	}

	public static MemberStatus getOrderStatus(int index)
	{
		MemberStatus us = null;
		switch (index)
		{
			case 0:
				us = MemberStatus.NORMAL;
				break;
			case 1:
				us = MemberStatus.DEL;
				break;
			case 2:
				us = MemberStatus.UNAUTH;
				break;
			case 3:
				us = MemberStatus.UNBOUND;
				break;
			default:
				us = MemberStatus.LOCK;
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
