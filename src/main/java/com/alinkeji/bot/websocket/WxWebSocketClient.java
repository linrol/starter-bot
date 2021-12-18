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

  private BotFactory botFactory;

  public WxWebSocketClient(String botName, String url, BotFactory botFactory) throws URISyntaxException {
    super(new URI(url));
    this.setConnectionLostTimeout(5);
    this.botName = botName;
    this.botFactory = botFactory;
  }

  @Override
  public void onOpen(ServerHandshake serverHandshake) {
    logger.info("已连接到微信服务器");
    // 新连接上的，创建一个对象
    Bot bot = botFactory.createBot(this);
    // 存入Map，方便在未收到消息时调用API发送消息(定时、Controller或其他方式触发)
    // todo 获取当前登陆人的身份信息
    BotGlobal.bots.put(botName, bot);
  }

  @Override
  public void onMessage(String s) {
    logger.info("收到微信消息:{}", s);
  }

  @Override
  public void onClose(int i, String s, boolean b) {
    BotGlobal.bots.remove(botName);
    logger.info("已断开和微信服务端的连接...");
  }

  @Override
  public void onError(Exception e) {
    BotGlobal.bots.remove(botName);
    logger.error("微信服务端的连接异常...", e);
  }
}
