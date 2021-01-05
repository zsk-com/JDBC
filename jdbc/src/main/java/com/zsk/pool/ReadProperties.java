package com.zsk.pool;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;
public class ReadProperties {
    //读取配置文件并保存在缓存里
    private static Properties ptiesroper = new Properties();//本质为map集合
    private static HashMap<String, Object> userBox = new HashMap();
    static {
        try {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("configure.properties");
            ptiesroper.load(is);
           Enumeration keyName= ptiesroper.propertyNames();//获取所有key
            while (keyName.hasMoreElements()) {//判断是否有下一个元素
                String key = (String) keyName.nextElement();
                String value=ptiesroper.getProperty(key);
                userBox.put(key,value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //提供获取value值的方法
    public static Object getValues(String key){
        return userBox.get(key);
    }
}

