package com.yinghua.translation.umpush.push.ios;


import com.yinghua.translation.umpush.push.IOSNotification;

public class IOSUnicast extends IOSNotification
{
	public IOSUnicast() {
		try {
			this.setPredefinedKeyValue("type", "unicast");	
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}
}
