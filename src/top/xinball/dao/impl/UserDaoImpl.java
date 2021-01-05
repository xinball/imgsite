package top.xinball.dao.impl;

import top.xinball.bean.*;
import top.xinball.dao.*;

public class UserDaoImpl extends BaseDao implements UserDao {

    @Override
    public User queryByUid(String uid) {
        String sql = "select * from user where uid = ?";
        return queryForOne(User.class, sql, uid);
    }
    @Override
    public User queryByName(String name) {
        String sql = "select * from user where name = ?";
        return queryForOne(User.class, sql, name);
    }
    @Override
    public User queryByTel(String tel){
        String sql = "select * from user where tel = ?";
        return queryForOne(User.class, sql, tel);
    }
    @Override
    public User queryByNamePwd(String name, String pwd) {
        String sql = "select * from user where name = ? and pwd = ?";
        return queryForOne(User.class, sql, name,pwd);
    }
    @Override
    public User queryByTelPwd(String tel,String pwd){
        String sql = "select * from user where tel = ? and pwd = ?";
        return queryForOne(User.class, sql, tel,pwd);
    }
    @Override
    public int save(User user) {
        String sql = "insert into user(`uid`,`name`,`pwd`,`email`,`tel`,`regtime`,`regip`,`sex`,`nickname`,`realname`,`avatar`,`slogan`,`birthday`,`livead`,`active`) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        return update(sql,user.getUid(),user.getName(),user.getPwd(),user.getEmail(),user.getTel(),user.getRegTime(),user.getRegIp(),user.getSex(),user.getNickname(),user.getRealname(),user.getAvatar(),user.getSlogan(),user.getBirthday(),user.getLivead(),user.getActive());
    }
    @Override
    public int update(User user){
        String sql = "update user set `name`=?,`pwd`=?,`email`=?,`tel`=?,`nickname`=?,`avatar`=?,`sex`=?,`realname`=?,`slogan`=?,`birthday`=?,`livead`=? where uid=?";
        return update(sql,user.getName(),user.getPwd(),user.getEmail(),user.getTel(),user.getNickname(),user.getAvatar(),user.getSex(),user.getRealname(),user.getSlogan(),user.getBirthday(),user.getLivead(),user.getUid());
    }
}

/**
 *     private String uid;//用户编号：6位
 *     private String name;//用户名
 *     private String pwd;//密码
 *     private String email;//电子邮箱
 *     private String tel;//手机号码
 *
 *     private String nickname;//用户昵称
 *     private String realname;//真名
 *     private String slogan;//个签
 *     private int sex;//性别
 *     private Date birthday;//生日
 *     private String livead;//住址
 *
 *     private Date regtime;//注册时间
 *     private String regip;//注册ip
 *     private int active;//是否激活
 */