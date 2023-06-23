package com.alinkeji.bot.boot;

import com.alinkeji.bot.BotGlobal;
import com.alinkeji.bot.bot.Bot;
import com.alinkeji.bot.bot.BotFactory;
import com.alinkeji.bot.bot.EventHandler;
import com.alinkeji.bot.websocket.WebSocketHandler;
import com.alinkeji.bot.websocket.WxWebSocketClient;
import java.net.URISyntaxException;
import java.util.Arrays;
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
  public WebSocketHandler createWebSocketHandler(EventHandler eventHandler) {
    return new WebSocketHandler(eventProperties, botFactory, eventHandler);
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
  @ConfigurationProperties(prefix = "bot.ws-server")
  public List<Bot> createWsBot() {
    return properties.getWsServer().entrySet().stream().map(m -> {
      String botName = m.getKey();
      String botWsUrl = m.getValue();
      try {
        new WxWebSocketClient(botName, botWsUrl, botFactory).connect();
        return BotGlobal.bots.get(botName);
      } catch (URISyntaxException e) {
        e.printStackTrace();
      }
      return null;
    }).filter(Objects::nonNull).collect(Collectors.toList());
  }

  @Bean
  @ConfigurationProperties(prefix = "bot.http-server")
  public List<Bot> createHttpBot(BotFactory botFactory) {
    return properties.getHttpServer().entrySet().stream().map(m -> {
      String botId = m.getKey();
      String httpServer = m.getValue();
      return botFactory.injectHttp(botId, httpServer.split(","));
    }).collect(Collectors.toList());
  }

}
