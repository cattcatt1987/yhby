package com.yinghua.translation.util;

import java.util.Random;

public class RandomNum
{

	public static String getStringNum(int length)
	{
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++)
		{
			sb.append((char) ('0' + rnd.nextInt(10)));
		}
		String code = sb.toString();
		return code;
	}

	
}
