package com.yinghua.translation.model.enumeration;

/**
 * 
 * @ClassName: BusinessType
 * @Description: 商旅类型
 * @author
 * @date 2015年1月17日 上午10:51:30
 *
 */
public enum Gender
{
	MALE("男", 0),
	FEMALE("女", 1);

	private String name;
	private int index;

	// 构造方法
	private Gender(String name, int index)
	{
		this.name = name;
		this.index = index;
	}

	public static Gender getGender(int index)
	{
		Gender us = null;
		switch (index)
		{
			case 0:
				us = Gender.MALE;
				break;
			case 1:
				us = Gender.FEMALE;
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
