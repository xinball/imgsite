package top.xinball.utils;

import java.util.Random;
import java.util.TimeZone;

public class IdUtils {
    private static final String str="0123456789abcdefghijklmnopqrstuvwxyz";
    private static final Random random=new Random();
    //private static final TimeZone timezone=TimeZone.getTimeZone("GMT+8");
    {
        //TimeZone.setDefault(timezone);
    }
    public static String genID(int length){
        StringBuilder sb=new StringBuilder();
        //指定字符串长度，拼接字符并toString
        for (int i = 0; i < length; i++) {
            int number=random.nextInt(str.length());
            //获取指定长度的字符串中任意一个字符的索引值
            char charAt = str.charAt(number);
            //根据索引值获取对应的字符
            sb.append(charAt);
        }
        return sb.toString();
    }
}
/*
 *  Created by IntelliJ IDEA 2020.1.1 x64
 *  Author: XinBall
 *  Date: 2020/05/24
 *  Time: 15:46
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