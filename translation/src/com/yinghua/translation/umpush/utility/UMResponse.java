package com.yinghua.translation.umpush.utility;

/**
 * Created by fengshuai on 15/6/24.
 */
public class UMResponse
{
    private String ret;
    private String msg_id;
    private String task_id;
    private String error_code;
    private String error_msg;

    public String getRet()
    {
        return ret;
    }

    public void setRet(String ret)
    {
        this.ret = ret;
    }

    public String getMsg_id()
    {
        return msg_id;
    }

    public void setMsg_id(String msg_id)
    {
        this.msg_id = msg_id;
    }

    public String getTask_id()
    {
        return task_id;
    }

    public void setTask_id(String task_id)
    {
        this.task_id = task_id;
    }

    public String getError_code()
    {
        return error_code;
    }

    public void setError_code(String error_code)
    {
        this.error_code = error_code;
    }

    public boolean isSuccess()
    {
        return this.getRet()!=null&&this.getRet().equalsIgnoreCase("SUCCESS");
    }

    public String getError_msg()
    {
        return error_msg;
    }

    public void setError_msg(String error_msg)
    {
        this.error_msg = error_msg;
    }
}
