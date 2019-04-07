package com.hhtholy.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author hht
 * @create 2019-04-06 10:54
 */
public class ReadProperties {
    public static String getPropertyValue(String key,String path) throws IOException {
            Properties properties = new Properties();
            InputStream in = ReadProperties.class.getClassLoader().getResourceAsStream(path); // 使用ClassLoader加载properties配置文件生成对应的输入流
            properties.load(in); // 使用properties对象加载输入流
            String value = properties.getProperty(key);
            return value;
    }
}
