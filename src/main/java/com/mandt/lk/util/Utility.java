package com.mandt.lk.util;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Utility {

    private static String path = "./mini/wiremini.txt";
    private static ClassLoader classLoader = Utility.class.getClassLoader();
    private static File file = new File(classLoader.getResource(path).getFile());
    public static String TRUE = "true";
    public static String SPACE = " ";

    /**
     *
     * @param accountNo
     * @return
     * @throws IOException
     */
    public static String getEligibilityFromMiniFile(String accountNo) throws IOException {
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
    public static String validate(String id){
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
    public static boolean isNum(String strNum) {
        boolean ret = true;
        try {

            Double.parseDouble(strNum);

        }catch (NumberFormatException e) {
            ret = false;
        }
        return ret;
    }
}
