package com.sayalala.wework.wehook.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.Objects;

public class ImageToBase64 {

    private static String strNetImageToBase64;

    public static void main(String[] args) {
        //第一个:把网络图片装换成Base64
        String netImagePath = "https://bpic.588ku.com/ad_diversion/20/05/12/d9b95089b2879292e8b39317519e0e9f.png!/fw/250/quality/99/unsharp/true/compress/true";
        //下面是网络图片转换Base64的方法
        NetImageToBase64(netImagePath);

    }

    /**
     * 网络图片转换Base64的方法
     *
     * @param netImagePath     
     */
    public static String NetImageToBase64(String netImagePath) {
        final ByteArrayOutputStream data = new ByteArrayOutputStream();
        try {
            // 创建URL
            URL url = new URL(netImagePath);
            final byte[] by = new byte[1024];
            // 创建链接
            final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        InputStream is = conn.getInputStream();
                        // 将内容读取内存中
                        int len = -1;
                        while ((len = is.read(by)) != -1) {
                            data.write(by, 0, len);
                        }
                        // 对字节数组Base64编码
                        Base64.Encoder encoder = Base64.getEncoder();
                        strNetImageToBase64 = encoder.encodeToString(data.toByteArray());
                        // 关闭流
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return strNetImageToBase64;
    }

    /**
     * 本地图片转换Base64的方法
     *
     * @param imgPath     
     */

    public static String ImageToBase64(String imgPath) {
        return ImageToBase64(new File(imgPath));
    }

    /**
     * 本地图片转换Base64的方法
     *    
     */
    public static String ImageToBase64(File imageFile) {
        byte[] data = null;
        // 读取图片字节数组
        try {
            InputStream in = new FileInputStream(imageFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        Base64.Encoder encoder = Base64.getEncoder();
        // 返回Base64编码过的字节数组字符串
        return encoder.encodeToString(Objects.requireNonNull(data));
    }


}
