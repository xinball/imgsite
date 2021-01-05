package top.xinball.bean;

import java.util.*;
public class Photo {
    private String pid;//图片编号：2位
    private String uid;//用户编号：6位
    private String mid;//消息编号：时间12位+随机1位
    private String url;//文件：uid+mid+pid+后缀格式
    private Date uploadTime;//上传时间
    private int display;//可见

    public Photo() {
    }

    public Photo(String pid, String uid, String mid, String url, Date uploadTime, int display) {
        this.pid = pid;
        this.uid = uid;
        this.mid = mid;
        this.url = url;
        this.uploadTime = uploadTime;
        this.display = display;
    }

    @Override
    public String toString() {
        return "{" +
                "\"pid\":\"" + pid + '\"' +
                ", \"uid\":\"" + uid + '\"' +
                ", \"mid\":\"" + mid + '\"' +
                ", \"url\":\"" + url + '\"' +
                ", \"uploadTime\":\"" + uploadTime + '\"' +
                ", \"display\":\"" + display + '\"' +
                '}';
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public int getDisplay() {
        return display;
    }

    public void setDisplay(int display) {
        this.display = display;
    }
}
