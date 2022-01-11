package com.alinkeji.bot.bot;

import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

public class ApiSender extends Thread {

  private final WebSocketSession apiSession;
  private final Long apiTimeout;
  private JSONObject responseJSON;

  public ApiSender(WebSocketSession apiSession, Long apiTimeout) {
    this.apiSession = apiSession;
    this.apiTimeout = apiTimeout;
  }

  public JSONObject sendApiJson(JSONObject apiJSON) throws IOException, InterruptedException {
    synchronized (apiSession) {
      apiSession.sendMessage(new TextMessage(apiJSON.toJSONString()));
    }
    synchronized (this) {
      this.wait(apiTimeout);
    }
    return responseJSON;
  }


  public void onReceiveJson(JSONObject responseJSON) {
    this.responseJSON = responseJSON;
    synchronized (this) {
      this.notify();
    }
  }

}
