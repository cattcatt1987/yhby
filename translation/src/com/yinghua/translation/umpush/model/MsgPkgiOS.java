package com.yinghua.translation.umpush.model;

import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fengshuai on 15/6/19.
 */


//{
//        "aps": {
//        "alert":"Message From Baidu Cloud Push-Service",
//        "sound":"",  //可选
//        "badge":0,    //可选
//        },
//        "key1":"value1",
//        "key2":"value2"
//}

public class MsgPkgiOS
{
    private String alert;
    private String sound;
    private int badge;
    private HashMap<String,String> misc;

    public HashMap<String, String> getMisc()
    {
        return this.misc;
    }

    public void setMisc(HashMap<String, String> misc)
    {
        this.misc = misc;
    }

    public String getAlert()
    {
        return alert;
    }

    public void setAlert(String alert)
    {
        this.alert = alert;
    }

    public String getSound()
    {
        return sound;
    }

    public void setSound(String sound)
    {
        this.sound = sound;
    }

    public int getBadge()
    {
        return badge;
    }

    public void setBadge(int badge)
    {
        this.badge = badge;
    }

    public String generateString()
    {
        JSONObject aps=new JSONObject();
        aps.element("alert",this.alert);
        aps.element("sound",this.sound!=null?this.sound:"default");
        aps.element("badge",this.badge);

        JSONObject jsonObject=new JSONObject();
        jsonObject.put("aps",aps);

        if(this.misc!=null&&this.misc.size()>0)
        {
            for(Map.Entry<String,String> entry:this.misc.entrySet())
            {
                jsonObject.put(entry.getKey(),entry.getValue());
            }
        }

        return jsonObject.toString();
    }
}
