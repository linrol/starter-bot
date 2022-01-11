package com.alinkeji.bot.bot;

import com.alinkeji.bot.BotGlobal;
import com.alinkeji.bot.boot.Properties;
import com.alinkeji.bot.bot.handler.HttpApiHandler;
import com.alinkeji.bot.bot.handler.WsApiHandler;
import com.alinkeji.bot.bot.handler.WsReverseApiHandler;
import java.util.List;
import org.java_websocket.client.WebSocketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

/**
 * 工厂类
 */
@Service
public class BotFactory {

  @Autowired
  private Properties properties;

  private Bot getBot(String botId) {
    BotGlobal.bots.putIfAbsent(botId, new Bot(botId, properties.getPluginList()));
    return BotGlobal.bots.get(botId);
  }

  public Bot destroy(String botId, ApiMethod apiMethod) {
    if (!BotGlobal.bots.containsKey(botId)) {
      return null;
    }
    return BotGlobal.bots.get(botId).removeApiHandler(apiMethod);
  }

  /**
   * 创建一个基于Http连接的Bot对象 把Ws Reverse的apiHandler放入对象
   *
   * @param botId
   * @param webSocketClient
   * @return
   */
  public Bot injectWs(String botId, WebSocketClient webSocketClient) {
    ApiHandler apiHandler = new WsApiHandler(webSocketClient);
    return getBot(botId).addApiHandler(ApiMethod.Ws, apiHandler);
  }

  /**
   * 创建一个基于Ws Reverse连接的Bot对象 把Ws Reverse的apiHandler放入对象
   *
   * @param botId
   * @param botSession
   * @return
   */
  public Bot injectWsReverse(String botId, WebSocketSession botSession) {
    ApiHandler apiHandler = new WsReverseApiHandler(botSession, properties.getApiTimeout());
    return getBot(botId).addApiHandler(ApiMethod.WsReverse, apiHandler);
  }

  /**
   * 创建一个基于Http连接的Bot对象 把Ws Reverse的apiHandler放入对象
   *
   * @param botId
   * @param botServerUrls
   * @return
   */
  public Bot injectHttp(String botId, List<String> botServerUrls) {
    ApiHandler apiHandler = new HttpApiHandler(botId, botServerUrls);
    return getBot(botId).addApiHandler(ApiMethod.Http, apiHandler);
  }

}
