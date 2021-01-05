package top.xinball.dao.impl;

import top.xinball.bean.Photo;
import top.xinball.dao.BaseDao;
import top.xinball.dao.PhotoDao;

import java.util.List;

public class PhotoDaoImpl extends BaseDao implements PhotoDao {

    /**
     *
     *
     private String pid;//图片编号：2位
     private String uid;//用户编号：6位
     private String mid;//消息编号：时间12位+随机1位
     private String url;//文件：uid+mid+pid+后缀格式
     private Date uploadTime;//上传时间
     private int display;//显示

     *
     **/

    @Override
    public Photo queryByUidMidPid(String uid, String mid, String pid) {
        String sql = "select * from photo where uid = ? and mid = ? and pid = ?";
        return queryForOne(Photo.class, sql,uid,mid,pid);
    }

    @Override
    public List<Photo> queryByUidMid(String uid,String mid) {
        String sql = "select * from photo where uid = ? and mid = ? order by uploadTime asc";
        return queryForList(Photo.class, sql, uid, mid);
    }

    @Override
    public List<Photo> queryByUid(String uid) {
        String sql = "select * from photo where uid = ? order by uploadTime asc";
        return queryForList(Photo.class, sql, uid);
    }

    @Override
    public int NumByUidMid(String uid, String mid) {
        String sql = "select count(*) from photo where uid = ? and mid = ?";
        return new Long((long)queryForSingleValue(sql, uid, mid)).intValue();
    }

    @Override
    public int NumByUid(String uid) {
        String sql = "select count(*) from photo where uid = ?";
        return new Long((long)queryForSingleValue(sql, uid)).intValue();
    }

    @Override
    public int Num() {
        String sql = "select count(*) from photo";
        return new Long((long)queryForSingleValue(sql)).intValue();    }

    @Override
    public int save(Photo photo) {
        String sql = "insert into photo(`pid`,`uid`,`mid`,`url`,`uploadTime`,`display`) values(?,?,?,?,?,?)";
        return update(sql,photo.getPid(),photo.getUid(),photo.getMid(),photo.getUrl(),photo.getUploadTime(),photo.getDisplay());
    }

    @Override
    public int update(Photo photo) {
        String sql = "update photo set `display`=? where `uid`=? and `mid`=? and `pid`=?";
        return update(sql,photo.getDisplay(),photo.getUid(),photo.getMid(),photo.getPid());
    }

}
