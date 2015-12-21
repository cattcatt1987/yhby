package com.yinghua.translation.model.enumeration;

/**
 * 
* @ClassName: FundType 
* @Description: 公积金登录账号类型
* @author fsjohnhuang
* @date 2015年1月17日 上午10:44:01 
*
 */
public enum CredentialsType
{

	IDCARD("身份证",1),
	MILITARY("军官证", 2),
	PASSPORT("护照", 3);

	private String name;
	private int index;

	// 构造方法
	private CredentialsType(String name, int index)
	{
		this.name = name;
		this.index = index;
	}

	public static CredentialsType getCredentialsType(int index)
	{
		CredentialsType us = null;
		switch (index)
		{
			case 1:
				us = CredentialsType.IDCARD;
				break;
			case 2:
				us = CredentialsType.MILITARY;
				break;
			case 3:
				us = CredentialsType.PASSPORT;
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
