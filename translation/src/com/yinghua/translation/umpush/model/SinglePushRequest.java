package com.yinghua.translation.umpush.model;

/**
 * Created by fengshuai on 15/6/19.
 */
public class SinglePushRequest extends PushMessage
{
    private String userID;

    public String getUserID()
    {
        return userID;
    }

    public void setUserID(String userID)
    {
        this.userID = userID;
    }
}
