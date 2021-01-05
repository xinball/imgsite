package top.xinball.utils;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JdbcUtils {
    private static String URL = null;
    private static String USER = null;
    private static String PASS = null;
    private static String DRIVER = null;

    static {
        try {
            Properties properties = new Properties();
// 读取 jdbc.properties 属性配置文件
            InputStream inputStream = JdbcUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
// 从流中加载数据
            properties.load(inputStream);
// 创建 数据库连接 池
            URL = properties.getProperty("url");
            USER = properties.getProperty("username");
            PASS = properties.getProperty("password");
            DRIVER = properties.getProperty("driverClassName");
            Class.forName(DRIVER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 获取数据库连接
     * @return 如果返回 null,说明获取连接失败，有值就是获取连接成功
     */
    public static Connection getConn(){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL,USER,PASS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
    /**
     * 关闭连接，放回数据库连接池
     * @param conn
     */
    public static void close(Connection conn){
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

/*
 *  Created by IntelliJ IDEA 2020.1.1 x64
 *  Author: XinBall
 *  Date: 2020/05/23
 *  Time: 23:48
 *  ┏┓     ┏┓
 * ┏┛┻━━━━━┛┻┓
 * ┃    ━    ┃
 * ┃ >     < ┃
 * ┃    -    ┃
 * ┗━┓     ┏━┛
 *   ┃     ┃
 *   ┃     ┃神兽保佑
 *   ┃     ┃代码无bug
 *   ┃     ┗━━━┓
 *   ┃         ┣┓
 *   ┃        ┏┛
 *   ┗┓┓┏━┳┓┏┛
 *    ┃┫┫ ┃┫┫
 *    ┗┻┛ ┗┻┛
 */