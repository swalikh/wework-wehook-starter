package com.sayalala.wework.wehook.service;

import com.alibaba.fastjson.JSONObject;
import com.sayalala.wework.wehook.config.MessagesenderProperties;
import com.sayalala.wework.wehook.entity.WeWorkWebhookMessage;
import com.sayalala.wework.wehook.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class MessageServiceImpl implements MessageService {

    private List<String> wechat_webhooks;


    public MessageServiceImpl(MessagesenderProperties messagesenderProperties) {
        this.wechat_webhooks = messagesenderProperties.getWechat_webhooks();
        if (wechat_webhooks == null || wechat_webhooks.size() == 0) {
            throw new RuntimeException("没有wechatwebhook配置");
        }
    }

    @Override
    public boolean send(WeWorkWebhookMessage weWorkWebhookMessage) {
        return send(weWorkWebhookMessage, wechat_webhooks.get(0));
    }

    @Override
    public boolean send(WeWorkWebhookMessage weWorkWebhookMessage, String webhook) {
        if (StringUtils.isEmpty(webhook)) {
            throw new RuntimeException("webhook地址为空");
        }
        Map<String, String> headerParams = new HashMap<>();
        headerParams.put("Content-Type", "application/json");
        headerParams.put("charset", "utf-8");
        String responseStr = HttpClientUtil.doPostJson(webhook, headerParams, JSONObject.toJSONString(weWorkWebhookMessage));
        if (JSONObject.parseObject(responseStr).getString("errmsg").equals("ok")) {
            log.info("企业微信webhook消息发送成功");
            log.debug("webhook地址为：[{}]",webhook);
            log.debug("消息内容为：[{}]",JSONObject.toJSONString(weWorkWebhookMessage,true));
            log.debug("响应为：[{}]",responseStr);
            return true;
        } else {
            log.error("企业微信webhook消息发送失败");
            log.error("webhook地址为：[{}]",webhook);
            log.error("消息内容为：[{}]",JSONObject.toJSONString(weWorkWebhookMessage,true));
            log.error("响应为：[{}]",responseStr);
            return false;
        }
    }
}
