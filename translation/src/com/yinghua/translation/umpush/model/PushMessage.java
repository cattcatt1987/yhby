package com.yinghua.translation.umpush.model;

/**
 * Created by fengshuai on 15/6/19.
 */
public class PushMessage
{
    private boolean isProduction;

    private MsgPkgiOS msgPkgiOS;

    private MsgPkgAndroid msgPkgAndroid;

    public MsgPkgiOS getMsgPkgiOS()
    {
        return msgPkgiOS;
    }

    public void setMsgPkgiOS(MsgPkgiOS msgPkgiOS)
    {
        this.msgPkgiOS = msgPkgiOS;
    }

    public MsgPkgAndroid getMsgPkgAndroid()
    {
        return msgPkgAndroid;
    }

    public void setMsgPkgAndroid(MsgPkgAndroid msgPkgAndroid)
    {
        this.msgPkgAndroid = msgPkgAndroid;
    }

    public boolean isProduction()
    {
        return isProduction;
    }

    public void setIsProduction(boolean isProduction)
    {
        this.isProduction = isProduction;
    }
}
