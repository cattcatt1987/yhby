package com.yinghua.translation.umpush.utility;

import com.yinghua.translation.umpush.constants.Environments;
import com.yinghua.translation.umpush.model.MsgPkgAndroid;
import com.yinghua.translation.umpush.model.MsgPkgiOS;
import com.yinghua.translation.umpush.push.AndroidNotification;
import com.yinghua.translation.umpush.push.IOSNotification;
import com.yinghua.translation.umpush.push.android.AndroidBroadcast;
import com.yinghua.translation.umpush.push.android.AndroidCustomizedcast;
import com.yinghua.translation.umpush.push.ios.IOSBroadcast;
import com.yinghua.translation.umpush.push.ios.IOSCustomizedcast;
import net.sf.json.JSONObject;

import java.util.Map;

/**
 * Created by fengshuai on 15/6/24.
 */
public class MsgSender
{

    public static synchronized MsgSender defaultSender()
    {
        return new MsgSender();
    }

    public UMResponse sendAndroidUnicast(String userId, MsgPkgAndroid msgPkgAndroid,boolean isProduction)
    {
        UMResponse umResponse=new UMResponse();

        try
        {
            AndroidCustomizedcast androidCustomizedcast = new AndroidCustomizedcast();
            androidCustomizedcast.setPredefinedKeyValue("production_mode", isProduction?"true":"false");
            androidCustomizedcast.setPredefinedKeyValue("alias", userId);
            androidCustomizedcast.setPredefinedKeyValue("alias_type", "8f8");
            this.fullfillAndroidNotification(androidCustomizedcast,msgPkgAndroid);

            JSONObject jsonObject= androidCustomizedcast.send();

            if(jsonObject!=null)
                this.fullfillResponse(umResponse,jsonObject);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            umResponse.setRet("FAIL");
            umResponse.setTask_id("");
            umResponse.setMsg_id("");
            umResponse.setError_code("9999");
            umResponse.setError_msg(ex.toString());
        }

        return umResponse;
    }

    public UMResponse sendAndroidBroadcast(MsgPkgAndroid msgPkgAndroid,boolean isProduction)
    {
        UMResponse umResponse=new UMResponse();

        try
        {
            AndroidBroadcast broadcast = new AndroidBroadcast();
            broadcast.setPredefinedKeyValue("production_mode", isProduction?"true":"false");
            this.fullfillAndroidNotification(broadcast,msgPkgAndroid);

            JSONObject jsonObject= broadcast.send();

            if(jsonObject!=null)
                this.fullfillResponse(umResponse,jsonObject);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            umResponse.setRet("FAIL");
            umResponse.setTask_id("");
            umResponse.setMsg_id("");
            umResponse.setError_code("9999");
            umResponse.setError_msg(ex.toString());
        }
        return umResponse;
    }

    public UMResponse sendiOSUnicast(String userId, MsgPkgiOS msgPkgiOS,boolean isProduction)
    {
        UMResponse umResponse=new UMResponse();

        try
        {
            IOSCustomizedcast customizedcast = new IOSCustomizedcast();
            customizedcast.setPredefinedKeyValue("production_mode", isProduction?"true":"false");
            customizedcast.setPredefinedKeyValue("alias", userId);
            customizedcast.setPredefinedKeyValue("alias_type", Environments.SDK_ALIAS_TYPE);
            this.fullfillIOSNotification(customizedcast, msgPkgiOS);

            JSONObject jsonObject= customizedcast.send();

            if(jsonObject!=null)
                this.fullfillResponse(umResponse,jsonObject);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            umResponse.setRet("FAIL");
            umResponse.setTask_id("");
            umResponse.setMsg_id("");
            umResponse.setError_code("9999");
            umResponse.setError_msg(ex.toString());
        }
        return umResponse;
    }

    public UMResponse sendiOSBroadcast(MsgPkgiOS msgPkgiOS,boolean isProduction)
    {
        UMResponse umResponse=new UMResponse();

        try
        {
            IOSBroadcast broadcast = new IOSBroadcast();
            broadcast.setPredefinedKeyValue("production_mode", isProduction?"true":"false");
            this.fullfillIOSNotification(broadcast, msgPkgiOS);

            JSONObject jsonObject= broadcast.send();

            if(jsonObject!=null)
                this.fullfillResponse(umResponse,jsonObject);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            umResponse.setRet("FAIL");
            umResponse.setTask_id("");
            umResponse.setMsg_id("");
            umResponse.setError_code("9999");
            umResponse.setError_msg(ex.toString());
        }
        return umResponse;
    }

    private void fullfillAndroidNotification(AndroidNotification androidNotification,MsgPkgAndroid msgPkgAndroid) throws Exception
    {
        androidNotification.setAppMasterSecret(Environments.SDK_Android_SECRET_KEY);
        androidNotification.setPredefinedKeyValue("appkey", Environments.SDK_Android_API_KEY);
        androidNotification.setPredefinedKeyValue("timestamp",  Integer.toString((int)(System.currentTimeMillis() / 1000)));
        androidNotification.setPredefinedKeyValue("ticker", msgPkgAndroid.getTicker());
        androidNotification.setPredefinedKeyValue("title",  msgPkgAndroid.getTitle());
        androidNotification.setPredefinedKeyValue("text",   msgPkgAndroid.getText());
        androidNotification.setPredefinedKeyValue("after_open", msgPkgAndroid.getAfter_open());
        androidNotification.setPredefinedKeyValue("url", msgPkgAndroid.getUrl());
        androidNotification.setPredefinedKeyValue("activity", msgPkgAndroid.getActivity());
        androidNotification.setPredefinedKeyValue("custom", msgPkgAndroid.getCustom());
        androidNotification.setPredefinedKeyValue("display_type", "notification");
        androidNotification.setPredefinedKeyValue("description",msgPkgAndroid.getText());
        androidNotification.setPredefinedKeyValue("thirdparty_id",Environments.SDK_ALIAS_TYPE);
        // Set customized fields

        if(msgPkgAndroid.getExtra()!=null&&msgPkgAndroid.getExtra().size()>0)
            for(Map.Entry<String,String> entry:msgPkgAndroid.getExtra().entrySet())
            {
                androidNotification.setExtraField(entry.getKey(), entry.getValue());
            }
    }

    private void fullfillIOSNotification(IOSNotification iosNotification,MsgPkgiOS msgPkgiOS) throws Exception
    {
        iosNotification.setAppMasterSecret(Environments.SDK_iOS_SECRET_KEY);
        iosNotification.setPredefinedKeyValue("appkey", Environments.SDK_iOS_API_KEY);
        iosNotification.setPredefinedKeyValue("timestamp",  Integer.toString((int)(System.currentTimeMillis() / 1000)));
        iosNotification.setPredefinedKeyValue("alert", msgPkgiOS.getAlert());
        iosNotification.setPredefinedKeyValue("badge", msgPkgiOS.getBadge());
        iosNotification.setPredefinedKeyValue("sound", msgPkgiOS.getSound()!=null?msgPkgiOS.getSound():"default");
        iosNotification.setPredefinedKeyValue("description",msgPkgiOS.getAlert());
        iosNotification.setPredefinedKeyValue("thirdparty_id",Environments.SDK_ALIAS_TYPE);

        if(msgPkgiOS.getMisc()!=null&&msgPkgiOS.getMisc().size()>0)
            for(Map.Entry<String,String> entry:msgPkgiOS.getMisc().entrySet())
            {
                iosNotification.setCustomizedField(entry.getKey(), entry.getValue());
            }
    }

    private void fullfillResponse(UMResponse response,JSONObject jsonObject)
    {
        response.setRet(jsonObject.getString("ret"));

        if(jsonObject.getJSONObject("data").containsKey("error_code"))
            response.setError_code(jsonObject.getJSONObject("data").getString("error_code"));
        else
            response.setError_code("0");

        if(jsonObject.getJSONObject("data").containsKey("msg_id"))
            response.setMsg_id(jsonObject.getJSONObject("data").getString("msg_id"));

        if(jsonObject.getJSONObject("data").containsKey("task_id"))
            response.setTask_id(jsonObject.getJSONObject("data").getString("task_id"));
    }

}
