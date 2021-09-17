### wework webhook robot
`a java SDK for wework webhook robot `
### 介绍(introduction)
一个企业微信webhook机器人javaSDK，配置好webhook地址之后就可以快速方便发送消息，摒弃了各种参数的拼接，用面向对象的方式来优雅的发送提醒
现在已经支持：
- 文本消息
- 图片消息
- 文本卡片消息
- 图文消息(批量)
- markdown消息

### 使用方法(quick start)
#### 1.添加maven依赖(import maven dependency)
```maven
        <dependency>
            <groupId>io.github.swalikh</groupId>
            <artifactId>wework-wehook-starter</artifactId>
            <version>1.0.0</version>
        </dependency>
```
#### 2.配置webhook地址(add webhook api)
> 可以配置一个或者多个，默认以第一个生效。或者手动修改webhook地址
```yml
spring:
  message:
    wechat-webhooks: 
    	- https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=xxxxxxxx
    	- https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=xxxxxxxx

```
#### 3.注入MessageService并且发送消息
```java
        @Autowired
        private MessageService messageService;
```

- 1.发送普通文本消息
```java
        WeWorkWebhookMessage weWorkWebhookMessage = WeWorkWebhookMessage.buildText("hello");
        messageService.send(weWorkWebhookMessage);
```
- 2.发送图片消息
```java
        // networkImage 和 localImage 均可，格式可支持jpg&png
        String networkImageUrl = "http://www.image.com/dog.jpg";
        String localImageFilePath = "/home/image/cat.png";
        WeWorkWebhookMessage imageMessage = 
                WeWorkWebhookMessage.buildImageMessage(networkImageUrl);
        messageService.send(imageMessage);
```
- 3.发送图文卡片消息
```java
        // networkImage 和 localImage 均可，格式可支持jpg&png
        String networkImageUrl = "http://www.image.com/dog.jpg";
        Article article = new Article()
                .setTitle("这是卡片的标题")
                .setUrl("http://www.google.com/这是点击的链接地址")
                .setPicurl(networkImageUrl)
                .setDescription("这是秒速文字");
        WeWorkWebhookMessage articleMessage =
                WeWorkWebhookMessage.buildNewsMessage(article);
        messageService.send(articleMessage);
```
- 4.发送markdown消息
```java
        MarkdownBuffer markdownBuffer = new MarkdownBuffer();
        markdownBuffer
                .h2("H2").nextLine()
                .h3("H3").nextLine()
                .quote("quote").quoteEnd()
                .green("greenText").nextLine()
                .orange("orangeText").nextLine()
                .gray("grayText").nextLine()
                .code("single line code").nextLine()
                .link("link title","line URL").nextLine();

        WeWorkWebhookMessage markDownMessage =
                WeWorkWebhookMessage.buildMarkDownMessage(markdownBuffer);
        messageService.send(markDownMessage);
```