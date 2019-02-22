package com.mandt.lk.util;

import com.mandt.lk.config.CacheConfig;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class Utility implements Util {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Utility.class);
    public static final String TRUE = "true";
    public static final String SPACE = " ";
    public String getPath1() {
        return path1;
    }
    public void setPath1(String path1) {
        this.path1 = path1;
    }


    //1.2.3.4
    @Value("${file.url}")
    private String path1;

    /**
     * this will return a file and it is cached after first time
     * @return
     */
    @Cacheable(CacheConfig.CACHE_TWO)
    public File getFile() {
        ClassLoader classLoader = Utility.class.getClassLoader();
        File file = new File(classLoader.getResource(path1).getFile());
        logger.info("Cache is not used .... ");
        return file;
    }


    /**
     * @param id
     * @return
     */
    public String validate(String id) {
        if (id == null) {
            return "Account can not be null";
        }
        if (id.equals(SPACE)) {
            return TRUE;
        }
        if (id.length() != 15) {
            return "Account number has to be 15 characters";
        }
        if (!isNum(id)) {
            return "Value is Non Numeric";
        }
        return TRUE;
    }

    /**
     * @param strNum
     * @return
     */
    public boolean isNum(String strNum) {
        boolean ret = true;
        try {

            Double.parseDouble(strNum);

        } catch (NumberFormatException e) {
            ret = false;
        }
        return ret;
    }


    //To resolve ${} in @Value
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
