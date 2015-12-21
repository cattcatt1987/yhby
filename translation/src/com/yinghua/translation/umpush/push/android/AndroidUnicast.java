package com.yinghua.translation.umpush.push.android;


import com.yinghua.translation.umpush.push.AndroidNotification;

public class AndroidUnicast extends AndroidNotification
{
	public AndroidUnicast() {
		try {
			this.setPredefinedKeyValue("type", "unicast");	
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}
}