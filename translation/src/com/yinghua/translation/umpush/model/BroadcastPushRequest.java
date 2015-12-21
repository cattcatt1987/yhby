package com.yinghua.translation.umpush.model;

/**
 * Created by fengshuai on 15/6/19.
 */
public class BroadcastPushRequest extends PushMessage
{
//    private List<String> userIDList;

    private String noticeTitle;
    private String noticeSummary;
    private String noticeContent;
    private String noticePicUrl;
    private String noticeUrl;

    public String getNoticeTitle()
    {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle)
    {
        this.noticeTitle = noticeTitle;
    }

    public String getNoticeSummary()
    {
        return noticeSummary;
    }

    public void setNoticeSummary(String noticeSummary)
    {
        this.noticeSummary = noticeSummary;
    }

    public String getNoticeContent()
    {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent)
    {
        this.noticeContent = noticeContent;
    }

    public String getNoticePicUrl()
    {
        return noticePicUrl;
    }

    public void setNoticePicUrl(String noticePicUrl)
    {
        this.noticePicUrl = noticePicUrl;
    }

    public String getNoticeUrl()
    {
        return noticeUrl;
    }

    public void setNoticeUrl(String noticeUrl)
    {
        this.noticeUrl = noticeUrl;
    }
}
