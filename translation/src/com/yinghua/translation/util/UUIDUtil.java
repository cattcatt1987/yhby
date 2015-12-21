package com.yinghua.translation.util;

import java.sql.Timestamp;
import java.util.UUID;

import org.apache.log4j.Logger;

/**
 * 内核实用方法
 */
public class UUIDUtil {
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(UUIDUtil.class);

	private UUIDUtil() {
	}

	/**
	 * 生成排序号
	 */
	public static Long generateSortIdx() {
		return System.currentTimeMillis();
	}

	/**
	 * 生成当前日间戳
	 */
	public static Timestamp generateTimestamp() {
		return new java.sql.Timestamp(System.currentTimeMillis());
	}

	/**
	 * 生成字符串型的UUID
	 */
	public static String genUUIDString() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}
