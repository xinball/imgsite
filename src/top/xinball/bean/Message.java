package top.xinball.bean;

import java.util.*;

public class Message {
    private String mid;//消息编号：时间12位+随机1位
    private String uid;//用户编号：6位
    private String content;//消息内容
    private Date sendTime;//发送时间
    private Date alterTime;//修改时间
    private int display;//显示

    public Message() {
    }

    public Message(String mid, String uid, String content, Date sendTime,Date alterTime,int display) {
        this.mid = mid;
        this.uid = uid;
        this.content = content;
        this.sendTime = sendTime;
        this.alterTime = alterTime;
        this.display = display;
    }

    @Override
    public String toString() {
        return "{" +
                "\"mid\":\"" + mid + '\"' +
                ",\"uid\":\"" + uid + '\"' +
                ",\"content\":\"'" + content + '\"' +
                ",\"sendTime\":\"" + sendTime + '\"' +
                ",\"alterTime\":\"" + alterTime + '\"' +
                ",\"display\":\"" + display + '\"' +
                '}';
    }
    public String getMid() {
        return mid;
    }


    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public int getDisplay() {
        return display;
    }

    public void setDisplay(int display) {
        this.display = display;
    }

    public Date getAlterTime() {
        return alterTime;
    }

    public void setAlterTime(Date alterTime) {
        this.alterTime = alterTime;
    }

}
/**

 * **/