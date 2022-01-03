package com.alinkeji.bot.bot;

import com.alibaba.fastjson.JSONObject;
import com.alinkeji.bot.boot.Properties;
import com.alinkeji.bot.utils.OkHttpClientUtil;
import com.alinkeji.bot.websocket.BotWebSocketSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * API处理类
 */
public class ApiHandler {

  private int apiEcho = 0;//用于标记是哪次发送api，接受时作为key放入apiResponseMap

  private Map<String, ApiSender> apiCallbackMap = new HashMap<>();//用于存放api调用，收到响应时put，处理完成remove

  Properties properties;

  public ApiHandler(Properties properties) {
    this.properties = properties;
  }

  /**
   * 收到 以前调用的API 的响应
   *
   * @param message 内容
   */
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
   * 调用定义好的API
   *
   * @param botSession 机器人session
   * @param action     执行的操作
   * @param params     参数
   * @return 结果
   */
  public JSONObject sendApiMessage(BotWebSocketSession botSession, ApiEnum action,
    JSONObject params) {
    String method = properties.getApiMethod().getOrDefault(action.getUrl(), action.getMethod());
    if (method.equals("http")) {
      Stack<String> urlStack = properties.getHttpUrl().stream()
        .collect(Collectors.toCollection(Stack::new));
      return callHttpApi(urlStack, botSession.getBotId(), action.getUrl(), params);
    }
    JSONObject apiJSON = constructApiJSON(action, params);
    String echo = apiJSON.getString("echo");
    ApiSender apiSender = new ApiSender(botSession.getWebSocketSession(),
      properties.getApiTimeout());
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

  /**
   * 发送自定义API
   *
   * @param botSession websocketSession
   * @param apiRequest 自定义请求
   * @return 结果
   * @throws IOException          发送异常
   * @throws InterruptedException 线程异常
   */
  @SuppressWarnings("unused")
  public JSONObject sendApiMessage(BotWebSocketSession botSession, IApiRequest apiRequest)
    throws IOException, InterruptedException {
    String method = properties.getApiMethod()
      .getOrDefault(apiRequest.getApiUrl(), apiRequest.getApiMethod());
    if (method.equals("http")) {
      Stack<String> urlStack = properties.getHttpUrl().stream()
        .collect(Collectors.toCollection(Stack::new));
      return callHttpApi(urlStack, botSession.getBotId(), apiRequest.getApiUrl(),
        apiRequest.getParams());
    }

    JSONObject apiJSON = constructApiJSON(apiRequest);
    String echo = apiJSON.getString("echo");
    ApiSender apiSender = new ApiSender(botSession.getWebSocketSession(),
      properties.getApiTimeout());
    apiCallbackMap.put(echo, apiSender);
    JSONObject retJson;
    retJson = apiSender.sendApiJson(apiJSON);
    return retJson;
  }

  private JSONObject callHttpApi(Stack<String> urlStack, String botId, String action,
    JSONObject params) {
    String apiUrl = String.format(urlStack.pop(), botId, action);
    OkHttpClientUtil<JSONObject> callClient = OkHttpClientUtil.build(JSONObject.class);
    JSONObject apiResult;
    try {
      apiResult = callClient.post(apiUrl, params.toJSONString());
    } catch (Exception e) {
      e.printStackTrace();
      apiResult = new JSONObject();
      apiResult.put("status", "failed");
      apiResult.put("retcode", -1);
    }
    if (apiResult.containsKey("status") && apiResult.getString("status").equals("ok")) {
      return apiResult;
    }
    if (urlStack.isEmpty()) {
      return apiResult;
    }
    return callHttpApi(urlStack, botId, action, params);
  }
}
