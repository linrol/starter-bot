package com.alinkeji.bot.bot.handler;

import com.alibaba.fastjson.JSONObject;
import com.alinkeji.bot.bot.ApiEnum;
import com.alinkeji.bot.bot.ApiHandler;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;

/**
 * API处理类
 */
@Slf4j
public class WsApiHandler extends ApiHandler {

  private WebSocketClient webSocketClient;

  public WsApiHandler(WebSocketClient webSocketClient) {
    this.webSocketClient = webSocketClient;
  }

  @Override
  public JSONObject callApi(ApiEnum apiEnum, JSONObject params) {
    webSocketClient.send(params.toJSONString());
    return null;
  }
}
