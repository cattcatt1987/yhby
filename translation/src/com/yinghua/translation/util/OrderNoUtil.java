package com.yinghua.translation.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderNoUtil extends Thread
{
	private static long orderNum = 0l;
	private static String date;

	/**
	 * 生成订单编号
	 * 
	 * @return
	 */
	public static synchronized String getOrderNo()
	{
		String str = new SimpleDateFormat("yyMMddHHmm").format(new Date(System
				.currentTimeMillis()));
		if (date == null || !date.equals(str))
		{
			date = str;
		}
		orderNum = new Long(RandomNum.getStringNum(4));
		long orderNo = Long.parseLong((date)) * 10000;
		orderNo += orderNum;
		return "OR" + orderNo;
	}
	
	public static synchronized String getOrderNo(String sign)
	{
		String str = new SimpleDateFormat("yyMMddHHmm").format(new Date(System
				.currentTimeMillis()));
		if (date == null || !date.equals(str))
		{
			date = str;
		}
		orderNum = new Long(RandomNum.getStringNum(4));
		long orderNo = Long.parseLong((date)) * 10000;
		orderNo += orderNum;
		return sign + orderNo;
	}
	
	public static void main(String[] args)
	{
		System.out.println(OrderNoUtil.getOrderNo("OR"));
		
	}
}
