package com.alinkeji.bot.boot;

import com.alinkeji.bot.websocket.WebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@ComponentScan(basePackages = {"com.alinkeji.bot"})
@EnableWebSocket
@EnableConfigurationProperties({Properties.class, EventProperties.class})
@Import(BotBean.class)
public class AutoConfiguration implements WebSocketConfigurer {


  @Autowired
  Properties properties;


  @Autowired
  WebSocketHandler webSocketHandler;

  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry.addHandler(webSocketHandler, properties.getWsReverseUrl()).setAllowedOrigins("*");
  }


}
