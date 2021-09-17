package com.sayalala.wework.wehook.utils;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * Copyright @2019 sayalala.com.
 *
 * @author: 黄磊
 * date:  2019/9/2  14:08
 * use to：ID生成工具类
 * modify：
 */
public class IDUtils {

    /**
     * 图片名生成
     */
    public static String genImageName() {
        //取当前时间的长整形值包含毫秒
        long millis = System.currentTimeMillis();
        //long millis = System.nanoTime();
        //加上三位随机数
        Random random = new Random();
        int end3 = random.nextInt(999);
        //如果不足三位前面补0
        String str = millis + String.format("%03d", end3);

        return str;
    }

    /**
     * 商品id生成
     */
    public static String genRandom() {
        //取当前时间的长整形值包含毫秒
        long millis = System.currentTimeMillis();
        //long millis = System.nanoTime();
        //加上两位随机数
        Random random = new Random();
        int end2 = random.nextInt(99);
        //如果不足两位前面补0
        String str = millis + String.format("%02d", end2);
        long id = new Long(str);
        String timeId = Long.toString(id);
        return timeId;
    }

    /**
     * 生成UUID工具类
     */
    public static String getUUID() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid;
    }


    //    生成时间+随机数的id
    public static String genRandom(String pre, Integer needLength) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
        String part2 = format.format(new Date());
        int originLength = pre.length() + part2.length();
        if (needLength == null || needLength <= originLength) {
            needLength = pre.length() + part2.length();
        }
        StringBuffer part3 = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < needLength - originLength; i++) {
            int rand = random.nextInt(10);
            part3.append(rand);
        }
        return pre + "" + part2 + "" + part3;
    }


    //    生成20位随机id
    public static Long genRandomId() {
        long timeMillis = System.currentTimeMillis();
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < 6; i++) {
            buffer.append(random.nextInt(10));
        }
        String id = timeMillis + buffer.toString();
        return Long.valueOf(id);
    }

    //    生成时间+随机数的id
    public static String genRandom(String pre,int needLength) {
        if (needLength <= pre.length()) {
            needLength = pre.length();
        }
        StringBuffer part3 = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < needLength - pre.length(); i++) {
            int rand = random.nextInt(10);
            part3.append(rand);
        }
        return pre  + "" + part3;
    }

    public static void main(String[] args) {

    }

    public static String genSMSCode() {
        String value = "";
        Random random = new Random();
        int gen = random.nextInt(2);
        String charOrNum = gen % 2 == 0 ? "char" : "num";
        if ("char".equals(charOrNum)) {
            //字符
            int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
            int ascii = random.nextInt(26);
            value += (char) (ascii + temp);
        } else if ("num".equals(charOrNum)) {
            //是数字
            value += String.valueOf(random.nextInt(10));
        }
        return value;
    }

    public static String genSMSCode_num() {
        String value = "";
        Random random = new Random();
        while (true) {
            value += String.valueOf(random.nextInt(10));
            if (value.length() == 6) {
                break;
            }
        }
        return value;

    }

    public static String genOssKey(String originalFilename) {
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        return getUUID() + suffix;
    }

}