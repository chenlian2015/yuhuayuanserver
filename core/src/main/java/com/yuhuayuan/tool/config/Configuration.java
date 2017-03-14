package com.yuhuayuan.tool.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by cl on 2017/3/11.
 */
public class Configuration {

    private static final Logger logger = LoggerFactory.getLogger(Configuration.class);
    private static final Properties evnProperties = new Properties();
    private static final String CONFIGURATION_NAME = "config.properties";

    static {
        init();
    }

    private Configuration() {
        throw new UnsupportedOperationException("illegal!");
    }

    private static void init() {
        load();
    }

    private static void load() {
        InputStream in = null;

        //优先读取conf 目录下的配置文件， 服务器方便调试
        try {
            in = new FileInputStream("./resources/" + CONFIGURATION_NAME);
            logger.debug("resources at ./resources/{}", CONFIGURATION_NAME);
        } catch (Exception e) {
            //logger.info("", e);
        }

        try {
            if (in == null) {
                in = Configuration.class.getClassLoader().getResourceAsStream(CONFIGURATION_NAME);
                logger.debug("resources at ./{}", CONFIGURATION_NAME);
            }

            if (in == null)
                throw new RuntimeException("evn config failed!");

            evnProperties.load(in);
        } catch (Exception e) {
            logger.info("", e);
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (Exception e) {
                logger.info("", e);
            }
        }
    }

    public static String getValue(final String key) {
        String r = evnProperties.getProperty(key);
        if (r == null)
            r = "";
        return r;
    }

    /**
     * 获取所有配置
     *
     * @return
     */
    protected static Map<String, String> getALL() {
        Map<String, String> keys = new HashMap<String, String>();
        Enumeration<Object> enums = evnProperties.keys();
        while (enums.hasMoreElements()) {
            Object key = enums.nextElement();
            keys.put(String.valueOf(key), getValue(String.valueOf(key)));
        }
        return keys;
    }

}
