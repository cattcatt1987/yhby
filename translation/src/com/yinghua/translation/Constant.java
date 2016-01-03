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
		packageNoMap.put("00000000001", "1002");
		packageNoMap.put("00000000002", "1002");
		packageNoMap.put("00000000003", "1002");
		packageNoMap.put("00000000004", "1002");
		packageNoMap.put("00000000005", "1002");
		packageNoMap.put("00000000006", "1002");
		packageNoMap.put("00000000007", "1002");
		packageNoMap.put("00000000008", "1001");
		packageNoMap.put("00000000009", "1001");
		packageNoMap.put("00000000010", "1001");
		packageNoMap.put("00000000011", "1001");
		packageNoMap.put("00000000012", "1001");
		packageNoMap.put("00000000013", "1001");
		packageNoMap.put("00000000014", "1001");
		packageNoMap.put("00000000015", "1001");
	}
}
