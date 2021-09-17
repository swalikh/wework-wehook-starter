package com.sayalala.wework.wehook.entity;


import com.sayalala.wework.wehook.utils.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class WeWorkWebhookMessage {

    private String webhook;

    private String msgtype;

    private Text text;

    private Markdown markdown;

    private Image image;

    private News news;


    @Data
    @Accessors(chain = true)
    public static class Text {
        private String content;
        private List<String> mentioned_list;
    }

    @Data
    @Accessors(chain = true)
    public static class Markdown {
        private String content;
    }

    @Data
    @Accessors(chain = true)
    public static class Image {
        private String base64;
        private String md5;
    }

    @Data
    @Accessors(chain = true)
    public static class News {
        private List<Article> articles;
    }


    /**
    *  @author swalikh on 2021/9/16 10:42
    *  @type   发送网络图片或者本地图片都可以
    *  @desc
    */
    public static WeWorkWebhookMessage buildImageMessage(String imagePath) {
        File file;
        try {
            WeWorkWebhookMessage message = new WeWorkWebhookMessage();
            message.setMsgtype("image");
            WeWorkWebhookMessage.Image image = new WeWorkWebhookMessage.Image();
            if (imagePath.startsWith("http")) {
                file = Fileutils.downloadFile(imagePath, "image", IDUtils.genRandom("image-", 15));
                image.setBase64(ImageToBase64.ImageToBase64(file));
                image.setMd5(MD5Utils.getFileMD5String(file));
                message.setImage(image);
                Fileutils.keepTop(file,1);
            } else {
                file = new File(imagePath);
                image.setBase64(ImageToBase64.ImageToBase64(file));
                image.setMd5(MD5Utils.getFileMD5String(file));
                message.setImage(image);
            }
            return message;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
    *  @author swalikh on 2021/9/16 10:46
    *  @type   构建markdown消息
    *  @desc
    */
    public static WeWorkWebhookMessage buildMarkDownMessage(MarkdownBuffer content) {
        WeWorkWebhookMessage message = new WeWorkWebhookMessage();
        message.setMsgtype("markdown");
        WeWorkWebhookMessage.Markdown markdown = new WeWorkWebhookMessage.Markdown();
        markdown.setContent(content.toString());
        message.setMarkdown(markdown);
        return message;
    }


    /**
    *  @author swalikh on 2021/9/16 10:46
    *  @type   批量构建图文卡片链接消息
    *  @desc
    */
    public static WeWorkWebhookMessage buildNewsMessage(List<Article> articles) {
        WeWorkWebhookMessage message = new WeWorkWebhookMessage();
        message.setMsgtype("news");
        WeWorkWebhookMessage.News news = new WeWorkWebhookMessage.News();
        news.setArticles(articles);
        message.setNews(news);
        return message;
    }

    /**
     *  @author swalikh on 2021/9/16 10:46
     *  @type   构建图文卡片链接消息
     *  @desc
     */
    public static WeWorkWebhookMessage buildNewsMessage(Article article) {
        WeWorkWebhookMessage message = new WeWorkWebhookMessage();
        message.setMsgtype("news");
        WeWorkWebhookMessage.News news = new WeWorkWebhookMessage.News();
        List<Article> list = new ArrayList();
        list.add(article);
        news.setArticles(list);
        message.setNews(news);
        return message;
    }


    /**
    *  @author swalikh on 2021/9/16 10:50
    *  @type   构建普通文本消息
    *  @desc
    */
    public static WeWorkWebhookMessage buildText(String content) {
        return buildText(content, false);
    }

    /**
     *  @author swalikh on 2021/9/16 10:50
     *  @type   构建普通文本消息（@ALL 指定webhookapi)
     *  @desc
     */
    public static WeWorkWebhookMessage buildText(String content, boolean atAll) {
        WeWorkWebhookMessage message = new WeWorkWebhookMessage();
        message.setMsgtype("text");
        WeWorkWebhookMessage.Text text = new WeWorkWebhookMessage.Text();
        text.setContent(content);
        List<String> mentioned_list = text.getMentioned_list();
        if (atAll) {
            if (mentioned_list == null) {
                mentioned_list = new ArrayList<>();
            }
            mentioned_list.add("@all");
            text.setMentioned_list(mentioned_list);
        }
        message.setText(text);
        return message;
    }

}
