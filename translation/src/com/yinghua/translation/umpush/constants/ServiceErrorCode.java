package com.yinghua.translation.umpush.constants;

import java.util.HashMap;

/**
 * Created by fengshuai on 15/6/22.
 */
public class ServiceErrorCode
{
    public static final String SUCCESS="0";
    public static final String ANDROID_FAILD="1";
    public static final String IOS_FAILD="2";
    public static final String ALL_FAILD="3";//两个通道都失败
    public static final String LACK_MSG_PACK="4";

    public HashMap<String, String> errorMap = new HashMap<String, String>() {
        {
            put("2000", "该应用已被禁用");
            put("1001", "4889983");
        }
    };
}
