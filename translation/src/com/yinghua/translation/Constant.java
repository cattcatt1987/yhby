package com.yinghua.translation;

import java.util.HashMap;
import java.util.Map;

public class Constant {
	
	public static final Map<String,String> call3Map=new HashMap<String,String>();//传递第三方通话号码
	public static final Map<String,String> callerMap=new HashMap<String,String>();//坐席接通状态
	public static final Map<String,String> calleeMap=new HashMap<String,String>();//第三方通话接通状态
	
	public static final String UUCALL_GROUP="22079104_";
	
	public static final Map<String,String> packageNoMap = new HashMap<String,String>();
	static {
		//应急1到7
		packageNoMap.put("00000000001", "1001");
		packageNoMap.put("00000000002", "1001");
		packageNoMap.put("00000000003", "1001");
		packageNoMap.put("00000000004", "1001");
		packageNoMap.put("00000000005", "1001");
		packageNoMap.put("00000000006", "1001");
		packageNoMap.put("00000000007", "1001");
		
		//生活11到17
		packageNoMap.put("00000000011", "1002");
		packageNoMap.put("00000000012", "1002");
		packageNoMap.put("00000000013", "1002");
		packageNoMap.put("00000000014", "1002");
		packageNoMap.put("00000000015", "1002");
		packageNoMap.put("00000000016", "1002");
		packageNoMap.put("00000000017", "1002");
//		packageNoMap.put("00000000015", "1002");
	}
	
	
}
