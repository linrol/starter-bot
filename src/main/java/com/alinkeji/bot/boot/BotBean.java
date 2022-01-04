package com.alinkeji.bot.boot;

import com.alinkeji.bot.bot.ApiHandler;
import com.alinkeji.bot.bot.BotFactory;
import com.alinkeji.bot.bot.EventHandler;
import com.alinkeji.bot.websocket.WebSocketHandler;
import com.alinkeji.bot.websocket.WxWebSocketClient;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

@Component
public class BotBean {

  @Autowired
  Properties properties;

  @Autowired
  EventProperties eventProperties;

  @Autowired
  BotFactory botFactory;

  @Autowired
  ApplicationContext applicationContext;

  @Bean
  @ConditionalOnMissingBean
  public WebSocketHandler createWebSocketHandler(ApiHandler apiHandler, EventHandler eventHandler) {
    return new WebSocketHandler(eventProperties, botFactory, apiHandler, eventHandler);
  }

  @Bean
  @ConditionalOnMissingBean
  public ApiHandler createApiHandler() {
    return new ApiHandler(properties);
  }

  @Bean
  @ConditionalOnMissingBean
  public EventHandler createEventHandler() {
    return new EventHandler(applicationContext);
  }

  @Bean
  @ConditionalOnMissingBean
  public ServletServerContainerFactoryBean createWebSocketContainer() {
    ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
    // ws 传输数据的时候，数据过大有时候会接收不到，所以在此处设置bufferSize
    container.setMaxTextMessageBufferSize(properties.getMaxTextMessageBufferSize());
    container.setMaxBinaryMessageBufferSize(properties.getMaxBinaryMessageBufferSize());
    container.setMaxSessionIdleTimeout(properties.getMaxSessionIdleTimeout());
    return container;
  }

  @Bean
  @ConditionalOnMissingBean
  @ConfigurationProperties(prefix = "bot.ws-url")
  public List<WxWebSocketClient> initWechatBotClient() {
    return properties.getWsUrl().entrySet().stream().map(m -> {
      String botName = m.getKey();
      String botWsUrl = m.getValue().toString();
      WxWebSocketClient botClient;
      try {
        botClient = new WxWebSocketClient(botName, botWsUrl, botFactory);
        // 建立连接
        botClient.connect();
        return botClient;
      } catch (URISyntaxException e) {
        e.printStackTrace();
        return null;
      }
    }).filter(Objects::nonNull).collect(Collectors.toList());
  }
}
