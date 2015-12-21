package com.yinghua.translation.umpush.push.android;


import com.yinghua.translation.umpush.push.AndroidNotification;

public class AndroidGroupcast extends AndroidNotification
{
	public AndroidGroupcast() {
		try {
			this.setPredefinedKeyValue("type", "groupcast");	
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}
}
