package com.alinkeji.bot.bot.handler;

import com.alibaba.fastjson.JSONObject;
import com.alinkeji.bot.bot.ApiEnum;
import com.alinkeji.bot.bot.ApiHandler;
import com.alinkeji.bot.bot.ApiSender;
import com.alinkeji.bot.bot.IApiRequest;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.WebSocketSession;

/**
 * API处理类
 */
@Slf4j
public class WsReverseApiHandler extends ApiHandler {

  private WebSocketSession wrBotSession;

  private long timeout;

  private int apiEcho = 0;//用于标记是哪次发送api，接受时作为key放入apiResponseMap

  private Map<String, ApiSender> apiCallbackMap = new HashMap<>();//用于存放api调用，收到响应时put，处理完成remove

  public WsReverseApiHandler(WebSocketSession webSocketSession, long timeout) {
    this.wrBotSession = webSocketSession;
    this.timeout = timeout;
  }

  /**
   * 收到 以前调用的API 的响应
   *
   * @param message 内容
   */
  @Override
  public void onReceiveApiMessage(JSONObject message) {
    String echo = message.get("echo").toString();
    ApiSender apiSender = apiCallbackMap.get(echo);
    apiSender.onReceiveJson(message);
    apiCallbackMap.remove(echo);
  }


  /**
   * 构造API需要的json，使用预定义的Enum
   *
   * @param action 需要调用的API
   * @param params 参数
   * @return 结果
   */
  private JSONObject constructApiJSON(ApiEnum action, JSONObject params) {
    JSONObject apiJSON = new JSONObject();
    apiJSON.put("action", action.getUrl());
    if (params != null) {
      apiJSON.put("params", params);
    }
    apiJSON.put("echo", apiEcho++);

    return apiJSON;
  }

  /**
   * 构造API需要的json，自定义request
   *
   * @param apiRequest 自定义请求
   * @return 结果
   */
  private JSONObject constructApiJSON(IApiRequest apiRequest) {
    JSONObject apiJSON = new JSONObject();
    apiJSON.put("action", apiRequest.getApiUrl());
    if (apiRequest.getParams() != null) {
      apiJSON.put("params", apiRequest.getParams());
    }
    apiJSON.put("echo", apiEcho++);
    return apiJSON;
  }

  @Override
  public JSONObject callApi(IApiRequest apiRequest) {
    JSONObject apiJSON = constructApiJSON(apiRequest);
    return callApi(apiJSON);
  }

  @Override
  public JSONObject callApi(ApiEnum apiEnum, JSONObject params) {
    JSONObject apiJSON = constructApiJSON(apiEnum, params);
    return callApi(apiJSON);
  }

  private JSONObject callApi(JSONObject apiJSON) {
    String echo = apiJSON.getString("echo");
    ApiSender apiSender = new ApiSender(wrBotSession, timeout);
    apiCallbackMap.put(echo, apiSender);
    JSONObject retJson;
    try {
      retJson = apiSender.sendApiJson(apiJSON);
    } catch (Exception e) {
      e.printStackTrace();
      retJson = new JSONObject();
      retJson.put("status", "failed");
      retJson.put("retcode", -1);
    }
    return retJson;
  }
}
