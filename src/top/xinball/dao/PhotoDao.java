package top.xinball.dao;


import top.xinball.bean.Photo;

import java.util.*;

public interface PhotoDao {
    /**
     * 根据用户、消息、图片编号查询图片
     * @param uid 用户编号
     * @param mid 消息编号
     * @param pid 图片编号
     * @return 如果返回 null,说明没有图片
     */
    Photo queryByUidMidPid(String uid,String mid,String pid);


    /**
     * 根据消息编号查询所有的图片
     * @param uid 用户编号
     * @param mid 消息编号
     * @return 如果返回 null,说明没有图片
     */
    List<Photo> queryByUidMid(String uid,String mid);


    /**
     * 根据用户编号查询所有的图片
     * @param uid 编号
     * @return 如果返回 null,说明没有图片
     */
    List<Photo> queryByUid(String uid);

    /**
     * 根据用户编号消息编号查询所有的图片的个数
     * @param mid 编号
     * @param uid 编号
     * @return 消息图片的个数
     */
    int NumByUidMid(String uid,String mid);

    /**
     * 根据用户编号查询所有的图片的个数
     * @param uid 编号
     * @return 用户图片的个数
     */
    int NumByUid(String uid);

    /**
     * 查询所有的图片的个数
     * @return 所有图片的个数
     */
    int Num();

    /**
     * 保存消息
     * @param photo 图片
     * @return 返回-1 表示操作失败，其他是 sql 语句影响的行数
     */
    int save(Photo photo);


    /**
     * 更新图片
     * @param photo 图片
     * @return 返回-1 表示操作失败，其他是 sql 语句影响的行数
     */
    int update(Photo photo);
}
