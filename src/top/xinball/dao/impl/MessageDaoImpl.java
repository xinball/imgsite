package top.xinball.dao.impl;

import top.xinball.bean.Message;
import top.xinball.bean.User;
import top.xinball.dao.BaseDao;
import top.xinball.dao.MessageDao;

import java.util.Date;
import java.util.List;

public class MessageDaoImpl extends BaseDao implements MessageDao {

    @Override
    public Message queryByUidMid(String uid,String mid) {
        String sql = "select * from message where uid = ? and mid = ?";
        return queryForOne(Message.class, sql, uid, mid);
    }

    @Override
    public List<Message> queryByUid(String uid) {
        String sql = "select * from message where uid = ? order by sendTime asc";
        return queryForList(Message.class, sql, uid);
    }

    @Override
    public List<Message> queryByUidKey(String uid, String key) {
        String sql = "select * from message where uid = ? and content like ? order by sendTime asc";
        return queryForList(Message.class, sql, uid, "%"+key+"%");
    }

    @Override
    public List<Message> queryAll() {
        String sql = "select * from message order by sendTime desc";
        return queryForList(Message.class, sql);
    }

    @Override
    public List<Message> queryAllKey(String key) {
        String sql = "select * from message where content like ? order by sendTime desc";
        return queryForList(Message.class, sql, "%"+key+"%");
    }

    @Override
    public int save(Message message) {
        String sql = "insert into message(`mid`,`uid`,`content`,`sendTime`,`display`) values(?,?,?,?,?)";
        return update(sql,message.getMid(),message.getUid(),message.getContent(),message.getSendTime(),message.getDisplay());
    }

    @Override
    public int update(Message message) {
        String sql = "update message set `content`=?,`alterTime`=?,`display`=? where `uid`=? and `mid`=?";
        return update(sql,message.getContent(),message.getAlterTime(),message.getDisplay(),message.getUid(),message.getMid());
    }
}

/**
 *     private String mid;//消息编号：时间12位+随机1位
 *     private String uid;//用户编号：6位
 *     private String content;//消息内容
 *     private Date sendTime;//发送时间
 *     private Date alterTime;//修改时间
 *     private int display;//显示
 * **/
