package com.alinkeji.bot.websocket;


import com.alinkeji.bot.BotGlobal;
import com.alinkeji.bot.bot.Bot;
import com.alinkeji.bot.bot.BotFactory;
import java.net.URI;
import java.net.URISyntaxException;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WxWebSocketClient extends WebSocketClient {

  private static Logger logger = LoggerFactory.getLogger(WxWebSocketClient.class);

  private String botName;

  private String serverUrl;

  private BotFactory botFactory;

  public WxWebSocketClient(String botName, String url, BotFactory botFactory)
    throws URISyntaxException {
    super(new URI(url));
    this.botName = botName;
    this.serverUrl = url;
    this.botFactory = botFactory;
  }

  @Override
  public void onOpen(ServerHandshake serverHandshake) {
    logger.info("微信机器人[{}]已连接hook server[{}]", botName, serverUrl);
    // 新连接上的，创建一个对象
    Bot bot = botFactory.createBot(this);
    // 存入Map，方便在未收到消息时调用API发送消息(定时、Controller或其他方式触发)
    // todo 获取当前登陆人的身份信息
    BotGlobal.bots.put(botName, bot);
  }

  @Override
  public void onMessage(String s) {
    logger.debug("微信机器人[{}]收到新消息:{}", botName, s);
  }

  @Override
  public void onClose(int i, String s, boolean b) {
    BotGlobal.bots.remove(botName);
    logger.error("微信机器人[{}]已断开连接hook server[{}]...", botName, serverUrl);
  }

  @Override
  public void onError(Exception e) {
    BotGlobal.bots.remove(botName);
    logger.error("微信机器人[{}]连接hook server[{}]异常...", botName, serverUrl, e);
  }
}
