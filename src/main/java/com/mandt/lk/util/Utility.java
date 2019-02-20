package com.mandt.lk.util;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Logger;

@Component
public class Utility {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Utility.class);

    public String getPath1() {
        return path1;
    }

    public void setPath1(String path1) {
        this.path1 = path1;
    }


    //1.2.3.4
    @Value("${file.url}")
    private String path1;
    private ClassLoader classLoader = Utility.class.getClassLoader();
    private File file ;
    @PostConstruct
    public void doLog() {
        file = new File(classLoader.getResource(path1).getFile());
        logger.info("Info message in Utility ");
    }

    public String TRUE = "true";
    public String SPACE = " ";

    /**
     *
     * @param accountNo
     * @return
     * @throws IOException
     */
    public String getEligibilityFromMiniFile(String accountNo) throws IOException {
        Scanner sc = null;
        try {
            sc = new Scanner(file, "UTF-8");
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] sArray = line.split("\\s{2,}");
                if(sArray[0].equals(accountNo)){
                    if(sArray.length == 1) {
                        return SPACE;
                    }
                    return sArray[1];
                }
            }
            // note that Scanner suppresses exceptions
            if (sc.ioException() != null) {
                throw sc.ioException();
            }
        } finally {
            if (sc != null) {
                sc.close();
            }
        }
        return "NA";
    }

    /**
     *
     * @param id
     * @return
     */
    public String validate(String id){
        System.out.println(path1);
        if (id == null) {
            return "Account can not be null";
        }
        if (id.equals(SPACE)) {
            return TRUE;
        }
        if (!(id.length() == 15)) {
            return "Account number has to be 15 characters";
        }
        if (!isNum(id)) {
            return "Value is Non Numeric";
        }
        return TRUE;
    }

    /**
     *
     * @param strNum
     * @return
     */
    public  boolean isNum(String strNum) {
        boolean ret = true;
        try {

            Double.parseDouble(strNum);

        }catch (NumberFormatException e) {
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
