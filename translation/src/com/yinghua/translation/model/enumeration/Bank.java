package com.yinghua.translation.model.enumeration;

/**
 * 
* @ClassName: Bank 
* @Description: 银行简称 
* @author 
* @date 2015年1月17日 上午11:07:45 
*
 */
public enum Bank
{
	ICBC("工商",1),
	BOC("中国",2),
	CMB("招商",3),
	GDB("广发",4),
	HXB("华夏",5),
	SPDB("浦发",6),
	CEB("光大",7),
	RCB("北京农商",8),
	CIB("兴业",9),
	CCB("建设",10),
	ABC("农业",11),
	BCM("交通",12),
	CNCB("中信",13),
	CMBC("民生",14),
	PINGAN("平安",15),
	BOB("北京",16),
	BOS("上海",17),
	PSBC("邮政储蓄",18);

	private String name;
	private int index;

	// 构造方法
	private Bank(String name, int index)
	{
		this.name = name;
		this.index = index;
	}

	public static Bank getOrderStatus(int index)
	{
		Bank us = null;
		switch (index)
		{
			case 1:
				us = Bank.ICBC;
				break;
			case 2:
				us = Bank.BOC;
				break;
			case 3:
				us = Bank.CMB;
				break;
			case 4:
				us = Bank.GDB;
				break;
			case 5:
				us = Bank.HXB;
				break;
			case 6:
				us = Bank.SPDB;
				break;
			case 7:
				us = Bank.CEB;
				break;
			case 8:
				us = Bank.RCB;
				break;
			case 9:
				us = Bank.CIB;
				break;
			case 10:
				us = Bank.CCB;
				break;
			case 11:
				us = Bank.ABC;
				break;
			case 12:
				us = Bank.BCM;
				break;
			case 13:
				us = Bank.CNCB;
				break;
			case 14:
				us = Bank.CMBC;
				break;
			case 15:
				us = Bank.PINGAN;
				break;
			case 16:
				us = Bank.BOB;
				break;
			case 17:
				us = Bank.BOS;
				break;
			case 18:
				us = Bank.PSBC;
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
