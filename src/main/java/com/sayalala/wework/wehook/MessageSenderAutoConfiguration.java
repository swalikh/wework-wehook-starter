package com.sayalala.wework.wehook;

import com.sayalala.wework.wehook.config.MessagesenderProperties;
import com.sayalala.wework.wehook.service.MessageService;
import com.sayalala.wework.wehook.service.MessageServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.util.ObjectUtils;


@Slf4j
@Configuration
@AutoConfigureOrder(value = Ordered.HIGHEST_PRECEDENCE)
public class MessageSenderAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean(search = SearchStrategy.CURRENT)
    public MessagesenderProperties getProperties() {
        return new MessagesenderProperties();
    }


    @Bean
    public MessageService getMessageSenderService() {
        MessagesenderProperties properties = getProperties();
        if(ObjectUtils.isEmpty(properties.getWechat_webhooks())){
            log.error("加载webhook—api默认配置失败");
            throw new RuntimeException("webhook—api没有默认配置");
        }
        log.info("已成功加载[{}]个webhook—api默认配置",properties.getWechat_webhooks().size());
        return new MessageServiceImpl(properties);
    }
}
