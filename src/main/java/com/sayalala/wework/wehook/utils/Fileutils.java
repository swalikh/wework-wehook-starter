package com.sayalala.wework.wehook.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

/**
 * Java原生的API可用于发送HTTP请求，即java.net.URL、java.net.URLConnection，这些API很好用、很常用，
 * 但不够简便；
 * <p>
 * 1.通过统一资源定位器（java.net.URL）获取连接器（java.net.URLConnection） 2.设置请求的参数 3.发送请求
 * 4.以输入流的形式获取返回内容 5.关闭输入流
 */
public class Fileutils {


    private static Logger logger = Logger.getLogger(Fileutils.class.getName());

    /**
     * 下载网络文件
     */
    public static File downloadFile(String urlPath, String folderName, String fileName) throws Exception {

        File folder = new File(folderName);
        if (!folder.exists()) {
            folder.mkdir();
        }
        File file = new File(folder + File.separator + fileName);
        BufferedInputStream bin = null;
        OutputStream out = null;
        try {
            // 统一资源
            URL url = new URL(urlPath);
            // 连接类的父类，抽象类
            URLConnection urlConnection = url.openConnection();
            // http的连接类
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            // 设定请求的方法，默认是GET
            httpURLConnection.setRequestMethod("GET");
            // 设置字符编码
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            // 打开到此 URL 引用的资源的通信链接（如果尚未建立这样的连接）。
            httpURLConnection.connect();
            // 文件大小
            int fileLength = httpURLConnection.getContentLength();
            // 文件名
            String filePathUrl = httpURLConnection.getURL().getFile();
            String fileFullName = filePathUrl.substring(filePathUrl.lastIndexOf(File.separatorChar) + 1);
            fileFullName = fileFullName.substring(fileFullName.lastIndexOf("/") + 1);
            url.openConnection();
            bin = new BufferedInputStream(httpURLConnection.getInputStream());
            out = new FileOutputStream(file);
            int size = 0;
            int len = 0;
            byte[] buf = new byte[1024];
            while ((size = bin.read(buf)) != -1) {
                len += size;
                out.write(buf, 0, size);
            }
            return file;
        } catch (Exception e) {
            throw new RuntimeException(e.toString() + "文件下载异常，请检查下载链接$$" + urlPath);
        } finally {
            if (bin != null) {
                bin.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * 时    间: 2020/5/22 11:54
     * 方法描述：保持最大的文件数量
     * 返回类型：
     * 修改内容：（若修改了请注明修改人，修改时间，修改内容）
     */
    public static void keepTop(File file, int max) {
        File[] files = file.getParentFile().listFiles();
        if (files.length > max) {
            List<File> list = new ArrayList<>();
            for (int i = 0; i < files.length; i++) {
                list.add(files[i]);
            }
            AtomicInteger removeCounter = new AtomicInteger(files.length - max);
            list.stream().sorted(Comparator.comparingLong(File::lastModified)).forEach(ele -> {
                if (removeCounter.get() > 0) {
                    ele.delete();
                }
                removeCounter.getAndDecrement();
            });
        }
    }
}