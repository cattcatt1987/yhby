package com.yinghua.translation.model.enumeration;

public enum PackageStatus
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
	 * 锁定
	 */
	LOCK("锁定", 3);

	private String name;
	private int index;

	// 构造方法
	private PackageStatus(String name, int index)
	{
		this.name = name;
		this.index = index;
	}

	public static PackageStatus getOrderStatus(int index)
	{
		PackageStatus us = null;
		switch (index)
		{
			case 0:
				us = PackageStatus.NORMAL;
				break;
			case 1:
				us = PackageStatus.DEL;
				break;
			case 2:
				us = PackageStatus.LOCK;
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
