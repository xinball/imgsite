package top.xinball.utils;


import org.dom4j.*;
import org.dom4j.io.SAXReader;

import java.lang.reflect.*;

public class BeanFactory {

    public static Object getBean(String id){
        try {
            //1.获取document对象
            Document doc=new SAXReader().read(BeanFactory.class.getClassLoader().getResourceAsStream("beans.xml"));

            //2.调用api selectSingleNode(表达式)
            Element beanEle=(Element) doc.selectSingleNode("//bean[@id='"+id+"']");

            //3.获取元素的class属性
            String classValue = beanEle.attributeValue("class");

            //4.通过反射返回实现类的对象
            final Object newInstance = Class.forName(classValue).newInstance();


            //5.判断是否是dao
            if(id.endsWith("Dao")){
                //若是dao 创建代理对象
                Object proxy = Proxy.newProxyInstance(newInstance.getClass().getClassLoader(), newInstance.getClass().getInterfaces(), new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        //判断是否是save方法,若是save方法 打印一句话
                        if("save".equals(method.getName())){
                            System.out.println("执行了dao中的保存操作");
                        }

                        return method.invoke(newInstance, args);
                    }
                });

                return proxy;
            }


            return newInstance;
        }  catch (Exception e) {
            e.printStackTrace();
            System.out.println("获取bean失败");
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(getBean("ProductDao1"));
    }
}
/*
 *  Created by IntelliJ IDEA 2020.1.1 x64
 *  Author: XinBall
 *  Date: 2020/05/25
 *  Time: 15:31
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