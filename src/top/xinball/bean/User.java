package top.xinball.bean;

import java.math.BigDecimal;
import java.util.*;

public class User {
    private String uid;//用户编号：6位
    private String name;//用户名
    private String pwd;//密码
    private String email;//电子邮箱
    private String tel;//手机号码

    private String nickname;//用户昵称
    private String realname;//真名
    private String avatar;//头像
    private String slogan;//个签
    private int sex;//性别
    private Date birthday;//生日
    private String livead;//住址

    private Date regTime;//注册时间
    private String regIp;//注册ip
    private int active;//是否激活

    public User() {
    }

    public User(String uid, String name, String pwd, String email, String tel, String nickname, String realname,String avatar, String slogan, int sex, Date birthday, String livead, Date regTime, String regIp, int active) {
        this.uid = uid;
        this.name = name;
        this.pwd = pwd;
        this.email = email;
        this.tel = tel;
        this.nickname = nickname;
        this.realname = realname;
        this.avatar=avatar;
        this.slogan = slogan;
        this.sex = sex;
        this.birthday = birthday;
        this.livead = livead;
        this.regTime = regTime;
        this.regIp = regIp;
        this.active = active;
    }

    @Override
    public String toString() {
        return "{" +
                "\"uid\":\"" + uid + '\"' +
                ", \"name\":\"" + name + '\"' +
                ", \"pwd\":\"" + pwd + '\"' +
                ", \"email\":\"" + email + '\"' +
                ", \"tel\":\"" + tel + '\"' +
                ", \"nickname\":\"" + nickname + '\"' +
                ", \"realname\":\"" + realname + '\"' +
                ", \"slogan\":\"" + slogan + '\"' +
                ", \"sex\":" + sex +
                ", \"birthday\":\"" + birthday + '\"' +
                ", \"livead\"=\"" + livead + '\"' +
                ", \"regTime\"=" + regTime + '\"' +
                ", \"regIp\":\"" + regIp + '\"' +
                ", \"active\":" + active +
                '}';
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getLivead() {
        return livead;
    }

    public void setLivead(String livead) {
        this.livead = livead;
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    public String getRegIp() {
        return regIp;
    }

    public void setRegIp(String regIp) {
        this.regIp = regIp;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
}
