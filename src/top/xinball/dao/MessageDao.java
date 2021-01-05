package top.xinball.dao;


import top.xinball.bean.Message;

import java.util.*;

public interface MessageDao {

    /**
     * 根据用户和消息编号查询消息
     * @param uid 用户编号
     * @param mid 消息编号
     * @return 如果返回 null,说明没有这个消息
     */
    public Message queryByUidMid(String uid,String mid);

    /**
     * 根据用户编号查询用户所有消息
     * @param uid 编号
     * @return 如果返回 null,说明没有消息
     */
    public List<Message> queryByUid(String uid);

    /**
     * 根据用户编号和关键词查询消息
     * @param uid 编号
     * @param key 关键词
     * @return 如果返回 null,说明没有消息
     */
    public List<Message> queryByUidKey(String uid,String key);

    /**
     * 查询所有的消息
     * @return 如果返回 null,说明没有消息
     */
    public List<Message> queryAll();


    /**
     * 查询所有的消息
     * @return 如果返回 null,说明没有消息
     */
    public List<Message> queryAllKey(String key);

    /**
     * 保存消息
     * @param message 消息
     * @return 返回-1 表示操作失败，其他是 sql 语句影响的行数
     */
    public int save(Message message);


    /**
     * 更新消息
     * @param message 消息
     * @return 返回-1 表示操作失败，其他是 sql 语句影响的行数
     */
    public int update(Message message);
}
