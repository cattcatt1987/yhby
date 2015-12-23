package com.yinghua.translation.model.enumeration;

/**
 * 
* @ClassName: Language 
* @Description: 语方种类
* @author 
* @date 2015年10月17日 上午11:07:45 
*
 */
public enum Language
{
	EN("英语",1),
	AR("阿拉伯语",2),
	RU("俄语",3),
	JA("日语",4),
	KO("韩语",5),
	ES("西语",6),
	FR("法语",7),
	DE("德语",8),
	PT("葡萄牙语",9),
	MSG("消息",10),
	VOIP("VOIP",11),
	CS("客服",12);
	private String name;
	private int index;

	// 构造方法
	private Language(String name, int index)
	{
		this.name = name;
		this.index = index;
	}

	public static Language getLanguageName(int index)
	{
		Language us = null;
		switch (index)
		{
			case 1:
				us = Language.EN;
				break;
			case 2:
				us = Language.AR;
				break;
			case 3:
				us = Language.RU;
				break;
			case 4:
				us = Language.JA;
				break;
			case 5:
				us = Language.KO;
				break;
			case 6:
				us = Language.ES;
				break;
			case 7:
				us = Language.FR;
				break;
			case 8:
				us = Language.DE;
				break;
			case 9:
				us = Language.PT;
				break;
			case 10:
				us = Language.MSG;
				break;
			case 11:
				us = Language.VOIP;
				break;
			case 12:
				us = Language.CS;
				break;
		}
		return us;
	}
	
	public static int getLanguageIndex(String name)
	{
		int us = 0;
		switch (name)
		{
			case "EN":
				us = 1;
				break;
			case "AR":
				us = 2;
				break;
			case "RU":
				us = 3;
				break;
			case "JA":
				us = 4;
				break;
			case "KO":
				us = 5;
				break;
			case "ES":
				us = 6;
				break;
			case "FR":
				us = 7;
				break;
			case "DE":
				us = 8;
				break;
			case "PT":
				us = 9;
				break;
			case "MSG":
				us = 10;
				break;
			case "VOIP":
				us = 11;
				break;
			case "CS":
				us = 12;
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
