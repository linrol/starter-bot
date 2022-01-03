package com.alinkeji.bot.websocket;


import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

/**
 * @Description
 * @ClassName BotWebSocketSession
 * @Author linrol
 * @date 2022年01月03日 12:53 Copyright (c) 2020, linrol@77hub.com All Rights Reserved.
 */
public class BotWebSocketSession {

  @Getter
  private String botId;

  @Getter
  private WebSocketSession webSocketSession;

  public static BotWebSocketSession of(String botId, WebSocketSession webSocketSession) {
    BotWebSocketSession session = new BotWebSocketSession();
    session.botId = botId;
    session.webSocketSession = webSocketSession;
    return session;
  }

}
