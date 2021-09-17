package com.sayalala.wework.wehook.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;


/**
 * @author smallAttr
 * @since 2019-11-13 15:58
 */
@Data
@ConfigurationProperties(prefix = "spring.message")
public class MessagesenderProperties {

    /**
     * message开关
     */
    private boolean enable = true;

    /**
     * wechet-webhookList
     */
    private List<String> wechat_webhooks;


}
