package com.alinkeji.bot.websocket;


import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

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
