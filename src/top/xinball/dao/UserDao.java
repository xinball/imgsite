package top.xinball.dao;

import top.xinball.bean.User;

public interface UserDao {
    /**
     * 根据编号查询用户信息
     * @param uid 编号
     * @return 如果返回 null,说明没有这个用户。反之亦然
     */
    public User queryByUid(String uid);
    /**
     * 根据用户名查询用户信息
     * @param name 用户名
     * @return 如果返回 null,说明没有这个用户。反之亦然
     */
    public User queryByName(String name);
    /**
     * 根据手机号查询用户信息
     * @param tel 手机号
     * @return 如果返回 null,说明没有这个用户。反之亦然
     */
    public User queryByTel(String tel);
    /**
     * 根据 用户名和密码查询用户信息
     * @param name 用户名
     * @param pwd 密码
     * @return 如果返回 null,说明用户名或密码错误,反之亦然
     */
    public User queryByNamePwd(String name,String pwd);

    /**
     * 根据 手机号码和密码查询用户信息
     * @param tel 用户名
     * @param pwd 密码
     * @return 如果返回 null,说明用户名或密码错误,反之亦然
     */
    public User queryByTelPwd(String tel,String pwd);
    /**
     * 保存用户信息
     * @param user 用户
     * @return 返回-1 表示操作失败，其他是 sql 语句影响的行数
     */
    public int save(User user);
    public int update(User user);
}
