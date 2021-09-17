package com.sayalala.wework.wehook.service;

import com.sayalala.wework.wehook.entity.WeWorkWebhookMessage;

public interface MessageService {

    /**
    *  @author swalikh on 2021/9/16 10:52
    *  @type   发送企业微信消息--系统配置发送者
    *  @desc
    */
    boolean send(WeWorkWebhookMessage weWorkWebhookMessage);

    /**
     *  @author swalikh on 2021/9/16 10:52
     *  @type   发送企业微信消息--自定义发送者
     *  @desc
     */
    boolean send(WeWorkWebhookMessage weWorkWebhookMessage, String webhook);


}
