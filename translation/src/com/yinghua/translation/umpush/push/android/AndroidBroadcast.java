package com.yinghua.translation.umpush.push.android;


import com.yinghua.translation.umpush.push.AndroidNotification;

public class AndroidBroadcast extends AndroidNotification
{
	public AndroidBroadcast() {
		try {
			this.setPredefinedKeyValue("type", "broadcast");	
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}
}
