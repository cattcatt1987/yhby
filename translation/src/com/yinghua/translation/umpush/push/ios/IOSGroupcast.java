package com.yinghua.translation.umpush.push.ios;


import com.yinghua.translation.umpush.push.IOSNotification;

public class IOSGroupcast extends IOSNotification
{
	public IOSGroupcast() {
		try {
			this.setPredefinedKeyValue("type", "groupcast");	
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}
}
