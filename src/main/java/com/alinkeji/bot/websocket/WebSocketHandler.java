package com.alinkeji.bot.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alinkeji.bot.BotGlobal;
import com.alinkeji.bot.boot.EventProperties;
import com.alinkeji.bot.bot.ApiHandler;
import com.alinkeji.bot.bot.Bot;
import com.alinkeji.bot.bot.BotFactory;
import com.alinkeji.bot.bot.EventHandler;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


/**
 * ws处理类
 * <p>
 * 建立连接 断开连接 收到消息
 */
@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {

  private BotFactory botFactory;
  private ApiHandler apiHandler;
  private EventHandler eventHandler;
  private ExecutorService executor;


  public WebSocketHandler(EventProperties eventProperties, BotFactory botFactory,
    ApiHandler apiHandler, EventHandler eventHandler) {
    this.botFactory = botFactory;
    this.apiHandler = apiHandler;
    this.eventHandler = eventHandler;
    this.executor =
      new ThreadPoolExecutor(eventProperties.getCorePoolSize(),
        eventProperties.getMaxPoolSize(),
        eventProperties.getKeepAliveTime(),
        TimeUnit.MILLISECONDS,
        new ArrayBlockingQueue<>(eventProperties.getWorkQueueSize()));
  }

  /**
   * ws建立连接，创建Bot对象，并放入BotGlobal.bots，是static Map，方便在jar外面获取
   *
   * @param session websocket session
   */
  @Override
  public void afterConnectionEstablished(WebSocketSession session) {
    long xSelfId = Long.parseLong(session.getHandshakeHeaders().get("x-self-id").get(0));
    log.info("{} connected", xSelfId);

    // 新连接上的，创建一个对象
    Bot bot = botFactory.createBot(xSelfId, session);

    // 存入Map，方便在未收到消息时调用API发送消息(定时、Controller或其他方式触发)
    BotGlobal.bots.put(String.valueOf(xSelfId), bot);
    afterEstablished();
  }

  /**
   * ws连接断开，需要清除之前的Bot对象
   *
   * @param session websocket session
   * @param status  状态码
   */
  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
    long xSelfId = Long.parseLong(session.getHandshakeHeaders().get("x-self-id").get(0));
    log.info("{} disconnected", xSelfId);

    BotGlobal.bots.remove(String.valueOf(xSelfId));
    afterClosed();
  }

  /**
   * ws收到消息时的方法 可能是api响应（包含echo字段） 可能是事件上报
   *
   * @param session websocketSession
   * @param message 消息内容
   */
  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) {
    long xSelfId = Long.parseLong(session.getHandshakeHeaders().get("x-self-id").get(0));
    String botId = String.valueOf(xSelfId);
    Bot bot = BotGlobal.bots.get(botId);

    // 防止网络问题，快速重连可能 （连接1，断开1，连接2） 变成 （连接1，连接2，断开1）
    if (bot == null) {
      bot = botFactory.createBot(xSelfId, session);
      BotGlobal.bots.put(botId, bot);
    }
    bot.setBotSession(BotWebSocketSession.of(botId, session));

    JSONObject recvJson = JSON.parseObject(message.getPayload());
    if (recvJson.containsKey("echo")) {
      // 带有echo说明是调用api的返回数据
      apiHandler.onReceiveApiMessage(recvJson);
    } else {
      // 不带有echo是事件上报
      Bot finalBot = bot;
      executor.execute(() -> eventHandler.handle(finalBot, recvJson));
    }
  }

  /**
   * 初始化完成后一下其他事件
   */
  protected void afterEstablished() {

  }

  /**
   * 初始化完成后一下其他事件
   */
  protected void afterClosed() {

  }
}