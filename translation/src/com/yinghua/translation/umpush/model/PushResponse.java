package com.yinghua.translation.umpush.model;

/**
 * Created by fengshuai on 15/6/19.
 */
public class PushResponse
{
    private String msgId = null;
    private String errorCode;
    private String errorMsg;

    public String getMsgId()
    {
        return msgId;
    }

    public String getErrorCode()
    {
        return errorCode;
    }

    public String getErrorMsg()
    {
        return errorMsg;
    }

    public void setMsgId(String msgId)
    {
        this.msgId = msgId;
    }

    public void setErrorCode(String errorCode)
    {
        this.errorCode = errorCode;
    }

    public void setErrorMsg(String errorMsg)
    {
        this.errorMsg = errorMsg;
    }
}
